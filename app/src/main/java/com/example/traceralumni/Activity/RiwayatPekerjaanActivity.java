package com.example.traceralumni.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.traceralumni.Adapter.RiwayatPekerjaanAdapter;
import com.example.traceralumni.JsonPlaceHolderApi;
import com.example.traceralumni.Model.RiwayatPekerjaanModel;
import com.example.traceralumni.R;
import com.example.traceralumni.Activity.TambahRiwayatPekerjaanActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.traceralumni.Activity.MainActivity.BASE_URL;
import static com.example.traceralumni.Activity.MainActivity.NIM;
import static com.example.traceralumni.Activity.MainActivity.TEXT_NO_INTERNET;

public class RiwayatPekerjaanActivity extends AppCompatActivity {
    TextView tvNavBar;
    ConstraintLayout cl_iconBack, cl_iconTambah;
    ImageView img_iconBack, img_iconTambah;
    RecyclerView riwayatRecycler;
    RiwayatPekerjaanAdapter riwayatAdapter;
    ArrayList<RiwayatPekerjaanModel> riwayatModels;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_pekerjaan);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        initView();
        riwayatModels = new ArrayList<>();
        riwayatRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        riwayatAdapter = new RiwayatPekerjaanAdapter(this, riwayatModels);
        riwayatRecycler.setAdapter(riwayatAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getRiwayatPekerjaan();
    }

    private void initView(){
        riwayatRecycler = findViewById(R.id.rv_riwayat_pekerjaan);
        tvNavBar = findViewById(R.id.tv_navbar_top);
        tvNavBar.setText("RIWAYAT PEKERJAAN");
        img_iconBack = findViewById(R.id.img_icon1);
        img_iconBack.setImageResource(R.drawable.ic_arrow_back);
        img_iconTambah = findViewById(R.id.img_icon4);
        img_iconTambah.setImageResource(R.drawable.ic_add);
        cl_iconBack = findViewById(R.id.cl_icon1);
        cl_iconBack.setVisibility(View.VISIBLE);
        cl_iconTambah = findViewById(R.id.cl_icon4);
        cl_iconTambah.setVisibility(View.VISIBLE);
        cl_iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        cl_iconTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RiwayatPekerjaanActivity.this, TambahRiwayatPekerjaanActivity.class);
                startActivity(i);
            }
        });
    }

    private void getRiwayatPekerjaan(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<ArrayList<RiwayatPekerjaanModel>> call = jsonPlaceHolderApi.getRiwayat(NIM);
        call.enqueue(new Callback<ArrayList<RiwayatPekerjaanModel>>() {
            @Override
            public void onResponse(Call<ArrayList<RiwayatPekerjaanModel>> call, Response<ArrayList<RiwayatPekerjaanModel>> response) {
                if (!response.isSuccessful()){
                    return;
                }

                riwayatModels.clear();
                ArrayList<RiwayatPekerjaanModel> riwayatPekerjaanModels = response.body();
                if (riwayatPekerjaanModels.get(0).getStatus_data().equals("y")){
                    riwayatModels.addAll(riwayatPekerjaanModels);
                    riwayatAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<RiwayatPekerjaanModel>> call, Throwable t) {
                if (t.getMessage().contains("Failed to connect")) {
                    Toast.makeText(RiwayatPekerjaanActivity.this, TEXT_NO_INTERNET, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
