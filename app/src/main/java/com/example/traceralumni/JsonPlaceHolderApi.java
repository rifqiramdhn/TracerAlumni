package com.example.traceralumni;

import com.example.traceralumni.Model.DaftarModel;
import com.example.traceralumni.Model.DonasiModel;
import com.example.traceralumni.Model.InfoModel;
import com.example.traceralumni.Model.LowonganModel;

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

    @GET("get_all_info.php")
    Call<ArrayList<InfoModel>> getAllInfo();

    @GET("get_all_lowongan_pekerjaan.php")
    Call<ArrayList<LowonganModel>> getAllLowongan();

    @FormUrlEncoded
    @POST("create_info.php")
    Call<Void> createInfo(@Field("idInfo") Integer integer,
                          @Field("judul") String judul,
                          @Field("keterangan") String keterangan,
                          @Field("link") String link);

    @FormUrlEncoded
    @POST("create_donasi.php")
    Call<Void> createDonasi(@Field("idDonasi") Integer integer,
                          @Field("namaKegiatan") String namaKegiatan,
                          @Field("noTelepon") String noTelepon,
                          @Field("keterangan") String keterangan,
                          @Field("totalAnggaran") Integer totalAnggaran);
}
