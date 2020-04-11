package com.ay.flats.service;

import com.ay.flats.domain.Flat;
import com.ay.flats.domain.PlotItem;
import com.ay.flats.repository.CommonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AveragePlotService implements PlotService {

    private static final Logger LOG = LoggerFactory.getLogger(AveragePlotService.class);

    private final FlatService flatService;
    private final CommonRepository<PlotItem> repository;

    public AveragePlotService(final FlatService flatService, final CommonRepository<PlotItem> repository) {
        this.flatService = flatService;
        this.repository = repository;
    }

    @Override
    public double calculateAverage(final int pages) {
        long startTime = System.currentTimeMillis();

        List<Flat> flats = flatService.fetchAndSaveFlats(pages);
        double avgPrice = Optional.of(flats.stream()
                .mapToDouble(flat -> flat.getPriceUsd().doubleValue() / flat.getTotalSquare())
                .average()
                .orElse(0.0))
                .map(avg -> Math.round(avg * 100d) / 100d)
                .map(Double.class::cast)
                .get();

        double elapsedTime = (double) (System.currentTimeMillis() - startTime) / 1000;

        LOG.info("\nCalculated average price: {}$\nFlats gathered: {}\nElapsed time: {}s",
                avgPrice, flats.size(), elapsedTime);

        return avgPrice;
    }

    @Override
    public PlotItem saveAverage(final double average) {
        return repository.saveOrUpdate(new PlotItem()
                .id(UUID.randomUUID().toString())
                .date(LocalDateTime.now())
                .price(average));
    }

    @Override
    public List<PlotItem> getPlotData() {
        return repository.findAll();
    }
}
