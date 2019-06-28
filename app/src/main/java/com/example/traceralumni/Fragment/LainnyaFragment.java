package com.example.traceralumni.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.traceralumni.Adapter.LainnyaAdapter;
import com.example.traceralumni.Model.LainnyaModel;
import com.example.traceralumni.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class LainnyaFragment extends Fragment {

    RecyclerView pengaturanRecycler;
    LainnyaAdapter lainnyaAdapter;
    ArrayList<LainnyaModel> lainnyaModels;
    View rootView;

    public LainnyaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_lainnya, container, false);
        setRecyclerView();
        return rootView;
    }

    private void setRecyclerView() {
        lainnyaModels = new ArrayList<>();
        lainnyaModels.add(new LainnyaModel("Kartu Identitas", R.mipmap.ic_launcher_round));
        lainnyaModels.add(new LainnyaModel("Donasi", R.mipmap.ic_launcher_round));
        lainnyaModels.add(new LainnyaModel("Legalisir Ijazah", R.mipmap.ic_launcher_round));
        lainnyaModels.add(new LainnyaModel("Tracer Studi", R.mipmap.ic_launcher_round));
        lainnyaModels.add(new LainnyaModel("Sunting Profil", R.mipmap.ic_launcher_round));
        lainnyaModels.add(new LainnyaModel("Riwayat Pekerjaaan", R.mipmap.ic_launcher_round));
        lainnyaModels.add(new LainnyaModel("Ganti Kata Sandi", R.mipmap.ic_launcher_round));
        lainnyaModels.add(new LainnyaModel("Hapus Semua Chat", R.mipmap.ic_launcher_round));
        lainnyaModels.add(new LainnyaModel("Tentang", R.mipmap.ic_launcher_round));
        lainnyaModels.add(new LainnyaModel("Keluar", R.mipmap.ic_launcher_round));

        pengaturanRecycler = rootView.findViewById(R.id.pengaturan_recycler_view);

        //Mengatur LayoutManager dari Recycler pengaturan
        pengaturanRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        lainnyaAdapter = new LainnyaAdapter(getContext(), lainnyaModels);
        pengaturanRecycler.setAdapter(lainnyaAdapter);
    }

}
