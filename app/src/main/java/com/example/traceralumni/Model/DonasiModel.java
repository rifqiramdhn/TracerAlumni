package com.example.traceralumni.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class DonasiModel implements Parcelable {

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
    private Double totalAnggaran;

    private String tanggal_opendonasi;

    private String status_data;

    public DonasiModel( Integer idDonasi, String namaKegiatan, String file, Integer noRekening, String keterangan, String lokasi, String contactPerson, Double totalAnggaran, String tanggal_opendonasi) {
        this.idDonasi = idDonasi;
        this.namaKegiatan = namaKegiatan;
        this.file = file;
        this.noRekening = noRekening;
        this.keterangan = keterangan;
        this.lokasi = lokasi;
        this.contactPerson = contactPerson;
        this.totalAnggaran = totalAnggaran;
        this.tanggal_opendonasi = tanggal_opendonasi;
    }

    protected DonasiModel(Parcel in) {
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
            totalAnggaran = in.readDouble();
        }
        tanggal_opendonasi = in.readString();
        status_data = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
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
            dest.writeDouble(totalAnggaran);
        }
        dest.writeString(tanggal_opendonasi);
        dest.writeString(status_data);
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

    public String getStatus_data() {
        return status_data;
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

    public Double getTotalAnggaran() {
        return totalAnggaran;
    }

    public String getTanggal_opendonasi() {
        return tanggal_opendonasi;
    }
}
