package com.example.traceralumni.Activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ParseException;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.traceralumni.Client;
import com.example.traceralumni.JsonApi;
import com.example.traceralumni.Model.LowonganModel;
import com.example.traceralumni.Model.PathModel;
import com.example.traceralumni.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.traceralumni.Activity.MainActivity.BASE_URL;
import static com.example.traceralumni.Activity.MainActivity.JENIS_USER;
import static com.example.traceralumni.Activity.MainActivity.JENIS_USER_ALUMNI;
import static com.example.traceralumni.Activity.MainActivity.NIM;
import static com.example.traceralumni.Activity.MainActivity.TEXT_NO_INTERNET;

public class TambahLowonganActivity extends AppCompatActivity {

    private ConstraintLayout cl_icon_back, cl_icon_ok;
    private ImageView img_icon_back, img_icon_ok;
    private TextView tv_navbar;
    LowonganModel lowonganModel;

    Integer kuota;
    String judulLowongan, jabatan, namaPerusahaan, alamatPerusahaan, gaji, syarat, website, email, notelp, cp, tanggal_lowongan, logo;
    EditText edt_syarat, edt_website, edt_email, edt_notelp, edt_cp, edt_judulLowongan, edt_jabatan, edt_namaPerusahaan, edt_alamatPerusahaan, edt_kuota, edt_gaji;
    AlertDialog.Builder builder;
    String photoPath;
    Uri uriKirim;

    CircleImageView img_logo_lowongan, img_edit_logo_lowongan;

    int CAN_CLICK_BUTTON_SAVE = 0; //0 bisa diklik, 1 tidak bisa diklik

