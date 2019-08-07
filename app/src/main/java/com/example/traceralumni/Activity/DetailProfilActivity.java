package com.example.traceralumni.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.traceralumni.Client;
import com.example.traceralumni.JsonApi;
import com.example.traceralumni.Model.DaftarModel;
import com.example.traceralumni.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.traceralumni.Activity.MainActivity.BASE_URL;
import static com.example.traceralumni.Activity.MainActivity.JENIS_USER;
import static com.example.traceralumni.Activity.MainActivity.JENIS_USER_ALUMNI;
import static com.example.traceralumni.Activity.MainActivity.JENIS_USER_OPERATOR;
import static com.example.traceralumni.Activity.MainActivity.NIM;
import static com.example.traceralumni.Activity.MainActivity.PASS;
import static com.example.traceralumni.Activity.MainActivity.TEXT_NO_INTERNET;

public class DetailProfilActivity extends AppCompatActivity {
    BottomNavigationView bnChat;
    DaftarModel daftarModel;
    TextView tvNama, tvProdi, tvAngkatan, tvThnLulus, tvTglYudisium, tvKwn, tvNegara, tvEmail, tvTTL, tvAlamat, tvKodePos, tvNoHp, tvNoTelp, tvFacebook, tvTwitter;
    TextView tvNim, tvPassword, tvJudulPassword;

    CircleImageView img_detail_profil;
    ConstraintLayout cl_wa;

    String oldPath = "";

