package com.example.serverIntelliTestinh.servive.impl;

import com.example.serverIntelliTestinh.model.User;
import com.example.serverIntelliTestinh.repo.UserRepo;
import com.example.serverIntelliTestinh.repo.repoImpl.UserRepoImpl;
import com.example.serverIntelliTestinh.servive.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {
    @Value("${upload.path}")
    private String uploadPath;

    @Override
    public void addUser(User newUser) throws IOException {
        UserRepo repo =new UserRepoImpl();
        String iconName = newUser.getLogin();
        //File Icon = new File("resources\\etc\\" + iconName + ".sys");
        //Icon.createNewFile();
        //FileOutputStream fout = new FileOutputStream(Icon);
        //fout.write(newIcon.getBytes());
        //fout.close();
        repo.save(newUser);
    }

    @Override
    public User login(String login, String password) throws IOException {
        UserRepo repo = new UserRepoImpl();
        if(repo.searchByLogin(login)!=null){
            User user = repo.searchByLogin(login);
            if(Objects.equals(user.getPassword(), password)){
                return user;
            }
        }
        return null;
    }

    @Override
    public void update(User user, String login) throws IOException {
        UserRepo repo = new UserRepoImpl();
        repo.update(user, login);
    }

    @Override
    public User[] getAll() throws FileNotFoundException, JsonProcessingException {
        UserRepo repo = new UserRepoImpl();
        return repo.getAll();
    }

    @Override
    public void isBlocked(String login, boolean isBlocked) throws IOException {
        UserRepo repo = new UserRepoImpl();
        User user = repo.searchByLogin(login);
        user.setBlocked(isBlocked);
        repo.update(user, login);
    }
}
