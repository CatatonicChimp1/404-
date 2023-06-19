package com.example.serverIntelliTestinh.servive;

import com.example.serverIntelliTestinh.model.test.Test;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface TestService {
    void addTest(Test newTest) throws IOException;

    void update(Test test, int id) throws IOException;

    void delete(int id) throws IOException;

    Test[] getAll() throws FileNotFoundException, JsonProcessingException;

    void importTest(Test[] tests);
}
