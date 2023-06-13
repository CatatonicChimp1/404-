package com.example.serverIntelliTestinh.servive;

import com.example.serverIntelliTestinh.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface UserService {

    void addUser(User newUser) throws IOException;

    User login(String login, String password) throws IOException;

    void update(User user, String login) throws IOException;

    User[] getAll() throws FileNotFoundException, JsonProcessingException;

    void isBlocked(String login, boolean isBlocked) throws IOException;
}
