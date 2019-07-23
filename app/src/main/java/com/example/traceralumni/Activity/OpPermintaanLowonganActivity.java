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

import com.example.traceralumni.Adapter.PermintaanLowonganAdapter;
import com.example.traceralumni.JsonPlaceHolderApi;
import com.example.traceralumni.Model.PermintaanLowonganModel;
import com.example.traceralumni.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.traceralumni.Activity.MainActivity.BASE_URL;
import static com.example.traceralumni.Activity.MainActivity.TEXT_NO_INTERNET;

public class OpPermintaanLowonganActivity extends AppCompatActivity {

    ConstraintLayout cl_back;
    ImageView imgBack;
    TextView tv_titleNavBar;

    RecyclerView permintaanLowonganRecycler;
    PermintaanLowonganAdapter permintaanLowonganAdapter;
    ArrayList<PermintaanLowonganModel> permintaanLowonganModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permintaan_lowongan);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        initView();

        permintaanLowonganModels = new ArrayList<>();

        permintaanLowonganAdapter = new PermintaanLowonganAdapter(OpPermintaanLowonganActivity.this, permintaanLowonganModels);
        permintaanLowonganRecycler = findViewById(R.id.rv_permintaan_lowongan);
        permintaanLowonganRecycler.setLayoutManager(new LinearLayoutManager(OpPermintaanLowonganActivity.this, LinearLayoutManager.VERTICAL, false));
        permintaanLowonganRecycler.setAdapter(permintaanLowonganAdapter);

        getAllPermintaanLowongan();
    }

    private void initView() {
        cl_back = findViewById(R.id.cl_icon1);
        imgBack = findViewById(R.id.img_icon1);
        tv_titleNavBar = findViewById(R.id.tv_navbar_top);
        tv_titleNavBar.setText("PERMINTAAN LOWONGAN");
        imgBack.setImageResource(R.drawable.ic_arrow_back);
        cl_back.setVisibility(View.VISIBLE);
        cl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void getAllPermintaanLowongan(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<ArrayList<PermintaanLowonganModel>> call = jsonPlaceHolderApi.getPerLowongan();
        call.enqueue(new Callback<ArrayList<PermintaanLowonganModel>>() {
            @Override
            public void onResponse(Call<ArrayList<PermintaanLowonganModel>> call, Response<ArrayList<PermintaanLowonganModel>> response) {
                if (!response.isSuccessful()){
                    return;
                }

                permintaanLowonganModels.clear();
                ArrayList<PermintaanLowonganModel> perLowonganModels = response.body();
                if (perLowonganModels.get(0).getStatus_data().equals("y")){
                    permintaanLowonganModels.addAll(perLowonganModels);
                    permintaanLowonganAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<PermintaanLowonganModel>> call, Throwable t) {
                if (t.getMessage().contains("Failed to connect")) {
                    Toast.makeText(OpPermintaanLowonganActivity.this, TEXT_NO_INTERNET, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}