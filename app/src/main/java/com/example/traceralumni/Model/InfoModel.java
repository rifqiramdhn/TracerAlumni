package com.example.traceralumni.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class InfoModel implements Parcelable {

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
    @SerializedName("id_info")
    private Integer idInfo;
    @SerializedName("judul")
    private String judul;
    @SerializedName("keterangan")
    private String keterangan;
    @SerializedName("link")
    private String link;
    private String tanggal_info;
    private String status_data;

    public InfoModel(Integer idInfo, String judul, String keterangan, String link, String tanggal_info) {
        this.idInfo = idInfo;
        this.judul = judul;
        this.keterangan = keterangan;
        this.link = link;
        this.tanggal_info = tanggal_info;
    }

    protected InfoModel(Parcel in) {
        if (in.readByte() == 0) {
            idInfo = null;
        } else {
            idInfo = in.readInt();
        }
        judul = in.readString();
        keterangan = in.readString();
        link = in.readString();
        tanggal_info = in.readString();
        status_data = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (idInfo == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(idInfo);
        }
        dest.writeString(judul);
        dest.writeString(keterangan);
        dest.writeString(link);
        dest.writeString(tanggal_info);
        dest.writeString(status_data);
    }

    public String getStatus_data() {
        return status_data;
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

    public String getTanggal_info() {
        return tanggal_info;
    }
}
