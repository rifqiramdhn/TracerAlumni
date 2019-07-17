package com.example.traceralumni.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.traceralumni.Adapter.RiwayatPekerjaanAdapter;
import com.example.traceralumni.JsonPlaceHolderApi;
import com.example.traceralumni.Model.DaftarModel;
import com.example.traceralumni.Model.RiwayatPekerjaanModel;
import com.example.traceralumni.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.traceralumni.Activity.MainActivity.BASE_URL;
import static com.example.traceralumni.Activity.MainActivity.JENIS_USER;
import static com.example.traceralumni.Activity.MainActivity.JENIS_USER_ALUMNI;

public class DetailProfilActivity extends AppCompatActivity {
    RecyclerView riwayatRecycler;
    RiwayatPekerjaanAdapter riwayatAdapter;
    ArrayList<RiwayatPekerjaanModel> riwayatModel;
    BottomNavigationView bnChat;
    DaftarModel daftarModel;
    TextView tvNama, tvProdi, tvAngkatan, tvThnLulus, tvTglYudisium, tvKwn, tvNegara, tvEmail, tvTTL, tvAlamat, tvKodePos, tvNoHp, tvNoTelp, tvFacebook, tvTwitter, tvStatus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_profil);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        initView();

        if (!JENIS_USER.equals(JENIS_USER_ALUMNI)) {
            bnChat.setVisibility(View.GONE);
        }

        riwayatModel = new ArrayList<>();
        riwayatRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        riwayatAdapter = new RiwayatPekerjaanAdapter(this, riwayatModel);
        riwayatRecycler.setAdapter(riwayatAdapter);

        riwayatRecycler.setNestedScrollingEnabled(false);

        bnChat.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.ic_chat) {
                    //tempat intent untuk pindah ke chat
                }
                return true;
            }
        });

        getDataFromIntent();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        daftarModel = intent.getParcelableExtra("daftarModel");
        if (daftarModel != null) {
            tvNama.setText(daftarModel.getNama());
            tvProdi.setText(daftarModel.getProdi());
            tvAngkatan.setText(daftarModel.getAngkatan());
            tvThnLulus.setText(daftarModel.getTahun_lulus());
            tvTglYudisium.setText(daftarModel.getTanggal_yudisium());
            tvKwn.setText(daftarModel.getKewarganegaraan());
            tvNegara.setText(daftarModel.getNama_negara());
            tvEmail.setText(daftarModel.getEmail());
            tvTTL.setText(daftarModel.getTempat_lahir().concat(", ").concat(daftarModel.getTanggal_lahir()));
            tvAlamat.setText(daftarModel.getAlamat());
            tvKodePos.setText(daftarModel.getKode_pos());
            tvNoHp.setText(daftarModel.getNomor_hp());
            tvNoTelp.setText(daftarModel.getNomor_telepon());
            tvFacebook.setText(daftarModel.getFacebook());
            tvTwitter.setText(daftarModel.getTwitter());
            tvStatus.setText(daftarModel.getStatus_bekerja());
            getRiwayatPekerjaan(daftarModel.getNim());
        } else {
            getDataFromNIM(intent.getStringExtra("nim"));
            getRiwayatPekerjaan(intent.getStringExtra("nim"));
        }
    }

    private void getRiwayatPekerjaan(String nim) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<ArrayList<RiwayatPekerjaanModel>> call = jsonPlaceHolderApi.getRiwayat(nim);
        call.enqueue(new Callback<ArrayList<RiwayatPekerjaanModel>>() {
            @Override
            public void onResponse(Call<ArrayList<RiwayatPekerjaanModel>> call, Response<ArrayList<RiwayatPekerjaanModel>> response) {
                if (!response.isSuccessful()) {
                    return;
                }

                riwayatModel.clear();
                ArrayList<RiwayatPekerjaanModel> riwayatPekerjaanModels = response.body();
                riwayatModel.addAll(riwayatPekerjaanModels);
                riwayatAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<RiwayatPekerjaanModel>> call, Throwable t) {
                Toast.makeText(DetailProfilActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getDataFromNIM(String nim) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<DaftarModel> call = jsonPlaceHolderApi.getUserData(nim);
        call.enqueue(new Callback<DaftarModel>() {
            @Override
            public void onResponse(Call<DaftarModel> call, Response<DaftarModel> response) {
                if (!response.isSuccessful()) {
                    return;
                }

                DaftarModel daftarModel = response.body();

                tvNama.setText(daftarModel.getNama());
                tvProdi.setText(daftarModel.getProdi());
                tvAngkatan.setText(daftarModel.getAngkatan());
                tvThnLulus.setText(daftarModel.getTahun_lulus());
                tvTglYudisium.setText(daftarModel.getTanggal_yudisium());
                tvKwn.setText(daftarModel.getKewarganegaraan());
                tvNegara.setText(daftarModel.getNama_negara());
                tvEmail.setText(daftarModel.getEmail());
                tvTTL.setText(daftarModel.getTempat_lahir().concat(", ").concat(daftarModel.getTanggal_lahir()));
                tvAlamat.setText(daftarModel.getAlamat());
                tvKodePos.setText(daftarModel.getKode_pos());
                tvNoHp.setText(daftarModel.getNomor_hp());
                tvNoTelp.setText(daftarModel.getNomor_telepon());
                tvFacebook.setText(daftarModel.getFacebook());
                tvTwitter.setText(daftarModel.getTwitter());
                tvStatus.setText(daftarModel.getStatus_bekerja());
            }

            @Override
            public void onFailure(Call<DaftarModel> call, Throwable t) {
                Toast.makeText(DetailProfilActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        riwayatRecycler = findViewById(R.id.rv_riwayat_pekerjaan);
        tvNama = findViewById(R.id.txt_nama);
        tvProdi = findViewById(R.id.txt_prodi);
        tvAngkatan = findViewById(R.id.txt_angkatan);
        tvThnLulus = findViewById(R.id.txt_thnLulus);
        tvTglYudisium = findViewById(R.id.txt_tglYudisium);
        tvKwn = findViewById(R.id.txt_kewarganegaraan);
        tvNegara = findViewById(R.id.txt_wargaNegara);
        tvEmail = findViewById(R.id.tv_email);
        tvTTL = findViewById(R.id.txt_ttl);
        tvAlamat = findViewById(R.id.txt_alamat);
        tvKodePos = findViewById(R.id.txt_kodePos);
        tvNoHp = findViewById(R.id.txt_noHP);
        tvNoTelp = findViewById(R.id.txt_notelp);
        tvFacebook = findViewById(R.id.txt_facebook);
        tvTwitter = findViewById(R.id.txt_twitter);
        tvStatus = findViewById(R.id.txt_status);
        bnChat = findViewById(R.id.bn_chat);
    }
}
