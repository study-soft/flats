package com.ay.flats.service;

import com.ay.flats.domain.Flat;

import java.util.List;

public interface OlxService {

    List<Flat> getFlats(int page);

    Flat getFlat(String url);

}
