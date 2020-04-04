package com.ay.flats.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledService {

    @Value("${com.ay.flats.stats.pages:5}")
    private int pages;

    private final PlotService plotService;

    public ScheduledService(final PlotService plotService) {
        this.plotService = plotService;
    }

    @Scheduled(cron = "0 0 18 * * ?")
    public void scheduleCalculateAverage() {
        plotService.saveAverage(plotService.calculateAverage(pages));
    }
}
