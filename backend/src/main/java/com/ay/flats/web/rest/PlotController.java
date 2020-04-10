package com.ay.flats.web.rest;

import com.ay.flats.domain.PlotItem;
import com.ay.flats.service.PlotService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PlotController {

    private final PlotService plotService;

    public PlotController(final PlotService plotService) {
        this.plotService = plotService;
    }

    @CrossOrigin("https://flats-app.herokuapp.com")
    @PostMapping("/average")
    public ResponseEntity<PlotItem> savePlotItem(
            @RequestParam(required = false, defaultValue = "${com.ay.flats.stats.pages}") final Integer pages) {
        return ResponseEntity.status(HttpStatus.OK).body(
                plotService.saveAverage(
                        plotService.calculateAverage(pages)));
    }

    @CrossOrigin("https://flats-app.herokuapp.com")
    @GetMapping("/average")
    public ResponseEntity<List<PlotItem>> getPlotData() {
        return ResponseEntity.ok(plotService.getPlotData());
    }
}
