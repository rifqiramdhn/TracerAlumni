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

    public static boolean SEARCH_DAFTAR_USE_ANGKATAN = false;
    public static boolean SEARCH_DAFTAR_USE_NAMA = false;

    public static String TEXT_SEARCH_DAFTAR_USE_ANGKATAN = "";
    public static String TEXT_SEARCH_DAFTAR_USE_NAMA = "";


    EditText edt_cari_nama, edt_cari_angkatan;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_daftar, container, false);

        edt_cari_nama = rootView.findViewById(R.id.edt_fragment_daftar_nama);
        edt_cari_angkatan = rootView.findViewById(R.id.edt_fragment_daftar_tahun);

        daftarModels = new ArrayList<>();
        daftarModels.add(new DaftarModel("Rifqi Ramdhani", "2019", 1));
        daftarModels.add(new DaftarModel("Budi Fauzan", "2016", 2));
        daftarModels.add(new DaftarModel("Rizaldy Ilham Akbar", "2014", 3));
        daftarModels.add(new DaftarModel("Rosa Mardiana", "2014", 4));

        daftarRecycler = rootView.findViewById(R.id.fragment_daftar_recycler_view);

        //Mengatur LayoutManager dari Recycler daftar
        daftarRecycler.setLayoutManager(new LinearLayoutManager(rootView.getContext(), LinearLayoutManager.VERTICAL, false));

        daftarAdapter = new DaftarAdapter(rootView.getContext(), daftarModels);
        daftarRecycler.setAdapter(daftarAdapter);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        edt_cari_nama.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (charSequence.length() == 0) {
//                    SEARCH_DAFTAR_USE_NAMA = true;
//                } else {
                    SEARCH_DAFTAR_USE_NAMA = true;
                    TEXT_SEARCH_DAFTAR_USE_NAMA = charSequence.toString().toLowerCase().trim();
                    daftarAdapter.getFilter().filter(charSequence);
//                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        edt_cari_angkatan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (charSequence.length() == 0) {
//                    SEARCH_DAFTAR_USE_ANGKATAN = true;
//                } else {
                    SEARCH_DAFTAR_USE_ANGKATAN = true;
                    TEXT_SEARCH_DAFTAR_USE_ANGKATAN = charSequence.toString().toLowerCase().trim();
                    daftarAdapter.getFilter().filter(charSequence);
//                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}
