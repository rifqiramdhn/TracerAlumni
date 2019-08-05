package com.example.traceralumni.Fragment;


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

import com.example.traceralumni.Adapter.DonasiAdapter;
import com.example.traceralumni.Client;
import com.example.traceralumni.JsonApi;
import com.example.traceralumni.Model.DonasiModel;
import com.example.traceralumni.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.traceralumni.Activity.MainActivity.BASE_URL;
import static com.example.traceralumni.Activity.MainActivity.TEXT_NO_INTERNET;

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

        donasiRecycler = rootView.findViewById(R.id.rv_fragment_pim_donasi);

        //Mengatur LayoutManager dari Recycler daftar
        donasiRecycler.setLayoutManager(new LinearLayoutManager(rootView.getContext(), LinearLayoutManager.VERTICAL, false));
        donasiAdapter = new DonasiAdapter(rootView.getContext(), arrayDonasi);
        donasiRecycler.setAdapter(donasiAdapter);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getAllDonasi();
    }

    private void getAllDonasi(){
        JsonApi jsonApi = Client.getClient().create(JsonApi.class);

        Call<ArrayList<DonasiModel>> call = jsonApi.getAllDonasi();
        call.enqueue(new Callback<ArrayList<DonasiModel>>() {
            @Override
            public void onResponse(Call<ArrayList<DonasiModel>> call, Response<ArrayList<DonasiModel>> response) {
                if (!response.isSuccessful()){
                    return;
                }

                arrayDonasi.clear();
                ArrayList<DonasiModel> donasiResponse = response.body();
                if (donasiResponse.get(0).getStatus_data().equals("y")){
                    arrayDonasi.addAll(donasiResponse);
                    final DonasiAdapter donasiAdapterNew = new DonasiAdapter(getActivity(), arrayDonasi);
                    donasiRecycler.setAdapter(donasiAdapterNew);
                    setSearch(donasiAdapterNew);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<DonasiModel>> call, Throwable t) {
                if (t.getMessage().contains("Failed to connect")) {
                    Toast.makeText(getContext(), TEXT_NO_INTERNET, Toast.LENGTH_SHORT).show();
                }
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
