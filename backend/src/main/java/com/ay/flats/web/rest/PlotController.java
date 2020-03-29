package com.ay.flats.web.rest;

import com.ay.flats.domain.PlotItem;
import com.ay.flats.service.FlatService;
import com.ay.flats.service.PlotService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PlotController {

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
        return ResponseEntity.status(HttpStatus.CREATED).body(plotService.saveAverage(flatService.fetchAndSaveFlats(pages)));
    }

    @GetMapping("/average")
    public ResponseEntity<List<PlotItem>> getPlotData() {
        return ResponseEntity.ok(plotService.getPlotData());
    }
}
