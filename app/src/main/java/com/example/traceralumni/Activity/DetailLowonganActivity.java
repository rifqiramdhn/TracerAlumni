package com.example.traceralumni.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.traceralumni.JsonPlaceHolderApi;
import com.example.traceralumni.Model.DaftarModel;
import com.example.traceralumni.Model.LowonganModel;
import com.example.traceralumni.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.traceralumni.Activity.MainActivity.BASE_URL;
import static com.example.traceralumni.Activity.MainActivity.INDEX_OPENED_TAB;
import static com.example.traceralumni.Activity.MainActivity.INDEX_OPENED_TAB_KEY;
import static com.example.traceralumni.Activity.MainActivity.JENIS_USER;
import static com.example.traceralumni.Activity.MainActivity.JENIS_USER_ALUMNI;
import static com.example.traceralumni.Activity.MainActivity.JENIS_USER_OPERATOR;
import static com.example.traceralumni.Activity.MainActivity.NIM;

public class DetailLowonganActivity extends AppCompatActivity {
    Button btn_profil, btn_hapus;
    ConstraintLayout clBtnHubungi, cl_iconBack, cl_iconHapus;
    ImageView img_iconBack, img_iconHapus;
    TextView tvNavBar;
    private TextView tvNamaLowongan, tvNamaPerusahaan, tvLokasi, tvKisaranGaji, tvProfil
            , tvSyarat, tvKuota, tvJabatan, tvWeb, tvEmail, tvTelepon, tvHubungi;
    Integer idLowongan;
    String username;
    String namaPelowong = "";
    AlertDialog.Builder builder;
    LowonganModel lowonganModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_lowongan);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        builder = new AlertDialog.Builder(this);

        initView();
        getData();
        getNama();
    }

    private void getData(){
        Intent intent = getIntent();
        lowonganModel = intent.getParcelableExtra("object_lowongan");

        if(lowonganModel != null) {
            tvNamaLowongan.setText(lowonganModel.getNama_lowongan());
            tvNamaPerusahaan.setText(lowonganModel.getNama_perusahaan());
            tvLokasi.setText(lowonganModel.getAlamat_perusahaan());
            tvKisaranGaji.setText("~Rp " + lowonganModel.getKisaran_gaji());
            tvSyarat.setText(lowonganModel.getSyarat_pekerjaan());
            tvKuota.setText(lowonganModel.getKuota() + " orang");
            tvJabatan.setText(lowonganModel.getJabatan());
            tvWeb.setText(lowonganModel.getWebsite());
            tvEmail.setText(lowonganModel.getEmail());
            tvTelepon.setText(lowonganModel.getNo_telp());
            tvHubungi.setText(lowonganModel.getCp());

            idLowongan = lowonganModel.getIdLowongan();
        }
    }

    private void initView(){
        cl_iconBack = findViewById(R.id.cl_icon1);
        cl_iconHapus = findViewById(R.id.cl_icon4);
        img_iconBack = findViewById(R.id.img_icon1);
        img_iconHapus = findViewById(R.id.img_icon4);
        tvNavBar = findViewById(R.id.tv_navbar_top);
        clBtnHubungi = findViewById(R.id.cl_btn_hubungi_lowongan);
        tvProfil = findViewById(R.id.tv_profil);
        tvNamaLowongan = findViewById(R.id.tv_nama_lowongan);
        tvNamaPerusahaan = findViewById(R.id.tv_nama_perusahaan);
        tvLokasi = findViewById(R.id.tv_lokasi_perusahaan);
        tvKisaranGaji = findViewById(R.id.tv_kisaran_gaji);
        tvProfil = findViewById(R.id.tv_profil);
        tvSyarat = findViewById(R.id.tv_syarat);
        tvKuota = findViewById(R.id.tv_kuota);
        tvJabatan = findViewById(R.id.tv_jabatan);
        tvWeb = findViewById(R.id.tv_web);
        tvEmail = findViewById(R.id.tv_email);
        tvTelepon = findViewById(R.id.tv_telpon);
        tvHubungi = findViewById(R.id.txt_hubungi);
        btn_profil = findViewById(R.id.btn_lihat_profil);

        tvNavBar.setText("DETAIL LOWONGAN");
        img_iconBack.setImageResource(R.drawable.ic_arrow_back);
        img_iconHapus.setImageResource(R.drawable.ic_delete);
        cl_iconBack.setVisibility(View.VISIBLE);

        cl_iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if (JENIS_USER.equalsIgnoreCase(JENIS_USER_OPERATOR)){
            cl_iconHapus.setVisibility(View.VISIBLE);
            cl_iconHapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showKonfirmasiHapus();
                }
            });
        } else {
            cl_iconHapus.setVisibility(View.GONE);
        }

        clBtnHubungi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DetailLowonganActivity.this, "hubungi ah", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void showKonfirmasiHapus(){
        builder.setMessage("Apakah anda yakin ingin menghapus lowongan pekerjaan?")
                .setTitle("Hapus Lowongan")
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteLowongan(idLowongan);
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteLowongan(Integer idLowongan){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<Void> call = jsonPlaceHolderApi.deleteLowongan(idLowongan);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()){
                    return;
                }
                Intent i = new Intent(DetailLowonganActivity.this, MainActivity.class);
                i.putExtra(INDEX_OPENED_TAB_KEY, INDEX_OPENED_TAB);
                startActivity(i);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(DetailLowonganActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getNama() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<DaftarModel> call = jsonPlaceHolderApi.getUserData(lowonganModel.getUsername());
        call.enqueue(new Callback<DaftarModel>() {
            @Override
            public void onResponse(Call<DaftarModel> call, Response<DaftarModel> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                final DaftarModel daftarModel = response.body();
                if(daftarModel.getStatus_data().equalsIgnoreCase("n")){
                    tvProfil.setText("Admin");
                    btn_profil.setVisibility(View.GONE);
                } else {
                    tvProfil.setText(daftarModel.getNama());
                    btn_profil.setVisibility(View.VISIBLE);
                    btn_profil.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(DetailLowonganActivity.this, DetailProfilActivity.class);
                            i.putExtra("daftarModel", daftarModel);
                            startActivity(i);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<DaftarModel> call, Throwable t) {
                Toast.makeText(DetailLowonganActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
