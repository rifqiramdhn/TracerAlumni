package com.example.traceralumni.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.traceralumni.Adapter.RiwayatPekerjaanAdapter;
import com.example.traceralumni.JsonPlaceHolderApi;
import com.example.traceralumni.Model.DaftarModel;
import com.example.traceralumni.Model.PathModel;
import com.example.traceralumni.Model.RiwayatPekerjaanModel;
import com.example.traceralumni.R;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.traceralumni.Activity.LocationPickerActivity.KODE_POS_EXTRA_KEY;
import static com.example.traceralumni.Activity.LocationPickerActivity.LOKASI_EXTRA_KEY;
import static com.example.traceralumni.Activity.MainActivity.BASE_URL;
import static com.example.traceralumni.Activity.MainActivity.JENIS_USER;
import static com.example.traceralumni.Activity.MainActivity.JENIS_USER_ALUMNI;
import static com.example.traceralumni.Activity.MainActivity.JENIS_USER_OPERATOR;
import static com.example.traceralumni.Activity.MainActivity.NIM;
import static com.example.traceralumni.Activity.MainActivity.TEXT_NO_INTERNET;
import static com.example.traceralumni.Activity.TambahLowonganActivity.PICK_PHOTO_REQUEST;

public class DetailProfilActivity extends AppCompatActivity {
    BottomNavigationView bnChat;
    DaftarModel daftarModel;
    TextView tvNama, tvProdi, tvAngkatan, tvThnLulus, tvTglYudisium, tvKwn, tvNegara, tvEmail, tvTTL, tvAlamat, tvKodePos, tvNoHp, tvNoTelp, tvFacebook, tvTwitter;
    TextView tvNim, tvPassword, tvJudulPassword;

    CircleImageView img_detail_profil;
    ConstraintLayout cl_wa;

    String oldPath = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_profil);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        initView();

        if (!JENIS_USER.equals(JENIS_USER_ALUMNI)) {
            bnChat.setVisibility(View.GONE);
        }

        bnChat.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.ic_chat) {
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
            oldPath = daftarModel.getFoto();
            Glide.with(DetailProfilActivity.this)
                    .load(BASE_URL + oldPath)
                    .into(img_detail_profil);
            cl_wa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    String urlNew = "http://wa.me/" + daftarModel.getNomor_hp().substring(1);
                    i.setData(Uri.parse(urlNew));
                    startActivity(i);
                }
            });
            tvPassword.setText(daftarModel.getPassword());
            tvNim.setText(daftarModel.getNim());
        } else {
            getDataFromNIM(intent.getStringExtra("nim"));
        }
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

                final DaftarModel daftarModel = response.body();
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
                tvPassword.setText(daftarModel.getPassword());
                tvNim.setText(daftarModel.getNim());
                oldPath = daftarModel.getFoto();
                if (!oldPath.equals("")) {
                    Glide.with(DetailProfilActivity.this)
                            .load(BASE_URL + oldPath)
                            .into(img_detail_profil);
                }
                cl_wa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        String urlNew = "http://wa.me/" + daftarModel.getNomor_hp().substring(1);
                        i.setData(Uri.parse(urlNew));
                        startActivity(i);
                    }
                });

            }

            @Override
            public void onFailure(Call<DaftarModel> call, Throwable t) {
                if (t.getMessage().contains("Failed to connect")) {
                    Toast.makeText(DetailProfilActivity.this, TEXT_NO_INTERNET, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initView() {
        img_detail_profil = findViewById(R.id.iv_activity_detail_profil_foto);
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
        bnChat = findViewById(R.id.bn_chat);
        cl_wa = findViewById(R.id.cl_detail_profil_wa);
        tvPassword = findViewById(R.id.tv_detail_profil_password);
        tvNim = findViewById(R.id.tv_detail_profil_nim);
        tvJudulPassword = findViewById(R.id.tv_judul_password);
        if (JENIS_USER.equals(JENIS_USER_OPERATOR)){
            tvJudulPassword.setVisibility(View.VISIBLE);
            tvPassword.setVisibility(View.VISIBLE);
        }
    }
}
