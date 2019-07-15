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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.traceralumni.Fragment.LowonganFragment;
import com.example.traceralumni.JsonPlaceHolderApi;
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
import static com.example.traceralumni.Activity.MainActivity.JENIS_USER_PIMPINAN;

public class LanjutanTambahLowonganActivity extends AppCompatActivity {
    private ConstraintLayout cl_icon_back, cl_icon_ok;
    private ImageView img_icon_back, img_icon_ok;
    private TextView tv_navbar;

    Integer idLowongan, kuota;
    String judulLowongan, jabatan, namaPerusahaan, alamatPerusahaan, gaji, syarat, website, email, notelp, cp;
    EditText edt_syarat, edt_website, edt_email, edt_notelp, edt_cp;

    AlertDialog.Builder builder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lanjutan_tambah_lowongan);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        builder = new AlertDialog.Builder(this);

        initView();

        img_icon_back.setImageResource(R.drawable.ic_arrow_back);
        img_icon_ok.setImageResource(R.drawable.ic_check);

        tv_navbar.setText("TAMBAH LOWONGAN");

        cl_icon_back.setVisibility(View.VISIBLE);
        cl_icon_ok.setVisibility(View.VISIBLE);

        cl_icon_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        cl_icon_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showKonfirmasiTambah();
            }
        });

        Intent i = getIntent();
        judulLowongan = i.getStringExtra("judulLowongan");
        jabatan = i.getStringExtra("jabatan");
        namaPerusahaan = i.getStringExtra("namaPerusahaan");
        alamatPerusahaan = i.getStringExtra("alamat");
        kuota = i.getIntExtra("kuota", 0);
        gaji = i.getStringExtra("gaji");
    }

    private void initView() {
        cl_icon_back = findViewById(R.id.cl_icon1);
        img_icon_back = findViewById(R.id.img_icon1);
        cl_icon_ok = findViewById(R.id.cl_icon4);
        img_icon_ok = findViewById(R.id.img_icon4);
        tv_navbar = findViewById(R.id.tv_navbar_top);

        edt_syarat = findViewById(R.id.edt_syarat);
        edt_website = findViewById(R.id.edt_web);
        edt_email = findViewById(R.id.edt_email);
        edt_notelp = findViewById(R.id.edt_notelp);
        edt_cp = findViewById(R.id.edt_kontak);
    }

    private void showKonfirmasiTambah() {
        builder.setMessage("Apakah yakin anda akan menambahkan lowongan ini?" + "\nAnda tidak bisa mengedit lowongan lagi.");
        builder.setTitle("Konfirmasi Tambah Lowongan");
        builder.setCancelable(false);
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                syarat = edt_syarat.getText().toString().trim();
                website = edt_website.getText().toString().trim();
                email = edt_email.getText().toString().trim();
                notelp = edt_notelp.getText().toString().trim();
                cp = edt_cp.getText().toString().trim();

                saveData(judulLowongan, jabatan, namaPerusahaan, alamatPerusahaan, kuota, gaji, syarat, website, email, notelp, cp);
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

    private void saveData(String judulLowongan, String jabatan, String namaPerusahaan, String alamat, Integer kuota, String gaji, String syarat, String website, String email, String notelp, String cp) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<Void> call = jsonPlaceHolderApi.createLowongan(judulLowongan, jabatan, namaPerusahaan, alamat, kuota, gaji, syarat, website, email, notelp, cp);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    return;
                }

                Toast.makeText(LanjutanTambahLowonganActivity.this, "Tunggu konfirmasi dari operator", Toast.LENGTH_SHORT).show();

                Intent a = new Intent(LanjutanTambahLowonganActivity.this, MainActivity.class);
                a.putExtra(INDEX_OPENED_TAB_KEY, INDEX_OPENED_TAB);
                startActivity(a);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(LanjutanTambahLowonganActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
