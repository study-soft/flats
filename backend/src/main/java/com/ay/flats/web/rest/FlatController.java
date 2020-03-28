package com.ay.flats.web.rest;

import com.ay.flats.domain.Flat;
import com.ay.flats.service.OlxService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FlatController {

    private final OlxService service;

    public FlatController(final OlxService service) {
        this.service = service;
    }

    @GetMapping("/flats")
    public ResponseEntity<List<Flat>> getAll() {
        return ResponseEntity.ok(service.getFlats(1));
    }

}
