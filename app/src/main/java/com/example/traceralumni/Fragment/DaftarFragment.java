package com.example.traceralumni.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.traceralumni.Adapter.DaftarAdapter;
import com.example.traceralumni.Model.DaftarModel;
import com.example.traceralumni.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class DaftarFragment extends Fragment {


    public DaftarFragment() {
        // Required empty public constructor
    }

    RecyclerView daftarRecycler;
    DaftarAdapter daftarAdapter;
    ArrayList<DaftarModel> daftarModels;
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_daftar, container, false);

        daftarModels = new ArrayList<>();
        daftarModels.add(new DaftarModel("Rifqi Ramdhani", "2019", 1));
        daftarModels.add(new DaftarModel("Budi Fauzan", "2016", 2));
        daftarModels.add(new DaftarModel("Rizaldy Firman T.", "2014", 3));

        daftarRecycler = rootView.findViewById(R.id.fragment_daftar_recycler_view);

        //Mengatur LayoutManager dari Recycler daftar
        daftarRecycler.setLayoutManager(new LinearLayoutManager(rootView.getContext(), LinearLayoutManager.VERTICAL, false));

        daftarAdapter = new DaftarAdapter(rootView.getContext(), daftarModels);
        daftarRecycler.setAdapter(daftarAdapter);

        return rootView;
    }

}
