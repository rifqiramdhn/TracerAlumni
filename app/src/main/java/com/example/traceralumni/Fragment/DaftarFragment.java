package com.example.traceralumni.Fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.traceralumni.Adapter.DaftarAdapter;
import com.example.traceralumni.Client;
import com.example.traceralumni.JsonApi;
import com.example.traceralumni.Model.DaftarModel;
import com.example.traceralumni.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.traceralumni.Activity.MainActivity.TEXT_NO_INTERNET;

/**
 * A simple {@link Fragment} subclass.
 */
public class DaftarFragment extends Fragment {

    public DaftarFragment() {
        // Required empty public constructor
    }

    View rootView;

    RecyclerView daftarRecycler;
    DaftarAdapter daftarAdapter;
    ArrayList<DaftarModel> daftarModels;
    Spinner spn_prodi;

    EditText edt_cari_nama, edt_cari_angkatan;

    public static String TEXT_SEARCH_DAFTAR_USE_ANGKATAN = "";
    public static String TEXT_SEARCH_DAFTAR_USE_NAMA = "";
    public static String SPINNER_SEARCH_DAFTAR_USE_PRODI = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_daftar, container, false);

        edt_cari_nama = rootView.findViewById(R.id.edt_fragment_daftar_nama);
        edt_cari_angkatan = rootView.findViewById(R.id.edt_fragment_daftar_tahun);

        daftarModels = new ArrayList<>();
        daftarAdapter = new DaftarAdapter(rootView.getContext(), daftarModels);
        daftarRecycler = rootView.findViewById(R.id.fragment_daftar_recycler_view);
        daftarRecycler.setLayoutManager(new LinearLayoutManager(rootView.getContext(), LinearLayoutManager.VERTICAL, false));
        daftarRecycler.setAdapter(daftarAdapter);

        spn_prodi = rootView.findViewById(R.id.spn_fragment_daftar_prodi);
        customSpinner();
        return rootView;
    }

//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        getDaftarAlumni();
//    }

    @Override
    public void onResume() {
        super.onResume();
        getDaftarAlumni();
    }

    private void getDaftarAlumni() {
        JsonApi jsonApi = Client.getClient().create(JsonApi.class);

        Call<ArrayList<DaftarModel>> call = jsonApi.getDaftarAlumni();
        call.enqueue(new Callback<ArrayList<DaftarModel>>() {
            @Override
            public void onResponse(Call<ArrayList<DaftarModel>> call, Response<ArrayList<DaftarModel>> response) {
                if (!response.isSuccessful()) {
                    return;
                }

                daftarModels.clear();
                ArrayList<DaftarModel> daftarModelsResponse = response.body();
                if (daftarModelsResponse.get(0).getStatus_data().equals("y")) {
                    daftarModels.addAll(daftarModelsResponse);
                    final DaftarAdapter daftarAdapterNew = new DaftarAdapter(getActivity(), daftarModels);
                    daftarRecycler.setAdapter(daftarAdapterNew);
                    setSearch(daftarAdapterNew);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<DaftarModel>> call, Throwable t) {
                if (t.getMessage().contains("Failed to connect")) {
                    Toast.makeText(getContext(), TEXT_NO_INTERNET, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void customSpinner() {

        String[] prodi = new String[]{
                "Prodi",
                "S1 Akuntansi (Internasional)",
                "S1 Ek., Keu. dan Perbankan (Internasional)",
                "S1 Ekonomi Pembangunan",
                "S1 Ekonomi Pembangunan (Internasional)",
                "S1 Ekonomi, Keuangan dan Perbankan",
                "S1 Kewirausahaan",
                "S1 Manajemen",
                "S1 Manajemen (Internasional)",
                "S2 Akuntansi",
                "S2 Manajemen",
                "S2 Ilmu Ekonomi",
                "S3 Ilmu Akuntansi",
                "S3 Ilmu Ekonomi",
                "S3 Ilmu Manajemen",
                "PPAk"
        };

        final List<String> prodiList = new ArrayList<>(Arrays.asList(prodi));

        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                rootView.getContext(), R.layout.card_spinner, prodiList) {
            @Override
            public boolean isEnabled(int position) {
                return true;
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spinnerArrayAdapter.setDropDownViewResource(R.layout.card_spinner);
        spn_prodi.setAdapter(spinnerArrayAdapter);
    }

    private void setSearch(final DaftarAdapter daftarAdapter) {
        edt_cari_nama.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                TEXT_SEARCH_DAFTAR_USE_NAMA = charSequence.toString();
                daftarAdapter.getFilter().filter(charSequence);
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
                TEXT_SEARCH_DAFTAR_USE_ANGKATAN = charSequence.toString();
                daftarAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        spn_prodi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SPINNER_SEARCH_DAFTAR_USE_PRODI = spn_prodi.getSelectedItem().toString();
                daftarAdapter.getFilter().filter(null);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


}
