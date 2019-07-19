package com.example.traceralumni.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.traceralumni.Adapter.BerandaAdapter;
import com.example.traceralumni.JsonPlaceHolderApi;
import com.example.traceralumni.Model.BerandaModel;
import com.example.traceralumni.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.traceralumni.Activity.MainActivity.BASE_URL;


/**
 * A simple {@link Fragment} subclass.
 */
public class BerandaFragment extends Fragment {

    RecyclerView berandaRecycler;
    BerandaAdapter berandaAdapter;
    ArrayList<BerandaModel> arrayBeranda;

    View rootView;

    public BerandaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_beranda, container, false);

        arrayBeranda = new ArrayList<>();

        berandaRecycler = rootView.findViewById(R.id.rv_fragment_beranda);
        berandaRecycler.setLayoutManager(new LinearLayoutManager(rootView.getContext(), LinearLayoutManager.VERTICAL, false));
        berandaAdapter = new BerandaAdapter(rootView.getContext(), arrayBeranda);
        berandaRecycler.setAdapter(berandaAdapter);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getAllBeranda();
    }

    private void sortArrayList(){
        Collections.sort(arrayBeranda, new Comparator<BerandaModel>() {
            @Override
            public int compare(BerandaModel berandaModel, BerandaModel berandaModel2) {
                return berandaModel2.getTanggal_beranda().compareTo(berandaModel.getTanggal_beranda());
            }
        });
    }

    private void getAllBeranda(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<ArrayList<BerandaModel>> call = jsonPlaceHolderApi.getAllBeranda();
        call.enqueue(new Callback<ArrayList<BerandaModel>>() {
            @Override
            public void onResponse(Call<ArrayList<BerandaModel>> call, Response<ArrayList<BerandaModel>> response) {
                if (!response.isSuccessful()){
                    return;
                }

                arrayBeranda.clear();
                ArrayList<BerandaModel> berandaResponse = response.body();
                if (berandaResponse.get(0).getStatus_data().equals("y")){
                    arrayBeranda.addAll(berandaResponse);
                    sortArrayList();

                    BerandaAdapter berandaAdapter = new BerandaAdapter(rootView.getContext(), arrayBeranda);
                    berandaRecycler.setAdapter(berandaAdapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<BerandaModel>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
