package com.ay.flats.web.rest;

import com.ay.flats.domain.Flat;
import com.ay.flats.service.FlatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FlatController {

    private final FlatService flatService;

    public FlatController(final FlatService flatService) {
        this.flatService = flatService;
    }

    @GetMapping("api/flats")
    public ResponseEntity<List<Flat>> getAll() {
        return ResponseEntity.ok(flatService.getAll());
    }

    @GetMapping("/api")
    public ResponseEntity<String> greeting() {
        return ResponseEntity.ok("Service successfully running!");
    }
}
