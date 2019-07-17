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

    private String tanggal_opendonasi;

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
        tanggal_opendonasi = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (donasiMasuk == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(donasiMasuk);
        }
        if (idDonasi == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(idDonasi);
        }
        dest.writeString(namaKegiatan);
        dest.writeString(file);
        if (noRekening == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(noRekening);
        }
        dest.writeString(keterangan);
        dest.writeString(lokasi);
        dest.writeString(contactPerson);
        if (totalAnggaran == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(totalAnggaran);
        }
        dest.writeString(tanggal_opendonasi);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public String getTanggal_opendonasi() {
        return tanggal_opendonasi;
    }

    public static Creator<DonasiModel> getCREATOR() {
        return CREATOR;
    }
}
