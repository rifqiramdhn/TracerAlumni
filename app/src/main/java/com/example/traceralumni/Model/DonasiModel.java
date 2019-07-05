package com.example.traceralumni.Model;

import java.util.Date;

public class DonasiModel {
    private String namaKegiatan, noTelp, keterangan, totalBiaya;
    private int id, fotoResId;
    private Date tanggal;

    public DonasiModel(String namaKegiatan, String noTelp, String keterangan, String totalBiaya, int id, int fotoResId, Date tanggal) {
        this.namaKegiatan = namaKegiatan;
        this.noTelp = noTelp;
        this.keterangan = keterangan;
        this.totalBiaya = totalBiaya;
        this.id = id;
        this.fotoResId = fotoResId;
        this.tanggal = tanggal;
    }

    public DonasiModel(String namaKegiatan, String keterangan, String totalBiaya) {
        this.namaKegiatan = namaKegiatan;
        this.keterangan = keterangan;
        this.totalBiaya = totalBiaya;
    }

    public String getNamaKegiatan() {
        return namaKegiatan;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public String getTotalBiaya() {
        return totalBiaya;
    }

    public int getId() {
        return id;
    }

    public int getFotoResId() {
        return fotoResId;
    }

    public Date getTanggal() {
        return tanggal;
    }
}
