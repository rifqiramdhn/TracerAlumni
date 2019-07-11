package com.example.traceralumni.Model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class InfoModel {

    @SerializedName("id_info")
    private Integer idInfo;

    @SerializedName("judul")
    private String judul;

    @SerializedName("keterangan")
    private String keterangan;

    @SerializedName("link")
    private String link;

    private Date tanggal;

//    public InfoModel(Integer idInfo, String judul, String keterangan, String link, Date tanggal) {
//        this.idInfo = idInfo;
//        this.judul = judul;
//        this.keterangan = keterangan;
//        this.link = link;
//        this.tanggal = tanggal;
//    }

    public Integer getIdInfo() {
        return idInfo;
    }

    public String getJudul() {
        return judul;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public String getLink() {
        return link;
    }

    public Date getTanggal() {
        return tanggal;
    }
}
