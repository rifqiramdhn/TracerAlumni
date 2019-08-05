package com.example.traceralumni.Activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.traceralumni.JsonPlaceHolderApi;
import com.example.traceralumni.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.traceralumni.Activity.MainActivity.BASE_URL;
import static com.example.traceralumni.Activity.MainActivity.NIM;
import static com.example.traceralumni.Activity.MainActivity.TEXT_NO_INTERNET;

public class ChangePasswordActivity extends AppCompatActivity {
    TextView tvNavBar;
    ConstraintLayout cl_iconBack, cl_iconConfirm;
    ImageView img_iconBack, img_iconConfirm;
    AlertDialog.Builder builder;
    EditText edtOldPass, edtNewPass, edtNewPassConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        builder = new AlertDialog.Builder(this);
        initView();
    }

    private void saveData() {
        if (edtOldPass.getText().toString().equals("")) {
            edtOldPass.setError("Wajib diisi");
        } else if (edtNewPass.getText().toString().equals("")) {
            edtNewPass.setError("Wajib diisi");
        } else if (edtNewPass.getText().length() < 8) {
            edtNewPass.setError("Panjang kata sandi minimal 8 karakter");
        } else if (!edtNewPass.getText().toString().equals(edtNewPassConfirm.getText().toString())) {
            edtNewPassConfirm.setError("Konfirmasi kata sandi tidak sesuai");
        } else {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
            Call<String> call = jsonPlaceHolderApi.changePassword(NIM, edtOldPass.getText().toString(), edtNewPass.getText().toString());
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (!response.isSuccessful()) {
                        return;
                    }
                    String kode = response.body();
                    if (kode.equals("1")) {
                        Toast.makeText(ChangePasswordActivity.this, "Password berhasil diperbaharui", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    } else {
                        Toast.makeText(ChangePasswordActivity.this, "Kata sandi lama anda salah", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    if (t.getMessage().contains("Failed to connect")) {
                        Toast.makeText(ChangePasswordActivity.this, TEXT_NO_INTERNET, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void initView() {
        edtNewPass = findViewById(R.id.edt_new_pass);
        edtNewPassConfirm = findViewById(R.id.edt_new_pass_confirm);
        edtOldPass = findViewById(R.id.edt_old_pass);
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
                showKonfirmasiDialog();
            }
        });
    }

    private void showKonfirmasiDialog() {
        builder.setMessage("Anda yakin ingin mengganti kata sandi?");
        builder.setTitle("Ganti kata sandi");
        builder.setCancelable(false);
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveData();
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


}
