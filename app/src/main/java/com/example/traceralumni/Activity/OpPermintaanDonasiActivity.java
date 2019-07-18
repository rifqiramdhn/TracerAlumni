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

import com.example.traceralumni.Adapter.PermintaanDonasiAdapter;
import com.example.traceralumni.JsonPlaceHolderApi;
import com.example.traceralumni.Model.PermintaanDonasiModel;
import com.example.traceralumni.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.traceralumni.Activity.MainActivity.BASE_URL;

public class OpPermintaanDonasiActivity extends AppCompatActivity {

    ConstraintLayout cl_back;
    ImageView imgBack;
    TextView tv_titleNavBar;

    RecyclerView perDonasiRecycler;
    PermintaanDonasiAdapter perDonasiAdapter;
    ArrayList<PermintaanDonasiModel> perDonasiModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permintaan_donasi);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        initView();

        perDonasiModels = new ArrayList<>();
        perDonasiAdapter = new PermintaanDonasiAdapter(OpPermintaanDonasiActivity.this, perDonasiModels);
        perDonasiRecycler = findViewById(R.id.rv_permintaan_donasi);
        perDonasiRecycler.setLayoutManager(new LinearLayoutManager(OpPermintaanDonasiActivity.this, LinearLayoutManager.VERTICAL, false));
        perDonasiRecycler.setAdapter(perDonasiAdapter);

        getAllPermintaanDonasi();
    }

    private void initView() {
        cl_back = findViewById(R.id.cl_icon1);
        imgBack = findViewById(R.id.img_icon1);
        tv_titleNavBar = findViewById(R.id.tv_navbar_top);
        tv_titleNavBar.setText("PERMINTAAN DONASI");
        imgBack.setImageResource(R.drawable.ic_arrow_back);
        cl_back.setVisibility(View.VISIBLE);
        cl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void getAllPermintaanDonasi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<ArrayList<PermintaanDonasiModel>> call = jsonPlaceHolderApi.getPerDonasi();
        call.enqueue(new Callback<ArrayList<PermintaanDonasiModel>>() {
            @Override
            public void onResponse(Call<ArrayList<PermintaanDonasiModel>> call, Response<ArrayList<PermintaanDonasiModel>> response) {
                if (!response.isSuccessful()) {
                    return;
                }

                perDonasiModels.clear();
                ArrayList<PermintaanDonasiModel> permintaanDonasiModels = response.body();
                perDonasiModels.addAll(permintaanDonasiModels);

                perDonasiAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<PermintaanDonasiModel>> call, Throwable t) {
                Toast.makeText(OpPermintaanDonasiActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
