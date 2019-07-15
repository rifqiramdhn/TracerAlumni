package com.example.traceralumni.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.traceralumni.JsonPlaceHolderApi;
import com.example.traceralumni.Model.LowonganModel;
import com.example.traceralumni.R;

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

public class DetailLowonganActivity extends AppCompatActivity {
    Button btn_profil, btn_hapus;
    ConstraintLayout clBtnHubungi;

    private TextView tvNamaLowongan, tvNamaPerusahaan, tvLokasi, tvKisaranGaji, tvProfil
            , tvSyarat, tvKuota, tvJabatan, tvWeb, tvEmail, tvTelepon, tvHubungi;
    Integer idLowongan;

    AlertDialog.Builder builder;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_lowongan);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        builder = new AlertDialog.Builder(this);

//        btn_profil.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(DetailLowonganActivity.this, DetailProfilActivity.class);
//                startActivity(i);
//            }
//        });

        clBtnHubungi = findViewById(R.id.cl_btn_hubungi_lowongan);
        clBtnHubungi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DetailLowonganActivity.this, "hubungi ah", Toast.LENGTH_SHORT).show();
            }
        });

        initView();

        getData();
        if (JENIS_USER.equalsIgnoreCase(JENIS_USER_OPERATOR)){
            btn_hapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showKonfirmasiHapus();
                }
            });
        }
    }

    private void getData(){
        Intent intent = getIntent();
        LowonganModel lowonganModel = intent.getParcelableExtra("object_lowongan");

        if(lowonganModel != null){
            tvNamaLowongan.setText(lowonganModel.getNama_lowongan());
            tvNamaPerusahaan.setText(lowonganModel.getNama_perusahaan());
            tvLokasi.setText(lowonganModel.getAlamat_perusahaan());
            tvKisaranGaji.setText("~Rp " + lowonganModel.getKisaran_gaji());
//          tvProfil.setText(lowonganModel.);
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
        btn_hapus = findViewById(R.id.btn_hapus_lowongan);

        if (JENIS_USER.equalsIgnoreCase(JENIS_USER_ALUMNI)){
            btn_hapus.setVisibility(View.GONE);
        } else {
            btn_hapus.setVisibility(View.VISIBLE);
        }
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
}
