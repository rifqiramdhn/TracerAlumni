package com.example.traceralumni.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.traceralumni.Adapter.DaftarAdapter;
import com.example.traceralumni.Client;
import com.example.traceralumni.Fragment.DaftarFragment;
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

public class DaftarAlumniActivity extends AppCompatActivity {
    ConstraintLayout cl_iconBack, cl_iconSearch, cl_activity_daftar_search;
    ImageView img_iconBack, img_iconSearch;
    TextView tvNavBar;

    RecyclerView daftarRecycler;
    DaftarAdapter daftarAdapter;
    ArrayList<DaftarModel> daftarModels;
    Spinner spn_prodi;

    EditText edt_cari_nama, edt_cari_angkatan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_alumni);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        daftarModels = new ArrayList<>();
        daftarAdapter = new DaftarAdapter(this, daftarModels);
        daftarRecycler = findViewById(R.id.rv_activity_daftar);
        daftarRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        daftarRecycler.setAdapter(daftarAdapter);

        ambilView();
        setNavBar();
        customSpinner();
        getDaftarAlumni();

        cl_iconSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cl_activity_daftar_search.getVisibility() == View.GONE) {
                    cl_activity_daftar_search.setVisibility(View.VISIBLE);
                } else {
                    cl_activity_daftar_search.setVisibility(View.GONE);
                }
            }
        });

        cl_iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void ambilView() {
        cl_iconBack = findViewById(R.id.cl_icon1);
        cl_iconSearch = findViewById(R.id.cl_icon4);
        img_iconBack = findViewById(R.id.img_icon1);
        img_iconSearch = findViewById(R.id.img_icon4);
        tvNavBar = findViewById(R.id.tv_navbar_top);
        edt_cari_nama = findViewById(R.id.edt_activity_daftar_nama);
        edt_cari_angkatan = findViewById(R.id.edt_activity_daftar_tahun);
        cl_activity_daftar_search = findViewById(R.id.cl_activity_daftar_search);
        spn_prodi = findViewById(R.id.spn_activity_daftar_prodi);
    }

    private void setNavBar() {
        img_iconBack.setImageResource(R.drawable.ic_arrow_back);
        img_iconSearch.setImageResource(R.drawable.ic_search);

        cl_iconBack.setVisibility(View.VISIBLE);
        cl_iconSearch.setVisibility(View.VISIBLE);

        tvNavBar.setText("DAFTAR ALUMNI");
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
                    final DaftarAdapter daftarAdapterNew = new DaftarAdapter(DaftarAlumniActivity.this, daftarModels);
                    daftarRecycler.setAdapter(daftarAdapterNew);
                    setSearch(daftarAdapterNew);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<DaftarModel>> call, Throwable t) {
                if (t.getMessage().contains("Failed to connect")) {
                    Toast.makeText(DaftarAlumniActivity.this, TEXT_NO_INTERNET, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void customSpinner() {

        String[] prodi = new String[]{
                "Prodi",
                "S1 Akuntansi",
                "S1 Akuntansi (Internasional)",
                "S1 Ekonomi, Keuangan, dan Perbankan (Internasional)",
                "S2 Akuntansi",
                "S3 Ilmu Akuntansi",
                "PPAk",
                "S1 Ekonomi Pembangunan",
                "S1 Ekonomi Pembangunan (Internasional)",
                "S1 Ekonomi Islam",
                "S2 Ilmu Ekonomi",
                "S3 Ilmu Ekonomi",
                "S1 Ekonomi, Keuangan, dan Perbankan",
                "S1 Kewirausahaan",
                "S1 Manajemen",
                "S1 Manajemen (Internasional)",
                "S2 Manajemen",
                "S3 Ilmu Manajemen"
        };

        final List<String> prodiList = new ArrayList<>(Arrays.asList(prodi));

        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this, R.layout.card_spinner, prodiList) {
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
                DaftarFragment.TEXT_SEARCH_DAFTAR_USE_NAMA = charSequence.toString();
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
                DaftarFragment.TEXT_SEARCH_DAFTAR_USE_ANGKATAN = charSequence.toString();
                daftarAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        spn_prodi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DaftarFragment.SPINNER_SEARCH_DAFTAR_USE_PRODI = spn_prodi.getSelectedItem().toString();
                daftarAdapter.getFilter().filter(null);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}
