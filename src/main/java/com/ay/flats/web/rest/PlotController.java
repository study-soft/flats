package com.ay.flats.web.rest;

import com.ay.flats.domain.PlotItem;
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

    public PlotController(final PlotService plotService) {
        this.plotService = plotService;
    }

    @PostMapping("api/average")
    public ResponseEntity<PlotItem> savePlotItem(
            @RequestParam(required = false, defaultValue = "${com.ay.flats.stats.pages}") final Integer pages) {
        return ResponseEntity.status(HttpStatus.OK).body(
                plotService.saveAverage(
                        plotService.calculateAverage(pages)));
    }

    @GetMapping("api/average")
    public ResponseEntity<List<PlotItem>> getPlotData() {
        return ResponseEntity.ok(plotService.getPlotData());
    }
}
