package org.example.model;

import lombok.*;


import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

@AllArgsConstructor
@Generated("jsonschema2pojo")
public class User {

    @SerializedName("login")
    @Expose
    private String login;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("patronymic")
    @Expose
    private String patronymic;
    @SerializedName("birthday")
    @Expose
    private String birthday;
    @SerializedName("group")
    @Expose
    private int group;
    @SerializedName("secretQuestion")
    @Expose
    private String secretQuestion;
    @SerializedName("answerOnQuestion")
    @Expose
    private String answerOnQuestion;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("numberPhone")
    @Expose
    private String numberPhone;
    @SerializedName("countItogTest")
    @Expose
    private int countItogTest;
    @SerializedName("blocked")
    @Expose
    private boolean blocked;

    @SerializedName("testIdToGrade")
    @Expose
    private HashMap<Integer, Integer> testIdToGrade;

    @SerializedName("role")
    @Expose
    private String role;

    public User(String login, String password, String firstName, String lastName, String patronymic, String birthday,
                int group, String secretQuestion, String answerOnQuestion, String email, String numberPhone,
                int countItogTest, boolean blocked, String role) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.birthday = birthday;
        this.group = group;
        this.secretQuestion = secretQuestion;
        this.answerOnQuestion = answerOnQuestion;
        this.email = email;
        this.numberPhone = numberPhone;
        this.countItogTest = countItogTest;
        this.blocked = blocked;
        this.role = role;
    }
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Integer getGroup() {
        return group;
    }

    public void setGroup(Integer group) {
        this.group = group;
    }

    public String getSecretQuestion() {
        return secretQuestion;
    }

    public void setSecretQuestion(String secretQuestion) {
        this.secretQuestion = secretQuestion;
    }

    public String getAnswerOnQuestion() {
        return answerOnQuestion;
    }

    public void setAnswerOnQuestion(String answerOnQuestion) {
        this.answerOnQuestion = answerOnQuestion;
    }

    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public Integer getCountItogTest() {
        return countItogTest;
    }

    public void setCountItogTest(Integer countItogTest) {
        this.countItogTest = countItogTest;
    }

    public Boolean getBlocked() {
        return blocked;
    }

    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
