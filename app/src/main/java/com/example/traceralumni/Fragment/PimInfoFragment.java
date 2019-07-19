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
import android.widget.Toast;

import com.example.traceralumni.Adapter.InfoAdapter;
import com.example.traceralumni.JsonPlaceHolderApi;
import com.example.traceralumni.Model.InfoModel;
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
public class PimInfoFragment extends Fragment {

    View rootView;
    InfoAdapter infoAdapter;
    RecyclerView infoRecycler;
    ArrayList<InfoModel> arrayInfo;
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

        arrayInfo = new ArrayList<>();

        infoRecycler = rootView.findViewById(R.id.rv_fragment_pim_info);
        infoRecycler.setLayoutManager(new LinearLayoutManager(rootView.getContext(), LinearLayoutManager.VERTICAL, false));
        infoAdapter = new InfoAdapter(rootView.getContext(), arrayInfo);
        infoRecycler.setAdapter(infoAdapter);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getAllInfo();
    }

    private void getAllInfo(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<ArrayList<InfoModel>> call = jsonPlaceHolderApi.getAllInfo();
        call.enqueue(new Callback<ArrayList<InfoModel>>() {
            @Override
            public void onResponse(Call<ArrayList<InfoModel>> call, Response<ArrayList<InfoModel>> response) {
                if (!response.isSuccessful()){
                    return;
                }

                arrayInfo.clear();
                ArrayList<InfoModel> donasiResponse = response.body();
                if (donasiResponse.get(0).getStatus_data().equals("y")){
                    arrayInfo.addAll(donasiResponse);
                    final InfoAdapter infoAdapterNew = new InfoAdapter(getActivity(), arrayInfo);
                    infoRecycler.setAdapter(infoAdapterNew);
                    setSearch(infoAdapterNew);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<InfoModel>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setSearch(final InfoAdapter infoAdapter){
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
