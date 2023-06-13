package com.example.serverIntelliTestinh.servive;

import com.example.serverIntelliTestinh.model.Alert;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface AlertService{
    public Alert[] getAll() throws FileNotFoundException, JsonProcessingException;

    void addAlert(Alert newAlert) throws IOException;
}
