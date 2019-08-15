package com.example.traceralumni.Fragment;


import android.content.Context;
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

    public static Context CONTEXT;
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
        CONTEXT = getActivity();
        return rootView;
    }

    private void setRecyclerView() {
        lainnyaModels = new ArrayList<>();
        lainnyaModels.add(new LainnyaModel("Legalisir Online", R.drawable.ic_ijazah));
        lainnyaModels.add(new LainnyaModel("Kartu Alumni", R.drawable.ic_kartu_alumni));
//        lainnyaModels.add(new LainnyaModel("Donasi", R.drawable.ic_attach_money));
        lainnyaModels.add(new LainnyaModel("Tracer Studi", R.drawable.ic_tracer_study));
        lainnyaModels.add(new LainnyaModel("Sunting Profil", R.drawable.ic_person));
        lainnyaModels.add(new LainnyaModel("Riwayat Pekerjaaan", R.drawable.ic_riwayat_pekerjaan));
//        lainnyaModels.add(new LainnyaModel("Status Permintaan Donasi", R.drawable.ic_wallet));
        lainnyaModels.add(new LainnyaModel("Status Permintaan Lowongan", R.drawable.ic_lowongan));
        lainnyaModels.add(new LainnyaModel("Ganti Kata Sandi", R.drawable.ic_ganti_password));
        lainnyaModels.add(new LainnyaModel("Hapus Semua Chat", R.drawable.ic_delete_chat));
        lainnyaModels.add(new LainnyaModel("Tentang", R.drawable.ic_about));
        lainnyaModels.add(new LainnyaModel("Hubungi Kami", R.drawable.ic_bantuan));
        lainnyaModels.add(new LainnyaModel("Keluar", R.drawable.ic_power_settings_new));

        pengaturanRecycler = rootView.findViewById(R.id.pengaturan_recycler_view);

        //Mengatur LayoutManager dari Recycler pengaturan
        pengaturanRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        lainnyaAdapter = new LainnyaAdapter(getContext(), lainnyaModels);
        pengaturanRecycler.setAdapter(lainnyaAdapter);
    }

}
