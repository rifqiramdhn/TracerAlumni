package com.example.traceralumni;

import com.example.traceralumni.Model.DaftarModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface JsonPlaceHolderApi {

    @FormUrlEncoded
    @POST("post_login.php")
    Call<DaftarModel> requestLogin(@Field("username") String username, @Field("password") String password);
}
