package com.example.serverIntelliTestinh.repo.repoImpl;

import com.example.serverIntelliTestinh.model.User;
import com.example.serverIntelliTestinh.repo.UserRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserRepoImpl implements UserRepo {
    private final String db = "/home/iliya/IdeaProjects/serverIntelliTestinh/src/main/resources/Users/DBUsers.sys";
    @Override
    public void save(User user) throws IOException {
        String db = new BufferedReader(new FileReader(this.db)).lines().collect(Collectors.joining());
        ObjectMapper mapper = new ObjectMapper();

        JsonNode jsonNode = mapper.readTree(db);
        ArrayNode jsonArray = (ArrayNode) jsonNode;
        JsonNode userJson = mapper.convertValue(user, JsonNode.class);
        jsonArray.add(userJson);
        String updatedJsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);
        BufferedWriter writer = new BufferedWriter(new FileWriter(this.db));
        writer.write(updatedJsonString);
        writer.close();
    }

    @Override
    public User searchByLogin(String login) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String db = new BufferedReader(new FileReader(this.db)).lines().collect(Collectors.joining());
        User[] users = mapper.readValue(db,User[].class);
        Optional<User> optionalUser = Arrays.stream(users).filter(user->user.getLogin().equals(login)).findFirst();

        User user = optionalUser.orElse(null);
        return user;
    }

    @Override
    public void update(User user, String login) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String db = new BufferedReader(new FileReader(this.db)).lines().collect(Collectors.joining());
        JsonNode jsonNode = mapper.readTree(db);
        ArrayNode jsonArray = (ArrayNode) jsonNode;
        JsonNode userJson = mapper.convertValue(user, JsonNode.class);

        User[] users = mapper.readValue(db,User[].class);
        List<User> optionalUser = new java.util.ArrayList<>(Arrays.stream(users).toList());
        int index = 0;
        for(int i=0;i< optionalUser.size();i++){
            if(optionalUser.get(i).getLogin().equals(login)){
                index = i;
            }
        }
        jsonArray.remove(index);
        jsonArray.add(userJson);
        String updatedJsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);
        BufferedWriter writer = new BufferedWriter(new FileWriter(this.db));
        writer.write(updatedJsonString);
        writer.close();
    }

    @Override
    public User[] getAll() throws FileNotFoundException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String db = new BufferedReader(new FileReader(this.db)).lines().collect(Collectors.joining());
        User[] users = mapper.readValue(db,User[].class);
        return users;
    }
}
