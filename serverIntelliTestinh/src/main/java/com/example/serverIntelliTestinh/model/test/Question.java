package com.example.serverIntelliTestinh.model.test;

import java.util.List;

public class Question {
    private String question;
    private List<String> answer;
    private int idTrueAnswer;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getAnswer() {
        return answer;
    }

    public void setAnswer(List<String> answer) {
        this.answer = answer;
    }

    public int getIdTrueAnswer() {
        return idTrueAnswer;
    }

    public void setIdTrueAnswer(int idTrueAnswer) {
        this.idTrueAnswer = idTrueAnswer;
    }
}
