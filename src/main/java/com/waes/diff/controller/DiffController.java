package com.waes.diff.controller;

import com.waes.diff.domain.service.DiffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/diff")

public class DiffController {

    private final DiffService service;

    @Autowired
    public DiffController(DiffService service) {
        this.service = service;
    }

    @PostMapping("/{id}/left")
    public ResponseEntity postLeft(@PathVariable("id") String id, @RequestBody String content) {
        try {
            service.saveLeft(id, content);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    @PostMapping("/{id}/right")
    public ResponseEntity postRight(@PathVariable("id") String id, @RequestBody String content) {
        try {
            service.saveRight(id, content);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getDiff(@PathVariable("id") String id) {
        try {
            return ResponseEntity.ok(service.getDiff(id));
        } catch (EmptyResultDataAccessException ex) {
            return ResponseEntity.notFound().build();
        }
    }

}
