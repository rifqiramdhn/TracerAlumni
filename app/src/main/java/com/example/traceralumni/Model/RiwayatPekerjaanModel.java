package com.example.traceralumni.Model;

public class RiwayatPekerjaanModel {
    private String jabatan, bidangKerja, namaPerusahaan, lokasiKerja, thnMulai, thnSelesai, golongan;
    private int pendapatan;

    public RiwayatPekerjaanModel(String jabatan, String bidangKerja, String namaPerusahaan, String lokasiKerja, String thnMulai, String thnSelesai, String golongan, int pendapatan) {
        this.jabatan = jabatan;
        this.bidangKerja = bidangKerja;
        this.namaPerusahaan = namaPerusahaan;
        this.lokasiKerja = lokasiKerja;
        this.thnMulai = thnMulai;
        this.thnSelesai = thnSelesai;
        this.golongan = golongan;
        this.pendapatan = pendapatan;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }

    public String getBidangKerja() {
        return bidangKerja;
    }

    public void setBidangKerja(String bidangKerja) {
        this.bidangKerja = bidangKerja;
    }

    public String getNamaPerusahaan() {
        return namaPerusahaan;
    }

    public void setNamaPerusahaan(String namaPerusahaan) {
        this.namaPerusahaan = namaPerusahaan;
    }

    public String getLokasiKerja() {
        return lokasiKerja;
    }

    public void setLokasiKerja(String lokasiKerja) {
        this.lokasiKerja = lokasiKerja;
    }

    public String getThnMulai() {
        return thnMulai;
    }

    public void setThnMulai(String thnMulai) {
        this.thnMulai = thnMulai;
    }

    public String getThnSelesai() {
        return thnSelesai;
    }

    public void setThnSelesai(String thnSelesai) {
        this.thnSelesai = thnSelesai;
    }

    public String getGolongan() {
        return golongan;
    }

    public void setGolongan(String golongan) {
        this.golongan = golongan;
    }

    public int getPendapatan() {
        return pendapatan;
    }

    public void setPendapatan(int pendapatan) {
        this.pendapatan = pendapatan;
    }
}
