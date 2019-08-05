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

import com.example.traceralumni.Client;
import com.example.traceralumni.JsonApi;
import com.example.traceralumni.Model.LowonganModel;
import com.example.traceralumni.R;
import com.example.traceralumni.Adapter.LowonganAdapter;

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
public class LowonganFragment extends Fragment {
    RecyclerView lowonganRecycler;
    LowonganAdapter lowonganAdapter;
    ArrayList<LowonganModel> arrayLowongan;

    EditText edt_cari;

    public LowonganFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lowongan, container, false);

        edt_cari = rootView.findViewById(R.id.edt_lowongan_cari);

        arrayLowongan = new ArrayList<>();

        lowonganRecycler = rootView.findViewById(R.id.rv_lowongan);

        lowonganRecycler.setLayoutManager(new LinearLayoutManager(rootView.getContext(), LinearLayoutManager.VERTICAL, false));

        lowonganAdapter = new LowonganAdapter(rootView.getContext(), arrayLowongan);
        lowonganRecycler.setAdapter(lowonganAdapter);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getAllLowongan();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void getAllLowongan(){
        JsonApi jsonApi = Client.getClient().create(JsonApi.class);

        Call<ArrayList<LowonganModel>> lowongan = jsonApi.getAllLowongan();
        lowongan.enqueue(new Callback<ArrayList<LowonganModel>>() {
            @Override
            public void onResponse(Call<ArrayList<LowonganModel>> call, Response<ArrayList<LowonganModel>> response) {
                if(!response.isSuccessful()){
                    return;
                }

                arrayLowongan.clear();
                ArrayList<LowonganModel> lowonganResponse = response.body();
                if (lowonganResponse.get(0).getStatus_data().equals("y")){
                    arrayLowongan.addAll(lowonganResponse);

                    final LowonganAdapter lowonganAdapterNew = new LowonganAdapter(getActivity(), arrayLowongan);
                    lowonganRecycler.setAdapter(lowonganAdapterNew);

                    setSearch(lowonganAdapterNew);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<LowonganModel>> call, Throwable t) {
                if (t.getMessage().contains("Failed to connect")) {
                    Toast.makeText(getContext(), TEXT_NO_INTERNET, Toast.LENGTH_SHORT).show();
                }
            }
        });
        lowonganAdapter.notifyDataSetChanged();
    }

    private void setSearch(final LowonganAdapter lowonganAdapter){
        edt_cari.addTextChangedListener(new TextWatcher() {
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
}
