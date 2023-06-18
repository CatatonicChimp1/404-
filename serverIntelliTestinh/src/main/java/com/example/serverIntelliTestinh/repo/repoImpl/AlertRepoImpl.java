package com.example.serverIntelliTestinh.repo.repoImpl;

import com.example.serverIntelliTestinh.model.Alert;
import com.example.serverIntelliTestinh.repo.AlertRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.io.*;
import java.util.stream.Collectors;

public class AlertRepoImpl implements AlertRepo {
    private final String db = "/home/iliya/IdeaProjects/serverIntelliTestinh/src/main/resources/alert/alerts.sys";
    @Override
    public Alert[] getAll() throws FileNotFoundException, JsonProcessingException {
        Gson gson = new Gson();
        String db = new BufferedReader(new FileReader(this.db)).lines().collect(Collectors.joining());
        Alert[] alerts = gson.fromJson(db, Alert[].class);
        return alerts;
    }

    @Override
    public void save(Alert newAlert) throws IOException {
        String db = new BufferedReader(new FileReader(this.db)).lines().collect(Collectors.joining());
        Gson gson = new Gson();

        JsonElement jsonElement = gson.fromJson(db, JsonElement.class);
        JsonArray jsonArray = jsonElement.getAsJsonArray();
        JsonElement alertJson = gson.toJsonTree(newAlert);
        jsonArray.add(alertJson);
        String updatedJsonString = gson.toJson(jsonElement);
        BufferedWriter writer = new BufferedWriter(new FileWriter(this.db));
        writer.write(updatedJsonString);
        writer.close();
    }
}
