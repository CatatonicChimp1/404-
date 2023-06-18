package org.example.model.Test;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;

public class Test {
    private int id;
    private String question;
    private List<String> answer;
    private int idTrueAnswer;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
