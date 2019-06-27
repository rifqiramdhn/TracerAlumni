package com.example.traceralumni.Model;

public class LowonganModel {
    private String username, url_logo, nama_perusahaan, nama_lowongan, lokasi_perusahaan, kisaran_gaji, syarat, jabatan, website, email, notelp, kontak;
    private int kuota;

    public LowonganModel(String username, String url_logo, String nama_perusahaan, String nama_lowongan, String lokasi_perusahaan, String kisaran_gaji, String syarat, String jabatan, String website, String email, String notelp, String kontak, int kuota) {
        this.username = username;
        this.url_logo = url_logo;
        this.nama_perusahaan = nama_perusahaan;
        this.nama_lowongan = nama_lowongan;
        this.lokasi_perusahaan = lokasi_perusahaan;
        this.kisaran_gaji = kisaran_gaji;
        this.syarat = syarat;
        this.jabatan = jabatan;
        this.website = website;
        this.email = email;
        this.notelp = notelp;
        this.kontak = kontak;
        this.kuota = kuota;
    }

    public LowonganModel(String nama_lowongan, String nama_perusahaan, String lokasi_perusahaan, String kisaran_gaji){
        this.nama_lowongan = nama_lowongan;
        this.nama_perusahaan = nama_perusahaan;
        this.lokasi_perusahaan = lokasi_perusahaan;
        this.kisaran_gaji = kisaran_gaji;
    }
    public String getUrl_logo() {
        return url_logo;
    }

    public void setUrl_logo(String url_logo) {
        this.url_logo = url_logo;
    }

    public String getNama_perusahaan() {
        return nama_perusahaan;
    }

    public void setNama_perusahaan(String nama_perusahaan) {
        this.nama_perusahaan = nama_perusahaan;
    }

    public String getNama_lowongan() {
        return nama_lowongan;
    }

    public void setNama_lowongan(String nama_lowongan) {
        this.nama_lowongan = nama_lowongan;
    }

    public String getLokasi_perusahaan() {
        return lokasi_perusahaan;
    }

    public void setLokasi_perusahaan(String lokasi_perusahaan) {
        this.lokasi_perusahaan = lokasi_perusahaan;
    }

    public String getKisaran_gaji() {
        return kisaran_gaji;
    }

    public void setKisaran_gaji(String kisaran_gaji) {
        this.kisaran_gaji = kisaran_gaji;
    }

    public String getSyarat() {
        return syarat;
    }

    public void setSyarat(String syarat) {
        this.syarat = syarat;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNotelp() {
        return notelp;
    }

    public void setNotelp(String notelp) {
        this.notelp = notelp;
    }

    public String getKontak() {
        return kontak;
    }

    public void setKontak(String kontak) {
        this.kontak = kontak;
    }

    public int getKuota() {
        return kuota;
    }

    public void setKuota(int kuota) {
        this.kuota = kuota;
    }
}
