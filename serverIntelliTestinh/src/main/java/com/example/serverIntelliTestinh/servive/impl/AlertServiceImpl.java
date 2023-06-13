package com.example.serverIntelliTestinh.servive.impl;

import com.example.serverIntelliTestinh.model.Alert;
import com.example.serverIntelliTestinh.repo.AlertRepo;
import com.example.serverIntelliTestinh.repo.repoImpl.AlertRepoImpl;
import com.example.serverIntelliTestinh.servive.AlertService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Service
public class AlertServiceImpl implements AlertService {
    @Override
    public Alert[] getAll() throws FileNotFoundException, JsonProcessingException {
        AlertRepo repo = new AlertRepoImpl();
        return repo.getAll();
    }

    @Override
    public void addAlert(Alert newAlert) throws IOException {
        AlertRepo repo = new AlertRepoImpl();
        repo.save(newAlert);
    }
}
