package com.example.traceralumni.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.traceralumni.Adapter.BerandaAdapter;
import com.example.traceralumni.Adapter.ChatAdapter;
import com.example.traceralumni.Model.BerandaModel;
import com.example.traceralumni.Model.ChatModel;
import com.example.traceralumni.Model.DonasiModel;
import com.example.traceralumni.Model.InfoModel;
import com.example.traceralumni.Model.LowonganModel;
import com.example.traceralumni.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class BerandaFragment extends Fragment {

    RecyclerView berandaRecycler;
    BerandaAdapter berandaAdapter;
    ArrayList<BerandaModel> listBeranda;

    View rootView;

    public BerandaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_beranda, container, false);

        listBeranda = new ArrayList<>();
        listBeranda.add(new BerandaModel("data_lowongan", new LowonganModel(1,"Staff Administrasi HRD", "PT. Bank Muamalat", "Kota Malang, Indonesia", "5.000.000")));

        listBeranda.add(new BerandaModel("data_donasi", new DonasiModel("Pembangunan Kantin", "Excepteur" +
                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.Excepteur" +
                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.Excepteur" +
                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.", "500.000.000")));

        listBeranda.add(new BerandaModel("data_info", new InfoModel("Pencurian AC", "Lorem ipsum dolor sit amet, consectetur" +
                " adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna" +
                " aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris" +
                " nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit" +
                " in voluptate velit esse cillum dolore eu fugiat nulla pariatur.", "https://www.google.com")));

        listBeranda.add(new BerandaModel("data_info", new InfoModel("Pencurian AC", "Lorem ipsum dolor sit amet, consectetur" +
                " adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna" +
                " aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris" +
                " nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit" +
                " in voluptate velit esse cillum dolore eu fugiat nulla pariatur.", "https://www.google.com")));

        listBeranda.add(new BerandaModel("data_donasi", new DonasiModel("Pembangunan Kantin", "Excepteur" +
                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.Excepteur" +
                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.Excepteur" +
                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.", "500.000.000")));

        listBeranda.add(new BerandaModel("data_lowongan", new LowonganModel(1,"Staff Administrasi HRD", "PT. Bank Muamalat", "Kota Malang, Indonesia", "5.000.000")));

        berandaRecycler = rootView.findViewById(R.id.rv_fragment_beranda);
        berandaRecycler.setLayoutManager(new LinearLayoutManager(rootView.getContext(), LinearLayoutManager.VERTICAL, false));
        berandaAdapter = new BerandaAdapter(rootView.getContext(), listBeranda);
        berandaRecycler.setAdapter(berandaAdapter);

        return rootView;
    }



}
