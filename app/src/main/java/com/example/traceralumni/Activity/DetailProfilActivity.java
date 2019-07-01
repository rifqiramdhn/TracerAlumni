package com.example.traceralumni.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.traceralumni.Adapter.RiwayatPekerjaanAdapter;
import com.example.traceralumni.Model.RiwayatPekerjaanModel;
import com.example.traceralumni.R;

import java.util.ArrayList;

public class DetailProfilActivity extends AppCompatActivity {
    RecyclerView riwayatRecycler;
    RiwayatPekerjaanAdapter riwayatAdapter;
    ArrayList<RiwayatPekerjaanModel> riwayatModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_profil);

        riwayatModel = new ArrayList<>();
        riwayatModel.add(new RiwayatPekerjaanModel("Manager", "Akuntansi", "PT. Galon Jaya", "Palembang, Indonesia", 50000000, "2012", "2018" ));
        riwayatModel.add(new RiwayatPekerjaanModel("Manager", "Akuntansi", "PT. Galon Jaya", "Palembang, Indonesia", 50000000, "2012", "2018" ));
        riwayatModel.add(new RiwayatPekerjaanModel("Manager", "Akuntansi", "PT. Galon Jaya", "Palembang, Indonesia", 50000000, "2012", "2018" ));
        riwayatModel.add(new RiwayatPekerjaanModel("Manager", "Akuntansi", "PT. Galon Jaya", "Palembang, Indonesia", 50000000, "2012", "2018" ));
        riwayatModel.add(new RiwayatPekerjaanModel("Manager", "Akuntansi", "PT. Galon Jaya", "Palembang, Indonesia", 50000000, "2012", "2018" ));

        riwayatRecycler = findViewById(R.id.rv_riwayat_pekerjaan);
        riwayatRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        riwayatAdapter = new RiwayatPekerjaanAdapter(riwayatModel);
        riwayatRecycler.setAdapter(riwayatAdapter);
    }
}
