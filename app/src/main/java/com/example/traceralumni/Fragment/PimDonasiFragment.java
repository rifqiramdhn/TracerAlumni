package com.example.traceralumni.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.traceralumni.Activity.PermintaanDonasiActivity;
import com.example.traceralumni.Adapter.DonasiAdapter;
import com.example.traceralumni.JsonPlaceHolderApi;
import com.example.traceralumni.Model.DonasiModel;
import com.example.traceralumni.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.traceralumni.Activity.MainActivity.BASE_URL;

/**
 * A simple {@link Fragment} subclass.
 */
public class PimDonasiFragment extends Fragment {

    RecyclerView donasiRecycler;
    DonasiAdapter donasiAdapter;
    ArrayList<DonasiModel> arrayDonasi;
    TextView tv_permintaan_donasi;
    View rootView;

    EditText edt_donasi_cari;

    public PimDonasiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_pim_donasi, container, false);
        edt_donasi_cari = rootView.findViewById(R.id.edt_fragment_pim_donasi_search);

        arrayDonasi = new ArrayList<>();

//        arrayDonasi.add(new DonasiModel("Pembangunan Kantin", "Excepteur" +
//                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.Excepteur" +
//                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.Excepteur" +
//                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.", "500.000.000"));
//        arrayDonasi.add(new DonasiModel("Pembangunan Gazebo", "Excepteur" +
//                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.Excepteur" +
//                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.Excepteur" +
//                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.", "320.000.000"));
//        arrayDonasi.add(new DonasiModel("Pembangunan Gedung", "Excepteur" +
//                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.Excepteur" +
//                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.Excepteur" +
//                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.", "453.000.000"));
//        arrayDonasi.add(new DonasiModel("Renovasi Kantin", "Excepteur" +
//                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.Excepteur" +
//                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.Excepteur" +
//                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.", "2.500.000.000"));
//        arrayDonasi.add(new DonasiModel("Renovasi Gazebo", "Excepteur" +
//                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.Excepteur" +
//                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.Excepteur" +
//                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.", "6.500.000.000"));
//        arrayDonasi.add(new DonasiModel("Renovasi Gedung", "Excepteur" +
//                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.Excepteur" +
//                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.Excepteur" +
//                " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt.", "8.900.000.000"));

        donasiRecycler = rootView.findViewById(R.id.rv_fragment_pim_donasi);

        //Mengatur LayoutManager dari Recycler daftar
        donasiRecycler.setLayoutManager(new LinearLayoutManager(rootView.getContext(), LinearLayoutManager.VERTICAL, false));
        donasiAdapter = new DonasiAdapter(rootView.getContext(), arrayDonasi);
        donasiRecycler.setAdapter(donasiAdapter);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.e("aldy", "onViewCreated jalan");
        super.onViewCreated(view, savedInstanceState);
        getAllDonasi();
    }

    private void getAllDonasi(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<ArrayList<DonasiModel>> call = jsonPlaceHolderApi.getAllDonasi();
        call.enqueue(new Callback<ArrayList<DonasiModel>>() {
            @Override
            public void onResponse(Call<ArrayList<DonasiModel>> call, Response<ArrayList<DonasiModel>> response) {
                if (!response.isSuccessful()){
                    return;
                }

                arrayDonasi.clear();
                ArrayList<DonasiModel> donasiResponse = response.body();
                arrayDonasi.addAll(donasiResponse);

                final DonasiAdapter donasiAdapterNew = new DonasiAdapter(getActivity(), arrayDonasi);
                donasiRecycler.setAdapter(donasiAdapterNew);

                setSearch(donasiAdapterNew);
            }

            @Override
            public void onFailure(Call<ArrayList<DonasiModel>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setSearch(final DonasiAdapter donasiAdapter){
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
    }
}
