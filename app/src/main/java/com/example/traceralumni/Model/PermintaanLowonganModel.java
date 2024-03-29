package com.example.traceralumni.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class PermintaanLowonganModel implements Parcelable {
    public static final Creator<PermintaanLowonganModel> CREATOR = new Creator<PermintaanLowonganModel>() {
        @Override
        public PermintaanLowonganModel createFromParcel(Parcel in) {
            return new PermintaanLowonganModel(in);
        }

        @Override
        public PermintaanLowonganModel[] newArray(int size) {
            return new PermintaanLowonganModel[size];
        }
    };
    @SerializedName("id_lowongan")
    private Integer idLowongan;
    @SerializedName("username")
    private String username;
    @SerializedName("nama")
    private String nama;
    @SerializedName("nama_lowongan")
    private String namaLowongan;
    @SerializedName("nama_perusahaan")
    private String namaPerusahaan;
    @SerializedName("alamat_perusahaan")
    private String alamatPerusahaan;
    @SerializedName("kisaran_gaji")
    private String kisaranGaji;
    @SerializedName("logo_perusahaan")
    private String logoPerusahaan;
    private String tanggal_lowongan;
    private String status_data;

    protected PermintaanLowonganModel(Parcel in) {
        if (in.readByte() == 0) {
            idLowongan = null;
        } else {
            idLowongan = in.readInt();
        }
        nama = in.readString();
        namaLowongan = in.readString();
        namaPerusahaan = in.readString();
        alamatPerusahaan = in.readString();
        kisaranGaji = in.readString();
        tanggal_lowongan = in.readString();
        logoPerusahaan = in.readString();
        status_data = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        if (idLowongan == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(idLowongan);
        }

        parcel.writeString(nama);
        parcel.writeString(namaLowongan);
        parcel.writeString(namaPerusahaan);
        parcel.writeString(alamatPerusahaan);
        parcel.writeString(kisaranGaji);
        parcel.writeString(tanggal_lowongan);
        parcel.writeString(logoPerusahaan);
        parcel.writeString(status_data);
    }

    public Integer getIdLowongan() {
        return idLowongan;
    }

    public String getUsername() {
        return username;
    }

    public String getNama() {
        return nama;
    }

    public String getNamaLowongan() {
        return namaLowongan;
    }

    public String getNamaPerusahaan() {
        return namaPerusahaan;
    }

    public String getAlamatPerusahaan() {
        return alamatPerusahaan;
    }

    public String getKisaranGaji() {
        return kisaranGaji;
    }

    public String getLogoPerusahaan() {
        return logoPerusahaan;
    }

    public String getTanggal_lowongan() {
        return tanggal_lowongan;
    }

    public String getStatus_data() {
        return status_data;
    }
}
