package org.example.model.Test;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Test {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("answer")
    @Expose
    private List<String> answer;
    @SerializedName("idTrueAnswer")
    @Expose
    private int idTrueAnswer;
    @SerializedName("testGroup")
    @Expose
    private String testGroup;

    public String getTestGroup() {
        return testGroup;
    }

    public void setTestGroup(String testGroup) {
        this.testGroup = testGroup;
    }
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
