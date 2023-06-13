package com.example.serverIntelliTestinh.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


public class User {
    private String login;
    private String password;
    private String  firstName;
    private String lastName;
    private String patronymic;
    private String birthday;
    private int group;
    private String secretQuestion;
    private String answerOnQuestion;
    private String email;
    private String numberPhone;
    //private String icon;
    //private Role role;
    private int countItogTest;

    private boolean isBlocked;

    public User(String login, String password, String firstName, String lastName, String patronymic, String birthday, int group, String secretQuestion, String answerOnQuestion, String email, String numberPhone) {
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
        //this.icon = icon;
        this.countItogTest = 0;
        this.isBlocked = false;
        //this.role = Role.USER;
    }

    public User(){}

    public void addCountTest()
    {
        this.countItogTest++;
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

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
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


    public int getCountItogTest() {
        return countItogTest;
    }

    public void setCountItogTest(int countItogTest) {
        this.countItogTest = countItogTest;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

}
