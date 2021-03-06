package com.ay.flats.service;

import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OldDesignOlxService extends AbstractOlxService implements OlxService {

    @Value("${com.ay.flats.olx.min.floor}")
    private Integer minFloor;
    @Value("${com.ay.flats.olx.min.rooms}")
    private Integer minRooms;
    @Value("${com.ay.flats.olx.max.rooms}")
    private Integer maxRooms;
    @Value("${com.ay.flats.olx.district.id}")
    private Integer districtId; // Solomensky

    @Override
    protected Class<OldDesignOlxService> getClassForLogger() {
        return OldDesignOlxService.class;
    }

    @Override
    protected String getAllUrl() {
        return "https://www.olx.ua/uk/" +
                "nedvizhimost/kvartiry-komnaty/prodazha-kvartir-komnat/kiev/" +
                "?search%5Bfilter_float_floor%3Afrom%5D=" + minFloor +
                "&search%5Bfilter_float_number_of_rooms%3Afrom%5D=" + minRooms +
                "&search%5Bfilter_float_number_of_rooms%3Ato%5D=" + maxRooms +
                "&search%5Bdistrict_id%5D=" + districtId +
                "&currency=USD";
    }

    @Override
    protected String getAllSelector() {
        return "td[class=offer] > div[class=offer-wrapper] > table";
    }

    @Override
    protected String getOneSelector() {
        return "div[class*=descriptioncontent] > table > tbody";
    }

    @Override
    protected int extractElementData(final Element tbody, final String section) {
        return Integer.parseInt(tbody.selectFirst("th:contains(" + section + ")")
                .nextElementSibling()
                .selectFirst("strong")
                .text()
                .replaceAll("\\D", ""));
    }
}