package com.elixir.elixir.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elixir.elixir.entity.Label;
import com.elixir.elixir.exceptions.LabelDuplicateException;
import com.elixir.elixir.exceptions.LabelNoSuchElementException;
import com.elixir.elixir.service.Interface.LabelService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("label")


public class LabelController {
    @Autowired
    private LabelService labelService;

    @GetMapping
    public List<Label> getLabels() {
        return labelService.getLabels();
    }

    @GetMapping("/{label_name}")
    public ResponseEntity<Label> getLabelByName(@PathVariable String label_name)
        throws LabelNoSuchElementException{
            return ResponseEntity.ok(labelService.getLabelByName(label_name).get());
    }
    
    @PostMapping("/{label_name}")
    public ResponseEntity<Label> createLabel(@PathVariable String label_name)
        throws LabelDuplicateException{
            Label result = labelService.createLabel(label_name);
            return ResponseEntity.created(URI.create("/labels/" + result.getLabel_id())).body(result);
        }
    }   
    
    
