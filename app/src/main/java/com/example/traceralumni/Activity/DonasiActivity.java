package com.example.traceralumni.Activity;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.traceralumni.Adapter.DonasiAdapter;
import com.example.traceralumni.Client;
import com.example.traceralumni.JsonApi;
import com.example.traceralumni.Model.DonasiModel;
import com.example.traceralumni.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.traceralumni.Activity.MainActivity.TEXT_NO_INTERNET;

public class DonasiActivity extends AppCompatActivity {

    ConstraintLayout cl_icon_back, cl_icon_search;
    ImageView img_icon_back, img_icon_search;
    TextView tv_navbar;

    ConstraintLayout cl_donasi_search;

    RecyclerView donasiRecycler;
    DonasiAdapter donasiAdapter;
    ArrayList<DonasiModel> arrayDonasi;

    EditText edt_donasi_cari;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donasi);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        initView();
        arrayDonasi = new ArrayList<>();
        donasiRecycler.setLayoutManager(new LinearLayoutManager(DonasiActivity.this, LinearLayoutManager.VERTICAL, false));
        donasiAdapter = new DonasiAdapter(DonasiActivity.this, arrayDonasi);
        donasiRecycler.setAdapter(donasiAdapter);

        getAllDonasi();
    }

    private void getAllDonasi() {
        JsonApi jsonApi = Client.getClient().create(JsonApi.class);

        Call<ArrayList<DonasiModel>> call = jsonApi.getAllDonasi();
        call.enqueue(new Callback<ArrayList<DonasiModel>>() {
            @Override
            public void onResponse(Call<ArrayList<DonasiModel>> call, Response<ArrayList<DonasiModel>> response) {
                if (!response.isSuccessful()) {
                    return;
                }

                arrayDonasi.clear();
                ArrayList<DonasiModel> donasiModels = response.body();
                if (donasiModels.get(0).getStatus_data().equals("y")) {
                    arrayDonasi.addAll(donasiModels);

                    final DonasiAdapter donasiAdapterNew = new DonasiAdapter(DonasiActivity.this, arrayDonasi);
                    donasiRecycler.setAdapter(donasiAdapterNew);

                    setSearch(donasiAdapterNew);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<DonasiModel>> call, Throwable t) {
                if (t.getMessage().contains("Failed to connect")) {
                    Toast.makeText(DonasiActivity.this, TEXT_NO_INTERNET, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initView() {
        donasiRecycler = findViewById(R.id.rv_activity_donasi);
        edt_donasi_cari = findViewById(R.id.edt_donasi_cari);
        cl_icon_back = findViewById(R.id.cl_icon1);
        cl_icon_back.setVisibility(View.VISIBLE);
        img_icon_back = findViewById(R.id.img_icon1);
        img_icon_back.setImageResource(R.drawable.ic_arrow_back);
        cl_icon_search = findViewById(R.id.cl_icon4);
        cl_icon_search.setVisibility(View.VISIBLE);
        img_icon_search = findViewById(R.id.img_icon4);
        img_icon_search.setImageResource(R.drawable.ic_search);
        tv_navbar = findViewById(R.id.tv_navbar_top);
        tv_navbar.setText("OPEN DONASI");
        cl_icon_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        cl_donasi_search = findViewById(R.id.cl_activity_donasi_search);
        cl_icon_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cl_donasi_search.getVisibility() == View.GONE) {
                    cl_donasi_search.setVisibility(View.VISIBLE);
                } else {
                    cl_donasi_search.setVisibility(View.GONE);
                }
            }
        });
    }

    private void setSearch(final DonasiAdapter donasiAdapter) {
        edt_donasi_cari.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                donasiAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}
