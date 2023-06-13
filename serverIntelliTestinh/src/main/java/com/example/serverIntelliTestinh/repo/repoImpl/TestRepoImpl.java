package com.example.serverIntelliTestinh.repo.repoImpl;

import com.example.serverIntelliTestinh.model.User;
import com.example.serverIntelliTestinh.model.test.Test;
import com.example.serverIntelliTestinh.repo.TestRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.*;
import java.util.stream.Collectors;

public class TestRepoImpl implements TestRepo {
    private final String db = "/home/iliya/IdeaProjects/serverIntelliTestinh/src/main/resources/Content/Content.sys";
    @Override
    public void add(Test newTest) throws IOException {
        String db = new BufferedReader(new FileReader(this.db)).lines().collect(Collectors.joining());
        ObjectMapper mapper = new ObjectMapper();

        JsonNode jsonNode = mapper.readTree(db);
        ArrayNode jsonArray = (ArrayNode) jsonNode;
        JsonNode testJson = mapper.convertValue(newTest, JsonNode.class);
        jsonArray.add(testJson);
        String updatedJsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);
        BufferedWriter writer = new BufferedWriter(new FileWriter(this.db));
        writer.write(updatedJsonString);
        writer.close();
    }

    @Override
    public void update(Test test, int id) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String db = new BufferedReader(new FileReader(this.db)).lines().collect(Collectors.joining());
        Test existingTest = mapper.readValue(new File(this.db), Test.class);

        // Проверка, что идентификаторы совпадают
        if (existingTest.getId() == id) {
            // Обновление полей с переданным объектом test
            existingTest.setQuestion(test.getQuestion());
            // Запись обновленного объекта в JSON-файл
            mapper.writeValue(new File("test.json"), existingTest);
        }
    }

    @Override
    public void delete(int id) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        // Чтение существующего JSON-файла
        Test[] tests = mapper.readValue(new File(this.db), Test[].class);

        // Поиск объекта с переданным идентификатором и удаление из массива
        for (int i = 0; i < tests.length; i++) {
            if (tests[i].getId() == id) {
                // Сдвигаем элементы массива для удаления
                System.arraycopy(tests, i + 1, tests, i, tests.length - 1 - i);

                // Обрезаем массив, исключая последний элемент
                Test[] newTests = new Test[tests.length - 1];
                System.arraycopy(tests, 0, newTests, 0, tests.length - 1);

                // Запись обновленного массива в JSON-файл
                mapper.writeValue(new File("tests.json"), newTests);

                break;
            }
        }

    }

    @Override
    public Test getAll() throws FileNotFoundException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String db = new BufferedReader(new FileReader(this.db)).lines().collect(Collectors.joining());
        Test test = mapper.readValue(db,Test.class);
        return test;
    }
}
