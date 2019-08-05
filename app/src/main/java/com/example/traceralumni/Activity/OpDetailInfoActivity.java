package com.example.traceralumni.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.ParseException;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.traceralumni.Client;
import com.example.traceralumni.JsonApi;
import com.example.traceralumni.Model.InfoModel;
import com.example.traceralumni.R;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.traceralumni.Activity.MainActivity.BASE_URL;
import static com.example.traceralumni.Activity.MainActivity.TEXT_NO_INTERNET;

public class OpDetailInfoActivity extends AppCompatActivity {
    TextView tvNavBar;
    ConstraintLayout cl_iconBack, cl_iconHapus;
    ImageView img_iconBack, img_iconHapus;
    TextInputLayout tv_inpJudul, tv_inpIsi, tv_inpURL;
    EditText edt_judul, edt_isi, edt_url;
    Button btn_simpan;
    AlertDialog.Builder builder;

    Integer idInfo;
    String judul, isi, link, tanggal_info;

    int CAN_CLICK_BUTTON_SAVE = 0; //0 bisa diklik, 1 tidak bisa diklik

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_op_detail_info);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        builder = new AlertDialog.Builder(this);

        initView();

        getData();

        btn_simpan = findViewById(R.id.btn_simpan);
        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                judul = edt_judul.getText().toString().trim();
                isi = edt_isi.getText().toString().trim();
                link = edt_url.getText().toString().trim();
                getTanggalHariIni();
                if (edt_judul.getText().toString().equalsIgnoreCase("")) {
                    edt_judul.setError("Wajib diisi!");
                } else if (edt_isi.getText().toString().equalsIgnoreCase("")) {
                    edt_isi.setError("Wajib diisi!");
                } else if (edt_url.getText().toString().equalsIgnoreCase("")) {
                    edt_url.setError("Wajib diisi!");
                } else {
                    if (CAN_CLICK_BUTTON_SAVE == 0) {
                        CAN_CLICK_BUTTON_SAVE = 1;
                        saveData(idInfo, judul, isi, link, tanggal_info);
                    }
                }
            }
        });

    }

    private void getTanggalHariIni() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = new Date();
            tanggal_info = dateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void hapusInfo() {
        builder.setMessage("Anda yakin ingin menghapus info ini?");
        builder.setCancelable(false);
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteInfoID(idInfo);
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

    private void deleteInfoID(Integer idInfo) {
        JsonApi jsonApi = Client.getClient().create(JsonApi.class);

        Call<Void> call = jsonApi.deleteInfo(idInfo);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                Intent i = new Intent(OpDetailInfoActivity.this, MainActivity.class);
                startActivity(i);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                if (t.getMessage().contains("Failed to connect")) {
                    Toast.makeText(OpDetailInfoActivity.this, TEXT_NO_INTERNET, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initView() {
        tvNavBar = findViewById(R.id.tv_navbar_top);
        tvNavBar.setText("DETAIL INFO");

        img_iconBack = findViewById(R.id.img_icon1);
        img_iconBack.setImageResource(R.drawable.ic_arrow_back);

        img_iconHapus = findViewById(R.id.img_icon4);
        img_iconHapus.setImageResource(R.drawable.ic_delete);

        cl_iconBack = findViewById(R.id.cl_icon1);
        cl_iconBack.setVisibility(View.VISIBLE);

        cl_iconHapus = findViewById(R.id.cl_icon4);
        cl_iconHapus.setVisibility(View.VISIBLE);

        cl_iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        cl_iconHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hapusInfo();
            }
        });

        edt_judul = findViewById(R.id.edt_judul);
        edt_isi = findViewById(R.id.edt_isi);
        edt_url = findViewById(R.id.edt_url);

        tv_inpJudul = findViewById(R.id.edt_judul_container);
        tv_inpIsi = findViewById(R.id.edt_isi_container);
        tv_inpURL = findViewById(R.id.edt_url_container);
    }

    private void getData() {
        Intent intent = getIntent();
        InfoModel infoModel = intent.getParcelableExtra("object_info");
        if (infoModel != null) {
            edt_judul.setText(infoModel.getJudul());
            edt_isi.setText(infoModel.getKeterangan());
            edt_url.setText(infoModel.getLink());
            idInfo = infoModel.getIdInfo();
        } else {
            cl_iconHapus.setVisibility(View.GONE);
        }
    }

    private void saveData(Integer idInfo, String judul, String keterangan, String link, String tanggal_info) {
        JsonApi jsonApi = Client.getClient().create(JsonApi.class);

        Call<Void> call = jsonApi.createInfo(idInfo, judul, keterangan, link, tanggal_info);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    CAN_CLICK_BUTTON_SAVE = 0;
                    return;
                }
                CAN_CLICK_BUTTON_SAVE = 0;
                Toast.makeText(OpDetailInfoActivity.this, "Data tersimpan", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                CAN_CLICK_BUTTON_SAVE = 0;
                if (t.getMessage().contains("Failed to connect")) {
                    Toast.makeText(OpDetailInfoActivity.this, TEXT_NO_INTERNET, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
