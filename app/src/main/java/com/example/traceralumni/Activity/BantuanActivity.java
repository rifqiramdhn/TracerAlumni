package com.example.traceralumni.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.traceralumni.R;

public class BantuanActivity extends AppCompatActivity {
    TextView tvNavBar;
    ConstraintLayout cl_iconBack;
    ImageView img_iconBack;
    ConstraintLayout wa, telp, email, wa2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bantuan);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        tvNavBar = findViewById(R.id.tv_navbar_top);
        tvNavBar.setText("HUBUNGI KAMI");
        img_iconBack = findViewById(R.id.img_icon1);
        img_iconBack.setImageResource(R.drawable.ic_arrow_back);
        cl_iconBack = findViewById(R.id.cl_icon1);
        cl_iconBack.setVisibility(View.VISIBLE);

        cl_iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        wa = findViewById(R.id.cl_bantuan_whatsapp);
        wa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                String urlNew = "http://wa.me/6281333555047";
                intent.setData(Uri.parse(urlNew));
                startActivity(intent);
            }
        });
        wa2 = findViewById(R.id.cl_bantuan_whatsapp2);
        wa2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                String urlNew = "http://wa.me/6281333444861";
                intent.setData(Uri.parse(urlNew));
                startActivity(intent);
            }
        });
        telp = findViewById(R.id.cl_bantuan_telp);
        telp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(BantuanActivity.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(BantuanActivity.this,
                            new String[]{Manifest.permission.CALL_PHONE}, 1);
                } else {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:0341555000"));
                    startActivity(callIntent);
                }
            }
        });
        email = findViewById(R.id.cl_bantuan_email);
        final String kirim [] = {"feb@ub.ac.id"};
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("message/rfc822");
                intent.putExtra(Intent.EXTRA_EMAIL, kirim);
                intent.putExtra(Intent.EXTRA_SUBJECT, "Layanan bantuan TracerAlumni");
                intent.putExtra(Intent.EXTRA_TEXT, "");
                startActivity(Intent.createChooser(intent, "Pilih klien email"));
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:0341555000"));
                    startActivity(callIntent);
                }
                return;
            }
        }
    }
}
