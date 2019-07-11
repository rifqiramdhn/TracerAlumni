package com.example.traceralumni.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.traceralumni.Model.LowonganModel;
import com.example.traceralumni.R;

public class DetailLowonganActivity extends AppCompatActivity {
    Button btn_profil;
    ConstraintLayout clBtnHubungi;

    private TextView tvNamaLowongan, tvNamaPerusahaan, tvLokasi, tvKisaranGaji, tvProfil
            , tvSyarat, tvKuota, tvJabatan, tvWeb, tvEmail, tvTelepon, tvHubungi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_lowongan);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        btn_profil = findViewById(R.id.btn_lihat_profil);
//        btn_profil.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(DetailLowonganActivity.this, DetailProfilActivity.class);
//                startActivity(i);
//            }
//        });

        clBtnHubungi = findViewById(R.id.cl_btn_hubungi_lowongan);
        clBtnHubungi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DetailLowonganActivity.this, "hubungi ah", Toast.LENGTH_SHORT).show();
            }
        });

        initView();

        getData();
    }

    private void getData(){
        Intent intent = getIntent();
        LowonganModel lowonganModel = intent.getParcelableExtra("object_lowongan");

        tvNamaLowongan.setText(lowonganModel.getNama_lowongan());
        tvNamaPerusahaan.setText(lowonganModel.getNama_perusahaan());
        tvLokasi.setText(lowonganModel.getAlamat_perusahaan());
        tvKisaranGaji.setText("~Rp " + lowonganModel.getKisaran_gaji());
//        tvProfil.setText(lowonganModel.);
        tvSyarat.setText(lowonganModel.getSyarat_pekerjaan());
        tvKuota.setText(lowonganModel.getKuota() + " orang");
        tvJabatan.setText(lowonganModel.getJabatan());
        tvWeb.setText(lowonganModel.getWebsite());
        tvEmail.setText(lowonganModel.getEmail());
        tvTelepon.setText(lowonganModel.getNo_telp());
        tvHubungi.setText(lowonganModel.getCp());
    }

    private void initView(){
        tvNamaLowongan = findViewById(R.id.tv_nama_lowongan);
        tvNamaPerusahaan = findViewById(R.id.tv_nama_perusahaan);
        tvLokasi = findViewById(R.id.tv_lokasi_perusahaan);
        tvKisaranGaji = findViewById(R.id.tv_kisaran_gaji);
        tvProfil = findViewById(R.id.tv_profil);
        tvSyarat = findViewById(R.id.tv_syarat);
        tvKuota = findViewById(R.id.tv_kuota);
        tvJabatan = findViewById(R.id.tv_jabatan);
        tvWeb = findViewById(R.id.tv_web);
        tvEmail = findViewById(R.id.tv_email);
        tvTelepon = findViewById(R.id.tv_telpon);
        tvHubungi = findViewById(R.id.txt_hubungi);
    }
}
