package com.example.traceralumni.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.traceralumni.Adapter.DonasiAdapter;
import com.example.traceralumni.Model.DonasiModel;
import com.example.traceralumni.Activity.PermintaanDonasiActivity;
import com.example.traceralumni.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class OpDonasiFragment extends Fragment {

    View rootView;

    RecyclerView donasiRecycler;
    DonasiAdapter donasiAdapter;
    ArrayList<DonasiModel> donasiModels;
    TextView tv_permintaan_donasi;

    EditText edt_donasi_cari;

    public OpDonasiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_op_donasi, container, false);
        edt_donasi_cari = rootView.findViewById(R.id.edt_fragment_op_donasi_search);

        donasiModels = new ArrayList<>();

//        donasiModels.add(new DonasiModel("Pembangunan Kantin", "Excepteur" +
//                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.Excepteur" +
//                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.Excepteur" +
//                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.", "500.000.000"));
//        donasiModels.add(new DonasiModel("Pembangunan Gazebo", "Excepteur" +
//                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.Excepteur" +
//                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.Excepteur" +
//                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.", "320.000.000"));
//        donasiModels.add(new DonasiModel("Pembangunan Gedung", "Excepteur" +
//                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.Excepteur" +
//                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.Excepteur" +
//                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.", "453.000.000"));
//        donasiModels.add(new DonasiModel("Renovasi Kantin", "Excepteur" +
//                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.Excepteur" +
//                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.Excepteur" +
//                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.", "2.500.000.000"));
//        donasiModels.add(new DonasiModel("Renovasi Gazebo", "Excepteur" +
//                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.Excepteur" +
//                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.Excepteur" +
//                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.", "6.500.000.000"));
//        donasiModels.add(new DonasiModel("Renovasi Gedung", "Excepteur" +
//                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.Excepteur" +
//                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.Excepteur" +
//                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.", "8.900.000.000"));
//        donasiModels.add(new DonasiModel("Pembangunan Kantin", "Excepteur" +
//                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.Excepteur" +
//                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.Excepteur" +
//                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.", "500.000.000"));
//        donasiModels.add(new DonasiModel("Pembangunan Gazebo", "Excepteur" +
//                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.Excepteur" +
//                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.Excepteur" +
//                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.", "320.000.000"));
//        donasiModels.add(new DonasiModel("Pembangunan Gedung", "Excepteur" +
//                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.Excepteur" +
//                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.Excepteur" +
//                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.", "453.000.000"));
//        donasiModels.add(new DonasiModel("Renovasi Kantin", "Excepteur" +
//                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.Excepteur" +
//                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.Excepteur" +
//                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.", "2.500.000.000"));
//        donasiModels.add(new DonasiModel("Renovasi Gazebo", "Excepteur" +
//                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.Excepteur" +
//                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.Excepteur" +
//                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.", "6.500.000.000"));
//        donasiModels.add(new DonasiModel("Renovasi Gedung", "Excepteur" +
//                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.Excepteur" +
//                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.Excepteur" +
//                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.", "8.900.000.000"));
//        donasiModels.add(new DonasiModel("Pembangunan Kantin", "Excepteur" +
//                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.Excepteur" +
//                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.Excepteur" +
//                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.", "500.000.000"));
//        donasiModels.add(new DonasiModel("Pembangunan Gazebo", "Excepteur" +
//                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.Excepteur" +
//                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.Excepteur" +
//                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.", "320.000.000"));
//        donasiModels.add(new DonasiModel("Pembangunan Gedung", "Excepteur" +
//                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.Excepteur" +
//                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.Excepteur" +
//                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.", "453.000.000"));
//        donasiModels.add(new DonasiModel("Renovasi Kantin", "Excepteur" +
//                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.Excepteur" +
//                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.Excepteur" +
//                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.", "2.500.000.000"));
//        donasiModels.add(new DonasiModel("Renovasi Gazebo", "Excepteur" +
//                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.Excepteur" +
//                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.Excepteur" +
//                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.", "6.500.000.000"));
//        donasiModels.add(new DonasiModel("Renovasi Gedung", "Excepteur" +
//                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.Excepteur" +
//                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.Excepteur" +
//                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.", "8.900.000.000"));
        donasiRecycler = rootView.findViewById(R.id.rv_fragment_op_donasi);

        //Mengatur LayoutManager dari Recycler daftar
        donasiRecycler.setLayoutManager(new LinearLayoutManager(rootView.getContext(), LinearLayoutManager.VERTICAL, false));
        donasiAdapter = new DonasiAdapter(rootView.getContext(), donasiModels);
        donasiRecycler.setAdapter(donasiAdapter);

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
        tv_permintaan_donasi = rootView.findViewById(R.id.tv_fragment_op_donasi_permintaan);
        tv_permintaan_donasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(rootView.getContext(), PermintaanDonasiActivity.class);
                rootView.getContext().startActivity(intent);
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        donasiRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0){
                    //scrolling up
                    tv_permintaan_donasi.setVisibility(View.GONE);
                } else {
                    //scrolling down
                    tv_permintaan_donasi.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
