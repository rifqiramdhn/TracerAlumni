package com.example.traceralumni;

import android.content.Intent;

import com.example.traceralumni.Model.DaftarModel;
import com.example.traceralumni.Model.DonasiModel;
import com.example.traceralumni.Model.InfoModel;
import com.example.traceralumni.Model.LowonganModel;
import com.example.traceralumni.Model.PathModel;
import com.example.traceralumni.Model.PermintaanDonasiModel;
import com.example.traceralumni.Model.PermintaanLowonganModel;
import com.example.traceralumni.Model.RiwayatPekerjaanModel;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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
                          @Field("link") String link,
                          @Field("tanggal_info") String tanggal_info);

    @FormUrlEncoded
    @POST("create_donasi.php")
    Call<Void> createDonasi(@Field("idDonasi") Integer integer,
                            @Field("namaKegiatan") String namaKegiatan,
                            @Field("noTelepon") String noTelepon,
                            @Field("keterangan") String keterangan,
                            @Field("totalAnggaran") Integer totalAnggaran,
                            @Field("tanggal_opendonasi") String tanggal_opendonasi);

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
    Call<Void> createLowongan(@Field("username") String username,
                              @Field("judulLowongan") String judulLowongan,
                              @Field("jabatan") String jabatan,
                              @Field("namaPerusahaan") String namaPerusahaan,
                              @Field("alamatPerusahaan") String alamat,
                              @Field("kuota") Integer kuota,
                              @Field("gaji") String gaji,
                              @Field("syarat") String syaratPekerjaan,
                              @Field("website") String website,
                              @Field("email") String email,
                              @Field("noTelp") String noTelp,
                              @Field("cp") String cp,
                              @Field("status") String status,
                              @Field("tanggal_lowongan") String tanggal,
                              @Field("logo_perusahaan") String logo);

    @FormUrlEncoded
    @POST("post_get_user_data.php")
    Call<DaftarModel> getUserData(@Field("nim") String nim);

    @FormUrlEncoded
    @POST("post_sunting_profil.php")
    Call<Void> suntingProfil(@Field("nim") String nim,
                             @Field("email") String email,
                             @Field("tempat_lahir") String tempat_lahir,
                             @Field("tanggal_lahir") String tanggal_lahir,
                             @Field("alamat") String alamat,
                             @Field("kode_pos") String kode_pos,
                             @Field("angkatan") String angkatan,
                             @Field("tahun_lulus") String tahun_lulus,
                             @Field("tanggal_yudisium") String tanggal_yudisium,
                             @Field("kewarganegaraan") String kewarganegaraan,
                             @Field("nama_negara") String nama_negara,
                             @Field("nomor_hp") String nomor_hp,
                             @Field("nomor_telepon") String nomor_telepon,
                             @Field("facebook") String facebook,
                             @Field("twitter") String twitter);

    @FormUrlEncoded
    @POST("create_permintaan_donasi.php")
    Call<Void> createPermintaanDonasi(@Field("idDonasi") Integer integer,
                                      @Field("nim") String nim,
                                      @Field("totalBantuan") Double totalBantuan,
                                      @Field("tanggal_daftar_donatur") String tanggal_daftar_donatur);

    @GET("get_all_permintaan_donasi.php")
    Call<ArrayList<PermintaanDonasiModel>> getPerDonasi();

    @FormUrlEncoded
    @POST("confirm_permintaan_donasi.php")
    Call<Void> confirmDonasi(@Field("idDaftarDonasi") Integer idDaftarDonasi,
                             @Field("confirm") String confirm);

    @GET("get_all_permintaan_lowongan.php")
    Call<ArrayList<PermintaanLowonganModel>> getPerLowongan();

    @FormUrlEncoded
    @POST("confirm_permintaan_lowongan.php")
    Call<Void> confirmLowongan(@Field("idPermintaanLowongan") Integer idPermintaanLowongan,
                               @Field("confirm") String confirm);

    @FormUrlEncoded
    @POST("post_get_donasi_data.php")
    Call<DonasiModel> getDonasi(@Field("idDaftarDonasi") Integer idDaftarDonasi);

    @FormUrlEncoded
    @POST("delete_lowongan_pekerjaan.php")
    Call<Void> deleteLowongan(@Field("idLowongan") Integer integer);

    @FormUrlEncoded
    @POST("post_change_password.php")
    Call<Void> changePassword(@Field("nim") String nim,
                              @Field("oldpass") String oldpass,
                              @Field("newpass") String newpass);

    @GET("get_count_permintaan_donasi.php")
    Call<String> getCountPermintaanDonasi();

    @GET("get_count_permintaan_lowongan.php")
    Call<String> getCountPermintaanLowongan();

    @GET("get_all_donatur.php")
    Call<ArrayList<PermintaanDonasiModel>> getAllDonatur(@Query("id_opendonasi") Integer id_opendonasi);

    @GET("get_jumlah_duit.php")
    Call<PermintaanDonasiModel> getJumlahDuit(@Query("id_opendonasi") Integer id_opendonasi);

    @FormUrlEncoded
    @POST("post_riwayat_pekerjaan.php")
    Call<ArrayList<RiwayatPekerjaanModel>> getRiwayat(@Field("nim") String nim);

    @FormUrlEncoded
    @POST("create_riwayat_pekerjaan.php")
    Call<Void> createRiwayat(@Field("idRiwayat") Integer idRiwayat,
                             @Field("nim") String nim,
                             @Field("nama_riwayat") String pekerjaan,
                             @Field("lokasi_riwayat") String lokasi,
                             @Field("perusahaan_riwayat") String namaPerusahaan,
                             @Field("gaji_riwayat") String gaji,
                             @Field("tahun_awal") String tahunAwal,
                             @Field("tahun_akhir") String tahunAkhir);

    @FormUrlEncoded
    @POST("delete_riwayat_pekerjaan.php")
    Call<Void> deleteRiwayat(@Field("idRiwayat") Integer idRiwayat);

    @Multipart
    @POST("upload_photo.php")
    Call<PathModel> uploadPhoto(@Part MultipartBody.Part photo);

    @FormUrlEncoded
    @POST("create_photo_path.php")
    Call<Void> updatePhotoPath(@Field("nim") String nim,
                               @Field("oldpath") String oldpath,
                               @Field("newpath") String newpath);

    @FormUrlEncoded
    @POST("create_logo_perusahaan.php")
    Call<Void> uploadLogoPerusahaan(@Field("nim") String nim,
                                    @Field("oldpath") String oldpath,
                                    @Field("newpath") String newpath);
}
