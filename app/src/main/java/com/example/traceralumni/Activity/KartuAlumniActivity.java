package com.example.traceralumni.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.traceralumni.Model.DaftarModel;
import com.example.traceralumni.R;

public class KartuAlumniActivity extends AppCompatActivity {
    DaftarModel daftarModel;
    TextView tvNamaAlumni, tvNim, tvAngkatan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kartu_alumni);
        daftarModel = getIntent().getParcelableExtra("daftarModel");
        getView();
    }

    private void getView() {
        tvNamaAlumni = findViewById(R.id.tv_kartu_alumni_nama);
        tvNim = findViewById(R.id.tv_kartu_alumni_nim);
        tvAngkatan = findViewById(R.id.tv_kartu_alumni_angkatan);
        tvNamaAlumni.setText(daftarModel.getNama());
        tvNim.setText(daftarModel.getNim());
        tvAngkatan.setText(daftarModel.getAngkatan());
    }

}
