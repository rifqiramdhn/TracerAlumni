package com.example.traceralumni.Activity;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.traceralumni.JsonPlaceHolderApi;
import com.example.traceralumni.Model.RiwayatPekerjaanModel;
import com.example.traceralumni.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.traceralumni.Activity.MainActivity.BASE_URL;
import static com.example.traceralumni.Activity.MainActivity.NIM;

public class TambahRiwayatPekerjaanActivity extends AppCompatActivity {
    TextView tvNavBar;
    ConstraintLayout cl_iconBack, cl_iconHapus;
    ImageView img_iconBack, img_iconConfirm;
    EditText edtPekerjaan, edtNamaPer, edtLokasi, edtThnAwal, edtThnAkhir, edtGaji;
    Button btnSimpan;

    Integer idRiwayat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_riwayat_pekerjaan);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        initView();
        getData();
    }

    private void hapusData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<Void> call = jsonPlaceHolderApi.deleteRiwayat(idRiwayat);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    return;
                }

                onBackPressed();
                Toast.makeText(TambahRiwayatPekerjaanActivity.this, "Berhasil dihapus", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(TambahRiwayatPekerjaanActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getData() {
        Intent intent = getIntent();
        RiwayatPekerjaanModel riwayatPekerjaanModel = intent.getParcelableExtra("object_riwayat");
        if (riwayatPekerjaanModel != null) {
            edtGaji.setText(riwayatPekerjaanModel.getGaji());
            edtThnAkhir.setText(riwayatPekerjaanModel.getTahunAkhir());
            edtThnAwal.setText(riwayatPekerjaanModel.getTahunAwal());
            edtLokasi.setText(riwayatPekerjaanModel.getLokasi());
            edtNamaPer.setText(riwayatPekerjaanModel.getNamaPerusahaan());
            edtPekerjaan.setText(riwayatPekerjaanModel.getPekerjaan());
            idRiwayat = riwayatPekerjaanModel.getIdRiwayat();
        } else {
            cl_iconHapus.setVisibility(View.GONE);
        }
    }

    private void submitData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<Void> call = jsonPlaceHolderApi.createRiwayat(idRiwayat, NIM,
                edtPekerjaan.getText().toString().trim(),
                edtLokasi.getText().toString().trim(),
                edtNamaPer.getText().toString().trim(),
                edtGaji.getText().toString().trim(),
                edtThnAwal.getText().toString().trim(),
                edtThnAkhir.getText().toString().trim());

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    return;
                }

                onBackPressed();
                Toast.makeText(TambahRiwayatPekerjaanActivity.this, "Riwayat Pekerjaan Berhasil Ditambahkan", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(TambahRiwayatPekerjaanActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkFieldToSave() {
        if (edtPekerjaan.getText().toString().equals("")) {
            edtPekerjaan.setError("Wajib diisi");
        } else if (edtNamaPer.getText().toString().equals("")) {
            edtNamaPer.setError("Wajib diisi");
        } else if (edtLokasi.getText().toString().equals("")) {
            edtLokasi.setError("Wajib diisi");
        } else if (edtThnAwal.getText().toString().equals("")) {
            edtThnAwal.setError("Wajib diisi");
        } else if (edtThnAkhir.getText().toString().equals("")) {
            edtThnAkhir.setError("Wajib diisi");
        } else if (edtGaji.getText().toString().equals("")) {
            edtGaji.setError("Wajib diisi");
        } else {
            submitData();
        }
    }

    private void initView() {
        btnSimpan = findViewById(R.id.btn_riwayat_simpan);
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkFieldToSave();
            }
        });
        edtPekerjaan = findViewById(R.id.edt_riwayat_pekerjaan);
        edtNamaPer = findViewById(R.id.edt_riwayat_nama_perusahaan);
        edtLokasi = findViewById(R.id.edt_riwayat_lokasi);
        edtThnAwal = findViewById(R.id.edt_riwayat_thn_awal);
        edtThnAkhir = findViewById(R.id.edt_riwayat_thn_akhir);
        edtGaji = findViewById(R.id.edt_riwayat_gaji);
        tvNavBar = findViewById(R.id.tv_navbar_top);
        tvNavBar.setText("DETAIL RIWAYAT PEKERJAAN");
        img_iconBack = findViewById(R.id.img_icon1);
        img_iconBack.setImageResource(R.drawable.ic_arrow_back);
        img_iconConfirm = findViewById(R.id.img_icon4);
        img_iconConfirm.setImageResource(R.drawable.ic_delete);
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
                hapusData();
                onBackPressed();
            }
        });
    }
}
