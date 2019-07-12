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
import retrofit2.http.Query;

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

    @FormUrlEncoded
    @POST("delete_info.php")
    Call<Void> deleteInfo(@Field("idInfo") Integer integer);

    @FormUrlEncoded
    @POST("delete_donasi.php")
    Call<Void> deleteDonasi(@Field("idDonasi") Integer integer);

    @GET("get_data_alumni_daftar.php")
    Call<ArrayList<DaftarModel>> getDataAlumniDaftar(
            @Query("jurusan") String jurusan,
            @Query("prodi") String prodi,
            @Query("angkatan") String angkatan,
            @Query("lainnya") String lainnya);

    @GET("get_data_alumni_count.php")
    Call<DaftarModel> getDataAlumniCount(
            @Query("jurusan") String jurusan,
            @Query("prodi") String prodi,
            @Query("angkatan") String angkatan,
            @Query("lainnya") String lainnya
    );
    @FormUrlEncoded
    @POST("create_lowongan_pekerjaan.php")
    Call<Void> createLowongan(@Field("judulLowongan") String judulLowongan,
                              @Field("jabatan") String jabatan,
                              @Field("namaPerusahaan") String namaPerusahaan,
                              @Field("alamatPerusahaan") String alamat,
                              @Field("kuota") Integer kuota,
                              @Field("gaji") String gaji,
                              @Field("syarat") String syaratPekerjaan,
                              @Field("website") String website,
                              @Field("email") String email,
                              @Field("noTelp") String noTelp,
                              @Field("cp") String cp);
}
