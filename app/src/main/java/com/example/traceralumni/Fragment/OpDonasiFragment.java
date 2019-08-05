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
import android.widget.Toast;

import com.example.traceralumni.Adapter.DonasiAdapter;
import com.example.traceralumni.Client;
import com.example.traceralumni.JsonApi;
import com.example.traceralumni.Model.DonasiModel;
import com.example.traceralumni.Activity.OpPermintaanDonasiActivity;
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

    static String jumlahRequest = "0";

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
                Intent intent = new Intent(rootView.getContext(), OpPermintaanDonasiActivity.class);
                rootView.getContext().startActivity(intent);
            }
        });

        return rootView;
    }

    public static boolean permintaanDonasi0(){
        if (jumlahRequest.equals("0")){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getRequestDonasi();
        getAllDonasi();
    }

    private void getRequestDonasi() {
        JsonApi jsonApi = Client.getClient().create(JsonApi.class);
        Call<String> call = jsonApi.getCountPermintaanDonasi();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (!response.isSuccessful()) {
                    return;
                }

                jumlahRequest = response.body();
                if (!jumlahRequest.equals("0")) {
                    tv_permintaan_donasi.setText(jumlahRequest + " Permintaan Donasi");
                    tv_permintaan_donasi.setVisibility(View.VISIBLE);
                } else {
                    tv_permintaan_donasi.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (t.getMessage().contains("Failed to connect")) {
                    Toast.makeText(getContext(), TEXT_NO_INTERNET, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getAllDonasi() {
        JsonApi jsonApi = Client.getClient().create(JsonApi.class);

        Call<ArrayList<DonasiModel>> call = jsonApi.getAllDonasi();
        call.enqueue(new Callback<ArrayList<DonasiModel>>() {
            @Override
            public void onResponse(Call<ArrayList<DonasiModel>> call, Response<ArrayList<DonasiModel>> response) {
                if (!response.isSuccessful()) {
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
