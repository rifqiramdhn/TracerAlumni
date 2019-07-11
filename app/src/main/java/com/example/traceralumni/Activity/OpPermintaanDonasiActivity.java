package com.example.traceralumni.Activity;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.traceralumni.Adapter.PermintaanDonasiAdapter;
import com.example.traceralumni.Model.PermintaanDonasiModel;
import com.example.traceralumni.R;

import java.util.ArrayList;

public class OpPermintaanDonasiActivity extends AppCompatActivity {

    ConstraintLayout cl_back;
    ImageView imgBack;
    TextView tv_titleNavBar;

    RecyclerView permintaanDonasiRecycler;
    PermintaanDonasiAdapter permintaanDonasiAdapter;
    ArrayList<PermintaanDonasiModel> permintaanDonasiModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permintaan_donasi);
        setIcon();

        permintaanDonasiModels = new ArrayList<>();
        permintaanDonasiModels.add(new PermintaanDonasiModel("Paidi Sugiono", "Pembangunan Kantin", 200000000));
        permintaanDonasiAdapter = new PermintaanDonasiAdapter(OpPermintaanDonasiActivity.this, permintaanDonasiModels);
        permintaanDonasiRecycler = findViewById(R.id.rv_permintaan_donasi);
        permintaanDonasiRecycler.setLayoutManager(new LinearLayoutManager(OpPermintaanDonasiActivity.this, LinearLayoutManager.VERTICAL, false));
        permintaanDonasiRecycler.setAdapter(permintaanDonasiAdapter);

    }

    private void setIcon() {
        cl_back = findViewById(R.id.cl_icon1);

        imgBack = findViewById(R.id.img_icon1);

        tv_titleNavBar = findViewById(R.id.tv_navbar_top);

        tv_titleNavBar.setText("PERMINTAAN DONASI");

        imgBack.setImageResource(R.drawable.ic_arrow_back);

        cl_back.setVisibility(View.VISIBLE);

        cl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
