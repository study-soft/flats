package com.ay.flats.service;

import com.ay.flats.domain.Flat;
import com.ay.flats.repository.FlatRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SimpleFlatService implements FlatService {

    private final OlxService olxService;
    private final FlatRepository repository;

    public SimpleFlatService(final OlxService olxService, final FlatRepository repository) {
        this.olxService = olxService;
        this.repository = repository;
    }

    @Override
    public List<Flat> fetchAndSaveFlats() {
        return olxService.getFlats(1).stream()
                .map(flat -> mergeFlat(flat, olxService.getFlat(flat.getUrl())))
                .map(repository::saveOrUpdate)
                .collect(Collectors.toList());
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
