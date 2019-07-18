package com.example.traceralumni.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class DetailDonasiActivity extends AppCompatActivity {

    ConstraintLayout cl_back, cl_kontak;
    ImageView imgBack, foto;
    TextView tv_titleNavBar, tv_namaKegiatan, tv_totalBiaya, tv_keterangan, tv_totalDonasi, tv_jumlahDonasi;
    Button btn_donasi;
    DonasiModel donasiModel;
    Intent intent;

    Integer mIdDonasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_donasi);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        initView();
        getData();
        getJumlahDuit();

        setDonasiButton();
    }

    private void getData() {

        Intent intent = getIntent();
        donasiModel = intent.getParcelableExtra("object_donasi");
        if (donasiModel != null) {
            mIdDonasi = donasiModel.getIdDonasi();

//            tv_jumlahDonasi.setText("" + donasiModel.getDonasiMasuk());
            tv_keterangan.setText(donasiModel.getKeterangan());
            tv_namaKegiatan.setText(donasiModel.getNamaKegiatan());
            tv_totalBiaya.setText("Rp " + String.format("%.0f", donasiModel.getTotalAnggaran()));
        } else {
            getDataFromID(intent.getIntExtra("id_donasi", -1));
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

//                    tv_jumlahDonasi.setText("" + donasiModelNew.getDonasiMasuk());
                    tv_keterangan.setText(donasiModelNew.getKeterangan());
                    tv_namaKegiatan.setText(donasiModelNew.getNamaKegiatan());
                    tv_totalBiaya.setText("" + donasiModelNew.getTotalAnggaran());
                }

                @Override
                public void onFailure(Call<DonasiModel> call, Throwable t) {
                    Toast.makeText(DetailDonasiActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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
        btn_donasi = findViewById(R.id.btn_detail_donasi_donasi);
        cl_kontak = findViewById(R.id.cl_detail_donasi_kontak);
        cl_kontak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:0377778888"));

                if (ActivityCompat.checkSelfPermission(DetailDonasiActivity.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);
            }
        });

        if (JENIS_USER.equalsIgnoreCase(JENIS_USER_ALUMNI)) {
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

    private void getJumlahDuit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<PermintaanDonasiModel> call = jsonPlaceHolderApi.getJumlahDuit(donasiModel.getIdDonasi());
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
                Toast.makeText(DetailDonasiActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
