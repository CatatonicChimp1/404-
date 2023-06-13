package com.example.serverIntelliTestinh.repo;

import com.example.serverIntelliTestinh.model.Alert;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface AlertRepo {
    Alert[] getAll() throws FileNotFoundException, JsonProcessingException;

    void save(Alert newAlert) throws IOException;
}
