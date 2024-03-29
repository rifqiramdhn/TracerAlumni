package com.example.traceralumni.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class LowonganModel implements Parcelable {
    public static final Creator<LowonganModel> CREATOR = new Creator<LowonganModel>() {
        @Override
        public LowonganModel createFromParcel(Parcel in) {
            return new LowonganModel(in);
        }

        @Override
        public LowonganModel[] newArray(int size) {
            return new LowonganModel[size];
        }
    };
    @SerializedName("id_lowongan")
    private Integer idLowongan;
    @SerializedName("username")
    private String username;
    @SerializedName("nama_lowongan")
    private String nama_lowongan;
    @SerializedName("nama_perusahaan")
    private String nama_perusahaan;
    @SerializedName("alamat_perusahaan")
    private String alamat_perusahaan;
    @SerializedName("kisaran_gaji")
    private String kisaran_gaji;
    @SerializedName("syarat_pekerjaan")
    private String syarat_pekerjaan;
    @SerializedName("pelamar_yang_dibutuhkan")
    private Integer kuota;
    @SerializedName("lowongan_jabatan")
    private String jabatan;
    @SerializedName("website_perusahaan")
    private String website;
    @SerializedName("email_perusahaan")
    private String email;
    @SerializedName("no_telp_perusahaan")
    private String no_telp;
    @SerializedName("contact_person")
    private String cp;
    @SerializedName("status")
    private String status_lowongan;
    @SerializedName("logo_perusahaan")
    private String logo_perusahaan;
    private String tanggal_lowongan;
    private String status_data;

    public LowonganModel(Integer idLowongan, String username, String nama_lowongan, String nama_perusahaan, String alamat_perusahaan, String kisaran_gaji, String syarat_pekerjaan, Integer kuota, String jabatan, String website, String email, String no_telp, String cp, String status_lowongan, String url_logo, String tanggal_lowongan) {
        this.idLowongan = idLowongan;
        this.username = username;
        this.nama_lowongan = nama_lowongan;
        this.nama_perusahaan = nama_perusahaan;
        this.alamat_perusahaan = alamat_perusahaan;
        this.kisaran_gaji = kisaran_gaji;
        this.syarat_pekerjaan = syarat_pekerjaan;
        this.kuota = kuota;
        this.jabatan = jabatan;
        this.website = website;
        this.email = email;
        this.no_telp = no_telp;
        this.cp = cp;
        this.status_lowongan = status_lowongan;
        this.logo_perusahaan = url_logo;
        this.tanggal_lowongan = tanggal_lowongan;
    }

    protected LowonganModel(Parcel in) {
        if (in.readByte() == 0) {
            idLowongan = null;
        } else {
            idLowongan = in.readInt();
        }
        username = in.readString();
        nama_lowongan = in.readString();
        nama_perusahaan = in.readString();
        alamat_perusahaan = in.readString();
        kisaran_gaji = in.readString();
        syarat_pekerjaan = in.readString();
        if (in.readByte() == 0) {
            kuota = null;
        } else {
            kuota = in.readInt();
        }
        jabatan = in.readString();
        website = in.readString();
        email = in.readString();
        no_telp = in.readString();
        cp = in.readString();
        status_lowongan = in.readString();
        logo_perusahaan = in.readString();
        tanggal_lowongan = in.readString();
        status_data = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (idLowongan == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(idLowongan);
        }
        parcel.writeString(username);
        parcel.writeString(nama_lowongan);
        parcel.writeString(nama_perusahaan);
        parcel.writeString(alamat_perusahaan);
        parcel.writeString(kisaran_gaji);
        parcel.writeString(syarat_pekerjaan);
        if (kuota == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(kuota);
        }
        parcel.writeString(jabatan);
        parcel.writeString(website);
        parcel.writeString(email);
        parcel.writeString(no_telp);
        parcel.writeString(cp);
        parcel.writeString(status_lowongan);
        parcel.writeString(logo_perusahaan);
        parcel.writeString(tanggal_lowongan);
        parcel.writeString(status_data);
    }

    public String getStatus_data() {
        return status_data;
    }

    public Integer getIdLowongan() {
        return idLowongan;
    }

    public String getUsername() {
        return username;
    }

    public String getNama_lowongan() {
        return nama_lowongan;
    }

    public String getNama_perusahaan() {
        return nama_perusahaan;
    }

    public String getAlamat_perusahaan() {
        return alamat_perusahaan;
    }

    public String getKisaran_gaji() {
        return kisaran_gaji;
    }

    public String getSyarat_pekerjaan() {
        return syarat_pekerjaan;
    }

    public Integer getKuota() {
        return kuota;
    }

    public String getJabatan() {
        return jabatan;
    }

    public String getWebsite() {
        return website;
    }

    public String getEmail() {
        return email;
    }

    public String getNo_telp() {
        return no_telp;
    }

    public String getCp() {
        return cp;
    }

    public String getTanggal_lowongan() {
        return tanggal_lowongan;
    }

    public String getStatus_lowongan() {
        return status_lowongan;
    }

    public String getLogo_perusahaan() {
        return logo_perusahaan;
    }
}
