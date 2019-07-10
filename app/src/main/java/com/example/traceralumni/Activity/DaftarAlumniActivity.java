package com.example.traceralumni.Activity;

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

import com.example.traceralumni.Adapter.DaftarAdapter;
import com.example.traceralumni.Model.DaftarModel;
import com.example.traceralumni.R;

import java.util.ArrayList;

import static com.example.traceralumni.Fragment.DaftarFragment.SEARCH_DAFTAR_USE_NAMA;
import static com.example.traceralumni.Fragment.DaftarFragment.TEXT_SEARCH_DAFTAR_USE_NAMA;

public class DaftarAlumniActivity extends AppCompatActivity {
    ConstraintLayout cl_iconBack, cl_iconSearch, cl_search_daftar;
    ImageView img_iconBack, img_iconSearch;
    TextView tvNavBar;
    RecyclerView daftarRecycler;
    DaftarAdapter daftarAdapter;
    ArrayList<DaftarModel> daftarModels;
    EditText edt_search;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pim_daftar_alumni);

        ambilView();
        setNavBar();

        daftarModels = new ArrayList<>();
//        daftarModels.add(new DaftarModel("Rifqi Ramdhani", "2019", 1));
//        daftarModels.add(new DaftarModel("Budi Fauzan", "2016", 2));
//        daftarModels.add(new DaftarModel("Rizaldy Ilham Akbar", "2014", 3));
//        daftarModels.add(new DaftarModel("Rosa Mardiana", "2014", 4));
//        daftarModels.add(new DaftarModel("Rifqi Ramdhani", "2019", 1));
//        daftarModels.add(new DaftarModel("Budi Fauzan", "2016", 2));
//        daftarModels.add(new DaftarModel("Rizaldy Ilham Akbar", "2014", 3));
//        daftarModels.add(new DaftarModel("Rosa Mardiana", "2014", 4));
//        daftarModels.add(new DaftarModel("Rifqi Ramdhani", "2019", 1));
//        daftarModels.add(new DaftarModel("Budi Fauzan", "2016", 2));
//        daftarModels.add(new DaftarModel("Rizaldy Ilham Akbar", "2014", 3));
//        daftarModels.add(new DaftarModel("Rosa Mardiana", "2014", 4));
//        daftarModels.add(new DaftarModel("Rifqi Ramdhani", "2019", 1));
//        daftarModels.add(new DaftarModel("Budi Fauzan", "2016", 2));
//        daftarModels.add(new DaftarModel("Rizaldy Ilham Akbar", "2014", 3));
//        daftarModels.add(new DaftarModel("Rosa Mardiana", "2014", 4));

        daftarRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        daftarAdapter = new DaftarAdapter(this, daftarModels);
        daftarRecycler.setAdapter(daftarAdapter);

        cl_iconSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cl_search_daftar.getVisibility() == View.GONE){
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

    private void ambilView(){
        cl_iconBack = findViewById(R.id.cl_icon1);
        cl_iconSearch = findViewById(R.id.cl_icon4);
        cl_search_daftar = findViewById(R.id.cl_activity_pim_daftar_search);
        img_iconBack = findViewById(R.id.img_icon1);
        img_iconSearch = findViewById(R.id.img_icon4);
        tvNavBar = findViewById(R.id.tv_navbar_top);
        daftarRecycler = findViewById(R.id.rv_daftar_alumni);
        edt_search = findViewById(R.id.edt_activity_pim_daftar_search);
    }

    private void setNavBar(){
        img_iconBack.setImageResource(R.drawable.ic_arrow_back);
        img_iconSearch.setImageResource(R.drawable.ic_search);

        cl_iconBack.setVisibility(View.VISIBLE);
        cl_iconSearch.setVisibility(View.VISIBLE);

        tvNavBar.setText("DAFTAR ALUMNI");
    }

    @Override
    protected void onResume() {
        super.onResume();

        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                SEARCH_DAFTAR_USE_NAMA = true;
                TEXT_SEARCH_DAFTAR_USE_NAMA = charSequence.toString().toLowerCase().trim();
                daftarAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
