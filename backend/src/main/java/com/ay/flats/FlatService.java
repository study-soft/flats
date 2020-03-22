package com.ay.flats;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlatService {

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
            "&currency=USD&page=5";

    public List<Flat> getFlats() throws IOException {
        return Jsoup.connect(GET_ALL_URL)
                .get()
                .select("td[class=offer] > div[class=offer-wrapper] > table > tbody")
                .stream()
                .map(this::extractFlatData)
                .collect(Collectors.toList());

    }

    private Flat extractFlatData(Element tbody) {
        Element firstTableRow = tbody.selectFirst("tr");

        String link = firstTableRow
                .selectFirst("td")
                .select("a").attr("href")
                .trim();

        String name = firstTableRow
                .select("td").get(1)
                .select("strong").text()
                .trim();

        Integer priceUsd = Integer.valueOf(firstTableRow
                .select("td").get(2)
                .select("td > div > p > strong").text()
                .replaceAll("\\D", ""));

        // TODO: deal with encoding
//        LocalDateTime adDate = new UaLocaleDateFormatter(tbody
//                .select("tr").get(1)
//                .select("td > div > p > small").get(1)
//                .select("span").text()
//                .trim())
//                .format();

        return new Flat(name, link, priceUsd, LocalDateTime.now());
    }
}