package com.example.traceralumni.Activity;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.traceralumni.R;

public class DetailDonasiActivity extends AppCompatActivity {

    ConstraintLayout cl_back;
    ImageView imgBack, foto;
    TextView tv_titleNavBar, tv_namaKegiatan, tv_totalBiaya, tv_keterangan, tv_totalDonasi, tv_jumlahDonasi;
    Button btn_donasi;

    Intent intent;

    String namaKegiatan, totalBiaya, keterangan, jumlahDonasiMasuk, fotoResId;

    public static String JENIS_USER;
    public static final String JENIS_USER_ALUMNI = "jenis_user_alumni";
    public static final String JENIS_USER_PIMPINAN = "jenis_user_pimpinan";
    public static final String JENIS_USER_OPERATOR = "jenis_user_operator";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_donasi);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        JENIS_USER = JENIS_USER_ALUMNI;

        setIcon();

        setBackButton(JENIS_USER);

        getView();

        getData();

        setView(JENIS_USER);
    }

    private void setIcon() {
        cl_back = findViewById(R.id.cl_icon1);

        imgBack = findViewById(R.id.img_icon1);

        tv_titleNavBar = findViewById(R.id.tv_navbar_top);

        tv_titleNavBar.setText("DETAIL DONASI");

        imgBack.setImageResource(R.drawable.ic_arrow_back);

        cl_back.setVisibility(View.VISIBLE);
    }

    private void getData() {
        intent = getIntent();
        fotoResId = intent.getStringExtra("fotoResId");
        namaKegiatan = intent.getStringExtra("namaKegiatan");
        totalBiaya = intent.getStringExtra("totalBiaya");
        keterangan = intent.getStringExtra("keterangan");
    }

    private void setView(String jenisUser) {
        foto.setImageResource(Integer.valueOf(fotoResId));
        tv_namaKegiatan.setText(namaKegiatan);
        tv_totalBiaya.setText("Rp" + totalBiaya);
        tv_keterangan.setText(keterangan);
        if (jenisUser.equalsIgnoreCase(JENIS_USER_ALUMNI)) {
            btn_donasi.setText("donasi");
            tv_totalDonasi.setVisibility(View.GONE);
            tv_jumlahDonasi.setVisibility(View.INVISIBLE);
        } else if (jenisUser.equalsIgnoreCase(JENIS_USER_PIMPINAN)) {
            btn_donasi.setText("daftar donatur");
        } else if (jenisUser.equalsIgnoreCase(JENIS_USER_OPERATOR)) {
            btn_donasi.setVisibility(View.GONE);
        }
    }

    private void getView() {
        foto = findViewById(R.id.iv_detail_donasi_foto);
        tv_namaKegiatan = findViewById(R.id.tv_detail_donasi_judul_donasi);
        tv_totalBiaya = findViewById(R.id.tv_detail_donasi_total_biaya);
        tv_keterangan = findViewById(R.id.tv_detail_donasi_keterangan);
        tv_totalDonasi = findViewById(R.id.tv_detail_donasi_total_donasi_masuk);
        tv_jumlahDonasi = findViewById(R.id.tv_detail_donasi_jumlah_donasi_masuk);
        btn_donasi = findViewById(R.id.btn_detail_donasi_donasi);
    }

    private void setBackButton(String jenisUser) {

        if (jenisUser.equalsIgnoreCase(JENIS_USER_ALUMNI)) {
            cl_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(DetailDonasiActivity.this, DonasiActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });
        } else if (jenisUser.equalsIgnoreCase(JENIS_USER_PIMPINAN)) {
            cl_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Diganti intentnya ke mana
                    Intent intent = new Intent(DetailDonasiActivity.this, DonasiActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });
        } else if (jenisUser.equalsIgnoreCase(JENIS_USER_OPERATOR)) {
            cl_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Diganti intentnya ke mana
                    Intent intent = new Intent(DetailDonasiActivity.this, DonasiActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });
        }

    }

    private void setDonasiButton(String jenisUser) {
        if (jenisUser.equalsIgnoreCase(JENIS_USER_ALUMNI)) {
            btn_donasi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Diganti intentnya ke mana
                }
            });
        } else if (jenisUser.equalsIgnoreCase(JENIS_USER_PIMPINAN)) {
            btn_donasi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Diganti intentnya ke mana
                }
            });

        }
    }
}
