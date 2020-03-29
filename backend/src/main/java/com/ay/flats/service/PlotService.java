package com.ay.flats.service;

import com.ay.flats.domain.Flat;
import com.ay.flats.domain.PlotItem;

import java.util.List;

public interface PlotService {

    PlotItem saveAverage(List<Flat> flats);

    List<PlotItem> getPlotData();
}
