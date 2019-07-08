   package com.example.traceralumni.Fragment;


import android.content.Intent;
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
import android.widget.TextView;

import com.example.traceralumni.Activity.OpPermintaanLowonganActivity;
import com.example.traceralumni.Adapter.LowonganAdapter;
import com.example.traceralumni.Model.LowonganModel;
import com.example.traceralumni.R;

import java.util.ArrayList;

   /**
 * A simple {@link Fragment} subclass.
 */
public class PimLowonganFragment extends Fragment {

       View rootview;

       RecyclerView lowonganRecycler;
       LowonganAdapter lowonganAdapter;
       ArrayList<LowonganModel> lowonganModels;

       EditText edt_lowongan_cari;

    public PimLowonganFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_pim_lowongan,  container,false);
        edt_lowongan_cari = rootview.findViewById(R.id.edt_fragment_pim_lowongan_search);

        lowonganModels = new ArrayList<>();

        lowonganModels.add(new LowonganModel(1,"Staff Administrasi HRD", "PT. Bank Muamalat", "Kota Malang, Indonesia", "5.000.000"));
        lowonganModels.add(new LowonganModel(1,"Staff Marketing", "PT. Bank Muamalat", "Kota Malang, Indonesia", "5.000.000"));
        lowonganModels.add(new LowonganModel(1,"Manager Personalia", "PT. Bank Muamalat", "Kota Malang, Indonesia", "5.000.000"));
        lowonganModels.add(new LowonganModel(1,"Direktur Bank", "PT. Bank Muamalat", "Kota Malang, Indonesia", "5.000.000"));
        lowonganModels.add(new LowonganModel(1,"Staff Akuntansi Negara", "PT. Bank Muamalat", "Kota Malang, Indonesia", "5.000.000"));
        lowonganModels.add(new LowonganModel(1,"Manager Pertambangan", "PT. Bank Muamalat", "Kota Malang, Indonesia", "5.000.000"));
        lowonganModels.add(new LowonganModel(1,"Staff Ekonomi", "PT. Bank Muamalat", "Kota Malang, Indonesia", "5.000.000"));
        lowonganModels.add(new LowonganModel(1,"Staff Kewirausahaan", "PT. Bank Muamalat", "Kota Malang, Indonesia", "5.000.000"));
        lowonganModels.add(new LowonganModel(1,"Direktur Perusahaan", "PT. Bank Muamalat", "Kota Malang, Indonesia", "5.000.000"));

        lowonganRecycler = rootview.findViewById(R.id.rv_fragment_pim_lowongan);

        lowonganRecycler.setLayoutManager(new LinearLayoutManager(rootview.getContext(), LinearLayoutManager.VERTICAL, false));
        lowonganAdapter = new LowonganAdapter(lowonganModels);
        lowonganRecycler.setAdapter(lowonganAdapter);

        edt_lowongan_cari.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                lowonganAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return rootview;
    }

}
