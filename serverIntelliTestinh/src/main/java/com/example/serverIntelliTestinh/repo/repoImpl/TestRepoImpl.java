package com.example.serverIntelliTestinh.repo.repoImpl;

import com.example.serverIntelliTestinh.model.User;
import com.example.serverIntelliTestinh.model.test.Test;
import com.example.serverIntelliTestinh.repo.TestRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.google.gson.*;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TestRepoImpl implements TestRepo {
    private final String db = "/home/iliya/IdeaProjects/serverIntelliTestinh/src/main/resources/Content/Content.sys";
    @Override
    public void add(Test newTest) throws IOException {
        String db = new BufferedReader(new FileReader(this.db)).lines().collect(Collectors.joining());
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        JsonArray jsonArray = gson.fromJson(db, JsonArray.class);
        JsonElement testJson = gson.toJsonTree(newTest);
        jsonArray.add(testJson);
        String updatedJsonString = gson.toJson(jsonArray);
        BufferedWriter writer = new BufferedWriter(new FileWriter(this.db));
        writer.write(updatedJsonString);
        writer.close();
    }

    @Override
    public void update(Test test, int id) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String db = new BufferedReader(new FileReader(this.db)).lines().collect(Collectors.joining());
        JsonArray jsonArray = gson.fromJson(db, JsonArray.class);
        JsonObject testJson = gson.toJsonTree(test).getAsJsonObject();

        Test[] testDb = gson.fromJson(db, Test[].class);
        List<Test> optionalTest = new java.util.ArrayList<>(Arrays.stream(testDb).toList());
        int index = 0;
        for (int i = 0; i < optionalTest.size(); i++) {
            if (optionalTest.get(i).getId() == id) {
                index = i;
            }
        }
        jsonArray.remove(index);
        jsonArray.add(testJson);
        String updatedJsonString = gson.toJson(jsonArray);
        BufferedWriter writer = new BufferedWriter(new FileWriter(this.db));
        writer.write(updatedJsonString);
        writer.close();
    }

    @Override
    public void delete(int id) throws IOException {
        Gson gson = new Gson();

// Чтение существующего JSON-файла
        Test[] tests = gson.fromJson(new FileReader(this.db), Test[].class);

// Поиск объекта с переданным идентификатором и удаление из массива
        for (int i = 0; i < tests.length; i++) {
            if (tests[i].getId() == id) {
                // Сдвигаем элементы массива для удаления
                System.arraycopy(tests, i + 1, tests, i, tests.length - 1 - i);

                // Обрезаем массив, исключая последний элемент
                Test[] newTests = new Test[tests.length - 1];
                System.arraycopy(tests, 0, newTests, 0, tests.length - 1);

                // Запись обновленного массива в JSON-файл
                FileWriter writer = new FileWriter(this.db);
                gson.toJson(newTests, writer);
                writer.close();

                break;
            }
        }

    }

    @Override
    public Test[] getAll() throws FileNotFoundException, JsonProcessingException {
        Gson gson = new Gson();
        String db = new BufferedReader(new FileReader(this.db)).lines().collect(Collectors.joining());
        Test[] tests = gson.fromJson(db, Test[].class);
        return tests;
    }
}
