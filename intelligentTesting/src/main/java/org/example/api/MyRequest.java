package org.example.api;

import com.google.gson.Gson;
import okhttp3.*;
import org.example.global.GlobalVariables;
import org.example.model.User;

import javax.swing.*;
import java.io.IOException;

public class MyRequest {
    public static Response requestAllUser() {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        okhttp3.Request requestAllUsers = new okhttp3.Request.Builder()
                .url("http://localhost:8080/user/all")
                .method("GET", null)
                .build();
        try {
            return client.newCall(requestAllUsers).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void requestAddUser(User user){
        Gson gson = new Gson();
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, gson.toJson(user));
        Request request = new Request.Builder()
                .url("http://localhost:8080/user/add")
                .method("POST", body)
                .addHeader("Accept-Charset", "application/json")
                .addHeader("Content-Type", "application/json")
                .build();
        try {
            Response response = client.newCall(request).execute();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void requestUpdateUser(User user) {
        Gson gson = new Gson();
        OkHttpClient client = new OkHttpClient();
        MediaType mediaTypeUpdate = MediaType.parse("application/json");
        RequestBody bodyUpdate = RequestBody.create(mediaTypeUpdate, gson.toJson(user));
        Request request = new Request.Builder()
                .url("http://localhost:8080/user/update?login=" + GlobalVariables.USER.getLogin())
                .method("PUT", bodyUpdate)
                .addHeader("Content-Type", "application/json")
                .build();
        try {
            Response response = client.newCall(request).execute();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static Response getLoginUser(String login, String password) {
        OkHttpClient client = new OkHttpClient();
        Request requestFromClient = new Request.Builder()
                .url("http://localhost:8080/user/login?login=" + login + "&" +
                        "password=" + password)
                .method("GET", null)
                .build();
        try {
            Gson gson = new Gson();
            Response response = client.newCall(requestFromClient).execute();
            return response;
            //System.out.println("User");
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void deleteUser(String login){
        OkHttpClient client = new OkHttpClient();
        MediaType mediaTypeDelete = MediaType.parse("text/plain");
        RequestBody bodyDelete = RequestBody.create(mediaTypeDelete, "");
        Request request = new Request.Builder()
                .url("http://localhost:8080/user/delete?login="+login)
                .method("DELETE", null)
                .build();
        try {
            Response response = client.newCall(request).execute();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void blockedUser(String login){
        OkHttpClient client = new OkHttpClient();
        MediaType mediaTypeBlock = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaTypeBlock, "");
        Request requestBlock = new Request.Builder()
                .url("http://localhost:8080/user/blocked?login=" + login + "&isBlocked=true")
                .method("PUT", body)
                .build();
        try {
            Response response = client.newCall(requestBlock).execute();
        } catch (IOException exc) {
            throw new RuntimeException(exc);
        }
    }

    public static void requestAddAlert(String login){
        OkHttpClient client = new OkHttpClient();
        MediaType mediaTypeBlock = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaTypeBlock, "");
        Request requestBlock = new Request.Builder()
                .url("http://localhost:8080/alert/add?description=" + login + " пользователь заблокирован")
                .method("POST", body)
                .build();
        try {
            Response responseBlock = client.newCall(requestBlock).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static Response requestAllAlert() {
        OkHttpClient clientAllAlert = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("http://localhost:8080/alert/all")
                .method("GET", null)
                .build();
        try {
            Response response = clientAllAlert.newCall(request).execute();
            return response;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
