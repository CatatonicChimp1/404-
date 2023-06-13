package com.example.serverIntelliTestinh.repo;

import com.example.serverIntelliTestinh.model.test.Test;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface TestRepo {
    void add(Test newTest) throws IOException;

    void update(Test test, int id) throws IOException;

    void delete(int id) throws IOException;

    Test getAll() throws FileNotFoundException, JsonProcessingException;
}
