package com.ay.flats;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class FlatController {

    private final FlatService service;

    public FlatController(FlatService service) {
        this.service = service;
    }

    @GetMapping("/flats")
    public ResponseEntity<List<Flat>> getAll() throws IOException {
        return ResponseEntity.ok(service.getFlats());
    }
}
