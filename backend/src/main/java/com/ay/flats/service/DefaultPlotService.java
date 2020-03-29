package com.ay.flats.service;

import com.ay.flats.domain.Flat;
import com.ay.flats.domain.PlotItem;
import com.ay.flats.repository.CommonRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DefaultPlotService implements PlotService {

    private final CommonRepository<PlotItem> repository;

    public DefaultPlotService(final CommonRepository<PlotItem> repository) {
        this.repository = repository;
    }

    @Override
    public PlotItem saveAverage(final List<Flat> flats) {
        Double avgPrice = Optional.of(flats.stream()
                .mapToDouble(flat -> flat.getPriceUsd().doubleValue() / flat.getTotalSquare())
                .average()
                .orElse(0.0))
                .map(avg -> Math.round(avg * 100d) / 100d)
                .map(Double.class::cast)
                .get();

        return repository.saveOrUpdate(new PlotItem()
                .id(UUID.randomUUID().toString())
                .date(LocalDateTime.now())
                .price(avgPrice));
    }

    @Override
    public List<PlotItem> getPlotData() {
        return repository.findAll();
    }
}
