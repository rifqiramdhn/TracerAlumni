package com.example.traceralumni.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import com.example.traceralumni.Adapter.DaftarAdapter;
import com.example.traceralumni.Client;
import com.example.traceralumni.JsonApi;
import com.example.traceralumni.Model.DaftarModel;
import com.example.traceralumni.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.traceralumni.Activity.MainActivity.TEXT_NO_INTERNET;
import static com.example.traceralumni.Fragment.DaftarFragment.TEXT_SEARCH_DAFTAR_USE_NAMA;

public class PimDaftarAlumniActivity extends AppCompatActivity {
    ConstraintLayout cl_iconBack, cl_iconSearch, cl_search_daftar;
    ImageView img_iconBack, img_iconSearch;
    TextView tvNavBar;
    RecyclerView daftarRecycler;
    DaftarAdapter daftarAdapter;
    ArrayList<DaftarModel> daftarModels;
    EditText edt_search;
    String jurusan, prodi, angkatan, jabatan;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pim_daftar_alumni);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        ambilView();
        setNavBar();

        getDataAlumniDaftar();

        daftarModels = new ArrayList<>();
        daftarAdapter = new DaftarAdapter(this, daftarModels);
        daftarRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        daftarRecycler.setAdapter(daftarAdapter);

        cl_iconSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cl_search_daftar.getVisibility() == View.GONE) {
                    cl_search_daftar.setVisibility(View.VISIBLE);
                } else {
                    cl_search_daftar.setVisibility(View.GONE);
                }
            }
        });

        cl_iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void ambilView() {
        cl_iconBack = findViewById(R.id.cl_icon1);
        cl_iconSearch = findViewById(R.id.cl_icon4);
        cl_search_daftar = findViewById(R.id.cl_activity_pim_daftar_search);
        img_iconBack = findViewById(R.id.img_icon1);
        img_iconSearch = findViewById(R.id.img_icon4);
        tvNavBar = findViewById(R.id.tv_navbar_top);
        daftarRecycler = findViewById(R.id.rv_daftar_alumni);
        edt_search = findViewById(R.id.edt_activity_pim_daftar_search);
    }

    private void setNavBar() {
        img_iconBack.setImageResource(R.drawable.ic_arrow_back);
        img_iconSearch.setImageResource(R.drawable.ic_search);

        cl_iconBack.setVisibility(View.VISIBLE);
        cl_iconSearch.setVisibility(View.VISIBLE);

        tvNavBar.setText("ALUMNI");
    }

    private void setSearch(final DaftarAdapter daftarAdapter) {
        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                TEXT_SEARCH_DAFTAR_USE_NAMA = charSequence.toString();
                daftarAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void getDataAlumniDaftar() {
        Intent intent = getIntent();

        jurusan = intent.getStringExtra("jurusan");
        prodi = intent.getStringExtra("prodi");
        angkatan = intent.getStringExtra("angkatan");
        jabatan = intent.getStringExtra("jabatan");

        JsonApi jsonApi = Client.getClient().create(JsonApi.class);

        Call<ArrayList<DaftarModel>> call;

        if (angkatan.equalsIgnoreCase("")) {
            call = jsonApi.getDataAlumniDaftar(
                    jurusan, prodi, "", jabatan);
        } else if (jabatan.equalsIgnoreCase("")) {
            call = jsonApi.getDataAlumniDaftar(
                    jurusan, prodi, angkatan, "");
        } else if (angkatan.equalsIgnoreCase("")
                && jabatan.equalsIgnoreCase("")) {
            call = jsonApi.getDataAlumniDaftar(
                    jurusan, prodi, "", "");
        } else {
            call = jsonApi.getDataAlumniDaftar(
                    jurusan, prodi, angkatan, jabatan);
        }
        call.enqueue(new Callback<ArrayList<DaftarModel>>() {
            @Override
            public void onResponse(Call<ArrayList<DaftarModel>> call, Response<ArrayList<DaftarModel>> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                daftarModels.clear();
                ArrayList<DaftarModel> daftarModelsResponse = response.body();
                if (daftarModelsResponse.get(0).getStatus_data().equals("y")) {
                    daftarModels.addAll(daftarModelsResponse);
                    final DaftarAdapter daftarAdapterNew = new DaftarAdapter(PimDaftarAlumniActivity.this, daftarModels);
                    daftarRecycler.setAdapter(daftarAdapterNew);
                    setSearch(daftarAdapterNew);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<DaftarModel>> call, Throwable t) {
                if (t.getMessage().contains("Failed to connect")) {
                    Toast.makeText(PimDaftarAlumniActivity.this, TEXT_NO_INTERNET, Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
}
