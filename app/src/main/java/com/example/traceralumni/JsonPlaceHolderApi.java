package com.example.traceralumni;

import com.example.traceralumni.Model.DaftarModel;
import com.example.traceralumni.Model.DonasiModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface JsonPlaceHolderApi {

    @FormUrlEncoded
    @POST("post_login.php")
    Call<DaftarModel> requestLogin(@Field("username") String username, @Field("password") String password);

    @GET("get_all_donasi.php")
    Call<ArrayList<DonasiModel>> getAllDonasi();

    @GET("get_daftar_alumni.php")
    Call<ArrayList<DaftarModel>> getDaftarAlumni();
}
