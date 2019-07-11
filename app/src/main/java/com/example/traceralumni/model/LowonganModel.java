package com.example.traceralumni.Model;

import com.google.gson.annotations.SerializedName;

public class LowonganModel {
    @SerializedName("id_lowongan")
    private Integer id;

    @SerializedName("username")
    private String username;

    @SerializedName("nama_lowongan")
    private String nama_lowongan;

    @SerializedName("nama_perusahaan")
    private String nama_perusahaan;

    @SerializedName("alamat_perusahaan")
    private String alamat_perusahaan;

    @SerializedName("kisaran_gaji")
    private String kisaran_gaji;

    @SerializedName("syarat_pekerjaan")
    private String syarat_pekerjaan;

    @SerializedName("pelamar_yang_dibutuhkan")
    private Integer kuota;

    @SerializedName("lowongan_jabatan")
    private String jabatan;

    @SerializedName("website_perusahaan")
    private String website;

    @SerializedName("email_perusahaan")
    private String email;

    @SerializedName("no_telp_perusahaan")
    private String no_telp;

    @SerializedName("contact_person")
    private String cp;

    @SerializedName("status")
    private String status_lowongan;

    @SerializedName("logo_perusahaan")
    private String url_logo;



    public LowonganModel(int id, String nama_lowongan, String nama_perusahaan, String lokasi_perusahaan, String kisaran_gaji){
        this.id = id;
        this.nama_lowongan = nama_lowongan;
        this.nama_perusahaan = nama_perusahaan;
        this.alamat_perusahaan = lokasi_perusahaan;
        this.kisaran_gaji = kisaran_gaji;
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getNama_lowongan() {
        return nama_lowongan;
    }

    public String getNama_perusahaan() {
        return nama_perusahaan;
    }

    public String getAlamat_perusahaan() {
        return alamat_perusahaan;
    }

    public String getKisaran_gaji() {
        return kisaran_gaji;
    }

    public String getSyarat_pekerjaan() {
        return syarat_pekerjaan;
    }

    public Integer getKuota() {
        return kuota;
    }

    public String getJabatan() {
        return jabatan;
    }

    public String getWebsite() {
        return website;
    }

    public String getEmail() {
        return email;
    }

    public String getNo_telp() {
        return no_telp;
    }

    public String getCp() {
        return cp;
    }

    public String getStatus_lowongan() {
        return status_lowongan;
    }

    public String getUrl_logo() {
        return url_logo;
    }
}
