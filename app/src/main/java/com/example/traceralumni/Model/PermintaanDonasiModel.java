package com.example.traceralumni.Model;

public class PermintaanDonasiModel {
    private String namaDonatur, detailDonasi;
    private int jumlahDonasi;

    public PermintaanDonasiModel(String namaDonatur, String detailDonasi, int jumlahDonasi) {
        this.namaDonatur = namaDonatur;
        this.detailDonasi = detailDonasi;
        this.jumlahDonasi = jumlahDonasi;
    }

    public String getNamaDonatur() {
        return namaDonatur;
    }

    public void setNamaDonatur(String namaDonatur) {
        this.namaDonatur = namaDonatur;
    }

    public String getDetailDonasi() {
        return detailDonasi;
    }

    public void setDetailDonasi(String detailDonasi) {
        this.detailDonasi = detailDonasi;
    }

    public int getJumlahDonasi() {
        return jumlahDonasi;
    }

    public void setJumlahDonasi(int jumlahDonasi) {
        this.jumlahDonasi = jumlahDonasi;
    }
}
