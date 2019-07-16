package com.example.traceralumni.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.traceralumni.JsonPlaceHolderApi;
import com.example.traceralumni.R;

import java.io.FileNotFoundException;
import java.io.InputStream;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.traceralumni.Activity.MainActivity.BASE_URL;
import static com.example.traceralumni.Activity.MainActivity.INDEX_OPENED_TAB;
import static com.example.traceralumni.Activity.MainActivity.JENIS_USER;
import static com.example.traceralumni.Activity.MainActivity.JENIS_USER_ALUMNI;
import static com.example.traceralumni.Activity.MainActivity.JENIS_USER_OPERATOR;
import static com.example.traceralumni.Activity.MainActivity.JENIS_USER_PIMPINAN;
import static com.example.traceralumni.Activity.SuntingProfilActivity.PICK_PHOTO_REQUEST;

public class TambahLowonganActivity extends AppCompatActivity {

    private ConstraintLayout cl_icon_back;
    private ImageView img_icon_back, img_logo;
    private TextView tv_navbar;
    private Button btn_next;

    EditText edt_judulLowongan, edt_jabatan, edt_namaPerusahaan, edt_alamatPerusahaan, edt_kuota, edt_gaji;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_lowongan);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        initView();

        img_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPhotoFromGallery();
            }
        });

        img_icon_back.setImageResource(R.drawable.ic_arrow_back);

        tv_navbar.setText("TAMBAH LOWONGAN");

        cl_icon_back.setVisibility(View.VISIBLE);
        cl_icon_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_judulLowongan.getText().toString().equalsIgnoreCase("")){
                    edt_judulLowongan.setError("Harap isi judul lowongan");
                } else if (edt_jabatan.getText().toString().equalsIgnoreCase("")){
                    edt_jabatan.setError("Harap isi jabatan");
                } else if (edt_namaPerusahaan.getText().toString().equalsIgnoreCase("")){
                    edt_namaPerusahaan.setError("Harap isi nama perusahaan");
                } else if (edt_alamatPerusahaan.getText().toString().equalsIgnoreCase("")){
                    edt_alamatPerusahaan.setError("Harap isi alamat perusahaan");
                } else if (edt_kuota.getText().toString().equalsIgnoreCase("")){
                    edt_kuota.setError("Harap isi kuota");
                } else if (edt_gaji.getText().toString().equalsIgnoreCase("")){
                    edt_gaji.setError("Harap isi gaji");
                } else {
                    Intent i = new Intent(TambahLowonganActivity.this, LanjutanTambahLowonganActivity.class);
                    i.putExtra("judulLowongan", edt_judulLowongan.getText().toString().trim());
                    i.putExtra("jabatan", edt_jabatan.getText().toString().trim());
                    i.putExtra("namaPerusahaan", edt_namaPerusahaan.getText().toString().trim());
                    i.putExtra("alamat", edt_alamatPerusahaan.getText().toString().trim());
                    i.putExtra("kuota", edt_kuota.getText().toString().trim());
                    i.putExtra("gaji", edt_gaji.getText().toString().trim());
                    startActivity(i);
                }
            }
        });
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
                try {
                    final Uri imageUri = data.getData();
                    final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
//                    image_view.setImageBitmap(selectedImage);
                    uploadPhoto(selectedImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void uploadPhoto(Bitmap selectedImage) {
        //bla bla bla upload ke database
        //kalau berhasil maka
        setPhotoFromDatabase(selectedImage);
    }

    private void setPhotoFromDatabase(Bitmap photo) {
        img_logo.setImageBitmap(photo);
    }

    private void initView() {
        cl_icon_back = findViewById(R.id.cl_icon1);
        img_icon_back = findViewById(R.id.img_icon1);
        tv_navbar = findViewById(R.id.tv_navbar_top);
        img_logo = findViewById(R.id.iv_tambah_lowongan_logo);

        btn_next = findViewById(R.id.btn_next);
        edt_judulLowongan = findViewById(R.id.edt_lowongan);
        edt_jabatan = findViewById(R.id.edt_jabatan);
        edt_namaPerusahaan = findViewById(R.id.edt_perusahaan);
        edt_alamatPerusahaan = findViewById(R.id.edt_lokasi);
        edt_kuota = findViewById(R.id.edt_kuota);
        edt_gaji = findViewById(R.id.edt_gaji);
    }
}
