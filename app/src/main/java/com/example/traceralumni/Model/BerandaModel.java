package com.example.traceralumni.Model;

public class BerandaModel {
    private String jenis_data;

    private DonasiModel donasiModel;
    private InfoModel infoModel;
    private LowonganModel lowonganModel;

    public BerandaModel(String jenis_data, DonasiModel donasiModel) {
        this.jenis_data = jenis_data;
        this.donasiModel = donasiModel;
    }

    public BerandaModel(String jenis_data, InfoModel infoModel) {
        this.jenis_data = jenis_data;
        this.infoModel = infoModel;
    }

    public BerandaModel(String jenis_data, LowonganModel lowonganModel) {
        this.jenis_data = jenis_data;
        this.lowonganModel = lowonganModel;
    }

    public String getJenis_data() {
        return jenis_data;
    }

    public DonasiModel getDonasiModel() {
        return donasiModel;
    }

    public InfoModel getInfoModel() {
        return infoModel;
    }

    public LowonganModel getLowonganModel() {
        return lowonganModel;
    }
}
