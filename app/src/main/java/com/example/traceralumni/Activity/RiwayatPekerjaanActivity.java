package com.example.traceralumni.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.traceralumni.Adapter.RiwayatPekerjaanAdapter;
import com.example.traceralumni.Model.RiwayatPekerjaanModel;
import com.example.traceralumni.R;
import com.example.traceralumni.Activity.TambahRiwayatPekerjaanActivity;

import java.util.ArrayList;

public class RiwayatPekerjaanActivity extends AppCompatActivity {
    TextView tvNavBar;
    ConstraintLayout cl_iconBack, cl_iconTambah;
    ImageView img_iconBack, img_iconTambah;
    RecyclerView riwayatRecycler;
    RiwayatPekerjaanAdapter riwayatAdapter;
    ArrayList<RiwayatPekerjaanModel> riwayatModels;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_pekerjaan);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        tvNavBar = findViewById(R.id.tv_navbar_top);
        tvNavBar.setText("RIWAYAT PEKERJAAN");

        img_iconBack = findViewById(R.id.img_icon1);
        img_iconBack.setImageResource(R.drawable.ic_arrow_back);

        img_iconTambah = findViewById(R.id.img_icon4);
        img_iconTambah.setImageResource(R.drawable.ic_add);

        cl_iconBack = findViewById(R.id.cl_icon1);
        cl_iconBack.setVisibility(View.VISIBLE);

        cl_iconTambah = findViewById(R.id.cl_icon4);
        cl_iconTambah.setVisibility(View.VISIBLE);

        cl_iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        cl_iconTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RiwayatPekerjaanActivity.this, TambahRiwayatPekerjaanActivity.class);
                startActivity(i);
            }
        });

        riwayatModels = new ArrayList<>();
        riwayatModels.add(new RiwayatPekerjaanModel("Manager", "Akuntansi", "PT. Galon Jaya", "Palembang, Indonesia", 50000000, "2012", "2018" ));
        riwayatModels.add(new RiwayatPekerjaanModel("Manager", "Akuntansi", "PT. Galon Jaya", "Palembang, Indonesia", 50000000, "2012", "2018" ));
        riwayatModels.add(new RiwayatPekerjaanModel("Manager", "Akuntansi", "PT. Galon Jaya", "Palembang, Indonesia", 50000000, "2012", "2018" ));
        riwayatModels.add(new RiwayatPekerjaanModel("Manager", "Akuntansi", "PT. Galon Jaya", "Palembang, Indonesia", 50000000, "2012", "2018" ));
        riwayatModels.add(new RiwayatPekerjaanModel("Manager", "Akuntansi", "PT. Galon Jaya", "Palembang, Indonesia", 50000000, "2012", "2018" ));

        riwayatRecycler = findViewById(R.id.rv_riwayat_pekerjaan);
        riwayatRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        riwayatAdapter = new RiwayatPekerjaanAdapter(riwayatModels);
        riwayatRecycler.setAdapter(riwayatAdapter);
    }
}
