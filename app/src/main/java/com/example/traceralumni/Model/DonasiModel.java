package com.example.traceralumni.Model;

public class DonasiModel {
    private String namaKegiatan, noRekening, keterangan, totalBiaya;
    private int fotoResId;

    public DonasiModel(String namaKegiatan, String noRekening, String keterangan, String totalBiaya, int fotoResId) {
        this.namaKegiatan = namaKegiatan;
        this.noRekening = noRekening;
        this.keterangan = keterangan;
        this.fotoResId = fotoResId;
        this.totalBiaya = totalBiaya;
    }

    public DonasiModel(String namaKegiatan, String keterangan, String totalBiaya) {
        this.namaKegiatan = namaKegiatan;
        this.keterangan = keterangan;
        this.totalBiaya = totalBiaya;
    }

    public String getNamaKegiatan() {
        return namaKegiatan;
    }

    public String getNoRekening() {
        return noRekening;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public int getFotoResId() {
        return fotoResId;
    }

    public String getTotalBiaya() {
        return totalBiaya;
    }
}
