package com.example.traceralumni.Activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.traceralumni.Adapter.RiwayatPekerjaanAdapter;
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
                if (menuItem.getItemId() == R.id.ic_chat){
                    //tempat intent untuk pindah ke chat
                }
                return true;
            }
        });
    }

}
