package com.example.traceralumni.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.traceralumni.R;

import static com.example.traceralumni.Activity.MainActivity.JENIS_USER_ALUMNI;

public class NominalDonasiActivity extends AppCompatActivity {
    ConstraintLayout cl_iconBack, cl_iconConfirm;
    ImageView img_iconBack, img_iconConfirm;
    TextView tv_navbar;
    AlertDialog.Builder builder;
    Intent intent;
    String namaKegiatan, totalBiaya, keterangan, fotoResId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nominal_donasi);

        builder = new AlertDialog.Builder(this);

        getData();

        cl_iconBack = findViewById(R.id.cl_icon1);
        cl_iconConfirm = findViewById(R.id.cl_icon4);

        img_iconBack = findViewById(R.id.img_icon1);
        img_iconConfirm = findViewById(R.id.img_icon4);

        tv_navbar = findViewById(R.id.tv_navbar_top);
        tv_navbar.setText("PILIH NOMINAL");

        cl_iconBack.setVisibility(View.VISIBLE);
        cl_iconConfirm.setVisibility(View.VISIBLE);

        img_iconBack.setImageResource(R.drawable.ic_arrow_back);
        img_iconConfirm.setImageResource(R.drawable.ic_check);

        img_iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        img_iconConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showKonfirmasiDialog();
            }
        });
    }

    private void getData() {
        intent = getIntent();
        fotoResId = intent.getStringExtra("fotoResId");
        namaKegiatan = intent.getStringExtra("namaKegiatan");
        totalBiaya = intent.getStringExtra("totalBiaya");
        keterangan = intent.getStringExtra("keterangan");
    }

    private void showKonfirmasiDialog(){
        builder.setMessage("Apakah nominal donasi yang anda masukkan sudah benar?");

        builder.setTitle("Konfirmasi Donasi");
        builder.setCancelable(false);
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(NominalDonasiActivity.this, DetailDonasiActivity.class);
                i.putExtra("namaKegiatan", namaKegiatan);
                i.putExtra("totalBiaya", totalBiaya);
                i.putExtra("keterangan", keterangan);
                i.putExtra("fotoResId", fotoResId);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
