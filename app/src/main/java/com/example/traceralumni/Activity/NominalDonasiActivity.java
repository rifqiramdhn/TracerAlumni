package com.example.traceralumni.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
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
import static com.example.traceralumni.Activity.MainActivity.SHARE_PREFS;
import static com.example.traceralumni.Activity.MainActivity.NIM;
import static com.example.traceralumni.Activity.MainActivity.NIM_PREF;

public class NominalDonasiActivity extends AppCompatActivity {
    ConstraintLayout cl_iconBack, cl_iconConfirm;
    ImageView img_iconBack, img_iconConfirm;
    TextView tv_navbar;
    EditText edt_lainnya;
    RadioGroup rdGroup;
    AlertDialog.Builder builder;

    Integer idDonasi;
    Double totalBantuan;
    String nim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nominal_donasi);
        builder = new AlertDialog.Builder(this);
        initView();
        getData();
        Toast.makeText(this, "" + idDonasi, Toast.LENGTH_SHORT).show();
    }

    private void initView() {
        rdGroup = findViewById(R.id.rdGroup);
        edt_lainnya = findViewById(R.id.edt_lainnya);
        cl_iconBack = findViewById(R.id.cl_icon1);
        cl_iconConfirm = findViewById(R.id.cl_icon4);
        img_iconBack = findViewById(R.id.img_icon1);
        img_iconConfirm = findViewById(R.id.img_icon4);
        tv_navbar = findViewById(R.id.tv_navbar_top);
        tv_navbar.setText("PILIH NOMINAL");
        cl_iconBack.setVisibility(View.VISIBLE);
        cl_iconConfirm.setVisibility(View.VISIBLE);
        img_iconBack.setImageResource(R.drawable.ic_arrow_back);
        img_iconConfirm.setImageResource(R.drawable.ic_check);
        img_iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        img_iconConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showKonfirmasiDialog();
            }
        });
        edt_lainnya.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    rdGroup.clearCheck();
                    edt_lainnya.requestFocus();
                }
            }
        });

        rdGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rdBtn_1:
                        totalBantuan = 5000000.0;
                        edt_lainnya.setText("");
                        edt_lainnya.clearFocus();
                        break;
                    case R.id.rdBtn_2:
                        totalBantuan = 10000000.0;
                        edt_lainnya.setText("");
                        edt_lainnya.clearFocus();
                        break;
                    case R.id.rdBtn_3:
                        totalBantuan = 20000000.0;
                        edt_lainnya.setText("");
                        edt_lainnya.clearFocus();
                        break;
                    case R.id.rdBtn_4:
                        totalBantuan = 50000000.0;
                        edt_lainnya.setText("");
                        edt_lainnya.clearFocus();
                        break;
                    case R.id.rdBtn_5:
                        totalBantuan = 100000000.0;
                        edt_lainnya.setText("");
                        edt_lainnya.clearFocus();
                        break;
                    case R.id.rdBtn_6:
                        totalBantuan = 200000000.0;
                        edt_lainnya.setText("");
                        edt_lainnya.clearFocus();
                        break;
                    case R.id.rdBtn_7:
                        totalBantuan = 500000000.0;
                        edt_lainnya.setText("");
                        edt_lainnya.clearFocus();
                        break;
                    case R.id.rdBtn_8:
                        totalBantuan = 1000000000.0;
                        edt_lainnya.setText("");
                        edt_lainnya.clearFocus();
                        break;
                }
            }
        });
    }

    private void getData() {
        Intent intent = getIntent();
        idDonasi = intent.getIntExtra("id_donasi", 0);

        nim = NIM;
    }

    private void showKonfirmasiDialog() {
        builder.setMessage("Apakah nominal donasi yang anda masukkan sudah benar?");
        builder.setTitle("Konfirmasi Donasi");
        builder.setCancelable(false);
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!edt_lainnya.getText().toString().equals("")) {
//                    totalBantuan = Integer.valueOf(edt_lainnya.getText().toString().trim());
                    totalBantuan = Double.parseDouble(edt_lainnya.getText().toString().trim());
                }

                if (edt_lainnya.getText().toString().equals("") && rdGroup.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(NominalDonasiActivity.this, "Nominal belum ditentukan", Toast.LENGTH_SHORT).show();
                } else {
                    saveData(totalBantuan);
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

    private void saveData(Double totalBantuan) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<Void> call = jsonPlaceHolderApi.createPermintaanDonasi(idDonasi, nim, totalBantuan);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    return;
                }

                Toast.makeText(NominalDonasiActivity.this, "Menunggu Konfirmasi Operator", Toast.LENGTH_SHORT).show();

                onBackPressed();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(NominalDonasiActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
