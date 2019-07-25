package com.example.traceralumni.Activity;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.traceralumni.Adapter.DonasiAdapter;
import com.example.traceralumni.Adapter.PermintaanDonasiAdapter;
import com.example.traceralumni.JsonPlaceHolderApi;
import com.example.traceralumni.Model.DonasiModel;
import com.example.traceralumni.Model.PermintaanDonasiModel;
import com.example.traceralumni.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.traceralumni.Activity.MainActivity.BASE_URL;
import static com.example.traceralumni.Activity.MainActivity.NIM;
import static com.example.traceralumni.Activity.MainActivity.TEXT_NO_INTERNET;

public class StatusPermintaanDonasiActivity extends AppCompatActivity {
    TextView tvNavBar;
    ConstraintLayout cl_iconBack;
    ImageView img_iconBack;
    RecyclerView rvStatusPermintaanDonasi;
    ArrayList<PermintaanDonasiModel> permintaanDonasiModels;
    PermintaanDonasiAdapter statusPermintaanDonasiAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_permintaan_donasi);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        tvNavBar = findViewById(R.id.tv_navbar_top);
        tvNavBar.setText("STATUS DONASI");
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
        rvStatusPermintaanDonasi = findViewById(R.id.rv_status_permintaan_donasi);
        permintaanDonasiModels = new ArrayList<>();
        rvStatusPermintaanDonasi.setLayoutManager(new LinearLayoutManager(StatusPermintaanDonasiActivity.this, LinearLayoutManager.VERTICAL, false));
        statusPermintaanDonasiAdapter = new PermintaanDonasiAdapter(StatusPermintaanDonasiActivity.this, permintaanDonasiModels);
        rvStatusPermintaanDonasi.setAdapter(statusPermintaanDonasiAdapter);

        getAllStatusPermintaanDonasi();
    }

    private void getAllStatusPermintaanDonasi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<ArrayList<PermintaanDonasiModel>> call = jsonPlaceHolderApi.getStatusPermintaanDonasi(NIM);
        call.enqueue(new Callback<ArrayList<PermintaanDonasiModel>>() {
            @Override
            public void onResponse(Call<ArrayList<PermintaanDonasiModel>> call, Response<ArrayList<PermintaanDonasiModel>> response) {
                if (!response.isSuccessful()) {
                    return;
                }

                permintaanDonasiModels.clear();
                ArrayList<PermintaanDonasiModel> donasiModels = response.body();
                if (donasiModels.get(0).getStatus_data().equals("y")) {
                    permintaanDonasiModels.addAll(donasiModels);

                    final PermintaanDonasiAdapter donasiAdapterNew = new PermintaanDonasiAdapter(StatusPermintaanDonasiActivity.this, permintaanDonasiModels);
                    rvStatusPermintaanDonasi.setAdapter(donasiAdapterNew);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<PermintaanDonasiModel>> call, Throwable t) {
                if (t.getMessage().contains("Failed to connect")) {
                    Toast.makeText(StatusPermintaanDonasiActivity.this, TEXT_NO_INTERNET, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
