package com.example.traceralumni.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.traceralumni.Model.LowonganModel;
import com.example.traceralumni.R;
import com.example.traceralumni.Adapter.LowonganAdapter;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class LowonganFragment extends Fragment {
    RecyclerView recyclerView;
    LowonganAdapter lowonganAdapter;
    ArrayList<LowonganModel> listLowongan;

    EditText edt_cari;

    public LowonganFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lowongan, container, false);

        edt_cari = rootView.findViewById(R.id.edt_lowongan_cari);

        listLowongan = new ArrayList<>();
        listLowongan.add(new LowonganModel(1,"Staff Administrasi HRD", "PT. Bank Muamalat", "Kota Malang, Indonesia", "5.000.000"));
        listLowongan.add(new LowonganModel(2,"Manager Administrasi HRD", "PT. Bank Muamalat", "Kota Malang, Indonesia", "5.000.000"));
        listLowongan.add(new LowonganModel(3,"Office Boy Administrasi HRD", "PT. Bank Muamalat", "Kota Malang, Indonesia", "5.000.000"));
        listLowongan.add(new LowonganModel(4,"Programmer Administrasi HRD", "PT. Bank Muamalat", "Kota Malang, Indonesia", "5.000.000"));
        listLowongan.add(new LowonganModel(5,"Database Admin Administrasi HRD", "PT. Bank Muamalat", "Kota Malang, Indonesia", "5.000.000"));
        listLowongan.add(new LowonganModel(6,"Staff Administrasi HRD", "PT. Bank Muamalat", "Kota Malang, Indonesia", "5.000.000"));
        listLowongan.add(new LowonganModel(7,"Staff Administrasi HRD", "PT. Bank Muamalat", "Kota Malang, Indonesia", "5.000.000"));
        listLowongan.add(new LowonganModel(8,"Staff Administrasi HRD", "PT. Bank Muamalat", "Kota Malang, Indonesia", "5.000.000"));

        recyclerView = rootView.findViewById(R.id.rv_lowongan);
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext(), LinearLayoutManager.VERTICAL, false));

        lowonganAdapter = new LowonganAdapter(listLowongan);
        recyclerView.setAdapter(lowonganAdapter);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        edt_cari.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                lowonganAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}
