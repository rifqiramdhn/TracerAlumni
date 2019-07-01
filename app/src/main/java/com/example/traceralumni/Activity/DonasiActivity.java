package com.example.traceralumni.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

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

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        donasiModels = new ArrayList<>();

        donasiModels.add(new DonasiModel("Pembangunan Kantin", "Excepteur" +
                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.Excepteur" +
                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.Excepteur" +
                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.", "500.000.000"));
        donasiModels.add(new DonasiModel("Pembangunan Gazebo", "Excepteur" +
                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.Excepteur" +
                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.Excepteur" +
                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.", "320.000.000"));
        donasiModels.add(new DonasiModel("Pembangunan Gedung", "Excepteur" +
                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.Excepteur" +
                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.Excepteur" +
                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.", "453.000.000"));
        donasiModels.add(new DonasiModel("Renovasi Kantin", "Excepteur" +
                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.Excepteur" +
                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.Excepteur" +
                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.", "2.500.000.000"));
        donasiModels.add(new DonasiModel("Renovasi Gazebo", "Excepteur" +
                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.Excepteur" +
                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.Excepteur" +
                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.", "6.500.000.000"));
        donasiModels.add(new DonasiModel("Renovasi Gedung", "Excepteur" +
                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.Excepteur" +
                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.Excepteur" +
                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.", "8.900.000.000"));
        donasiRecycler = findViewById(R.id.activity_donasi_recycler_view);

        //Mengatur LayoutManager dari Recycler daftar
        donasiRecycler.setLayoutManager(new LinearLayoutManager(DonasiActivity.this, LinearLayoutManager.VERTICAL, false));
        donasiAdapter = new DonasiAdapter(DonasiActivity.this, donasiModels);
        donasiRecycler.setAdapter(donasiAdapter);

    }
}
