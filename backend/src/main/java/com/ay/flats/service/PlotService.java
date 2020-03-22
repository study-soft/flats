package com.ay.flats.service;

import com.ay.flats.domain.PlotItem;

import java.util.List;

public interface PlotService {

    PlotItem saveAverage(final int pages);

    List<PlotItem> getPlotData();
}
