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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.traceralumni.R;

import java.io.FileNotFoundException;
import java.io.InputStream;

import static com.example.traceralumni.Activity.MainActivity.INDEX_OPENED_TAB;
import static com.example.traceralumni.Activity.MainActivity.JENIS_USER;
import static com.example.traceralumni.Activity.MainActivity.JENIS_USER_ALUMNI;
import static com.example.traceralumni.Activity.MainActivity.JENIS_USER_OPERATOR;
import static com.example.traceralumni.Activity.SuntingProfilActivity.PICK_PHOTO_REQUEST;

public class TambahLowonganActivity extends AppCompatActivity {

    private ConstraintLayout cl_icon_back;
    private ImageView img_icon_back, img_logo;
    private TextView tv_navbar;
    private Button btn_next;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_lowongan);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        cl_icon_back = findViewById(R.id.cl_icon1);
        img_icon_back = findViewById(R.id.img_icon1);
        tv_navbar = findViewById(R.id.tv_navbar_top);
        btn_next = findViewById(R.id.btn_next);

        img_logo = findViewById(R.id.iv_tambah_lowongan_logo);

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
                if (JENIS_USER.equalsIgnoreCase(JENIS_USER_ALUMNI)){
                    Intent i = new Intent(TambahLowonganActivity.this, LanjutanTambahLowonganActivity.class);
                    i.putExtra("Tambah", INDEX_OPENED_TAB);
                    startActivity(i);
                } else if (JENIS_USER.equalsIgnoreCase(JENIS_USER_OPERATOR)){
                    Intent i = new Intent(TambahLowonganActivity.this, LanjutanTambahLowonganActivity.class);
                    i.putExtra("Tab", INDEX_OPENED_TAB);
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), "Lanjut", Toast.LENGTH_SHORT).show();
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
}
