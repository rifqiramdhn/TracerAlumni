package com.example.traceralumni.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class PermintaanDonasiModel implements Parcelable {
    @SerializedName("id_daftardonatur")
    Integer idDonatur;

    @SerializedName("id_opendonasi")
    Integer idDonasi;

    @SerializedName("nim")
    String nim;

    @SerializedName("status")
    String status;

    @SerializedName("nama")
    String namaDonatur;

    @SerializedName("bantuan")
    Double bantuan;

    @SerializedName("nama_kegiatan")
    String namaDonasi;

    protected PermintaanDonasiModel(Parcel in) {
        if (in.readByte() == 0) {
            idDonatur = null;
        } else {
            idDonatur = in.readInt();
        }
        if (in.readByte() == 0) {
            idDonasi = null;
        } else {
            idDonasi = in.readInt();
        }
        nim = in.readString();
        status = in.readString();
        namaDonatur = in.readString();
        if (in.readByte() == 0) {
            bantuan = null;
        } else {
            bantuan = in.readDouble();
        }
        namaDonasi = in.readString();
    }

    public static final Creator<PermintaanDonasiModel> CREATOR = new Creator<PermintaanDonasiModel>() {
        @Override
        public PermintaanDonasiModel createFromParcel(Parcel in) {
            return new PermintaanDonasiModel(in);
        }

        @Override
        public PermintaanDonasiModel[] newArray(int size) {
            return new PermintaanDonasiModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (idDonatur == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(idDonatur);
        }
        if (idDonasi == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(idDonasi);
        }
        parcel.writeString(nim);
        parcel.writeString(status);
        parcel.writeString(namaDonatur);
        if (bantuan == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(bantuan);
        }
        parcel.writeString(namaDonasi);
    }

    public Integer getIdDonatur() {
        return idDonatur;
    }

    public Integer getIdDonasi() {
        return idDonasi;
    }

    public String getNim() {
        return nim;
    }

    public String getStatus() {
        return status;
    }

    public String getNamaDonatur() {
        return namaDonatur;
    }

    public Double getBantuan() {
        return bantuan;
    }

    public String getNamaDonasi() {
        return namaDonasi;
    }
}
