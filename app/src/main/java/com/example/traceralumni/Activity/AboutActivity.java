package com.example.traceralumni.Activity;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.traceralumni.R;

public class AboutActivity extends AppCompatActivity {
    TextView tvNavBar, tvChangeLog, tvVersion;
    ConstraintLayout cl_iconBack;
    ImageView img_iconBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        tvNavBar = findViewById(R.id.tv_navbar_top);
        tvNavBar.setText("TENTANG");
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

        tvChangeLog = findViewById(R.id.tv_changelog);
        tvVersion = findViewById(R.id.tv_current_version);

        String currentVersion = "v1.0";
        String changeLog = "v1.0 15 Agustus 2019" +
                "\n- Aplikasi rilis";

        tvVersion.setText(currentVersion);
        tvChangeLog.setText(changeLog);
    }
}
