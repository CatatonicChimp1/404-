package org.example.client;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import org.example.model.User;
import retrofit2.Call;
import retrofit2.http.*;

public interface ApiService {
    //Было бы славно её настроить для SpringBoot, ну или костыли...
    @Multipart
    @POST("/user/add")
    Call<RequestBody> addUser(@Part("User") User user, @Part MultipartBody.Part image);
}
