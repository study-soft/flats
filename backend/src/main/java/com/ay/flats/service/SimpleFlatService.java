package com.ay.flats.service;

import com.ay.flats.domain.Flat;
import com.ay.flats.domain.PlotItem;
import com.ay.flats.repository.CommonRepository;
import com.ay.flats.util.UkLocaleDateFormatter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
public class SimpleFlatService implements FlatService {

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

    private final CommonRepository<Flat> flatRepository;
    private final CommonRepository<PlotItem> plotItemRepository;

    public SimpleFlatService(final CommonRepository<Flat> flatRepository,
                             final CommonRepository<PlotItem> plotItemRepository) {
        this.flatRepository = flatRepository;
        this.plotItemRepository = plotItemRepository;
    }

    public final List<PlotItem> getPlotData() {
        return plotItemRepository.findAll();
    }

    public final PlotItem saveAverage(final int pages) {
        double avgPrice = IntStream.range(1, pages)
                .mapToObj(this::getFlats)
                .flatMap(Collection::stream)
                .mapToInt(Flat::getPriceUsd)
                .average()
                .orElse(0.0);

        return plotItemRepository.save(new PlotItem()
                .date(LocalDateTime.now())
                .price(avgPrice));
    }

    public final List<Flat> getFlats(final int page) {
        try {
            return Jsoup.connect(GET_ALL_URL + "&page=" + page)
                    .get()
                    .select("td[class=offer] > div[class=offer-wrapper] > table")
                    .stream()
                    .map(this::extractFlatData)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    private Flat extractFlatData(Element table) {
        Long olxId = Long.valueOf(table.attr("data-id"));

        String link = table
                .selectFirst("tbody > tr > td")
                .select("a").attr("href")
                .trim();

        String name = table
                .select("tbody > tr > td").get(1)
                .select("strong").text()
                .trim();

        Integer priceUsd = Integer.valueOf(table
                .select("tbody > tr > td").get(2)
                .select("td > div > p > strong").text()
                .replaceAll("\\D", ""));

        LocalDateTime adDate = new UkLocaleDateFormatter(table
                .select("tbody > tr").get(1)
                .select("td > div > p > small").get(1)
                .select("span").text()
                .trim())
                .format();

        // TODO: think about saving flat
        return new Flat()
                .olxId(olxId)
                .name(name)
                .link(link)
                .priceUsd(priceUsd)
                .adDate(adDate);
    }
}