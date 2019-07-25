package com.example.traceralumni.Activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.traceralumni.JsonPlaceHolderApi;
import com.example.traceralumni.Model.DaftarModel;
import com.example.traceralumni.Model.LowonganModel;
import com.example.traceralumni.Model.PermintaanLowonganModel;
import com.example.traceralumni.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.hdodenhof.circleimageview.CircleImageView;
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
import static com.example.traceralumni.Activity.MainActivity.TEXT_NO_INTERNET;
import static com.example.traceralumni.Activity.StatusPermintaanLowonganActivity.BUKA_STATUS_LOWONGAN;

public class DetailLowonganActivity extends AppCompatActivity {
    Button btn_profil, btn_hapus;
    ConstraintLayout clBtnHubungi, cl_iconBack, cl_iconHapus, cl_iconEdit;
    ImageView img_iconBack, img_iconHapus, img_iconEdit;
    CircleImageView img_logo_perusahaan;
    TextView tvNavBar;
    private TextView tvNamaLowongan, tvNamaPerusahaan, tvLokasi, tvKisaranGaji, tvProfil, tvSyarat, tvKuota, tvJabatan, tvWeb, tvEmail, tvTelepon, tvHubungi;
    Integer idLowongan;
    String username;
    String oldPath = "";
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

    }

    private void getData() {
        Intent intent = getIntent();
        lowonganModel = intent.getParcelableExtra("object_lowongan");

        if (BUKA_STATUS_LOWONGAN){
            cl_iconEdit.setVisibility(View.VISIBLE);
            cl_iconHapus.setVisibility(View.VISIBLE);
        }

        if (intent.getStringExtra("status") != null && intent.getStringExtra("status").equals("Valid")){
            cl_iconEdit.setVisibility(View.GONE);
            cl_iconHapus.setVisibility(View.GONE);
        }

        if (lowonganModel != null) {
            tvNamaLowongan.setText(lowonganModel.getNama_lowongan());
            tvNamaPerusahaan.setText(lowonganModel.getNama_perusahaan());
            tvLokasi.setText(lowonganModel.getAlamat_perusahaan());
            tvKisaranGaji.setText("~Rp " + lowonganModel.getKisaran_gaji() + " juta");
            tvSyarat.setText(lowonganModel.getSyarat_pekerjaan());
            tvKuota.setText(lowonganModel.getKuota() + " orang");
            tvJabatan.setText(lowonganModel.getJabatan());
            tvWeb.setText(lowonganModel.getWebsite());
            tvEmail.setText(lowonganModel.getEmail());
            tvTelepon.setText(lowonganModel.getNo_telp());
            tvHubungi.setText(lowonganModel.getCp());
            oldPath = lowonganModel.getLogo_perusahaan();
            Glide.with(DetailLowonganActivity.this)
                    .load(BASE_URL + oldPath)
                    .into(img_logo_perusahaan);
            idLowongan = lowonganModel.getIdLowongan();
            getNama();
        } else if (intent.getIntExtra("object_permintaan_lowongan", -1) != -1) {
            cl_iconHapus.setVisibility(View.GONE);
            getLowonganFromId(intent.getIntExtra("object_permintaan_lowongan", -1));
        }
    }

    private void initView() {
        cl_iconBack = findViewById(R.id.cl_icon1);
        cl_iconHapus = findViewById(R.id.cl_icon4);
        img_iconBack = findViewById(R.id.img_icon1);
        img_iconHapus = findViewById(R.id.img_icon4);
        img_iconEdit = findViewById(R.id.img_icon3);
        img_logo_perusahaan = findViewById(R.id.iv_activity_detail_lowongan_logo);
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
        img_iconEdit.setImageResource(R.drawable.ic_edit);
        cl_iconBack.setVisibility(View.VISIBLE);

        cl_iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if (JENIS_USER.equalsIgnoreCase(JENIS_USER_OPERATOR)) {
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
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + lowonganModel.getNo_telp()));
                if (ActivityCompat.checkSelfPermission(DetailLowonganActivity.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);
            }
        });

        cl_iconEdit = findViewById(R.id.cl_icon3);
        cl_iconEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editPermintaanLowongan();
            }
        });
        cl_iconHapus = findViewById(R.id.cl_icon4);
        cl_iconHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hapusPermintaanLowongan();
            }
        });
    }

    private void hapusPermintaanLowongan(){
        Toast.makeText(this, "hapus lowongan", Toast.LENGTH_SHORT).show();
    }

    private void editPermintaanLowongan(){
        Toast.makeText(this, "edit lowongan", Toast.LENGTH_SHORT).show();
//        if (lowonganModel != null){
//            Intent intent = new Intent(DetailLowonganActivity.this, TambahLowonganActivity.class);
//            intent.putExtra("object_lowongan")
//        }
    }

    private void showKonfirmasiHapus() {
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

    private void deleteLowongan(Integer idLowongan) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<Void> call = jsonPlaceHolderApi.deleteLowongan(idLowongan);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                Intent i = new Intent(DetailLowonganActivity.this, MainActivity.class);
                i.putExtra(INDEX_OPENED_TAB_KEY, INDEX_OPENED_TAB);
                startActivity(i);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                if (t.getMessage().contains("Failed to connect")) {
                    Toast.makeText(DetailLowonganActivity.this, TEXT_NO_INTERNET, Toast.LENGTH_SHORT).show();
                }
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
                if (daftarModel.getStatus_data().equalsIgnoreCase("n")) {
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
                if (t.getMessage().contains("Failed to connect")) {
                    Toast.makeText(DetailLowonganActivity.this, TEXT_NO_INTERNET, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void getLowonganFromId(Integer id_lowongan) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<LowonganModel> call = jsonPlaceHolderApi.getLowonganFromId(id_lowongan);
        call.enqueue(new Callback<LowonganModel>() {
            @Override
            public void onResponse(Call<LowonganModel> call, Response<LowonganModel> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                lowonganModel = response.body();
                tvNamaLowongan.setText(lowonganModel.getNama_lowongan());
                tvNamaPerusahaan.setText(lowonganModel.getNama_perusahaan());
                tvLokasi.setText(lowonganModel.getAlamat_perusahaan());
                tvKisaranGaji.setText("~Rp " + lowonganModel.getKisaran_gaji() + " juta");
                tvSyarat.setText(lowonganModel.getSyarat_pekerjaan());
                tvKuota.setText(lowonganModel.getKuota() + " orang");
                tvJabatan.setText(lowonganModel.getJabatan());
                tvWeb.setText(lowonganModel.getWebsite());
                tvEmail.setText(lowonganModel.getEmail());
                tvTelepon.setText(lowonganModel.getNo_telp());
                tvHubungi.setText(lowonganModel.getCp());
                oldPath = lowonganModel.getLogo_perusahaan();
                Glide.with(DetailLowonganActivity.this)
                        .load(BASE_URL + oldPath)
                        .into(img_logo_perusahaan);
                idLowongan = lowonganModel.getIdLowongan();
                getNama();

            }

            @Override
            public void onFailure(Call<LowonganModel> call, Throwable t) {
                if (t.getMessage().contains("Failed to connect")) {
                    Toast.makeText(DetailLowonganActivity.this, TEXT_NO_INTERNET, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
