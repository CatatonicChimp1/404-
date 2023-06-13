package com.example.serverIntelliTestinh.controller;

import com.example.serverIntelliTestinh.model.Alert;
import com.example.serverIntelliTestinh.servive.AlertService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("/alert")
public class AlertController {
    private final AlertService service;


    public AlertController(AlertService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Alert>> getAll() throws FileNotFoundException, JsonProcessingException {
        return ResponseEntity.ok(Arrays.asList(service.getAll()));
    }

    @RequestMapping(path = "/add",method = RequestMethod.POST, params = {"description"})
    public void addAlert(@RequestParam("description") String description) throws IOException {
        Alert newAlert = new Alert(new Random().nextInt(0, 1024), description);
        service.addAlert(newAlert);
    }
}
