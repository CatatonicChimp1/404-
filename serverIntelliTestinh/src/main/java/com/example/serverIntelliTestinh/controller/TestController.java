package com.example.serverIntelliTestinh.controller;

import com.example.serverIntelliTestinh.model.test.Test;
import com.example.serverIntelliTestinh.servive.TestService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.IOException;

@Controller
@RequestMapping("/tests")
public class TestController {
    private final TestService service;

    public TestController(TestService service) {
        this.service = service;
    }

    @RequestMapping(path = "/add",method = RequestMethod.POST)
    public void addTest(@RequestBody Test newTest) throws IOException {
        service.addTest(newTest);
    }

    @PutMapping(value = "/update", params = {"id"})
    public void updateTest(@RequestBody Test test, @RequestParam("id") int id) throws IOException {
        service.update(test, id);
    }

    @DeleteMapping(value = "/delete", params = {"id"})
    public void deleteTest(@RequestParam("id") int id) throws IOException {
        service.delete(id);
    }

    @RequestMapping(path = "/all",method = RequestMethod.GET)
    public ResponseEntity<Test> getAll() throws FileNotFoundException, JsonProcessingException {
        return ResponseEntity.ok(service.getAll());
    }
}
