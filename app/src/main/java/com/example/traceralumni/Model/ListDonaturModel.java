package com.example.traceralumni.Model;

public class ListDonaturModel {
    private String namaDonatur;
    private int jumlahDOnasi;

    public ListDonaturModel(String namaDonatur, int jumlahDonasi) {
        this.namaDonatur = namaDonatur;
        this.jumlahDOnasi = jumlahDonasi;
    }

    public String getNamaDonatur() {
        return namaDonatur;
    }

    public void setNamaDonatur(String namaDonatur) {
        this.namaDonatur = namaDonatur;
    }

    public int getJumlahDonasi() {
        return jumlahDOnasi;
    }

    public void setJumlahDonasi(int jumlahDOnasi) {
        this.jumlahDOnasi = jumlahDOnasi;
    }
}
