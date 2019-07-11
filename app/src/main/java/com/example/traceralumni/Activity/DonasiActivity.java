package com.example.traceralumni.Activity;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.traceralumni.Adapter.DonasiAdapter;
import com.example.traceralumni.JsonPlaceHolderApi;
import com.example.traceralumni.Model.DonasiModel;
import com.example.traceralumni.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.traceralumni.Activity.MainActivity.BASE_URL;

public class DonasiActivity extends AppCompatActivity {
    ConstraintLayout cl_iconBack;
    ImageView img_iconBack;
    TextView tv_titleBar;

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

        edt_donasi_cari = findViewById(R.id.edt_donasi_cari);

        cl_iconBack = findViewById(R.id.cl_icon1);
        cl_iconBack.setVisibility(View.VISIBLE);

        img_iconBack = findViewById(R.id.img_icon1);
        img_iconBack.setImageResource(R.drawable.ic_arrow_back);

        tv_titleBar = findViewById(R.id.tv_navbar_top);
        tv_titleBar.setText("DAFTAR DONASI");

        cl_icon_back = findViewById(R.id.cl_icon1);
        cl_icon_search = findViewById(R.id.cl_icon4);
        img_icon_back = findViewById(R.id.img_icon1);
        img_icon_search = findViewById(R.id.img_icon4);
        tv_navbar = findViewById(R.id.tv_navbar_top);
        cl_donasi_search = findViewById(R.id.cl_activity_donasi_search);

        img_icon_back.setImageResource(R.drawable.ic_arrow_back);
        img_icon_search.setImageResource(R.drawable.ic_search);

        tv_navbar.setText("OPEN DONASI");

        cl_icon_back.setVisibility(View.VISIBLE);
        cl_icon_search.setVisibility(View.VISIBLE);

        cl_icon_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

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

        donasiRecycler = findViewById(R.id.rv_activity_donasi);

        //Mengatur LayoutManager dari Recycler daftar
        donasiRecycler.setLayoutManager(new LinearLayoutManager(DonasiActivity.this, LinearLayoutManager.VERTICAL, false));
        donasiAdapter = new DonasiAdapter(DonasiActivity.this, arrayDonasi);
        donasiRecycler.setAdapter(donasiAdapter);

        getAllDonasi();

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

    private void getAllDonasi(){
        arrayDonasi = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<ArrayList<DonasiModel>> call = jsonPlaceHolderApi.getAllDonasi();
        call.enqueue(new Callback<ArrayList<DonasiModel>>() {
            @Override
            public void onResponse(Call<ArrayList<DonasiModel>> call, Response<ArrayList<DonasiModel>> response) {
                if (!response.isSuccessful()){
                    return;
                }

                ArrayList<DonasiModel> donasiModels = response.body();
                arrayDonasi.addAll(donasiModels);
            }

            @Override
            public void onFailure(Call<ArrayList<DonasiModel>> call, Throwable t) {
                Toast.makeText(DonasiActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        donasiAdapter.notifyDataSetChanged();
    }
}
