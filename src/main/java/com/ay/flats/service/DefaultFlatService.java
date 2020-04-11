package com.ay.flats.service;

import com.ay.flats.domain.Flat;
import com.ay.flats.repository.FlatRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class DefaultFlatService implements FlatService {

    private final OlxService olxService;
    private final FlatRepository repository;

    public DefaultFlatService(final OlxService olxService, final FlatRepository repository) {
        this.olxService = olxService;
        this.repository = repository;
    }

    @Override
    public List<Flat> fetchAndSaveFlats(final int pages) {
        return IntStream.range(1, pages + 1)
                .mapToObj(i -> olxService.getFlats(i).stream()
                        .map(flat -> mergeFlat(flat, getFlatDetailsFromOlxOrDb(flat)))
                        .map(repository::saveOrUpdate)
                        .collect(Collectors.toList()))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    @Override
    public List<Flat> getAll() {
        return repository.findAll();
    }

    private Flat getFlatDetailsFromOlxOrDb(final Flat baseFlat) {
        Flat detailedFlat = repository.findFlatByOlxId(baseFlat.getOlxId()).orElse(new Flat());
        if (!baseFlat.getPriceUsd().equals(detailedFlat.getPriceUsd())) {
            return olxService.getFlat(baseFlat.getUrl());
        }
        return detailedFlat;
    }

    private Flat mergeFlat(final Flat baseFlat, final Flat detailedFlat) {
        return baseFlat
                .floor(detailedFlat.getFloor())
                .floorsTotal(detailedFlat.getFloorsTotal())
                .totalSquare(detailedFlat.getTotalSquare())
                .kitchenSquare(detailedFlat.getKitchenSquare())
                .roomCount(detailedFlat.getRoomCount());
    }
}
