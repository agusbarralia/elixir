package com.elixir.elixir.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elixir.elixir.entity.Variety;
import com.elixir.elixir.exceptions.VarietyDuplicateException;
import com.elixir.elixir.exceptions.VarietyNoSuchElementException;
import com.elixir.elixir.service.Interface.VarietyService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("varieties")
public class VarietyController {
    @Autowired
    private VarietyService varietyService;

    @GetMapping
    public List<Variety> getVarieties() {
        return varietyService.getVarieties();
    }

    @GetMapping("/{variety_name}")
    public ResponseEntity<Variety> getVarietyByName(@PathVariable String variety_name)
        throws VarietyNoSuchElementException{
            return ResponseEntity.ok(varietyService.getVarietyByName(variety_name).get());
    }
    
    @PostMapping("/admin/{variety_name}")
    public ResponseEntity<Variety> createVariety(@PathVariable String variety_name)
        throws VarietyDuplicateException{
            Variety result = varietyService.createVariety(variety_name);
            return ResponseEntity.created(URI.create("/varieties/" + result.getVariety_id())).body(result);
        }

    @DeleteMapping("/admin/{varietyId}")
    public ResponseEntity<Variety> deleteVariety(@PathVariable Long varietyId)
        throws VarietyNoSuchElementException{
            Variety result = varietyService.deleteVariety(varietyId);
            return ResponseEntity.ok(result);
        }
    }