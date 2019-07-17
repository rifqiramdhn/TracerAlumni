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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.traceralumni.Activity.OpPermintaanLowonganActivity;
import com.example.traceralumni.Adapter.LowonganAdapter;
import com.example.traceralumni.JsonPlaceHolderApi;
import com.example.traceralumni.Model.LowonganModel;
import com.example.traceralumni.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
public class OpLowonganFragment extends Fragment {
    View rootview;

    RecyclerView lowonganRecycler;
    LowonganAdapter lowonganAdapter;
    ArrayList<LowonganModel> arrayLowongan;

    EditText edt_lowongan_cari;
    TextView tv_permintaan;

    static String jumlahRequestLowongan = "0";

    public OpLowonganFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_op_lowongan,  container,false);
        edt_lowongan_cari = rootview.findViewById(R.id.edt_fragment_op_lowongan_search);
//        tv_permintaan.setText();
        tv_permintaan = rootview.findViewById(R.id.tv_permintaan_lowongan);

        arrayLowongan = new ArrayList<>();

        lowonganRecycler = rootview.findViewById(R.id.rv_fragment_op_lowongan);

        lowonganRecycler.setLayoutManager(new LinearLayoutManager(rootview.getContext(), LinearLayoutManager.VERTICAL, false));
        lowonganAdapter = new LowonganAdapter(rootview.getContext(), arrayLowongan);
        lowonganRecycler.setAdapter(lowonganAdapter);

        tv_permintaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(rootview.getContext(), OpPermintaanLowonganActivity.class);
                rootview.getContext().startActivity(i);
            }
        });
        return rootview;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        getRequestLowongan();
        getAllLowongan();
        lowonganRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0){
                    //scrolling up
                    tv_permintaan.setVisibility(View.GONE);
                } else {
                    //scrolling down
                    tv_permintaan.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void setSearch(final LowonganAdapter lowonganAdapter){
        edt_lowongan_cari.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                lowonganAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void getAllLowongan(){
        Gson gson = new GsonBuilder()
                .setLenient().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<ArrayList<LowonganModel>> lowongan = jsonPlaceHolderApi.getAllLowongan();
        lowongan.enqueue(new Callback<ArrayList<LowonganModel>>() {
            @Override
            public void onResponse(Call<ArrayList<LowonganModel>> call, Response<ArrayList<LowonganModel>> response) {
                if(!response.isSuccessful()){
                    return;
                }

                arrayLowongan.clear();
                ArrayList<LowonganModel> lowonganResponse = response.body();
                arrayLowongan.addAll(lowonganResponse);

                final LowonganAdapter lowonganAdapterNew = new LowonganAdapter(getActivity(), arrayLowongan);
                lowonganRecycler.setAdapter(lowonganAdapterNew);

                setSearch(lowonganAdapterNew);
            }

            @Override
            public void onFailure(Call<ArrayList<LowonganModel>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        lowonganAdapter.notifyDataSetChanged();
    }

    private void getRequestLowongan(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<String> call = jsonPlaceHolderApi.getCountPermintaanLowongan();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(!response.isSuccessful()){
                    return;
                }

                jumlahRequestLowongan = response.body();
                if (!jumlahRequestLowongan.equals("0")){
                    tv_permintaan.setText(jumlahRequestLowongan + " Permintaan Lowongan");
                    tv_permintaan.setVisibility(View.VISIBLE);
                } else {
                    tv_permintaan.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
