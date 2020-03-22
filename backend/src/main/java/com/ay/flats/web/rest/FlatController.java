package com.ay.flats.web.rest;

import com.ay.flats.domain.Flat;
import com.ay.flats.domain.PlotItem;
import com.ay.flats.service.FlatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FlatController {

    private final FlatService service;

    public FlatController(final FlatService service) {
        this.service = service;
    }

    @GetMapping("/flats")
    public ResponseEntity<List<Flat>> getAll() {
        return ResponseEntity.ok(service.getFlats(1));
    }

    @PostMapping("/average")
    public ResponseEntity<PlotItem> savePlotItem(
            @RequestParam(required = false, defaultValue = "${com.ay.flats.stats.pages}") final Integer pages
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveAverage(pages));
    }

    @GetMapping("/average")
    public ResponseEntity<List<PlotItem>> getPlotData() {
        return ResponseEntity.ok(service.getPlotData());
    }

}
