package com.ay.flats.service;

import com.ay.flats.domain.Flat;

import java.util.List;

public interface FlatService {

    List<Flat> fetchAndSaveFlats(int pages);
}
