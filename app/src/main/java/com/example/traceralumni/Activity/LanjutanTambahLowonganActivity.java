package com.example.traceralumni.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.ParseException;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.traceralumni.Fragment.LowonganFragment;
import com.example.traceralumni.JsonPlaceHolderApi;
import com.example.traceralumni.Model.DaftarModel;
import com.example.traceralumni.Model.PathModel;
import com.example.traceralumni.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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
import static com.example.traceralumni.Activity.MainActivity.NIM;

public class LanjutanTambahLowonganActivity extends AppCompatActivity {
    private ConstraintLayout cl_icon_back, cl_icon_ok;
    private ImageView img_icon_back, img_icon_ok;
    private TextView tv_navbar;
    private String status;
    Integer kuota;
    String judulLowongan, jabatan, namaPerusahaan, alamatPerusahaan, gaji, syarat, website, email, notelp, cp, tanggal_lowongan, logo;
    EditText edt_syarat, edt_website, edt_email, edt_notelp, edt_cp;

    AlertDialog.Builder builder;
    DaftarModel daftarModel;

    Uri uriTerima;
    String photoPath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lanjutan_tambah_lowongan);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        builder = new AlertDialog.Builder(this);

        getData();
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
                if (edt_syarat.getText().toString().equalsIgnoreCase("")){
                    edt_syarat.setError("Harap isi syarat pekerjaan");
                } else if (edt_website.getText().toString().equalsIgnoreCase("")){
                    edt_website.setError("Harap isi website");
                } else if (edt_email.getText().toString().equalsIgnoreCase("")){
                    edt_email.setError("Harap isi email");
                } else if (edt_notelp.getText().toString().equalsIgnoreCase("")){
                    edt_notelp.setError("Harap isi nomor telepon");
                } else if (edt_cp.getText().toString().equalsIgnoreCase("")){
                    edt_cp.setError("Harap isi kontak yang dapat dihubungi");
                } else {
                    showKonfirmasiTambah();
                }
            }
        });
        Intent i = getIntent();
        judulLowongan = i.getStringExtra("judulLowongan");
        jabatan = i.getStringExtra("jabatan");
        namaPerusahaan = i.getStringExtra("namaPerusahaan");
        alamatPerusahaan = i.getStringExtra("alamat");
        kuota = Integer.valueOf(i.getStringExtra("kuota"));
        gaji = i.getStringExtra("gaji");
        logo = i.getStringExtra("logo");
        uriTerima = Uri.parse(i.getStringExtra("uri"));
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

    private void getTanggalLowongan() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = new Date();
            tanggal_lowongan = dateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
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
                getTanggalLowongan();
                uploadPhoto(uriTerima);
//                if(JENIS_USER.equalsIgnoreCase(JENIS_USER_ALUMNI)){
//                    saveData(daftarModel.getNim(), judulLowongan, jabatan, namaPerusahaan, alamatPerusahaan, kuota, gaji, syarat, website, email, notelp, cp, "BelumValid",tanggal_lowongan, logo);
//                } else {
//                    saveData("Admin", judulLowongan, jabatan, namaPerusahaan, alamatPerusahaan, kuota, gaji, syarat, website, email, notelp, cp, "Valid",tanggal_lowongan, logo);
//                }
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

    private void saveData(String username, String judulLowongan, String jabatan, String namaPerusahaan, String alamat, Integer kuota, String gaji, String syarat, String website, String email, String notelp, String cp, String status, String tglLowongan, String logo) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<Void> call = jsonPlaceHolderApi.createLowongan(username, judulLowongan, jabatan, namaPerusahaan, alamat, kuota, gaji, syarat, website, email, notelp, cp, status, tglLowongan, logo);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    return;
                }

                if (JENIS_USER.equalsIgnoreCase(JENIS_USER_ALUMNI)){
                    Toast.makeText(LanjutanTambahLowonganActivity.this, "Tunggu konfirmasi dari operator", Toast.LENGTH_SHORT).show();
                    Intent a = new Intent(LanjutanTambahLowonganActivity.this, MainActivity.class);
                    a.putExtra(INDEX_OPENED_TAB_KEY, INDEX_OPENED_TAB);
                    startActivity(a);
                } else {
                    Toast.makeText(LanjutanTambahLowonganActivity.this, "Lowongan Berhasil Ditambahkan", Toast.LENGTH_SHORT).show();
                    Intent a = new Intent(LanjutanTambahLowonganActivity.this, MainActivity.class);
                    a.putExtra(INDEX_OPENED_TAB_KEY, INDEX_OPENED_TAB);
                    startActivity(a);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(LanjutanTambahLowonganActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getData() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<DaftarModel> call = jsonPlaceHolderApi.getUserData(NIM);
        call.enqueue(new Callback<DaftarModel>() {
            @Override
            public void onResponse(Call<DaftarModel> call, Response<DaftarModel> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                daftarModel = response.body();
            }

            @Override
            public void onFailure(Call<DaftarModel> call, Throwable t) {
                Toast.makeText(LanjutanTambahLowonganActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void uploadPhoto(Uri fileUri) {
        File file = new File(getRealPathFromURI(fileUri));
        File compressedFile = new File(getRealPathFromURI(fileUri));
        try {
            compressedFile = new Compressor(this).compressToFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse(getContentResolver().getType(fileUri)), compressedFile);
        MultipartBody.Part kirim = MultipartBody.Part.createFormData("image", file.getName(), requestBody);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<PathModel> call = jsonPlaceHolderApi.uploadPhoto(kirim);
        call.enqueue(new Callback<PathModel>() {
            @Override
            public void onResponse(Call<PathModel> call, Response<PathModel> response) {
                if (!response.isSuccessful()){
                    return;
                }
                PathModel pathModel = response.body();
                if (!pathModel.getPath().equals("invalid")){
                    photoPath = pathModel.getPath();
                    if(JENIS_USER.equalsIgnoreCase(JENIS_USER_ALUMNI)){
                        saveData(daftarModel.getNim(), judulLowongan, jabatan, namaPerusahaan, alamatPerusahaan, kuota, gaji, syarat, website, email, notelp, cp, "BelumValid",tanggal_lowongan, photoPath);
                    } else {
                        saveData("Admin", judulLowongan, jabatan, namaPerusahaan, alamatPerusahaan, kuota, gaji, syarat, website, email, notelp, cp, "Valid",tanggal_lowongan, photoPath);
                    }
                } else {
                    Toast.makeText(LanjutanTambahLowonganActivity.this, "Gagal Upload", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PathModel> call, Throwable t) {
                Toast.makeText(LanjutanTambahLowonganActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getRealPathFromURI(Uri contentUri){
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(this, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }
}