    static final int PICK_PHOTO_REQUEST = 1;
    Integer idLowongan;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_lowongan);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        builder = new AlertDialog.Builder(this);
        initView();
        getDataFromIntent();
        img_edit_logo_lowongan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(TambahLowonganActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(TambahLowonganActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    getPhotoFromGallery();
                }
            }
        });


        cl_icon_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        cl_icon_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uriKirim == null) {
                    Toast.makeText(TambahLowonganActivity.this, "Belum memilih foto", Toast.LENGTH_SHORT).show();
                } else if (edt_judulLowongan.getText().toString().equalsIgnoreCase("")) {
                    edt_judulLowongan.setError("Harap isi judul lowongan");
                } else if (edt_jabatan.getText().toString().equalsIgnoreCase("")) {
                    edt_jabatan.setError("Harap isi jabatan");
                } else if (edt_namaPerusahaan.getText().toString().equalsIgnoreCase("")) {
                    edt_namaPerusahaan.setError("Harap isi nama perusahaan");
                } else if (edt_alamatPerusahaan.getText().toString().equalsIgnoreCase("")) {
                    edt_alamatPerusahaan.setError("Harap isi alamat perusahaan");
                } else if (edt_kuota.getText().toString().equalsIgnoreCase("")) {
                    edt_kuota.setError("Harap isi kuota");
                } else if (edt_gaji.getText().toString().equalsIgnoreCase("")) {
                    edt_gaji.setError("Harap isi gaji");
                } else if (edt_syarat.getText().toString().equalsIgnoreCase("")) {
                    edt_syarat.setError("Harap isi syarat pekerjaan");
                } else if (edt_website.getText().toString().equalsIgnoreCase("")) {
                    edt_website.setError("Harap isi website");
                } else if (edt_email.getText().toString().equalsIgnoreCase("")) {
                    edt_email.setError("Harap isi email");
                } else if (edt_notelp.getText().toString().equalsIgnoreCase("")) {
                    edt_notelp.setError("Harap isi nomor telepon");
                } else if (edt_cp.getText().toString().equalsIgnoreCase("")) {
                    edt_cp.setError("Harap isi kontak yang dapat dihubungi");
                } else if (edt_cp.getText().length() < 10) {
                    edt_cp.setError("Nomor kontak tidak valid");
                } else {
                    showKonfirmasiTambah();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getPhotoFromGallery();
                }
                return;
            }
        }
    }

    private void getDataFromIntent() {
        lowonganModel = getIntent().getParcelableExtra("object_lowongan");
        if (lowonganModel != null) {
            if (!lowonganModel.getLogo_perusahaan().equals("")) {
                Glide.with(this)
                        .load(BASE_URL + lowonganModel.getLogo_perusahaan())
                        .into(img_logo_lowongan);
            }
            edt_judulLowongan.setText(lowonganModel.getNama_lowongan());
            edt_jabatan.setText(lowonganModel.getJabatan());
            edt_namaPerusahaan.setText(lowonganModel.getNama_perusahaan());
            edt_alamatPerusahaan.setText(lowonganModel.getAlamat_perusahaan());
            edt_gaji.setText(lowonganModel.getKisaran_gaji());
            edt_syarat.setText(lowonganModel.getSyarat_pekerjaan());
            edt_website.setText(lowonganModel.getWebsite());
            edt_email.setText(lowonganModel.getEmail());
            edt_notelp.setText(lowonganModel.getNo_telp());
            edt_cp.setText(lowonganModel.getCp());
            edt_kuota.setText(lowonganModel.getKuota());
            idLowongan = lowonganModel.getIdLowongan();
        }
    }

    private void getPhotoFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_PHOTO_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PICK_PHOTO_REQUEST) {
            if (resultCode == RESULT_OK) {
                Uri imageUri = data.getData();
                uriKirim = imageUri;
                Glide.with(this)
                        .load(new File(getRealPathFromURI(imageUri)))
                        .into(img_logo_lowongan);
            }
        }
    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(this, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    private void showKonfirmasiTambah() {
        if (idLowongan != null) {
            builder.setMessage("Apakah yakin anda akan merubah lowongan ini?");
        } else {
            builder.setMessage("Apakah yakin anda akan menambahkan lowongan ini?");
        }
        builder.setTitle("Konfirmasi Lowongan");
        builder.setCancelable(false);
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                judulLowongan = edt_judulLowongan.getText().toString().trim();
                jabatan = edt_jabatan.getText().toString().trim();
                namaPerusahaan = edt_namaPerusahaan.getText().toString().trim();
                alamatPerusahaan = edt_alamatPerusahaan.getText().toString().trim();
                gaji = edt_gaji.getText().toString().trim();
                syarat = edt_syarat.getText().toString().trim();
                website = edt_website.getText().toString().trim();
                email = edt_email.getText().toString().trim();
                notelp = edt_notelp.getText().toString().trim();
                kuota = Integer.valueOf(edt_kuota.getText().toString());

                if (edt_cp.getText().toString().charAt(0) == '0') {
                    cp = "+62" + edt_cp.getText().toString().substring(1, edt_cp.getText().length());
                } else if (edt_cp.getText().toString().charAt(0) != '+') {
                    cp = "+" + edt_cp.getText();
                } else {
                    cp = edt_cp.getText().toString();
                }

                if (CAN_CLICK_BUTTON_SAVE == 0) {
                    CAN_CLICK_BUTTON_SAVE = 1;
                    getTanggalLowongan();
                    uploadPhoto(uriKirim);
                }
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

    private void saveData(Integer idLowongan, String nim, String judulLowongan, String jabatan, String namaPerusahaan, String alamat, Integer kuota, String gaji, String syarat, String website, String email, String notelp, String cp, String status, String tglLowongan, String logo) {
        JsonApi jsonApi = Client.getClient().create(JsonApi.class);

        Call<Void> call;
        if (idLowongan != null) {
            call = jsonApi.updateLowongan(idLowongan, judulLowongan, jabatan, namaPerusahaan, alamat, kuota, gaji, syarat, website, email, notelp, cp, status, tglLowongan, logo);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (!response.isSuccessful()) {
                        CAN_CLICK_BUTTON_SAVE = 0;
                        return;
                    }
                    CAN_CLICK_BUTTON_SAVE = 0;
                    if (JENIS_USER.equalsIgnoreCase(JENIS_USER_ALUMNI)) {
                        Toast.makeText(TambahLowonganActivity.this, "Tunggu konfirmasi dari operator", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    } else {
                        Toast.makeText(TambahLowonganActivity.this, "Lowongan Berhasil Disimpan", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    CAN_CLICK_BUTTON_SAVE = 0;
                    if (t.getMessage().contains("Failed to connect")) {
                        Toast.makeText(TambahLowonganActivity.this, TEXT_NO_INTERNET, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            call = jsonApi.createLowongan(nim, judulLowongan, jabatan, namaPerusahaan, alamat, kuota, gaji, syarat, website, email, notelp, cp, status, tglLowongan, logo);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (!response.isSuccessful()) {
                        CAN_CLICK_BUTTON_SAVE = 0;
                        return;
                    }
                    CAN_CLICK_BUTTON_SAVE = 0;
                    if (JENIS_USER.equalsIgnoreCase(JENIS_USER_ALUMNI)) {
                        Toast.makeText(TambahLowonganActivity.this, "Tunggu konfirmasi dari operator", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    } else {
                        Toast.makeText(TambahLowonganActivity.this, "Lowongan Berhasil Ditambahkan", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    CAN_CLICK_BUTTON_SAVE = 0;
                    if (t.getMessage().contains("Failed to connect")) {
                        Toast.makeText(TambahLowonganActivity.this, TEXT_NO_INTERNET, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
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

        final JsonApi jsonApi = Client.getClient().create(JsonApi.class);

        Call<PathModel> call = jsonApi.uploadPhoto(kirim);
        call.enqueue(new Callback<PathModel>() {
            @Override
            public void onResponse(Call<PathModel> call, Response<PathModel> response) {
                if (!response.isSuccessful()) {
                    CAN_CLICK_BUTTON_SAVE = 0;
                    return;
                }
                PathModel pathModel = response.body();
                if (!pathModel.getPath().equals("invalid")) {
                    photoPath = pathModel.getPath();
                    if (JENIS_USER.equalsIgnoreCase(JENIS_USER_ALUMNI)) {
                        saveData(idLowongan, NIM, judulLowongan, jabatan, namaPerusahaan, alamatPerusahaan, kuota, gaji, syarat, website, email, notelp, cp, "BelumValid", tanggal_lowongan, photoPath);
                    } else {
                        saveData(idLowongan, "Admin", judulLowongan, jabatan, namaPerusahaan, alamatPerusahaan, kuota, gaji, syarat, website, email, notelp, cp, "Valid", tanggal_lowongan, photoPath);
                    }
                } else {
                    CAN_CLICK_BUTTON_SAVE = 0;
                    Toast.makeText(TambahLowonganActivity.this, "Gagal Upload Photo", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PathModel> call, Throwable t) {
                CAN_CLICK_BUTTON_SAVE = 0;
                if (t.getMessage().contains("Failed to connect")) {
                    Toast.makeText(TambahLowonganActivity.this, TEXT_NO_INTERNET, Toast.LENGTH_SHORT).show();
                }
            }
        });
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

    private void initView() {
        cl_icon_back = findViewById(R.id.cl_icon1);
        img_icon_back = findViewById(R.id.img_icon1);
        tv_navbar = findViewById(R.id.tv_navbar_top);
        img_logo_lowongan = findViewById(R.id.iv_tambah_lowongan_logo);
        img_edit_logo_lowongan = findViewById(R.id.iv_edit_lowongan_logo);
        edt_judulLowongan = findViewById(R.id.edt_lowongan);
        edt_jabatan = findViewById(R.id.edt_riwayat_pekerjaan);
        edt_namaPerusahaan = findViewById(R.id.edt_perusahaan);
        edt_alamatPerusahaan = findViewById(R.id.edt_riwayat_lokasi);
        edt_kuota = findViewById(R.id.edt_kuota);
        edt_gaji = findViewById(R.id.edt_riwayat_gaji);

        cl_icon_ok = findViewById(R.id.cl_icon4);
        img_icon_ok = findViewById(R.id.img_icon4);
        tv_navbar = findViewById(R.id.tv_navbar_top);

        edt_syarat = findViewById(R.id.edt_syarat);
        edt_website = findViewById(R.id.edt_web);
        edt_email = findViewById(R.id.edt_email);
        edt_notelp = findViewById(R.id.edt_notelp);
        edt_cp = findViewById(R.id.edt_kontak);

        img_icon_ok.setImageResource(R.drawable.ic_check);
        cl_icon_ok.setVisibility(View.VISIBLE);
        img_icon_back.setImageResource(R.drawable.ic_arrow_back);
        tv_navbar.setText("TAMBAH LOWONGAN");
        cl_icon_back.setVisibility(View.VISIBLE);
    }
}