    Button btnDelete;

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_profil);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        initView();

        if (!JENIS_USER.equals(JENIS_USER_ALUMNI)) {
            bnChat.setVisibility(View.GONE);
        }

        getDataFromIntent();

        if (daftarModel.getNim().equals(NIM)){
            bnChat.setVisibility(View.GONE);
        }

        bnChat.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.ic_chat) {
                    moveToPesanActivity();
                }
                return true;
            }
        });
    }

    private void moveToPesanActivity(){
        if (daftarModel != null){
            Intent intent = new Intent(DetailProfilActivity.this, PesanActivity.class);
            intent.putExtra("userId", daftarModel.getUserId());
            startActivity(intent);
        }
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        daftarModel = intent.getParcelableExtra("daftarModel");
        if (daftarModel != null) {
            tvNama.setText(daftarModel.getNama());
            tvProdi.setText(daftarModel.getProdi());
            tvAngkatan.setText(daftarModel.getAngkatan());
            tvThnLulus.setText(daftarModel.getTahun_lulus());
            tvTglYudisium.setText(daftarModel.getTanggal_yudisium());
            tvKwn.setText(daftarModel.getKewarganegaraan());
            tvNegara.setText(daftarModel.getNama_negara());
            tvEmail.setText(daftarModel.getEmail());
            tvTTL.setText(daftarModel.getTempat_lahir().concat(", ").concat(daftarModel.getTanggal_lahir()));
            tvAlamat.setText(daftarModel.getAlamat());
            tvKodePos.setText(daftarModel.getKode_pos());
            tvNoHp.setText(daftarModel.getNomor_hp());
            tvNoTelp.setText(daftarModel.getNomor_telepon());
            tvFacebook.setText(daftarModel.getFacebook());
            tvTwitter.setText(daftarModel.getTwitter());
            oldPath = daftarModel.getFoto();
            Glide.with(DetailProfilActivity.this)
                    .load(BASE_URL + oldPath)
                    .into(img_detail_profil);
            cl_wa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    String urlNew = "http://wa.me/" + daftarModel.getNomor_hp().substring(1);
                    i.setData(Uri.parse(urlNew));
                    startActivity(i);
                }
            });
            tvPassword.setText(daftarModel.getPassword());
            tvNim.setText(daftarModel.getNim());
        } else {
            getDataFromNIM(intent.getStringExtra("edtEmail"));
        }
    }

    private void getDataFromNIM(String nim) {
        JsonApi jsonApi = Client.getClient().create(JsonApi.class);

        Call<DaftarModel> call = jsonApi.getUserData(nim);
        call.enqueue(new Callback<DaftarModel>() {
            @Override
            public void onResponse(Call<DaftarModel> call, Response<DaftarModel> response) {
                if (!response.isSuccessful()) {
                    return;
                }

                daftarModel = response.body();
                tvNama.setText(daftarModel.getNama());
                tvProdi.setText(daftarModel.getProdi());
                tvAngkatan.setText(daftarModel.getAngkatan());
                tvThnLulus.setText(daftarModel.getTahun_lulus());
                tvTglYudisium.setText(daftarModel.getTanggal_yudisium());
                tvKwn.setText(daftarModel.getKewarganegaraan());
                tvNegara.setText(daftarModel.getNama_negara());
                tvEmail.setText(daftarModel.getEmail());
                tvTTL.setText(daftarModel.getTempat_lahir().concat(", ").concat(daftarModel.getTanggal_lahir()));
                tvAlamat.setText(daftarModel.getAlamat());
                tvKodePos.setText(daftarModel.getKode_pos());
                tvNoHp.setText(daftarModel.getNomor_hp());
                tvNoTelp.setText(daftarModel.getNomor_telepon());
                tvFacebook.setText(daftarModel.getFacebook());
                tvTwitter.setText(daftarModel.getTwitter());
                tvPassword.setText(daftarModel.getPassword());
                tvNim.setText(daftarModel.getNim());
                oldPath = daftarModel.getFoto();
                if (!oldPath.equals("")) {
                    Glide.with(DetailProfilActivity.this)
                            .load(BASE_URL + oldPath)
                            .into(img_detail_profil);
                }
                cl_wa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        String urlNew = "http://wa.me/" + daftarModel.getNomor_hp().substring(1);
                        i.setData(Uri.parse(urlNew));
                        startActivity(i);
                    }
                });

            }

            @Override
            public void onFailure(Call<DaftarModel> call, Throwable t) {
                if (t.getMessage().contains("Failed to connect")) {
                    Toast.makeText(DetailProfilActivity.this, TEXT_NO_INTERNET, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void showHapusDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Apakah anda yakin ingin menghapus data alumni ini?");
        builder.setTitle("Hapus Alumni");
        builder.setCancelable(false);
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                hapusAlumni(daftarModel.getNim(), daftarModel.getPassword());
            }
        });

        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void hapusAlumni(String nim, final String pass) {
        JsonApi jsonApi = Client.getClient().create(JsonApi.class);

        Call<Void> call = jsonApi.deleteAlumni(nim);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                hapusAlumniFirebase(daftarModel.getEmail(), pass);
                onBackPressed();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                if (t.getMessage().contains("Failed to connect")) {
                    Toast.makeText(DetailProfilActivity.this, TEXT_NO_INTERNET, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initView() {
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        img_detail_profil = findViewById(R.id.iv_activity_detail_profil_foto);
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
        bnChat = findViewById(R.id.bn_chat);
        cl_wa = findViewById(R.id.cl_detail_profil_wa);
        tvPassword = findViewById(R.id.tv_detail_profil_password);
        tvNim = findViewById(R.id.tv_detail_profil_nim);
        tvJudulPassword = findViewById(R.id.tv_judul_password);
        btnDelete = findViewById(R.id.btn_hapus_alumni);
        if (JENIS_USER.equals(JENIS_USER_OPERATOR)) {
            tvJudulPassword.setVisibility(View.VISIBLE);
            tvPassword.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.VISIBLE);
        }
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showHapusDialog();
            }
        });
    }

    private void hapusAlumniFirebase(String email, String pass){
        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    final FirebaseUser user = mAuth.getCurrentUser();
                    user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                mDatabase.child(user.getUid()).removeValue();
                                Toast.makeText(DetailProfilActivity.this, "Alumni berhasil dihapus!", Toast.LENGTH_SHORT).show();
                                mAuth.signOut();
                            }
                        }
                    });

                }
            }
        });
    }
}
