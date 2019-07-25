package com.example.traceralumni.Activity;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.traceralumni.Adapter.LowonganAdapter;
import com.example.traceralumni.JsonPlaceHolderApi;
import com.example.traceralumni.Model.LowonganModel;
import com.example.traceralumni.Model.RiwayatPekerjaanModel;
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

public class StatusPermintaanLowonganActivity extends AppCompatActivity {
    TextView tvNavBar;
    ConstraintLayout cl_iconBack;
    ImageView img_iconBack;
    RecyclerView recyclerView;
    LowonganAdapter lowonganAdapter;
    ArrayList<LowonganModel> arrayModels;
    public static boolean BUKA_STATUS_LOWONGAN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BUKA_STATUS_LOWONGAN = true;
        setContentView(R.layout.activity_status_permintaan_lowongan);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        initView();
        arrayModels = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        lowonganAdapter = new LowonganAdapter(this, arrayModels);
        recyclerView.setAdapter(lowonganAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getStatusLowongan();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        BUKA_STATUS_LOWONGAN = false;
    }

    private void initView(){
        recyclerView = findViewById(R.id.rv_status_loker);
        tvNavBar = findViewById(R.id.tv_navbar_top);
        tvNavBar.setText("STATUS LOKER");
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
    }

    private void getStatusLowongan(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<ArrayList<LowonganModel>> call = jsonPlaceHolderApi.getStatusLowongan(NIM);
        call.enqueue(new Callback<ArrayList<LowonganModel>>() {
            @Override
            public void onResponse(Call<ArrayList<LowonganModel>> call, Response<ArrayList<LowonganModel>> response) {
                if (!response.isSuccessful()){
                    return;
                }

                arrayModels.clear();
                ArrayList<LowonganModel> lowonganModels = response.body();
                if (lowonganModels.get(0).getStatus_data().equals("y")){
                    arrayModels.addAll(lowonganModels);
                    lowonganAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<LowonganModel>> call, Throwable t) {
                if (t.getMessage().contains("Failed to connect")) {
                    Toast.makeText(StatusPermintaanLowonganActivity.this, TEXT_NO_INTERNET, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
