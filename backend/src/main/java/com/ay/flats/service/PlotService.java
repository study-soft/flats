package com.ay.flats.service;

import com.ay.flats.domain.PlotItem;

import java.util.List;

public interface PlotService {

    double calculateAverage(int pages);

    PlotItem saveAverage(double average);

    List<PlotItem> getPlotData();
}
