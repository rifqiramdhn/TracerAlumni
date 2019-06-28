package com.example.traceralumni.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.traceralumni.Adapter.DaftarAdapter;
import com.example.traceralumni.Adapter.DonasiAdapter;
import com.example.traceralumni.Model.DaftarModel;
import com.example.traceralumni.Model.DonasiModel;
import com.example.traceralumni.R;

import java.util.ArrayList;

public class DonasiActivity extends AppCompatActivity {

    RecyclerView donasiRecycler;
    DonasiAdapter donasiAdapter;
    ArrayList<DonasiModel> donasiModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donasi);
        donasiModels = new ArrayList<>();

        donasiModels.add(new DonasiModel("Pembangunan Kantin", "500000000"));
        donasiModels.add(new DonasiModel("Pembangunan Gedung", "460000000"));
        donasiModels.add(new DonasiModel("Pembangunan Gazebo", "57500000000"));
        donasiModels.add(new DonasiModel("Pembangunan Gedung Pengajaran", "54300000000"));
        donasiModels.add(new DonasiModel("Pembangunan Kantin", "500000000"));
        donasiModels.add(new DonasiModel("Pembangunan Gedung", "460000000"));
        donasiModels.add(new DonasiModel("Pembangunan Gazebo", "57500000000"));
        donasiModels.add(new DonasiModel("Pembangunan Gedung Pengajaran", "54300000000"));
        donasiModels.add(new DonasiModel("Pembangunan Kantin", "500000000"));
        donasiModels.add(new DonasiModel("Pembangunan Gedung", "460000000"));
        donasiModels.add(new DonasiModel("Pembangunan Gazebo", "57500000000"));
        donasiModels.add(new DonasiModel("Pembangunan Gedung Pengajaran", "54300000000"));
        donasiModels.add(new DonasiModel("Pembangunan Kantin", "500000000"));
        donasiModels.add(new DonasiModel("Pembangunan Gedung", "460000000"));
        donasiModels.add(new DonasiModel("Pembangunan Gazebo", "57500000000"));
        donasiModels.add(new DonasiModel("Pembangunan Gedung Pengajaran", "54300000000"));

        donasiRecycler = findViewById(R.id.activity_donasi_recycler_view);

        //Mengatur LayoutManager dari Recycler daftar
        donasiRecycler.setLayoutManager(new LinearLayoutManager(DonasiActivity.this, LinearLayoutManager.VERTICAL, false));
        donasiAdapter = new DonasiAdapter(DonasiActivity.this, donasiModels);
        donasiRecycler.setAdapter(donasiAdapter);

    }
}
