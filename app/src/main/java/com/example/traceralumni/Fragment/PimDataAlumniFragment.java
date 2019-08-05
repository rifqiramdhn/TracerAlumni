package com.example.traceralumni.Fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.traceralumni.Activity.PimDaftarAlumniActivity;
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
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.traceralumni.Activity.MainActivity.BASE_URL;
import static com.example.traceralumni.Activity.MainActivity.TEXT_NO_INTERNET;

/**
 * A simple {@link Fragment} subclass.
 */
public class PimDataAlumniFragment extends Fragment {
    View rootView;
    Spinner spn_jurusan, spn_prodi;
    TextView tv_totalAlumni;
    EditText edt_angkatan, edt_jabatan;
    Button btn_lihatDaftar;
    ArrayList<DaftarModel> daftarModels;
    ProgressBar progressBar;

    int CAN_CLICK_BUTTON_SAVE = 0; //0 bisa diklik, 1 tidak bisa diklik

    public PimDataAlumniFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_pim_data_alumni, container, false);

        daftarModels = new ArrayList<>();

        ambilView();
        customSpinner();


        btn_lihatDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CAN_CLICK_BUTTON_SAVE == 0){
                    CAN_CLICK_BUTTON_SAVE = 1;
                    getDataAlumniDaftar();
                }
            }
        });

        return rootView;
    }

    private void ambilView() {
        spn_jurusan = rootView.findViewById(R.id.spn_daftar_jurusan);
        spn_prodi = rootView.findViewById(R.id.spn_daftar_prodi);
        tv_totalAlumni = rootView.findViewById(R.id.tv_total_alumni);
        edt_angkatan = rootView.findViewById(R.id.edt_angkatan);
        edt_jabatan = rootView.findViewById(R.id.edt_jabatan_kerja);
        btn_lihatDaftar = rootView.findViewById(R.id.btn_lihat_daftar);
        progressBar = rootView.findViewById(R.id.pb_fragment_pim_data_alumni);
        setSpinnerOnItemSelectedListener(spn_jurusan);
        setSpinnerOnItemSelectedListener(spn_prodi);
    }

    private void customSpinner() {
        String[] jurusan = new String[]{
                "Jurusan",
                "Akuntansi",
                "Ilmu Ekonomi",
                "Manajemen"
        };

        String[] prodi = new String[]{
                "Prodi",
                "S1 Akuntansi (Internasional)",
                "S1 Ekonomi, Keuangan, dan Perbankan (Internasional)",
                "S2 Akuntansi",
                "S3 Ilmu Akuntansi",
                "PPAk",
                "S1 Ekonomi Pembangunan",
                "S1 Ekonomi Pembangunan (Internasional)",
                "S2 Ilmu Ekonomi",
                "S3 Ilmu Ekonomi",
                "S1 Ekonomi, Keuangan, dan Perbankan",
                "S1 Kewirausahaan",
                "S1 Manajemen",
                "S1 Manajemen (Internasional)",
                "S2 Manajemen",
                "S3 Ilmu Manajemen"
        };

        String[] prodiAkuntansi = new String[]{
                "Prodi",
                "S1 Akuntansi (Internasional)",
                "S1 Ekonomi, Keuangan, dan Perbankan (Internasional)",
                "S2 Akuntansi",
                "S3 Ilmu Akuntansi",
                "PPAk"
        };

        String[] prodiIlmuEkonomi = new String[]{
                "Prodi",
                "S1 Ekonomi Pembangunan",
                "S1 Ekonomi Pembangunan (Internasional)",
                "S2 Ilmu Ekonomi",
                "S3 Ilmu Ekonomi",
        };

        String[] prodiManajemen = new String[]{
                "Prodi",
                "S1 Ekonomi, Keuangan, dan Perbankan",
                "S1 Kewirausahaan",
                "S1 Manajemen",
                "S1 Manajemen (Internasional)",
                "S2 Manajemen",
                "S3 Ilmu Manajemen",
        };

        final List<String> jurusanList = new ArrayList<>(Arrays.asList(jurusan));
        final List<String> prodiList = new ArrayList<>(Arrays.asList(prodi));
        final List<String> prodiListAkuntansi = new ArrayList<>(Arrays.asList(prodiAkuntansi));
        final List<String> prodiListIlmuEkonomi = new ArrayList<>(Arrays.asList(prodiIlmuEkonomi));
        final List<String> prodiListManajemen = new ArrayList<>(Arrays.asList(prodiManajemen));

        final ArrayAdapter<String> spinnerArrayAdapterJurusan = new ArrayAdapter<String>(
                rootView.getContext(), R.layout.card_spinner, jurusanList) {
            @Override
            public boolean isEnabled(int position) {
                return true;
            }

            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(getResources().getColor(R.color.colorIconBiru));
                }
                return view;
            }
        };

        final ArrayAdapter<String> spinnerArrayAdapterProdi = new ArrayAdapter<String>(
                rootView.getContext(), R.layout.card_spinner, prodiList) {
            @Override
            public boolean isEnabled(int position) {
                return true;
            }

            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(getResources().getColor(R.color.colorIconBiru));
                }
                return view;
            }
        };

        final ArrayAdapter<String> spinnerArrayAdapterAkuntansi = new ArrayAdapter<String>(
                rootView.getContext(), R.layout.card_spinner, prodiListAkuntansi) {
            @Override
            public boolean isEnabled(int position) {
                return true;
            }

            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(getResources().getColor(R.color.colorIconBiru));
                }
                return view;
            }
        };

        final ArrayAdapter<String> spinnerArrayAdapterIlmuEkonomi = new ArrayAdapter<String>(
                rootView.getContext(), R.layout.card_spinner, prodiListIlmuEkonomi) {
            @Override
            public boolean isEnabled(int position) {
                return true;
            }

            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(getResources().getColor(R.color.colorIconBiru));
                }
                return view;
            }
        };

        final ArrayAdapter<String> spinnerArrayAdapterManajemen = new ArrayAdapter<String>(
                rootView.getContext(), R.layout.card_spinner, prodiListManajemen) {
            @Override
            public boolean isEnabled(int position) {
                return true;
            }

            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(getResources().getColor(R.color.colorIconBiru));
                }
                return view;
            }
        };

        spinnerArrayAdapterJurusan.setDropDownViewResource(R.layout.card_spinner);

        spn_jurusan.setAdapter(spinnerArrayAdapterJurusan);

        spn_jurusan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    spinnerArrayAdapterAkuntansi.setDropDownViewResource(R.layout.card_spinner);
                    spn_prodi.setAdapter(spinnerArrayAdapterAkuntansi);
                } else if (position == 2) {
                    spinnerArrayAdapterIlmuEkonomi.setDropDownViewResource(R.layout.card_spinner);
                    spn_prodi.setAdapter(spinnerArrayAdapterIlmuEkonomi);
                } else if (position == 3) {
                    spinnerArrayAdapterManajemen.setDropDownViewResource(R.layout.card_spinner);
                    spn_prodi.setAdapter(spinnerArrayAdapterManajemen);
                } else {
                    spinnerArrayAdapterProdi.setDropDownViewResource(R.layout.card_spinner);
                    spn_prodi.setAdapter(spinnerArrayAdapterProdi);
                }
                getDataAlumniCount();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void getDataAlumniDaftar() {
        JsonApi jsonApi = Client.getClient().create(JsonApi.class);

        Call<ArrayList<DaftarModel>> call;

        if (edt_angkatan.getText().toString().equalsIgnoreCase("")) {
            call = jsonApi.getDataAlumniDaftar(
                    spn_jurusan.getSelectedItem().toString(),
                    spn_prodi.getSelectedItem().toString(),
                    "",
                    edt_jabatan.getText().toString());
        } else if (edt_jabatan.getText().toString().equalsIgnoreCase("")) {
            call = jsonApi.getDataAlumniDaftar(
                    spn_jurusan.getSelectedItem().toString(),
                    spn_prodi.getSelectedItem().toString(),
                    edt_angkatan.getText().toString(),
                    "");
        } else if (edt_angkatan.getText().toString().equalsIgnoreCase("")
                && edt_jabatan.getText().toString().equalsIgnoreCase("")) {
            call = jsonApi.getDataAlumniDaftar(
                    spn_jurusan.getSelectedItem().toString(),
                    spn_prodi.getSelectedItem().toString(),
                    "",
                    "");
        } else {
            call = jsonApi.getDataAlumniDaftar(
                    spn_jurusan.getSelectedItem().toString(),
                    spn_prodi.getSelectedItem().toString(),
                    edt_angkatan.getText().toString(),
                    edt_jabatan.getText().toString());
        }
        call.enqueue(new Callback<ArrayList<DaftarModel>>() {
            @Override
            public void onResponse(Call<ArrayList<DaftarModel>> call, Response<ArrayList<DaftarModel>> response) {
                if (!response.isSuccessful()) {
                    CAN_CLICK_BUTTON_SAVE = 0;
                    return;
                }
                CAN_CLICK_BUTTON_SAVE = 0;
                daftarModels.clear();
                ArrayList<DaftarModel> daftarModelsResponse = response.body();
                daftarModels.addAll(daftarModelsResponse);
                Intent intent = new Intent(getActivity(), PimDaftarAlumniActivity.class);
                intent.putParcelableArrayListExtra("daftarModels", daftarModels);
                rootView.getContext().startActivity(intent);
            }

            @Override
            public void onFailure(Call<ArrayList<DaftarModel>> call, Throwable t) {
                CAN_CLICK_BUTTON_SAVE = 0;
                if (t.getMessage().contains("Failed to connect")) {
                    Toast.makeText(getContext(), TEXT_NO_INTERNET, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getDataAlumniCount() {
        JsonApi jsonApi = Client.getClient().create(JsonApi.class);

        Call<DaftarModel> call;

        if (edt_angkatan.getText().toString().equalsIgnoreCase("")) {
            call = jsonApi.getDataAlumniCount(
                    spn_jurusan.getSelectedItem().toString(),
                    spn_prodi.getSelectedItem().toString(),
                    "",
                    edt_jabatan.getText().toString());
        } else if (edt_jabatan.getText().toString().equalsIgnoreCase("")) {
            call = jsonApi.getDataAlumniCount(
                    spn_jurusan.getSelectedItem().toString(),
                    spn_prodi.getSelectedItem().toString(),
                    edt_angkatan.getText().toString(),
                    "");
        } else if (edt_angkatan.getText().toString().equalsIgnoreCase("")
                && edt_jabatan.getText().toString().equalsIgnoreCase("")) {
            call = jsonApi.getDataAlumniCount(
                    spn_jurusan.getSelectedItem().toString(),
                    spn_prodi.getSelectedItem().toString(),
                    "",
                    "");
        } else {
            call = jsonApi.getDataAlumniCount(
                    spn_jurusan.getSelectedItem().toString(),
                    spn_prodi.getSelectedItem().toString(),
                    edt_angkatan.getText().toString(),
                    edt_jabatan.getText().toString());
        }
        progressBar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<DaftarModel>() {
            @Override
            public void onResponse(Call<DaftarModel> call, Response<DaftarModel> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                DaftarModel daftarResponse = response.body();
                tv_totalAlumni.setText(daftarResponse.getJumlah());
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<DaftarModel> call, Throwable t) {
                if (t.getMessage().contains("Failed to connect")) {
                    Toast.makeText(getContext(), TEXT_NO_INTERNET, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setSpinnerOnItemSelectedListener(Spinner spinner) {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setEditTextTextChangeListener(edt_angkatan);
                setEditTextTextChangeListener(edt_jabatan);
                getDataAlumniCount();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setEditTextTextChangeListener(EditText editText) {

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                getDataAlumniCount();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

}
