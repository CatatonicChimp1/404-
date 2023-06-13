package com.example.serverIntelliTestinh.model.test;

import java.util.HashMap;

public class Test {
    private int id;
    private HashMap<String, Question> question;

    public HashMap<String, Question> getQuestion() {
        return question;
    }

    public void setQuestion(HashMap<String, Question> question) {
        this.question = question;
    }

    public int getId() {
        return this.id;
    }
}
