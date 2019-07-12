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
import android.widget.Toast;

import com.example.traceralumni.JsonPlaceHolderApi;
import com.example.traceralumni.Model.DonasiModel;
import com.example.traceralumni.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.traceralumni.Activity.MainActivity.BASE_URL;
import static com.example.traceralumni.Activity.MainActivity.INDEX_OPENED_TAB;
import static com.example.traceralumni.Activity.MainActivity.INDEX_OPENED_TAB_KEY;

public class OpDetailDonasiActivity extends AppCompatActivity {
    TextView tvNavBar, tvTotalDonasi,tvFile;
    ConstraintLayout cl_iconBack, cl_iconHapus;
    ImageView img_iconBack, img_iconHapus;
    EditText edt_judul, edt_donasi, edt_deskripsi, edt_noTelp;
    Button btn_list_donatur, btn_simpan;
    AlertDialog.Builder builder;

    Integer idDonasi, totalAnggaran;
    String namaKegiatan, noTelepon, deskripsi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_op_detail_donasi);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        builder = new AlertDialog.Builder(this);
        initView();
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
                totalAnggaran = Integer.valueOf(edt_donasi.getText().toString().trim());
                namaKegiatan = edt_judul.getText().toString().trim();
                noTelepon = edt_noTelp.getText().toString().trim();
                deskripsi = edt_deskripsi.getText().toString().trim();

                saveData(idDonasi, namaKegiatan, deskripsi, noTelepon, totalAnggaran);

                onBackPressed();
            }
        });
    }

    private void initView(){
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

        edt_judul = findViewById(R.id.edt_judul_donasi);
        edt_donasi = findViewById(R.id.edt_total_donasi);
        edt_deskripsi = findViewById(R.id.edt_deskripsi);
        edt_noTelp = findViewById(R.id.edt_notelp_op_donasi);
        tvTotalDonasi = findViewById(R.id.tv_total_donasi);
        tvFile = findViewById(R.id.tv_file);
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
        Intent intent = getIntent();
        DonasiModel donasiModel = intent.getParcelableExtra("object_donasi");
        if (donasiModel != null){
            edt_judul.setText(donasiModel.getNamaKegiatan());
            edt_donasi.setText("" + donasiModel.getTotalAnggaran());
            edt_deskripsi.setText(donasiModel.getKeterangan());
            edt_noTelp.setText(donasiModel.getContactPerson());
            idDonasi = donasiModel.getIdDonasi();
        }
    }

    private void saveData(Integer idDonasi, String namaKegiatan, String keterangan, String noTelepon, Integer totalAnggaran){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<Void> call = jsonPlaceHolderApi.createDonasi(idDonasi, namaKegiatan, noTelepon, keterangan, totalAnggaran);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()){
                    return;
                }

                Toast.makeText(OpDetailDonasiActivity.this, "Data tersimpan", Toast.LENGTH_SHORT).show();

                onBackPressed();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(OpDetailDonasiActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
