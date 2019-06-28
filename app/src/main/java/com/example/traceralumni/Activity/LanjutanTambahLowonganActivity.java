package com.example.traceralumni.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.traceralumni.R;

public class LanjutanTambahLowonganActivity extends AppCompatActivity {
    private ConstraintLayout cl_icon_back, cl_icon_ok;
    private ImageView img_icon_back, img_icon_ok;
    private TextView tv_navbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_lowongan);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        cl_icon_back = findViewById(R.id.cl_icon1);
        img_icon_back = findViewById(R.id.img_icon1);
        cl_icon_ok = findViewById(R.id.cl_icon4);
        img_icon_ok = findViewById(R.id.img_icon4);
        tv_navbar = findViewById(R.id.tv_navbar_top);

        img_icon_back.setImageResource(R.drawable.ic_arrow_back);
        img_icon_ok.setImageResource(R.drawable.ic_check);

        tv_navbar.setText("TAMBAH LOWONGAN");

        cl_icon_back.setVisibility(View.VISIBLE);
        cl_icon_ok.setVisibility(View.VISIBLE);

        cl_icon_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        cl_icon_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
