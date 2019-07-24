package com.example.traceralumni.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.traceralumni.JsonPlaceHolderApi;
import com.example.traceralumni.Model.DaftarModel;
import com.example.traceralumni.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.traceralumni.Activity.MainActivity.BASE_URL;
import static com.example.traceralumni.Activity.MainActivity.NIM;

public class KartuAlumniActivity extends AppCompatActivity {
    DaftarModel daftarModel;
    TextView tvNamaAlumni, tvNim, tvAngkatan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kartu_alumni);
        daftarModel = getIntent().getParcelableExtra("daftarModel");
        getView();
    }

    private void getView() {
        tvNamaAlumni = findViewById(R.id.tv_kartu_alumni_nama);
        tvNim = findViewById(R.id.tv_kartu_alumni_nim);
        tvAngkatan = findViewById(R.id.tv_kartu_alumni_angkatan);
        tvNamaAlumni.setText(daftarModel.getNama());
        tvNim.setText(daftarModel.getNim());
        tvAngkatan.setText(daftarModel.getAngkatan());
    }

}
