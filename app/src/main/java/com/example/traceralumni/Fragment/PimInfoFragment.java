package com.example.traceralumni.Fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.traceralumni.Adapter.InfoAdapter;
import com.example.traceralumni.Model.InfoModel;
import com.example.traceralumni.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class PimInfoFragment extends Fragment {

    View rootView;
    InfoAdapter infoAdapter;
    RecyclerView infoRecycler;
    ArrayList<InfoModel> infoModels;
    EditText edt_cari_judul;

    public PimInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_pim_info, container, false);

        edt_cari_judul = rootView.findViewById(R.id.edt_fragment_pim_info_search);

        infoModels = new ArrayList<>();

        infoRecycler = rootView.findViewById(R.id.rv_fragment_pim_info);
        infoRecycler.setLayoutManager(new LinearLayoutManager(rootView.getContext(), LinearLayoutManager.VERTICAL, false));
        infoAdapter = new InfoAdapter(rootView.getContext(), infoModels);
        infoRecycler.setAdapter(infoAdapter);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        edt_cari_judul.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                infoAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}
