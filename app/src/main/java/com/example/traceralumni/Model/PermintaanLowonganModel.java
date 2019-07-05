package com.example.traceralumni.Model;

import java.util.Date;

public class PermintaanLowonganModel {
    private String id, url_logo, pelowong, nama_lowongan, nama_perusahaan, lokasi_perusahaan;
    private int gaji;
    private Date tanggal_permintaan;

    public PermintaanLowonganModel(String id, String url_logo, String pelowong, String nama_lowongan, String nama_perusahaan, String lokasi_perusahaan, int gaji) {
        this.id = id;
        this.url_logo = url_logo;
        this.pelowong = pelowong;
        this.nama_lowongan = nama_lowongan;
        this.nama_perusahaan = nama_perusahaan;
        this.lokasi_perusahaan = lokasi_perusahaan;
        this.gaji = gaji;
//        this.tanggal_permintaan = tanggal_permintaan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl_logo() {
        return url_logo;
    }

    public void setUrl_logo(String url_logo) {
        this.url_logo = url_logo;
    }

    public String getPelowong() {
        return pelowong;
    }

    public void setPelowong(String pelowong) {
        this.pelowong = pelowong;
    }

    public String getNama_lowongan() {
        return nama_lowongan;
    }

    public void setNama_lowongan(String nama_lowongan) {
        this.nama_lowongan = nama_lowongan;
    }

    public String getNama_perusahaan() {
        return nama_perusahaan;
    }

    public void setNama_perusahaan(String nama_perusahaan) {
        this.nama_perusahaan = nama_perusahaan;
    }

    public String getLokasi_perusahaan() {
        return lokasi_perusahaan;
    }

    public void setLokasi_perusahaan(String lokasi_perusahaan) {
        this.lokasi_perusahaan = lokasi_perusahaan;
    }

    public int getGaji() {
        return gaji;
    }

    public void setGaji(int gaji) {
        this.gaji = gaji;
    }

    public Date getTanggal_permintaan() {
        return tanggal_permintaan;
    }

    public void setTanggal_permintaan(Date tanggal_permintaan) {
        this.tanggal_permintaan = tanggal_permintaan;
    }
}
