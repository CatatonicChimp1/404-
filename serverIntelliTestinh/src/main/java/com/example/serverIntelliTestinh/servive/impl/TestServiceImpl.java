package com.example.serverIntelliTestinh.servive.impl;

import com.example.serverIntelliTestinh.model.test.Test;
import com.example.serverIntelliTestinh.repo.TestRepo;
import com.example.serverIntelliTestinh.repo.repoImpl.TestRepoImpl;
import com.example.serverIntelliTestinh.servive.TestService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Service
public class TestServiceImpl implements TestService {
    @Override
    public void addTest(Test newTest) throws IOException {
        TestRepo repo = new TestRepoImpl();
        repo.add(newTest);
    }

    @Override
    public void update(Test test, int id) throws IOException {
        TestRepo repo = new TestRepoImpl();
        repo.update(test, id);
    }

    @Override
    public void delete(int id) throws IOException {
        TestRepo repo = new TestRepoImpl();
        repo.delete(id);
    }

    @Override
    public Test getAll() throws FileNotFoundException, JsonProcessingException {
        TestRepo repo = new TestRepoImpl();
        return repo.getAll();
    }
}
