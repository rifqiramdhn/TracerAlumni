package com.example.traceralumni.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.traceralumni.R;

public class OpDetailInfoActivity extends AppCompatActivity {
    TextView tvNavBar;
    ConstraintLayout cl_iconBack, cl_iconHapus;
    ImageView img_iconBack, img_iconHapus;
    TextInputLayout tv_inpJudul, tv_inpIsi, tv_inpURL;
    EditText edt_judul, edt_isi, edt_url;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_op_detail_info);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        builder = new AlertDialog.Builder(this);

        tvNavBar = findViewById(R.id.tv_navbar_top);
        tvNavBar.setText("DETAIL INFO");

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
                hapusInfo();
            }
        });

        edt_judul = findViewById(R.id.edt_judul);
        edt_isi = findViewById(R.id.edt_isi);
        edt_url = findViewById(R.id.edt_url);

        tv_inpJudul = findViewById(R.id.edt_judul_container);
        tv_inpIsi = findViewById(R.id.edt_isi_container);
        tv_inpURL = findViewById(R.id.edt_url_container);

        getData();

    }

    private void hapusInfo(){
        builder.setMessage("Anda Yakin Ingin Menghapus Ini?");
        builder.setCancelable(false);
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(OpDetailInfoActivity.this, MainActivity.class);
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
            edt_judul.setText(i.getStringExtra("judul"));
            edt_judul.setTextColor(getResources().getColor(R.color.black));
            edt_judul.setSelection(edt_judul.getText().length());
        }

        if (edt_isi != null){
            Intent i = getIntent();
            edt_isi.setText(i.getStringExtra("isi"));
            edt_isi.setTextColor(getResources().getColor(R.color.black));
            edt_isi.setSelection(edt_isi.getText().length());
        }

        if (edt_url != null){
            Intent i = getIntent();
            edt_url.setText(i.getStringExtra("link"));
            edt_url.setTextColor(getResources().getColor(R.color.black));
            edt_url.setSelection(edt_url.getText().length());
        }
    }
}
