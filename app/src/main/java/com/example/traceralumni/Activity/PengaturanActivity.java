package com.example.traceralumni.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.traceralumni.Adapter.PengaturanAdapter;
import com.example.traceralumni.Model.PengaturanModel;
import com.example.traceralumni.R;

import java.util.ArrayList;

public class PengaturanActivity extends AppCompatActivity {
    RecyclerView pengaturanRecycler;
    PengaturanAdapter pengaturanAdapter;
    ArrayList<PengaturanModel> pengaturanModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengaturan);

        pengaturanModels = new ArrayList<>();
        pengaturanModels.add(new PengaturanModel("Kartu Identitas"));
        pengaturanModels.add(new PengaturanModel("Legalisir Ijazah"));
        pengaturanModels.add(new PengaturanModel("Tracer Studi"));
        pengaturanModels.add(new PengaturanModel("Sunting Profil"));
        pengaturanModels.add(new PengaturanModel("Riwayat Pekerjaaan"));
        pengaturanModels.add(new PengaturanModel("Ganti Kata Sandi"));
        pengaturanModels.add(new PengaturanModel("Hapus Semua Chat"));
        pengaturanModels.add(new PengaturanModel("Tentang"));
        pengaturanModels.add(new PengaturanModel("Keluar"));

        pengaturanRecycler = findViewById(R.id.pengaturan_recycler_view);

        //Mengatur LayoutManager dari Recycler pengaturan
        pengaturanRecycler.setLayoutManager(new LinearLayoutManager(PengaturanActivity.this, LinearLayoutManager.VERTICAL, false));

        pengaturanAdapter = new PengaturanAdapter(PengaturanActivity.this, pengaturanModels);
        pengaturanRecycler.setAdapter(pengaturanAdapter);


    }
}
