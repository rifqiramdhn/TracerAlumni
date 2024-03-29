package com.example.traceralumni;

import com.example.traceralumni.Model.BerandaModel;
import com.example.traceralumni.Model.DaftarModel;
import com.example.traceralumni.Model.DonasiModel;
import com.example.traceralumni.Model.InfoModel;
import com.example.traceralumni.Model.LowonganModel;
import com.example.traceralumni.Model.PathModel;
import com.example.traceralumni.Model.PermintaanDonasiModel;
import com.example.traceralumni.Model.PermintaanLowonganModel;
import com.example.traceralumni.Model.RiwayatPekerjaanModel;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface JsonApi {

    @FormUrlEncoded
    @POST("post_login.php")
    Call<DaftarModel> login(@Field("nim") String nim, @Field("password") String password);

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
                            @Field("totalAnggaran") Double totalAnggaran,
                            @Field("tanggal_opendonasi") String tanggal_opendonasi,
                            @Field("file") String file);

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
            @Query("jabatan") String jabatan);

    @GET("get_data_alumni_count.php")
    Call<DaftarModel> getDataAlumniCount(
            @Query("jurusan") String jurusan,
            @Query("prodi") String prodi,
            @Query("angkatan") String angkatan,
            @Query("jabatan") String jabatan
    );

    @FormUrlEncoded
    @POST("create_lowongan_pekerjaan.php")
    Call<Void> createLowongan(@Field("nim") String nim,
                              @Field("judulLowongan") String judulLowongan,
                              @Field("jabatan") String jabatan,
                              @Field("namaPerusahaan") String namaPerusahaan,
                              @Field("alamatPerusahaan") String alamat,
                              @Field("kuota") Integer kuota,
                              @Field("gaji") String gaji,
                              @Field("syarat") String syaratPekerjaan,
                              @Field("website") String website,
                              @Field("email") String email,
                              @Field("notelp") String noTelp,
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
    Call<String> changePassword(@Field("nim") String nim,
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

    @GET("get_all_beranda.php")
    Call<ArrayList<BerandaModel>> getAllBeranda();

    @FormUrlEncoded
    @POST("post_get_lowongan_from_id.php")
    Call<LowonganModel> getLowonganFromId(@Field("id_lowongan") Integer id_lowongan);

    @FormUrlEncoded
    @POST("get_permintaan_lowongan_per_alumni.php")
    Call<ArrayList<LowonganModel>> getStatusLowongan(@Field("nim") String nim);

    @FormUrlEncoded
    @POST("get_permintaan_donasi_per_alumni.php")
    Call<ArrayList<PermintaanDonasiModel>> getStatusPermintaanDonasi(@Field("nim") String nim);

    @FormUrlEncoded
    @POST("post_change_permintaan_lowongan.php")
    Call<Void> updateLowongan(@Field("idLowongan") Integer idLowongan,
                              @Field("judulLowongan") String judulLowongan,
                              @Field("jabatan") String jabatan,
                              @Field("namaPerusahaan") String namaPerusahaan,
                              @Field("alamatPerusahaan") String alamat,
                              @Field("kuota") Integer kuota,
                              @Field("gaji") String gaji,
                              @Field("syarat") String syaratPekerjaan,
                              @Field("website") String website,
                              @Field("email") String email,
                              @Field("notelp") String noTelp,
                              @Field("cp") String cp,
                              @Field("status") String status,
                              @Field("tanggal_lowongan") String tanggal,
                              @Field("logo_perusahaan") String logo);

    @FormUrlEncoded
    @POST("create_alumni.php")
    Call<String> createAlumni(@Field("nama") String nama,
                              @Field("nim") String nim,
                              @Field("email") String email,
                              @Field("password") String password,
                              @Field("id_jurusan") Integer idJurusan,
                              @Field("id_prodi") Integer idProdi);

    @FormUrlEncoded
    @POST("delete_alumni.php")
    Call<Void> deleteAlumni(@Field("nim") String nim);

    @FormUrlEncoded
    @POST("post_add_userid_alumni.php")
    Call<Void> addUserIdAlumni(@Field("nim") String nim,
                               @Field("userId") String userId);

    @GET("get_count_alumni_aktif.php")
    Call<String> countAlumniAktif();
}
