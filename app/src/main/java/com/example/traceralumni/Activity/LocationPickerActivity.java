package com.example.traceralumni.Activity;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.traceralumni.Fragment.MapsFragment;
import com.example.traceralumni.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

public class LocationPickerActivity extends AppCompatActivity {

    ConstraintLayout cl_iconBack, cl_iconConfirm;
    ImageView img_iconBack, img_iconConfirm;
    TextView tv_navBar;

    public static String LOKASI_EXTRA = "";
    public static final String LOKASI_EXTRA_KEY = "lokasi_extra_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_picker);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        cl_iconBack = findViewById(R.id.cl_icon1);
        cl_iconConfirm = findViewById(R.id.cl_icon4);
        img_iconBack = findViewById(R.id.img_icon1);
        img_iconConfirm = findViewById(R.id.img_icon4);
        tv_navBar = findViewById(R.id.tv_navbar_top);

        img_iconBack.setImageResource(R.drawable.ic_arrow_back);
        img_iconConfirm.setImageResource(R.drawable.ic_check);

        tv_navBar.setText("PILIH LOKASI");

        cl_iconBack.setVisibility(View.VISIBLE);
        cl_iconConfirm.setVisibility(View.VISIBLE);

        cl_iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        cl_iconConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendLocation();
            }
        });

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_maps_container, new MapsFragment())
                .commit();
    }

    public void sendLocation(){
        Intent intent = new Intent(LocationPickerActivity.this, SuntingProfilActivity.class);
        intent.putExtra(LOKASI_EXTRA_KEY, LOKASI_EXTRA);
        startActivity(intent);
    }
}
