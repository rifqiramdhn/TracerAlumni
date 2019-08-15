package com.example.traceralumni.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class PermintaanDonasiModel implements Parcelable {
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
    @SerializedName("id_daftardonatur")
    private Integer idDonatur;
    @SerializedName("id_opendonasi")
    private Integer idDonasi;
    @SerializedName("nim")
    private String nim;
    @SerializedName("status")
    private String status;
    @SerializedName("nama")
    private String namaDonatur;
    @SerializedName("bantuan")
    private Double bantuan;
    @SerializedName("nama_kegiatan")
    private String namaDonasi;
    private String status_data;
    private String tanggal_daftar_donatur;
    private Double total;

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
        status_data = in.readString();
        tanggal_daftar_donatur = in.readString();
        if (in.readByte() == 0) {
            total = null;
        } else {
            total = in.readDouble();
        }
    }

    public static Creator<PermintaanDonasiModel> getCREATOR() {
        return CREATOR;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (idDonatur == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(idDonatur);
        }
        if (idDonasi == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(idDonasi);
        }
        dest.writeString(nim);
        dest.writeString(status);
        dest.writeString(namaDonatur);
        if (bantuan == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(bantuan);
        }
        dest.writeString(namaDonasi);
        dest.writeString(status_data);
        dest.writeString(tanggal_daftar_donatur);
        if (total == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(total);
        }
    }

    @Override
    public int describeContents() {
        return 0;
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

    public String getStatus_data() {
        return status_data;
    }

    public String getTanggal_daftar_donatur() {
        return tanggal_daftar_donatur;
    }

    public Double getTotal() {
        return total;
    }
}
