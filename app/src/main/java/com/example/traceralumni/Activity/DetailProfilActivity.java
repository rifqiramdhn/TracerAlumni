package com.example.traceralumni.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.traceralumni.Adapter.RiwayatPekerjaanAdapter;
import com.example.traceralumni.Model.DaftarModel;
import com.example.traceralumni.Model.RiwayatPekerjaanModel;
import com.example.traceralumni.R;

import java.util.ArrayList;

import static com.example.traceralumni.Activity.MainActivity.JENIS_USER;
import static com.example.traceralumni.Activity.MainActivity.JENIS_USER_ALUMNI;

public class DetailProfilActivity extends AppCompatActivity {
    RecyclerView riwayatRecycler;
    RiwayatPekerjaanAdapter riwayatAdapter;
    ArrayList<RiwayatPekerjaanModel> riwayatModel;
    BottomNavigationView bnChat;

    TextView tvNama, tvProdi, tvAngkatan, tvThnLulus, tvTglYudisium, tvKwn, tvNegara, tvEmail, tvTTL, tvAlamat, tvKodePos, tvNoHp, tvNoTelp, tvFacebook, tvTwitter, tvStatus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_profil);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        bnChat = findViewById(R.id.bn_chat);

        if (!JENIS_USER.equals(JENIS_USER_ALUMNI)) {
            bnChat.setVisibility(View.GONE);
        }

        riwayatModel = new ArrayList<>();
        riwayatModel.add(new RiwayatPekerjaanModel("Manager", "Akuntansi", "PT. Galon Jaya", "Palembang, Indonesia", 50000000, "2012", "2018"));
        riwayatModel.add(new RiwayatPekerjaanModel("Manager", "Akuntansi", "PT. Galon Jaya", "Palembang, Indonesia", 50000000, "2012", "2018"));
        riwayatModel.add(new RiwayatPekerjaanModel("Manager", "Akuntansi", "PT. Galon Jaya", "Palembang, Indonesia", 50000000, "2012", "2018"));
        riwayatModel.add(new RiwayatPekerjaanModel("Manager", "Akuntansi", "PT. Galon Jaya", "Palembang, Indonesia", 50000000, "2012", "2018"));
        riwayatModel.add(new RiwayatPekerjaanModel("Manager", "Akuntansi", "PT. Galon Jaya", "Palembang, Indonesia", 50000000, "2012", "2018"));

        riwayatRecycler = findViewById(R.id.rv_riwayat_pekerjaan);
        riwayatRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        riwayatAdapter = new RiwayatPekerjaanAdapter(riwayatModel);
        riwayatRecycler.setAdapter(riwayatAdapter);

        riwayatRecycler.setNestedScrollingEnabled(false);

        bnChat.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.ic_chat) {
                    //tempat intent untuk pindah ke chat
                }
                return true;
            }
        });

        tvNama = findViewById(R.id.txt_nama);
        tvProdi = findViewById(R.id.txt_prodi);
        tvAngkatan = findViewById(R.id.txt_angkatan);
        tvThnLulus = findViewById(R.id.txt_thnLulus);
        tvTglYudisium = findViewById(R.id.txt_tglYudisium);
        tvKwn = findViewById(R.id.txt_kewarganegaraan);
        tvNegara = findViewById(R.id.txt_wargaNegara);
        tvEmail = findViewById(R.id.tv_email);
        tvTTL = findViewById(R.id.txt_ttl);
        tvAlamat = findViewById(R.id.txt_alamat);
        tvKodePos = findViewById(R.id.txt_kodePos);
        tvNoHp = findViewById(R.id.txt_noHP);
        tvNoTelp = findViewById(R.id.txt_notelp);
        tvFacebook = findViewById(R.id.txt_facebook);
        tvTwitter = findViewById(R.id.txt_twitter);
        tvStatus = findViewById(R.id.txt_status);

        Intent intent = getIntent();
        DaftarModel daftarModel = intent.getParcelableExtra("daftarModel");
        tvNama.setText(daftarModel.getNama());
        tvProdi.setText(daftarModel.getProdi());
        tvAngkatan.setText(daftarModel.getAngkatan());
        tvThnLulus.setText(daftarModel.getTahun_lulus());
//        tvTglYudisium.setText(daftarModel.getTanggal_yudisium());
        tvKwn.setText(daftarModel.getKewarganegaraan());
        tvNegara.setText(daftarModel.getNama_negara());
        tvEmail.setText(daftarModel.getEmail());
        tvTTL.setText(daftarModel.getTempat_lahir());
        tvAlamat.setText(daftarModel.getAlamat());
        tvKodePos.setText(daftarModel.getKode_pos());
        tvNoHp.setText(daftarModel.getNomor_hp());
        tvNoTelp.setText(daftarModel.getNomor_telepon());
        tvFacebook.setText(daftarModel.getFacebook());
        tvTwitter.setText(daftarModel.getTwitter());
        tvStatus.setText(daftarModel.getStatus_bekerja());
    }

}
