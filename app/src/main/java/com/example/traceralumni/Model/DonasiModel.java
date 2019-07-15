package com.example.traceralumni.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class DonasiModel implements Parcelable {

    @SerializedName("donasi_masuk")
    private Integer donasiMasuk;

    @SerializedName("id_opendonasi")
    private Integer idDonasi;

    @SerializedName("nama_kegiatan")
    private String namaKegiatan;

    @SerializedName("file")
    private String file;

    @SerializedName("no_rekening")
    private Integer noRekening;

    @SerializedName("keterangan")
    private String keterangan;

    @SerializedName("lokasi")
    private String lokasi;

    @SerializedName("contact_person")
    private String contactPerson;

    @SerializedName("total_anggaran")
    private Integer totalAnggaran;

    protected DonasiModel(Parcel in) {
        if (in.readByte() == 0) {
            donasiMasuk = null;
        } else {
            donasiMasuk = in.readInt();
        }
        if (in.readByte() == 0) {
            idDonasi = null;
        } else {
            idDonasi = in.readInt();
        }
        namaKegiatan = in.readString();
        file = in.readString();
        if (in.readByte() == 0) {
            noRekening = null;
        } else {
            noRekening = in.readInt();
        }
        keterangan = in.readString();
        lokasi = in.readString();
        contactPerson = in.readString();
        if (in.readByte() == 0) {
            totalAnggaran = null;
        } else {
            totalAnggaran = in.readInt();
        }
    }

    public static final Creator<DonasiModel> CREATOR = new Creator<DonasiModel>() {
        @Override
        public DonasiModel createFromParcel(Parcel in) {
            return new DonasiModel(in);
        }

        @Override
        public DonasiModel[] newArray(int size) {
            return new DonasiModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (donasiMasuk == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(donasiMasuk);
        }
        if (idDonasi == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(idDonasi);
        }
        parcel.writeString(namaKegiatan);
        parcel.writeString(file);
        if (noRekening == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(noRekening);
        }
        parcel.writeString(keterangan);
        parcel.writeString(lokasi);
        parcel.writeString(contactPerson);
        if (totalAnggaran == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(totalAnggaran);
        }
    }

    public Integer getDonasiMasuk() {
        return donasiMasuk;
    }

    public Integer getIdDonasi() {
        return idDonasi;
    }

    public String getNamaKegiatan() {
        return namaKegiatan;
    }

    public String getFile() {
        return file;
    }

    public Integer getNoRekening() {
        return noRekening;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public String getLokasi() {
        return lokasi;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public Integer getTotalAnggaran() {
        return totalAnggaran;
    }
}
