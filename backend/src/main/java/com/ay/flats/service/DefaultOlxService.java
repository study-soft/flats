package com.ay.flats.service;

import com.ay.flats.domain.Flat;
import com.ay.flats.util.UkLocaleDateFormatter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class DefaultOlxService implements OlxService {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultOlxService.class);

    @Value("${com.ay.flats.olx.min.floor}")
    private Integer minFloor;
    @Value("${com.ay.flats.olx.min.rooms}")
    private Integer minRooms = 2;
    @Value("${com.ay.flats.olx.max.rooms}")
    private Integer maxRooms = 3;
    @Value("${com.ay.flats.olx.district.id}")
    private Integer districtId = 17; // Solomensky

    private final String getAllUrl = "https://www.olx.ua/uk/" +
            "nedvizhimost/kvartiry-komnaty/prodazha-kvartir-komnat/kiev/" +
            "?search%5Bfilter_float_floor%3Afrom%5D=" + minFloor +
            "&search%5Bfilter_float_number_of_rooms%3Afrom%5D=" + minRooms +
            "&search%5Bfilter_float_number_of_rooms%3Ato%5D=" + maxRooms +
            "&search%5Bdistrict_id%5D=" + districtId +
            "&currency=USD";

    /**
     * Example source: https://www.olx.ua/uk/nedvizhimost/kvartiry-komnaty/prodazha-kvartir-komnat/kiev/?search%5Bfilter_float_floor%3Afrom%5D=2&search%5Bfilter_float_number_of_rooms%3Afrom%5D=2&search%5Bfilter_float_number_of_rooms%3Ato%5D=3&search%5Bdistrict_id%5D=17&currency=USD
     */
    @Override
    public final List<Flat> getFlats(final int page) {
        try {
            LOG.info("GET {}&page={}", getAllUrl, page);

            Thread.sleep(ThreadLocalRandom.current().nextInt(3000, 7000));

            return Jsoup.connect(getAllUrl + "&page=" + page)
                    .get()
                    .select("td[class=offer] > div[class=offer-wrapper] > table")
                    .stream()
                    .map(this::extractBaseFlatData)
                    .collect(Collectors.toList());
        } catch (IOException | InterruptedException e) {
            LOG.error("Seems that Olx is down. {}", e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    /**
     * IMPORTANT!!! Use this method very carefully to avoid spam to Olx
     * <p>
     * Example source: https://www.olx.ua/uk/obyavlenie/bolshaya-3-k-kvartira-84m2-dlya-semi-ot-kievgorstroy-v-zhk-otrada-IDEljix.html#e93f49b155
     */
    @Override
    public final Flat getFlat(final String url) { // TODO: get all info, not only additional
        try {
            LOG.info("GET {}", url);

            Thread.sleep(ThreadLocalRandom.current().nextInt(3000, 7000));

            Document htmlPage = Jsoup.connect(url)
                    .get();

            return Optional.ofNullable(Optional
                    .ofNullable(htmlPage.selectFirst("div[class*=descriptioncontent] > table > tbody"))
                    .orElse(htmlPage.selectFirst("div[class*=descriptioncontent] > table")))
                    .map(this::extractDetailedFlatData)
                    .get();
        } catch (IOException | InterruptedException e) {
            LOG.error("Seems that Olx is down. {}", e.getMessage(), e);
            return new Flat();
        }
    }

    private Flat extractBaseFlatData(final Element table) {
        Long olxId = Long.valueOf(table.attr("data-id"));

        String link = table
                .selectFirst("tbody > tr > td")
                .select("a")
                .attr("href")
                .trim();

        String name = table
                .select("tbody > tr > td")
                .get(1)
                .select("strong")
                .text()
                .trim();

        Integer priceUsd = Integer.valueOf(table
                .select("tbody > tr > td")
                .get(2)
                .select("td > div > p > strong")
                .text()
                .replaceAll("\\D", ""));

        LocalDateTime adDate = new UkLocaleDateFormatter(table
                .select("tbody > tr")
                .get(1)
                .select("td > div > p > small")
                .get(1)
                .select("span")
                .text()
                .trim())
                .format();

        return new Flat()
                .olxId(olxId)
                .name(name)
                .url(link)
                .priceUsd(priceUsd)
                .adDate(adDate);
    }

    private Flat extractDetailedFlatData(final Element tbody) {
        return new Flat()
                .floor(extractTableData(tbody, "Поверх"))
                .floorsTotal(extractTableData(tbody, "Поверховість"))
                .totalSquare(extractTableData(tbody, "Загальна площа"))
                .kitchenSquare(extractTableData(tbody, "Площа кухні"))
                .roomCount(extractTableData(tbody, "Кількість кімнат"));
    }

    private int extractTableData(final Element tbody, final String section) {
        return Integer.parseInt(tbody.selectFirst("th:contains(" + section + ")")
                .nextElementSibling()
                .selectFirst("strong")
                .text()
                .replaceAll("\\D", ""));
    }
}