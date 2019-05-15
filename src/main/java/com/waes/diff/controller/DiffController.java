package com.waes.diff.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DiffController {

    @PostMapping("/v1/diff/{id}/left")
    public ResponseEntity postLeft(@PathVariable("id") String id) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/v1/diff/{id}/right")
    public ResponseEntity postRight(@PathVariable("id") String id) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/v1/diff/{id}")
    public ResponseEntity getDiff(@PathVariable("id") String id) {
        return ResponseEntity.ok().build();
    }

}
