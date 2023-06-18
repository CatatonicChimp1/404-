package com.example.serverIntelliTestinh.repo.repoImpl;

import com.example.serverIntelliTestinh.model.User;
import com.example.serverIntelliTestinh.repo.UserRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.google.gson.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserRepoImpl implements UserRepo {
    private final String db = "/home/iliya/IdeaProjects/serverIntelliTestinh/src/main/resources/Users/DBUsers.sys";
    @Override
    public void save(User user) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        String userJsonString = gson.toJson(user);
        String db = Files.readString(Paths.get(this.db));
        JsonElement jsonElement = gson.fromJson(db, JsonElement.class);
        JsonElement userJson = gson.toJsonTree(user);
        JsonArray jsonArray = jsonElement.getAsJsonArray();
        jsonArray.add(userJson);
        String updatedJsonString = gson.toJson(jsonElement);
        Files.writeString(Paths.get(this.db), updatedJsonString);
    }

    @Override
    public User searchByLogin(String login) throws IOException {
        Gson gson = new Gson();
        String db = new BufferedReader(new FileReader(this.db)).lines().collect(Collectors.joining());
        User[] users = gson.fromJson(db, User[].class);
        Optional<User> optionalUser = Arrays.stream(users).filter(user -> user.getLogin().equals(login)).findFirst();

        User user = optionalUser.orElse(null);
        return user;
    }

    @Override
    public void update(User user, String login) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String db = new BufferedReader(new FileReader(this.db)).lines().collect(Collectors.joining());
        JsonArray jsonArray = gson.fromJson(db, JsonArray.class);
        JsonObject userJson = gson.toJsonTree(user).getAsJsonObject();

        User[] users = gson.fromJson(db, User[].class);
        List<User> optionalUser = new java.util.ArrayList<>(Arrays.stream(users).toList());
        int index = 0;
        for (int i = 0; i < optionalUser.size(); i++) {
            if (optionalUser.get(i).getLogin().equals(login)) {
                index = i;
            }
        }
        jsonArray.remove(index);
        jsonArray.add(userJson);
        String updatedJsonString = gson.toJson(jsonArray);
        BufferedWriter writer = new BufferedWriter(new FileWriter(this.db));
        writer.write(updatedJsonString);
        writer.close();
    }

    @Override
    public User[] getAll() throws FileNotFoundException, JsonProcessingException {
        Gson gson = new Gson();
        String db = new BufferedReader(new FileReader(this.db)).lines().collect(Collectors.joining());
        User[] users = gson.fromJson(db, User[].class);
        return users;
    }
}
