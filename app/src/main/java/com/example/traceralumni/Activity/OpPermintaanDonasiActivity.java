package com.example.traceralumni.Activity;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.traceralumni.Adapter.PermintaanDonasiAdapter;
import com.example.traceralumni.Client;
import com.example.traceralumni.JsonApi;
import com.example.traceralumni.Model.PermintaanDonasiModel;
import com.example.traceralumni.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.traceralumni.Activity.MainActivity.TEXT_NO_INTERNET;

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
        JsonApi jsonApi = Client.getClient().create(JsonApi.class);
        Call<ArrayList<PermintaanDonasiModel>> call = jsonApi.getPerDonasi();
        call.enqueue(new Callback<ArrayList<PermintaanDonasiModel>>() {
            @Override
            public void onResponse(Call<ArrayList<PermintaanDonasiModel>> call, Response<ArrayList<PermintaanDonasiModel>> response) {
                if (!response.isSuccessful()) {
                    return;
                }

                perDonasiModels.clear();
                ArrayList<PermintaanDonasiModel> permintaanDonasiModels = response.body();
                if (permintaanDonasiModels.get(0).getStatus_data().equals("y")) {
                    perDonasiModels.addAll(permintaanDonasiModels);
                    perDonasiAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<PermintaanDonasiModel>> call, Throwable t) {
                if (t.getMessage().contains("Failed to connect")) {
                    Toast.makeText(OpPermintaanDonasiActivity.this, TEXT_NO_INTERNET, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
