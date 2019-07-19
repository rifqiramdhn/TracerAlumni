package com.example.traceralumni.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class RiwayatPekerjaanModel implements Parcelable{
    @SerializedName("id_riwayat_pekerjaan")
    Integer idRiwayat;

    @SerializedName("nim")
    String nim;

    @SerializedName("nama_riwayat")
    String pekerjaan;

    @SerializedName("lokasi_riwayat")
    String lokasi;

    @SerializedName("perusahaan_riwayat")
    String namaPerusahaan;

    @SerializedName("gaji_riwayat")
    String gaji;

    @SerializedName("tahun_awal_riwayat")
    String tahunAwal;

    @SerializedName("tahun_akhir_riwayat")
    String tahunAkhir;

    String status_data;

    protected RiwayatPekerjaanModel(Parcel in) {
        if (in.readByte() == 0) {
            idRiwayat = null;
        } else {
            idRiwayat = in.readInt();
        }
        nim = in.readString();
        pekerjaan = in.readString();
        lokasi = in.readString();
        namaPerusahaan = in.readString();
        gaji = in.readString();
        tahunAwal = in.readString();
        tahunAkhir = in.readString();
        status_data = in.readString();
    }

    public static final Parcelable.Creator<RiwayatPekerjaanModel> CREATOR = new Parcelable.Creator<RiwayatPekerjaanModel>() {
        @Override
        public RiwayatPekerjaanModel createFromParcel(Parcel in) {
            return new RiwayatPekerjaanModel(in);
        }

        @Override
        public RiwayatPekerjaanModel[] newArray(int size) {
            return new RiwayatPekerjaanModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (idRiwayat == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(idRiwayat);
        }
        parcel.writeString(nim);
        parcel.writeString(pekerjaan);
        parcel.writeString(lokasi);
        parcel.writeString(namaPerusahaan);
        parcel.writeString(gaji);
        parcel.writeString(tahunAwal);
        parcel.writeString(tahunAkhir);
        parcel.writeString(status_data);
    }

    public String getStatus_data() {
        return status_data;
    }

    public Integer getIdRiwayat() {
        return idRiwayat;
    }

    public String getNim() {
        return nim;
    }

    public String getPekerjaan() {
        return pekerjaan;
    }

    public String getLokasi() {
        return lokasi;
    }

    public String getNamaPerusahaan() {
        return namaPerusahaan;
    }

    public String getGaji() {
        return gaji;
    }

    public String getTahunAwal() {
        return tahunAwal;
    }

    public String getTahunAkhir() {
        return tahunAkhir;
    }
}
