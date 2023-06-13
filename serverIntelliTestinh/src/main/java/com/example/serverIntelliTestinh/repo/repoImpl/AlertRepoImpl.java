package com.example.serverIntelliTestinh.repo.repoImpl;

import com.example.serverIntelliTestinh.model.Alert;
import com.example.serverIntelliTestinh.model.User;
import com.example.serverIntelliTestinh.repo.AlertRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.http.ResponseEntity;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

public class AlertRepoImpl implements AlertRepo {
    private final String db = "/home/iliya/IdeaProjects/serverIntelliTestinh/src/main/resources/alert/alerts.sys";
    @Override
    public Alert[] getAll() throws FileNotFoundException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String db = new BufferedReader(new FileReader(this.db)).lines().collect(Collectors.joining());
        Alert[] alerts = mapper.readValue(db,Alert[].class);
        return alerts;
    }

    @Override
    public void save(Alert newAlert) throws IOException {
        String db = new BufferedReader(new FileReader(this.db)).lines().collect(Collectors.joining());
        ObjectMapper mapper = new ObjectMapper();

        JsonNode jsonNode = mapper.readTree(db);
        ArrayNode jsonArray = (ArrayNode) jsonNode;
        JsonNode alertJson = mapper.convertValue(newAlert, JsonNode.class);
        jsonArray.add(alertJson);
        String updatedJsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);
        BufferedWriter writer = new BufferedWriter(new FileWriter(this.db));
        writer.write(updatedJsonString);
        writer.close();
    }
}
