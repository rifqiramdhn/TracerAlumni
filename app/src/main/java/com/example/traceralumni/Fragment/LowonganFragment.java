package com.example.traceralumni.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.traceralumni.R;
import com.example.traceralumni.Adapter.LowonganAdapter;
import com.example.traceralumni.Model.LowonganModel;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class LowonganFragment extends Fragment {
    RecyclerView recyclerView;
    LowonganAdapter lowonganAdapter;
    ArrayList<LowonganModel> listLowongan;

    public LowonganFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lowongan, container, false);

        listLowongan = new ArrayList<>();
        listLowongan.add(new LowonganModel("Staff Administrasi HRD", "PT. Bank Muamalat", "Kota Malang, Indonesia", "5.000.000"));
        listLowongan.add(new LowonganModel("Staff Administrasi HRD", "PT. Bank Muamalat", "Kota Malang, Indonesia", "5.000.000"));
        listLowongan.add(new LowonganModel("Staff Administrasi HRD", "PT. Bank Muamalat", "Kota Malang, Indonesia", "5.000.000"));
        listLowongan.add(new LowonganModel("Staff Administrasi HRD", "PT. Bank Muamalat", "Kota Malang, Indonesia", "5.000.000"));
        listLowongan.add(new LowonganModel("Staff Administrasi HRD", "PT. Bank Muamalat", "Kota Malang, Indonesia", "5.000.000"));
        listLowongan.add(new LowonganModel("Staff Administrasi HRD", "PT. Bank Muamalat", "Kota Malang, Indonesia", "5.000.000"));
        listLowongan.add(new LowonganModel("Staff Administrasi HRD", "PT. Bank Muamalat", "Kota Malang, Indonesia", "5.000.000"));
        listLowongan.add(new LowonganModel("Staff Administrasi HRD", "PT. Bank Muamalat", "Kota Malang, Indonesia", "5.000.000"));

        recyclerView = rootView.findViewById(R.id.rv_lowongan);
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext(), LinearLayoutManager.VERTICAL, false));

        lowonganAdapter = new LowonganAdapter(listLowongan);
        recyclerView.setAdapter(lowonganAdapter);

        return rootView;
    }

}
