package com.ay.flats.service;

import com.ay.flats.domain.Flat;
import com.ay.flats.util.UkLocaleDateFormatter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public abstract class AbstractOlxService implements OlxService {

    protected final Logger LOG = LoggerFactory.getLogger(getClassForLogger());

    /**
     * Example source: https://www.olx.ua/uk/nedvizhimost/kvartiry-komnaty/prodazha-kvartir-komnat/kiev/?search%5Bfilter_float_floor%3Afrom%5D=2&search%5Bfilter_float_number_of_rooms%3Afrom%5D=2&search%5Bfilter_float_number_of_rooms%3Ato%5D=3&search%5Bdistrict_id%5D=17&currency=USD
     */
    @Override
    public List<Flat> getFlats(final int page) {
        try {
            LOG.info("GET {}&page={}", getAllUrl(), page);

            Thread.sleep(ThreadLocalRandom.current().nextInt(3000, 7000));

            return Jsoup.connect(getAllUrl() + "&page=" + page)
                    .get()
                    .select(getAllSelector())
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

            return Optional.of(Jsoup.connect(url)
                    .get()
                    .selectFirst(getOneSelector()))
                    .map(this::extractDetailedFlatData)
                    .get();
        } catch (IOException | InterruptedException e) {
            LOG.error("Seems that Olx is down. {}", e.getMessage(), e);
            return new Flat();
        }
    }

    protected abstract Class<? extends AbstractOlxService> getClassForLogger();

    protected abstract String getAllUrl();

    protected abstract String getAllSelector();

    protected abstract String getOneSelector();

    protected Flat extractBaseFlatData(final Element table) {
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

    protected Flat extractDetailedFlatData(final Element tbody) {
        return new Flat()
                .floor(extractElementData(tbody, "Поверх"))
                .floorsTotal(extractElementData(tbody, "Поверховість"))
                .totalSquare(extractElementData(tbody, "Загальна площа"))
                .kitchenSquare(extractElementData(tbody, "Площа кухні"))
                .roomCount(extractElementData(tbody, "Кількість кімнат"));
    }

    protected abstract int extractElementData(Element tbody, String section);
}
