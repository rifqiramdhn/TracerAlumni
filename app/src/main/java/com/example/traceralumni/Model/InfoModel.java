package com.example.traceralumni.Model;

import java.util.Date;

public class InfoModel {
    private String judul,isi,link;
    private Date tanggal;

    public InfoModel(String judul, String isi, String link, Date tanggal) {
        this.judul = judul;
        this.isi = isi;
        this.link = link;
        this.tanggal = tanggal;
    }

    public InfoModel(String judul, String isi, String link) {
        this.judul = judul;
        this.isi = isi;
        this.link = link;
    }

    public String getJudul() {
        return judul;
    }

    public String getIsi() {
        return isi;
    }

    public String getLink() {
        return link;
    }

    public Date getTanggal() {
        return tanggal;
    }
}
