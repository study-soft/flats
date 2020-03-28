package com.ay.flats.service;

import com.ay.flats.domain.Flat;
import com.ay.flats.util.UkLocaleDateFormatter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SimpleOlxService implements OlxService {

    // https://www.olx.ua/uk/nedvizhimost/kvartiry-komnaty/prodazha-kvartir-komnat/kiev/?search%5Bfilter_float_floor%3Afrom%5D=2&search%5Bfilter_float_number_of_rooms%3Afrom%5D=2&search%5Bfilter_float_number_of_rooms%3Ato%5D=3&search%5Bdistrict_id%5D=17&currency=USD

    private static final int MIN_FLOOR = 2;
    private static final int MIN_ROOMS = 2;
    private static final int MAX_ROOMS = 3;
    private static final int DISTRICT_ID = 17; // Solomensky

    private static final String GET_ALL_URL = "https://www.olx.ua/uk/" +
            "nedvizhimost/kvartiry-komnaty/prodazha-kvartir-komnat/kiev/" +
            "?search%5Bfilter_float_floor%3Afrom%5D=" + MIN_FLOOR +
            "&search%5Bfilter_float_number_of_rooms%3Afrom%5D=" + MIN_ROOMS +
            "&search%5Bfilter_float_number_of_rooms%3Ato%5D=" + MAX_ROOMS +
            "&search%5Bdistrict_id%5D=" + DISTRICT_ID +
            "&currency=USD";

    @Override
    public final List<Flat> getFlats(final int page) {
        try {
            return Jsoup.connect(GET_ALL_URL + "&page=" + page)
                    .get()
                    .select("td[class=offer] > div[class=offer-wrapper] > table")
                    .stream()
                    .map(this::extractBaseFlatData)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.err.print("Seems that Olx is down. " + e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * IMPORTANT!!! Use this method very carefully to avoid spam to Olx
     */
    @Override
    public final Flat getFlat(final String url) {
        try {
            return Optional.of(Jsoup.connect(url)
                    .get()
                    .selectFirst("div[class*=descriptioncontent] > table > tbody"))
                    .map(this::extractDetailedFlatData)
                    .get();
        } catch (IOException e) {
            System.err.print("Seems that Olx is down. " + e.getMessage());
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
                .floor(extractTableData(tbody, 1, 1))
                .floorsTotal(extractTableData(tbody, 2, 0))
                .totalSquare(extractTableData(tbody, 2, 1))
                .kitchenSquare(extractTableData(tbody, 3, 0))
                .roomCount(extractTableData(tbody, 3, 1));
    }

    private int extractTableData(final Element tbody, final int trIndex, final int tdIndex) {
        return Integer.parseInt(tbody
                .children()
                .get(trIndex)
                .children()
                .get(tdIndex)
                .select("table > tbody > tr > td > strong")
                .text()
                .replaceAll("\\D", ""));
    }
}