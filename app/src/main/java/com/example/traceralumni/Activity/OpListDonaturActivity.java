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

import com.example.traceralumni.Adapter.ListDonaturAdapter;
import com.example.traceralumni.Client;
import com.example.traceralumni.JsonApi;
import com.example.traceralumni.Model.PermintaanDonasiModel;
import com.example.traceralumni.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.traceralumni.Activity.MainActivity.BASE_URL;
import static com.example.traceralumni.Activity.MainActivity.TEXT_NO_INTERNET;

public class OpListDonaturActivity extends AppCompatActivity {
    ConstraintLayout cl_iconBack;
    ImageView img_iconBack;
    TextView tvNavBar;

    RecyclerView listDonaturRecycler;
    ListDonaturAdapter listDonaturAdapter;
    ArrayList<PermintaanDonasiModel> listDonaturModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_op_list_donatur);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        tvNavBar = findViewById(R.id.tv_navbar_top);
        tvNavBar.setText("LIST DONATUR");

        cl_iconBack = findViewById(R.id.cl_icon1);
        cl_iconBack.setVisibility(View.VISIBLE);

        img_iconBack = findViewById(R.id.img_icon1);
        img_iconBack.setImageResource(R.drawable.ic_arrow_back);

        cl_iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        listDonaturRecycler = findViewById(R.id.rv_list_donatur);
        listDonaturModels = new ArrayList<>();
        listDonaturRecycler.setLayoutManager(new LinearLayoutManager(OpListDonaturActivity.this, LinearLayoutManager.VERTICAL, false));
        listDonaturAdapter = new ListDonaturAdapter(OpListDonaturActivity.this, listDonaturModels);
        listDonaturRecycler.setAdapter(listDonaturAdapter);
        getAllDonatur();
    }

    private void getAllDonatur() {
        JsonApi jsonApi = Client.getClient().create(JsonApi.class);

        Call<ArrayList<PermintaanDonasiModel>> call = jsonApi.getAllDonatur(getIntent().getIntExtra("id_donasi", 0));
        call.enqueue(new Callback<ArrayList<PermintaanDonasiModel>>() {
            @Override
            public void onResponse(Call<ArrayList<PermintaanDonasiModel>> call, Response<ArrayList<PermintaanDonasiModel>> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                listDonaturModels.clear();
                ArrayList<PermintaanDonasiModel> listDonaturModelResponse = response.body();
                listDonaturModels.addAll(listDonaturModelResponse);

                if (!listDonaturModels.get(0).getStatus_data().equalsIgnoreCase("n")) {
                    final ListDonaturAdapter listDonaturAdapterNew = new ListDonaturAdapter(OpListDonaturActivity.this, listDonaturModels);
                    listDonaturRecycler.setAdapter(listDonaturAdapterNew);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<PermintaanDonasiModel>> call, Throwable t) {
                if (t.getMessage().contains("Failed to connect")) {
                    Toast.makeText(OpListDonaturActivity.this, TEXT_NO_INTERNET, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
