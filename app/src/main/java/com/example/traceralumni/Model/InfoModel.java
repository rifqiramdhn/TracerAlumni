package com.example.traceralumni.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class InfoModel implements Parcelable {

    @SerializedName("id_info")
    private Integer idInfo;

    @SerializedName("judul")
    private String judul;

    @SerializedName("keterangan")
    private String keterangan;

    @SerializedName("link")
    private String link;

    private Date tanggal;

    protected InfoModel(Parcel in) {
        if (in.readByte() == 0) {
            idInfo = null;
        } else {
            idInfo = in.readInt();
        }
        judul = in.readString();
        keterangan = in.readString();
        link = in.readString();
    }

    public static final Creator<InfoModel> CREATOR = new Creator<InfoModel>() {
        @Override
        public InfoModel createFromParcel(Parcel in) {
            return new InfoModel(in);
        }

        @Override
        public InfoModel[] newArray(int size) {
            return new InfoModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (idInfo == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(idInfo);
        }
        parcel.writeString(judul);
        parcel.writeString(keterangan);
        parcel.writeString(link);
    }

    public Integer getIdInfo() {
        return idInfo;
    }

    public String getJudul() {
        return judul;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public String getLink() {
        return link;
    }

    public Date getTanggal() {
        return tanggal;
    }
}
