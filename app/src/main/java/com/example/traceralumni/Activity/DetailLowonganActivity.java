package com.example.traceralumni.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.traceralumni.R;

public class DetailLowonganActivity extends AppCompatActivity {
    Button btn_profil;
    ConstraintLayout clBtnHubungi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_lowongan);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        btn_profil = findViewById(R.id.btn_lihat_profil);
        btn_profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetailLowonganActivity.this, DetailProfilActivity.class);
                startActivity(i);
            }
        });

        clBtnHubungi = findViewById(R.id.cl_btn_hubungi_lowongan);
        clBtnHubungi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DetailLowonganActivity.this, "hubungi ah", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
