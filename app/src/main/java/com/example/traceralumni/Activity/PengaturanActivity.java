package com.example.traceralumni.Activity;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.traceralumni.Adapter.PengaturanAdapter;
import com.example.traceralumni.Model.PengaturanModel;
import com.example.traceralumni.R;

import java.util.ArrayList;

public class PengaturanActivity extends AppCompatActivity {
    ConstraintLayout cl_icon1, cl_icon2, cl_icon3, cl_icon4;
    ImageView imgIcon1;
    TextView tv_titleNavBar;

    RecyclerView pengaturanRecycler;
    PengaturanAdapter pengaturanAdapter;
    ArrayList<PengaturanModel> pengaturanModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengaturan);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        setNavBar();

        setRecyclerView();

    }

    private void setNavBar() {
        cl_icon1 = findViewById(R.id.cl_icon1);
        cl_icon2 = findViewById(R.id.cl_icon2);
        cl_icon3 = findViewById(R.id.cl_icon3);
        cl_icon4 = findViewById(R.id.cl_icon4);

        imgIcon1 = findViewById(R.id.img_icon1);

        tv_titleNavBar = findViewById(R.id.tv_navbar_top);

        cl_icon2.setVisibility(View.GONE);
        cl_icon3.setVisibility(View.GONE);
        cl_icon4.setVisibility(View.GONE);

        cl_icon1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PengaturanActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        imgIcon1.setImageResource(R.drawable.ic_arrow_back);

        tv_titleNavBar.setText("LAINNYA");
    }

    private void setRecyclerView() {
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
