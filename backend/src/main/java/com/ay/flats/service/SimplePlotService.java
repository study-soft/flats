package com.ay.flats.service;

import com.ay.flats.domain.Flat;
import com.ay.flats.domain.PlotItem;
import com.ay.flats.repository.CommonRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;

@Service
public class SimplePlotService implements PlotService {

    private final FlatService flatService;
    private final CommonRepository<PlotItem> plotItemRepository;

    public SimplePlotService(final FlatService flatService, final CommonRepository<PlotItem> plotItemRepository) {
        this.flatService = flatService;
        this.plotItemRepository = plotItemRepository;
    }

    @Override
    public PlotItem saveAverage(final int pages) {
        Double avgPrice = Optional.of(IntStream.range(1, pages)
                .mapToObj(flatService::getFlats)
                .flatMap(Collection::stream)
                .mapToInt(Flat::getPriceUsd)
                .average()
                .orElse(0.0))
                .map(avg -> Math.round(avg * 100d) / 100d)
                .map(Double.class::cast)
                .get();

        return plotItemRepository.save(new PlotItem()
                .id(UUID.randomUUID().toString())
                .date(LocalDateTime.now())
                .price(avgPrice));
    }

    @Override
    public List<PlotItem> getPlotData() {
        return plotItemRepository.findAll();
    }
}
