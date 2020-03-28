package com.ay.flats.service;

import com.ay.flats.domain.Flat;
import com.ay.flats.repository.FlatRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SimpleFlatService implements FlatService {

    private final OlxService olxService;
    private final FlatRepository repository;

    public SimpleFlatService(final OlxService olxService, final FlatRepository repository) {
        this.olxService = olxService;
        this.repository = repository;
    }

    @Override
    public void fetchAndSaveFlats() {
        List<Flat> flats = olxService.getFlats(1);
        repository.saveAll(flats);
    }
}
