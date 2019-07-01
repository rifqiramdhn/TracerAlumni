package com.example.traceralumni.Activity;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.traceralumni.R;

public class ChangePasswordActivity extends AppCompatActivity {
    TextView tvNavBar;
    ConstraintLayout cl_iconBack, cl_iconConfirm;
    ImageView img_iconBack, img_iconConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        tvNavBar = findViewById(R.id.tv_navbar_top);
        tvNavBar.setText("GANTI KATA SANDI");

        img_iconBack = findViewById(R.id.img_icon1);
        img_iconBack.setImageResource(R.drawable.ic_arrow_back);

        img_iconConfirm = findViewById(R.id.img_icon4);
        img_iconConfirm.setImageResource(R.drawable.ic_check);

        cl_iconBack = findViewById(R.id.cl_icon1);
        cl_iconBack.setVisibility(View.VISIBLE);

        cl_iconConfirm = findViewById(R.id.cl_icon4);
        cl_iconConfirm.setVisibility(View.VISIBLE);

        cl_iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        cl_iconConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogConfirmation();
            }
        });
    }

    public void showDialogConfirmation(){

    }
}
