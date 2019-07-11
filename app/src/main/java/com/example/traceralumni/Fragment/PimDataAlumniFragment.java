package com.example.traceralumni.Fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.traceralumni.Activity.PimDaftarAlumniActivity;
import com.example.traceralumni.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PimDataAlumniFragment extends Fragment {
    View rootView;
    Spinner spn_jurusan, spn_prodi;
    TextView tv_totalAlumni;
    EditText edt_angkatan, edt_jabatan;
    Button btn_lihatDaftar;

    public PimDataAlumniFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_pim_data_alumni, container, false);

        ambilView();
        customSpinner();

        btn_lihatDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), PimDaftarAlumniActivity.class);
                startActivity(i);
            }
        });
        return rootView;
    }

    private void ambilView(){
        spn_jurusan = rootView.findViewById(R.id.spn_daftar_jurusan);
        spn_prodi = rootView.findViewById(R.id.spn_daftar_prodi);
        tv_totalAlumni = rootView.findViewById(R.id.tv_total_alumni);
        edt_angkatan = rootView.findViewById(R.id.edt_angkatan);
        edt_jabatan = rootView.findViewById(R.id.edt_jabatan);
        btn_lihatDaftar = rootView.findViewById(R.id.btn_lihat_daftar);
    }

    private void customSpinner(){
        String[] jurusan = new String[]{
            "Ilmu Ekonomi",
            "Akuntansi",
            "Manajemen"
        };

        String[] prodi = new String[]{
            "S1 - Akuntansi (Internasional)",
            "S1 - Ekonomi, Keuangan, dan Perbankan",
            "S1 - Ekonomi, Keuangan, dan Perbankan (Internasional)",
            "S1 - Ekonomi Pembangunan",
            "S1 - Ekonomi Pembangunan (Internasional)",
            "S1 - Kewirausahaan",
            "S1 - Manajemen",
            "S1 - Manajemen (Internasional)",
            "S2 - Akuntansi",
            "S2 - Manajemen",
            "S2 - Ilmu Ekonomi",
            "S3 - Ilmu Akuntansi",
            "S3 - Ilmu Ekonomi",
            "S3 - Ilmu Manajemen",
            "PPAk"
        };

        final List<String> jurusanList = new ArrayList<>(Arrays.asList(jurusan));
        final List<String> prodiList = new ArrayList<>(Arrays.asList(prodi));

        final ArrayAdapter<String> spinnerArrayAdapterJurusan = new ArrayAdapter<String>(
            rootView.getContext(), R.layout.card_spinner, jurusanList) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                } else {
                    return true;
                }
            }

            public View getDropDownView(int position, View convertView, ViewGroup parent){
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0){
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
                if (position == 0) {
                    return false;
                } else {
                    return true;
                }
            }

            public View getDropDownView(int position, View convertView, ViewGroup parent){
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0){
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(getResources().getColor(R.color.colorIconBiru));
                }
                return view;
            }
        };

        spinnerArrayAdapterJurusan.setDropDownViewResource(R.layout.card_spinner);
        spinnerArrayAdapterProdi.setDropDownViewResource(R.layout.card_spinner);
        spn_jurusan.setAdapter(spinnerArrayAdapterJurusan);
        spn_prodi.setAdapter(spinnerArrayAdapterProdi);
    }
}
