package com.ay.flats.web.rest;

import com.ay.flats.domain.Flat;
import com.ay.flats.domain.PlotItem;
import com.ay.flats.service.DefaultOlxService;
import com.ay.flats.service.FlatService;
import com.ay.flats.service.PlotService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PlotController {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultOlxService.class);

    private final PlotService plotService;
    private final FlatService flatService;

    public PlotController(final PlotService plotService, final FlatService flatService) {
        this.plotService = plotService;
        this.flatService = flatService;
    }

    @PostMapping("/average")
    public ResponseEntity<PlotItem> savePlotItem(
            @RequestParam(required = false, defaultValue = "${com.ay.flats.stats.pages}") final Integer pages
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(calculateAverage(pages));
    }

    @GetMapping("/average")
    public ResponseEntity<List<PlotItem>> getPlotData() {
        return ResponseEntity.ok(plotService.getPlotData());
    }

    private PlotItem calculateAverage(final Integer pages) {
        long startTime = System.currentTimeMillis();
        List<Flat> flats = flatService.fetchAndSaveFlats(pages);
        PlotItem plotItem = plotService.saveAverage(flats);

        long endTime = System.currentTimeMillis();
        double elapsedTime = (double) (endTime - startTime) / 1000;

        LOG.info("\nCalculated average price: {}$\nFlats gathered: {}\nElapsed time: {}s",
                plotItem.getPrice(), flats.size(), elapsedTime);

        return plotItem;
    }
}
