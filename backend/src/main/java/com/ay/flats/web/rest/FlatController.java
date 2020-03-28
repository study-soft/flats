package com.ay.flats.web.rest;

import com.ay.flats.domain.Flat;
import com.ay.flats.service.FlatService;
import com.ay.flats.service.OlxService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FlatController {

    private final OlxService olxService;
    private final FlatService flatService;

    public FlatController(final OlxService olxService, final FlatService flatService) {
        this.olxService = olxService;
        this.flatService = flatService;
    }

    @GetMapping("/flats")
    public ResponseEntity<List<Flat>> getAll() {
        return ResponseEntity.ok(olxService.getFlats(1));
    }

}
