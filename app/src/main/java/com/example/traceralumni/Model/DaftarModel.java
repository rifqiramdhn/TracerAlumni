package com.example.traceralumni.Model;

import java.util.Date;

public class DaftarModel {
    private String email, nim, nama, jenjang, angkatan, tahun_lulus, kewarganegaraan, alamat,
            kodePos, namaNegara, namaProvinsi, namaKota, nomorTelepon, nomorHp, jenisKelamin,
            tempatLahir, facebook, twitter, foto, status_bekerja;
    private int idJurusan, idProdi;
    private Date tanggalYudisium, tanggalLahir;

    public DaftarModel(String email, String nim, String nama, String jenjang, String angkatan,
                       String tahun_lulus, String kewarganegaraan, String alamat, String kodePos,
                       String namaNegara, String namaProvinsi, String namaKota, String nomorTelepon,
                       String nomorHp, String jenisKelamin, String tempatLahir, String facebook,
                       String twitter, String foto, String status_bekerja, int idJurusan,
                       int idProdi, Date tanggalYudisium, Date tanggalLahir) {
        this.email = email;
        this.nim = nim;
        this.nama = nama;
        this.jenjang = jenjang;
        this.angkatan = angkatan;
        this.tahun_lulus = tahun_lulus;
        this.kewarganegaraan = kewarganegaraan;
        this.alamat = alamat;
        this.kodePos = kodePos;
        this.namaNegara = namaNegara;
        this.namaProvinsi = namaProvinsi;
        this.namaKota = namaKota;
        this.nomorTelepon = nomorTelepon;
        this.nomorHp = nomorHp;
        this.jenisKelamin = jenisKelamin;
        this.tempatLahir = tempatLahir;
        this.facebook = facebook;
        this.twitter = twitter;
        this.foto = foto;
        this.status_bekerja = status_bekerja;
        this.idJurusan = idJurusan;
        this.idProdi = idProdi;
        this.tanggalYudisium = tanggalYudisium;
        this.tanggalLahir = tanggalLahir;
    }

    public DaftarModel(String nama, String angkatan, int idProdi) {
        this.nama = nama;
        this.angkatan = angkatan;
        this.idProdi = idProdi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJenjang() {
        return jenjang;
    }

    public void setJenjang(String jenjang) {
        this.jenjang = jenjang;
    }

    public String getAngkatan() {
        return angkatan;
    }

    public void setAngkatan(String angkatan) {
        this.angkatan = angkatan;
    }

    public String getTahun_lulus() {
        return tahun_lulus;
    }

    public void setTahun_lulus(String tahun_lulus) {
        this.tahun_lulus = tahun_lulus;
    }

    public String getKewarganegaraan() {
        return kewarganegaraan;
    }

    public void setKewarganegaraan(String kewarganegaraan) {
        this.kewarganegaraan = kewarganegaraan;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getKodePos() {
        return kodePos;
    }

    public void setKodePos(String kodePos) {
        this.kodePos = kodePos;
    }

    public String getNamaNegara() {
        return namaNegara;
    }

    public void setNamaNegara(String namaNegara) {
        this.namaNegara = namaNegara;
    }

    public String getNamaProvinsi() {
        return namaProvinsi;
    }

    public void setNamaProvinsi(String namaProvinsi) {
        this.namaProvinsi = namaProvinsi;
    }

    public String getNamaKota() {
        return namaKota;
    }

    public void setNamaKota(String namaKota) {
        this.namaKota = namaKota;
    }

    public String getNomorTelepon() {
        return nomorTelepon;
    }

    public void setNomorTelepon(String nomorTelepon) {
        this.nomorTelepon = nomorTelepon;
    }

    public String getNomorHp() {
        return nomorHp;
    }

    public void setNomorHp(String nomorHp) {
        this.nomorHp = nomorHp;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getTempatLahir() {
        return tempatLahir;
    }

    public void setTempatLahir(String tempatLahir) {
        this.tempatLahir = tempatLahir;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getStatus_bekerja() {
        return status_bekerja;
    }

    public void setStatus_bekerja(String status_bekerja) {
        this.status_bekerja = status_bekerja;
    }

    public int getIdJurusan() {
        return idJurusan;
    }

    public void setIdJurusan(int idJurusan) {
        this.idJurusan = idJurusan;
    }

    public int getIdProdi() {
        return idProdi;
    }

    public void setIdProdi(int idProdi) {
        this.idProdi = idProdi;
    }

    public Date getTanggalYudisium() {
        return tanggalYudisium;
    }

    public void setTanggalYudisium(Date tanggalYudisium) {
        this.tanggalYudisium = tanggalYudisium;
    }

    public Date getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(Date tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }
}
