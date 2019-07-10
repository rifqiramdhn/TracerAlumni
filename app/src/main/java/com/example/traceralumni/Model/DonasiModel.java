package com.example.traceralumni.Model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class DonasiModel {
    @SerializedName("id_opendonasi")
    private Integer idDonasi;

    @SerializedName("nama_kegiatan")
    private String namaKegiatan;

    @SerializedName("file")
    private String file;

    @SerializedName("no_rekening")
    private Integer noRekening;

    @SerializedName("keterangan")
    private String keterangan;

    @SerializedName("lokasi")
    private String lokasi;

    @SerializedName("contact_person")
    private String contactPerson;

    @SerializedName("total_anggaran")
    private Integer totalAnggaran;

    public DonasiModel(String namaKegiatan, Integer totalAnggaran) {
        this.namaKegiatan = namaKegiatan;
        this.totalAnggaran = totalAnggaran;
    }

    public Integer getIdDonasi() {
        return idDonasi;
    }

    public String getNamaKegiatan() {
        return namaKegiatan;
    }

    public String getFile() {
        return file;
    }

    public Integer getNoRekening() {
        return noRekening;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public String getLokasi() {
        return lokasi;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public Integer getTotalAnggaran() {
        return totalAnggaran;
    }
}
