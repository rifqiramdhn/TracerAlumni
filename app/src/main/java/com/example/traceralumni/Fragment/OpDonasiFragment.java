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

import com.example.traceralumni.Activity.DonasiActivity;
import com.example.traceralumni.Adapter.DonasiAdapter;
import com.example.traceralumni.JsonPlaceHolderApi;
import com.example.traceralumni.Model.DonasiModel;
import com.example.traceralumni.Activity.PermintaanDonasiActivity;
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
public class OpDonasiFragment extends Fragment {

    public OpDonasiFragment() {
        // Required empty public constructor
    }

    View rootView;

    RecyclerView donasiRecycler;
    DonasiAdapter donasiAdapter;
    ArrayList<DonasiModel> arrayDonasi;
    TextView tv_permintaan_donasi;

    EditText edt_donasi_cari;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_op_donasi, container, false);

        edt_donasi_cari = rootView.findViewById(R.id.edt_fragment_op_donasi_search);

        arrayDonasi = new ArrayList<>();
        donasiAdapter = new DonasiAdapter(rootView.getContext(), arrayDonasi);
        donasiRecycler = rootView.findViewById(R.id.rv_fragment_op_donasi);
        donasiRecycler.setLayoutManager(new LinearLayoutManager(rootView.getContext(), LinearLayoutManager.VERTICAL, false));
        donasiRecycler.setAdapter(donasiAdapter);

        tv_permintaan_donasi = rootView.findViewById(R.id.tv_fragment_op_donasi_permintaan);
        tv_permintaan_donasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(rootView.getContext(), PermintaanDonasiActivity.class);
                rootView.getContext().startActivity(intent);
            }
        });

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.e("aldy", "onViewCreated jalan");
        super.onViewCreated(view, savedInstanceState);
        getAllDonasi();
    }

    @Override
    public void onResume() {
        Log.e("aldy", "onResume jalan");
        super.onResume();

//        getAllDonasi();

        donasiRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
                    //scrolling up
                    tv_permintaan_donasi.setVisibility(View.GONE);
                } else {
                    //scrolling down
                    tv_permintaan_donasi.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void getAllDonasi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<ArrayList<DonasiModel>> call = jsonPlaceHolderApi.getAllDonasi();
        call.enqueue(new Callback<ArrayList<DonasiModel>>() {
            @Override
            public void onResponse(Call<ArrayList<DonasiModel>> call, Response<ArrayList<DonasiModel>> response) {
                if (!response.isSuccessful()) {
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

    private void setSearch(final DonasiAdapter donasiAdapter) {
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
