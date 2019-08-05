package com.example.traceralumni.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.traceralumni.JsonPlaceHolderApi;
import com.example.traceralumni.Model.DonasiModel;
import com.example.traceralumni.Model.PermintaanDonasiModel;
import com.example.traceralumni.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.traceralumni.Activity.MainActivity.BASE_URL;
import static com.example.traceralumni.Activity.MainActivity.JENIS_USER;
import static com.example.traceralumni.Activity.MainActivity.JENIS_USER_ALUMNI;
import static com.example.traceralumni.Activity.MainActivity.JENIS_USER_OPERATOR;
import static com.example.traceralumni.Activity.MainActivity.JENIS_USER_PIMPINAN;
import static com.example.traceralumni.Activity.MainActivity.TEXT_NO_INTERNET;

public class DetailDonasiActivity extends AppCompatActivity {

    ConstraintLayout cl_back, cl_kontak;
    ImageView imgBack, foto;
    TextView tv_titleNavBar, tv_namaKegiatan, tv_totalBiaya, tv_keterangan, tv_totalDonasi, tv_jumlahDonasi, tv_kontak;
    Button btn_donasi;
    DonasiModel donasiModel;
    Intent intent;
    Integer mIdDonasi;
    String kontak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_donasi);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        initView();
        getData();
        setDonasiButton();
    }

    private void getData() {
        Intent intent = getIntent();
        donasiModel = intent.getParcelableExtra("object_donasi");
        if (donasiModel != null) {
            mIdDonasi = donasiModel.getIdDonasi();
            tv_keterangan.setText(donasiModel.getKeterangan());
            tv_namaKegiatan.setText(donasiModel.getNamaKegiatan());
            tv_totalBiaya.setText("Rp " + String.format("%.0f", donasiModel.getTotalAnggaran()));
            tv_kontak.setText("Hubungi\n" + donasiModel.getContactPerson());
            kontak = donasiModel.getContactPerson();
            Glide.with(DetailDonasiActivity.this)
                    .load(BASE_URL + donasiModel.getFile())
                    .into(foto);
            getJumlahDuit(donasiModel.getIdDonasi());
        } else {
            getDataFromID(intent.getIntExtra("id_donasi", -1));
            getJumlahDuit(intent.getIntExtra("id_donasi", -1));
        }
    }

    private void getDataFromID(int idDonasi) {
        if (idDonasi != -1) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

            Call<DonasiModel> call = jsonPlaceHolderApi.getDonasi(idDonasi);
            call.enqueue(new Callback<DonasiModel>() {
                @Override
                public void onResponse(Call<DonasiModel> call, Response<DonasiModel> response) {
                    if (!response.isSuccessful()) {
                        return;
                    }

                    DonasiModel donasiModelNew = response.body();
                    mIdDonasi = donasiModelNew.getIdDonasi();

                    tv_keterangan.setText(donasiModelNew.getKeterangan());
                    tv_namaKegiatan.setText(donasiModelNew.getNamaKegiatan());
                    tv_totalBiaya.setText("Rp " + String.format("%.0f", donasiModelNew.getTotalAnggaran()));
                    tv_kontak.setText("Hubungi\n" + donasiModelNew.getContactPerson());
                    kontak = donasiModelNew.getContactPerson();
                    Glide.with(DetailDonasiActivity.this)
                            .load(BASE_URL + donasiModelNew.getFile())
                            .into(foto);
                }

                @Override
                public void onFailure(Call<DonasiModel> call, Throwable t) {
                    if (t.getMessage().contains("Failed to connect")) {
                        Toast.makeText(DetailDonasiActivity.this, TEXT_NO_INTERNET, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void initView() {
        cl_back = findViewById(R.id.cl_icon1);
        imgBack = findViewById(R.id.img_icon1);
        tv_titleNavBar = findViewById(R.id.tv_navbar_top);
        tv_titleNavBar.setText("DETAIL DONASI");
        imgBack.setImageResource(R.drawable.ic_arrow_back);
        cl_back.setVisibility(View.VISIBLE);
        cl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        foto = findViewById(R.id.iv_detail_donasi_foto);
        tv_namaKegiatan = findViewById(R.id.tv_detail_donasi_judul_donasi);
        tv_totalBiaya = findViewById(R.id.tv_detail_donasi_total_biaya);
        tv_keterangan = findViewById(R.id.tv_detail_donasi_keterangan);
        tv_totalDonasi = findViewById(R.id.tv_detail_donasi_total_donasi_masuk);
        tv_jumlahDonasi = findViewById(R.id.tv_detail_donasi_jumlah_donasi_masuk);
        tv_kontak = findViewById(R.id.tv_detail_donasi_kontak);
        btn_donasi = findViewById(R.id.btn_detail_donasi_donasi);
        cl_kontak = findViewById(R.id.cl_detail_donasi_kontak);
        cl_kontak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(DetailDonasiActivity.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(DetailDonasiActivity.this,
                            new String[]{Manifest.permission.CALL_PHONE}, 1);
                } else {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + kontak));
                    startActivity(callIntent);
                }
            }
        });
        if (getIntent().getBooleanExtra("statusDonasi", false)) {
            btn_donasi.setVisibility(View.GONE);
            btn_donasi.setText("donasi");
            tv_totalDonasi.setVisibility(View.GONE);
            tv_jumlahDonasi.setVisibility(View.INVISIBLE);
        } else if (JENIS_USER.equalsIgnoreCase(JENIS_USER_ALUMNI)) {
            btn_donasi.setVisibility(View.VISIBLE);
            btn_donasi.setText("donasi");
            tv_totalDonasi.setVisibility(View.GONE);
            tv_jumlahDonasi.setVisibility(View.INVISIBLE);
        } else if (JENIS_USER.equalsIgnoreCase(JENIS_USER_PIMPINAN)) {
            btn_donasi.setVisibility(View.VISIBLE);
            btn_donasi.setText("daftar donatur");
            tv_totalDonasi.setVisibility(View.VISIBLE);
            tv_jumlahDonasi.setVisibility(View.VISIBLE);
        } else if (JENIS_USER.equalsIgnoreCase(JENIS_USER_OPERATOR)) {
            btn_donasi.setVisibility(View.GONE);
            tv_totalDonasi.setVisibility(View.VISIBLE);
            tv_jumlahDonasi.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (!kontak.equals("")) {
                        Intent callIntent = new Intent(Intent.ACTION_DIAL);
                        callIntent.setData(Uri.parse("tel:" + kontak));
                        startActivity(callIntent);
                    }
                }
                return;
            }
        }
    }

    private void setDonasiButton() {
        if (JENIS_USER.equalsIgnoreCase(JENIS_USER_ALUMNI)) {
            btn_donasi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(DetailDonasiActivity.this, NominalDonasiActivity.class);
                    i.putExtra("id_donasi", mIdDonasi);
                    startActivity(i);
                }
            });
        } else if (JENIS_USER.equalsIgnoreCase(JENIS_USER_PIMPINAN)) {
            btn_donasi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(DetailDonasiActivity.this, OpListDonaturActivity.class);
                    intent.putExtra("id_donasi", mIdDonasi);
                    startActivity(intent);
                }
            });

        }
    }

    private void getJumlahDuit(Integer idDonasi) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<PermintaanDonasiModel> call = jsonPlaceHolderApi.getJumlahDuit(idDonasi);
        call.enqueue(new Callback<PermintaanDonasiModel>() {
            @Override
            public void onResponse(Call<PermintaanDonasiModel> call, Response<PermintaanDonasiModel> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                PermintaanDonasiModel donaturModelResponse = response.body();
                if (donaturModelResponse.getTotal() != null) {
                    tv_jumlahDonasi.setText("Rp " + String.format("%.0f", donaturModelResponse.getTotal()));
                } else {
                    tv_jumlahDonasi.setText("Rp 0");
                }
            }

            @Override
            public void onFailure(Call<PermintaanDonasiModel> call, Throwable t) {
                if (t.getMessage().contains("Failed to connect")) {
                    Toast.makeText(DetailDonasiActivity.this, TEXT_NO_INTERNET, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
