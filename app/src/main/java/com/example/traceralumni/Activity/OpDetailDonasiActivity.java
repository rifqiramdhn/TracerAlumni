package com.example.traceralumni.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.traceralumni.R;

import static com.example.traceralumni.Activity.MainActivity.INDEX_OPENED_TAB;
import static com.example.traceralumni.Activity.MainActivity.INDEX_OPENED_TAB_KEY;

public class OpDetailDonasiActivity extends AppCompatActivity {
    TextView tvNavBar, tvTotalDonasi,tvFile;
    ConstraintLayout cl_iconBack, cl_iconHapus;
    ImageView img_iconBack, img_iconHapus;
    EditText edt_judul, edt_donasi, edt_deskripsi, edt_notelp;
    Button btn_list_donatur, btn_simpan;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_op_detail_donasi);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        builder = new AlertDialog.Builder(this);

        tvNavBar = findViewById(R.id.tv_navbar_top);
        tvNavBar.setText("DETAIL DONASI");

        img_iconBack = findViewById(R.id.img_icon1);
        img_iconBack.setImageResource(R.drawable.ic_arrow_back);

        img_iconHapus = findViewById(R.id.img_icon4);
        img_iconHapus.setImageResource(R.drawable.ic_delete);

        cl_iconBack = findViewById(R.id.cl_icon1);
        cl_iconBack.setVisibility(View.VISIBLE);

        cl_iconHapus = findViewById(R.id.cl_icon4);
        cl_iconHapus.setVisibility(View.VISIBLE);

        cl_iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        cl_iconHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hapusDonasi();
            }
        });

        edt_judul = findViewById(R.id.edt_judul);
        edt_donasi = findViewById(R.id.edt_total_donasi);
        edt_deskripsi = findViewById(R.id.edt_deskripsi);
        edt_notelp = findViewById(R.id.edt_notelp);
        tvTotalDonasi = findViewById(R.id.tv_total_donasi);
        tvFile = findViewById(R.id.tv_file);

        getData();

        btn_list_donatur = findViewById(R.id.btn_list_donatur);
        btn_list_donatur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OpDetailDonasiActivity.this, OpListDonaturActivity.class);
                startActivity(i);
            }
        });

        btn_simpan = findViewById(R.id.btn_simpan);
        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(OpDetailDonasiActivity.this, MainActivity.class);
//                i.putExtra("Tab", INDEX_OPENED_TAB);
//                startActivity(i);
                onBackPressed();
            }
        });
    }

    private void hapusDonasi(){
        builder.setMessage("Anda yakin ingin menghapus donasi ini?");
        builder.setCancelable(false);
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(OpDetailDonasiActivity.this, MainActivity.class);
                i.putExtra(INDEX_OPENED_TAB_KEY,INDEX_OPENED_TAB);
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

    private void getData(){
        if (edt_judul != null){
            Intent i = getIntent();
            edt_judul.setText(i.getStringExtra("namaKegiatan"));
            edt_judul.setTextColor(getResources().getColor(R.color.black));
            edt_judul.setSelection(edt_judul.getText().length());
        } else {
            edt_judul.setText("");
        }

        if (edt_donasi != null){
            Intent i = getIntent();
            edt_donasi.setText(i.getStringExtra("totalBiaya"));
            edt_donasi.setTextColor(getResources().getColor(R.color.black));
            edt_donasi.setSelection(edt_donasi.getText().length());
        } else {
            edt_donasi.setText("");
        }

        if (edt_deskripsi != null){
            Intent i = getIntent();
            edt_deskripsi.setText(i.getStringExtra("keterangan"));
            edt_deskripsi.setTextColor(getResources().getColor(R.color.black));
            edt_deskripsi.setSelection(edt_deskripsi.getText().length());
        } else {
            edt_deskripsi.setText("");
        }

        if (edt_notelp != null){
            Intent i = getIntent();
            edt_notelp.setText(i.getStringExtra("notelp"));
            edt_notelp.setTextColor(getResources().getColor(R.color.black));
            edt_notelp.setSelection(edt_notelp.getText().length());
        } else {
            edt_notelp.setText("");
        }
    }
}
