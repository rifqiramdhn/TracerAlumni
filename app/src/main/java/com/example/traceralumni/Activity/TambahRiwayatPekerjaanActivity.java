package com.example.traceralumni.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ParseException;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.traceralumni.JsonPlaceHolderApi;
import com.example.traceralumni.Model.RiwayatPekerjaanModel;
import com.example.traceralumni.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.traceralumni.Activity.MainActivity.BASE_URL;
import static com.example.traceralumni.Activity.MainActivity.NIM;
import static com.example.traceralumni.Activity.MainActivity.TEXT_NO_INTERNET;

public class TambahRiwayatPekerjaanActivity extends AppCompatActivity {
    TextView tvNavBar;
    ConstraintLayout cl_iconBack, cl_iconHapus;
    ImageView img_iconBack, img_iconConfirm;
    EditText edtPekerjaan, edtNamaPer, edtLokasi, edtGaji;
    TextView tvTahunMasuk, tvTahunKeluar;
    Spinner spnTahunMasuk, spnTahunKeluar;
    Button btnSimpan;

    Integer idRiwayat;
    String tanggal_sekarang;
    Integer tahun_sekarang;

    int CAN_CLICK_BUTTON_SAVE = 0; //0 bisa diklik, 1 tidak bisa diklik

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_riwayat_pekerjaan);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        initView();
        getData();
        getTanggalLowongan();
        customSpinner();
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
                if (t.getMessage().contains("Failed to connect")) {
                    Toast.makeText(TambahRiwayatPekerjaanActivity.this, TEXT_NO_INTERNET, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getData() {
        Intent intent = getIntent();
        RiwayatPekerjaanModel riwayatPekerjaanModel = intent.getParcelableExtra("object_riwayat");
        if (riwayatPekerjaanModel != null) {
            edtGaji.setText(riwayatPekerjaanModel.getGaji());
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
                spnTahunMasuk.getSelectedItem().toString(),
                spnTahunKeluar.getSelectedItem().toString());

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    CAN_CLICK_BUTTON_SAVE = 0;
                    return;
                }

                onBackPressed();
                Toast.makeText(TambahRiwayatPekerjaanActivity.this, "Riwayat Pekerjaan Berhasil Ditambahkan", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                CAN_CLICK_BUTTON_SAVE = 0;
                if (t.getMessage().contains("Failed to connect")) {
                    Toast.makeText(TambahRiwayatPekerjaanActivity.this, TEXT_NO_INTERNET, Toast.LENGTH_SHORT).show();
                }
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
        } else if (spnTahunMasuk.getSelectedItem().toString().equalsIgnoreCase("Tahun Masuk")) {
            Toast.makeText(this, "Anda belum memilih tahun masuk!", Toast.LENGTH_SHORT).show();
        } else if (spnTahunKeluar.getSelectedItem().toString().equalsIgnoreCase("Tahun Keluar")) {
            Toast.makeText(this, "Anda belum memilih tahun keluar!", Toast.LENGTH_SHORT).show();
        } else if (edtGaji.getText().toString().equals("")) {
            edtGaji.setError("Wajib diisi");
        } else {
            if (CAN_CLICK_BUTTON_SAVE == 0){
                CAN_CLICK_BUTTON_SAVE = 1;
                submitData();
            }
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
        edtGaji = findViewById(R.id.edt_riwayat_gaji);
        spnTahunMasuk = findViewById(R.id.spn_tahun_masuk);
        spnTahunKeluar = findViewById(R.id.spn_tahun_keluar);
        tvTahunMasuk = findViewById(R.id.tv_judul_thnMasuk);
        tvTahunKeluar = findViewById(R.id.tv_judul_thnKeluar);
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

    private void getTanggalLowongan() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = new Date();
            tanggal_sekarang = dateFormat.format(date);
            tahun_sekarang = Integer.valueOf(""+tanggal_sekarang.substring(0, 4));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void customSpinner(){
        List<String> tahunMasuk = new ArrayList<>();
        tahunMasuk.add("Tahun Masuk");
        for (int i = 1; i < 50; i++){
            Integer tahun = tahun_sekarang;
            tahunMasuk.add(tahun.toString());
            tahun_sekarang--;
        }

        List<String> tahunKeluar = new ArrayList<>();
        tahunKeluar.add("Tahun Keluar");
        for (int i = 1; i < 50; i++){
            Integer tahun = tahun_sekarang + 50;
            tahunKeluar.add(tahun.toString());
            tahun_sekarang++;
        }

        final ArrayAdapter<String> spinnerArrayAdapterTahunMasuk = new ArrayAdapter<String>(
                TambahRiwayatPekerjaanActivity.this, R.layout.card_spinner, tahunMasuk) {
            @Override
            public boolean isEnabled(int position) {
                return true;
            }

            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(getResources().getColor(R.color.colorIconBiru));
                }
                return view;
            }
        };

        final ArrayAdapter<String> spinnerArrayAdapterTahunKeluar = new ArrayAdapter<String>(
                TambahRiwayatPekerjaanActivity.this, R.layout.card_spinner, tahunKeluar) {
            @Override
            public boolean isEnabled(int position) {
                return true;
            }

            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(getResources().getColor(R.color.colorIconBiru));
                }
                return view;
            }
        };

        spinnerArrayAdapterTahunMasuk.setDropDownViewResource(R.layout.card_spinner);
        spinnerArrayAdapterTahunKeluar.setDropDownViewResource(R.layout.card_spinner);
        spnTahunMasuk.setAdapter(spinnerArrayAdapterTahunMasuk);
        spnTahunKeluar.setAdapter(spinnerArrayAdapterTahunKeluar);

        spnTahunMasuk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spnTahunMasuk.getSelectedItem().toString().equalsIgnoreCase("Tahun Masuk")){
                    tvTahunMasuk.setVisibility(View.INVISIBLE);
                } else {
                    tvTahunMasuk.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnTahunKeluar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spnTahunKeluar.getSelectedItem().toString().equalsIgnoreCase("Tahun Keluar")){
                    tvTahunKeluar.setVisibility(View.INVISIBLE);
                } else {
                    tvTahunKeluar.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (getIntent().getParcelableExtra("object_riwayat") != null){
            RiwayatPekerjaanModel riwayatPekerjaanModel = getIntent().getParcelableExtra("object_riwayat");
            for (int i = 0; i < tahunMasuk.size(); i++){
                if (tahunMasuk.get(i).equalsIgnoreCase(riwayatPekerjaanModel.getTahunAwal())){
                    spnTahunMasuk.setSelection(i);
                }
            }

            for (int i = 0; i < tahunKeluar.size(); i++){
                if (tahunKeluar.get(i).equalsIgnoreCase(riwayatPekerjaanModel.getTahunAkhir())){
                    spnTahunKeluar.setSelection(i);
                }
            }
        }
    }
}
