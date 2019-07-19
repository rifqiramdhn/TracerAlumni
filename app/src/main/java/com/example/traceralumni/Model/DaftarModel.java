package com.example.traceralumni.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class DaftarModel implements Parcelable {
    private String username;
    private String email;
    private String nim;
    private String nama;
    private String jenjang;
    private String jurusan;
    private String prodi;
    private String angkatan;
    private String tahun_lulus;
    private String kewarganegaraan;
    private String alamat;
    private String kode_pos;
    private String nama_negara;
    private String nama_provinsi;
    private String nama_kota;
    private String nomor_telepon;
    private String nomor_hp;
    private String jenis_kelamin;
    private String tempat_lahir;
    private String facebook;
    private String twitter;
    private String foto;
    private String status_bekerja;
    private String status_alumni;

    private String jenis_user;
    private String status_data;

    private String tanggal_yudisium;
    private String tanggal_lahir;

    private String jumlah;
    private String password;


    protected DaftarModel(Parcel in) {
        username = in.readString();
        email = in.readString();
        nim = in.readString();
        nama = in.readString();
        jenjang = in.readString();
        jurusan = in.readString();
        prodi = in.readString();
        angkatan = in.readString();
        tahun_lulus = in.readString();
        kewarganegaraan = in.readString();
        alamat = in.readString();
        kode_pos = in.readString();
        nama_negara = in.readString();
        nama_provinsi = in.readString();
        nama_kota = in.readString();
        nomor_telepon = in.readString();
        nomor_hp = in.readString();
        jenis_kelamin = in.readString();
        tempat_lahir = in.readString();
        facebook = in.readString();
        twitter = in.readString();
        foto = in.readString();
        status_bekerja = in.readString();
        status_alumni = in.readString();
        jenis_user = in.readString();
        status_data = in.readString();
        tanggal_yudisium = in.readString();
        tanggal_lahir = in.readString();
        jumlah = in.readString();
        password = in.readString();
    }

    public static final Creator<DaftarModel> CREATOR = new Creator<DaftarModel>() {
        @Override
        public DaftarModel createFromParcel(Parcel in) {
            return new DaftarModel(in);
        }

        @Override
        public DaftarModel[] newArray(int size) {
            return new DaftarModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(email);
        dest.writeString(nim);
        dest.writeString(nama);
        dest.writeString(jenjang);
        dest.writeString(jurusan);
        dest.writeString(prodi);
        dest.writeString(angkatan);
        dest.writeString(tahun_lulus);
        dest.writeString(kewarganegaraan);
        dest.writeString(alamat);
        dest.writeString(kode_pos);
        dest.writeString(nama_negara);
        dest.writeString(nama_provinsi);
        dest.writeString(nama_kota);
        dest.writeString(nomor_telepon);
        dest.writeString(nomor_hp);
        dest.writeString(jenis_kelamin);
        dest.writeString(tempat_lahir);
        dest.writeString(facebook);
        dest.writeString(twitter);
        dest.writeString(foto);
        dest.writeString(status_bekerja);
        dest.writeString(status_alumni);
        dest.writeString(jenis_user);
        dest.writeString(status_data);
        dest.writeString(tanggal_yudisium);
        dest.writeString(tanggal_lahir);
        dest.writeString(jumlah);
        dest.writeString(password);
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getNim() {
        return nim;
    }

    public String getNama() {
        return nama;
    }

    public String getJenjang() {
        return jenjang;
    }

    public String getJurusan() {
        return jurusan;
    }

    public String getProdi() {
        return prodi;
    }

    public String getAngkatan() {
        return angkatan;
    }

    public String getTahun_lulus() {
        return tahun_lulus;
    }

    public String getKewarganegaraan() {
        return kewarganegaraan;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getKode_pos() {
        return kode_pos;
    }

    public String getNama_negara() {
        return nama_negara;
    }

    public String getNama_provinsi() {
        return nama_provinsi;
    }

    public String getNama_kota() {
        return nama_kota;
    }

    public String getNomor_telepon() {
        return nomor_telepon;
    }

    public String getNomor_hp() {
        return nomor_hp;
    }

    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public String getTempat_lahir() {
        return tempat_lahir;
    }

    public String getFacebook() {
        return facebook;
    }

    public String getTwitter() {
        return twitter;
    }

    public String getFoto() {
        return foto;
    }

    public String getStatus_bekerja() {
        return status_bekerja;
    }

    public String getStatus_alumni() {
        return status_alumni;
    }

    public String getJenis_user() {
        return jenis_user;
    }

    public String getStatus_data() {
        return status_data;
    }

    public String getTanggal_yudisium() {
        return tanggal_yudisium;
    }

    public String getTanggal_lahir() {
        return tanggal_lahir;
    }

    public String getJumlah() {
        return jumlah;
    }

    public String getPassword() {
        return password;
    }

    public static Creator<DaftarModel> getCREATOR() {
        return CREATOR;
    }
}
