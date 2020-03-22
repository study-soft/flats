package com.ay.flats.service;

import com.ay.flats.domain.Flat;
import com.ay.flats.domain.PlotItem;

import java.util.List;

public interface FlatService {

    List<Flat> getFlats(int page);

    PlotItem saveAverage(final int pages);

    List<PlotItem> getPlotData();
}
