package com.example.serverIntelliTestinh.controller;

import com.example.serverIntelliTestinh.model.User;
import com.example.serverIntelliTestinh.servive.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @RequestMapping(path = "/add",method = RequestMethod.POST)
    public void addUser(@RequestBody User newUser) throws IOException {
        service.addUser(newUser);
    }

    @GetMapping(value = "/login", params = {"login", "password"})
    public ResponseEntity<User> loginUser(@RequestParam("login") String login, @RequestParam("password") String password) throws IOException {
        User user = service.login(login, password);
        return ResponseEntity.ok(user);
    }

    @PutMapping(value = "/blocked", params = {"login", "isBlocked"})
    public void blockedUser(@RequestParam("login") String login, @RequestParam("isBlocked") boolean isBlocked) throws IOException {
        service.isBlocked(login, isBlocked);
    }

    @PutMapping(value = "/update", params = {"login"})
    public void updateUser(@RequestBody User user, @RequestParam("login") String login) throws IOException {
        service.update(user, login);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<User>> getAll() throws FileNotFoundException, JsonProcessingException {
        return ResponseEntity.ok(List.of(service.getAll()));
    }
}
