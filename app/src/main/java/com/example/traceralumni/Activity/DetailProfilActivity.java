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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.traceralumni.Activity.MainActivity.BASE_URL;
import static com.example.traceralumni.Activity.MainActivity.JENIS_USER;
import static com.example.traceralumni.Activity.MainActivity.JENIS_USER_ALUMNI;
import static com.example.traceralumni.Activity.MainActivity.JENIS_USER_OPERATOR;
import static com.example.traceralumni.Activity.MainActivity.NIM;
import static com.example.traceralumni.Activity.MainActivity.TEXT_NO_INTERNET;

public class DetailProfilActivity extends AppCompatActivity {
    BottomNavigationView bnChat;
    DaftarModel daftarModel;
    TextView tvNama, tvProdi, tvAngkatan, tvThnLulus, tvTglYudisium, tvKwn, tvNegara, tvEmail, tvTTL, tvAlamat, tvKodePos, tvNoHp, tvNoTelp, tvFacebook, tvTwitter;
    TextView tvNim, tvPassword, tvJudulPassword;
    View garis;

    CircleImageView img_detail_profil;
    ConstraintLayout cl_wa;

    String oldPath = "";

    Button btnDelete;

    FirebaseAuth mAuth;
    DatabaseReference mDatabaseUser, mDatabaseToken, mDatabaseChat, mDatabaseChatlist;
    FirebaseUser user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_profil);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        initView();

        if (!JENIS_USER.equals(JENIS_USER_ALUMNI)) {
            bnChat.setVisibility(View.GONE);
        } else if (getIntent().getBooleanExtra("dariPesanActivity", false)) {
            bnChat.setVisibility(View.GONE);
        }

        getDataFromIntent();


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

    private void moveToPesanActivity() {
        if (daftarModel != null) {
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
            tvPassword.setText(daftarModel.getPassword());
            tvNim.setText(daftarModel.getNim());
            if (daftarModel.getNim().equals(NIM)) {
                bnChat.setVisibility(View.GONE);
            }
        } else {
            getDataFromNIM(intent.getStringExtra("nim"));
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
                if (daftarModel.getNim().equals(NIM)) {
                    bnChat.setVisibility(View.GONE);
                }
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
        mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("Users");
        mDatabaseToken = FirebaseDatabase.getInstance().getReference().child("Tokens");
        mDatabaseChat = FirebaseDatabase.getInstance().getReference().child("Chats");
        mDatabaseChatlist = FirebaseDatabase.getInstance().getReference().child("Chatlist");

        garis = findViewById(R.id.v_garis16);
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
            garis.setVisibility(View.VISIBLE);
        }
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showHapusDialog();
            }
        });
    }

    private void hapusAlumniFirebase(String email, String pass) {
        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    user = mAuth.getCurrentUser();
                    hapusRootToken(user.getUid());
                } else {
                    Toast.makeText(DetailProfilActivity.this, "Gagal login", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void hapusRootToken(final String uid) {
        mDatabaseToken.child(uid).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(DetailProfilActivity.this, "Hapus token berhasil", Toast.LENGTH_SHORT).show();
                    hapusChat(uid);
                } else {
                    Toast.makeText(DetailProfilActivity.this, "Hapus token gagal", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void hapusChat(final String uid) {
        mDatabaseChat.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.child("sender").getValue().toString().equals(uid) ||
                            data.child("receiver").getValue().toString().equals(uid)) {
                        mDatabaseChat.child(data.getKey()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(DetailProfilActivity.this, "Berhasil menghapus chat", Toast.LENGTH_SHORT).show();
                                    hapusChildChatlist(uid);
                                } else {
                                    Toast.makeText(DetailProfilActivity.this, "Gagal menghapus chat", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void hapusChatlist(final String uid) {
        mDatabaseChatlist.child(uid).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(DetailProfilActivity.this, "Hapus chatlist berhasil", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DetailProfilActivity.this, "Hapus chatlist gagal", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void hapusChildChatlist(final String uid) {
        mDatabaseChatlist.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (final DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.getKey().equals(uid)) {
                        hapusChatlist(uid);
                    }
                    mDatabaseChatlist.child(data.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                if (snapshot.child("id").getValue().toString().equals(uid)) {
                                    mDatabaseChatlist.child(data.getKey()).child(snapshot.getKey()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(DetailProfilActivity.this, "Berhasil menghapus chatlist receiver", Toast.LENGTH_SHORT).show();
                                                hapusRootUser(uid);
                                            } else {
                                                Toast.makeText(DetailProfilActivity.this, "Gagal menghapus chatlist receiver", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void hapusRootUser(String uid) {
        mDatabaseUser.child(uid).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(DetailProfilActivity.this, "Hapus root database user berhasil", Toast.LENGTH_SHORT).show();
                    hapusAuthFirebase(user);
                } else {
                    Toast.makeText(DetailProfilActivity.this, "Hapus root database gagal", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void hapusAuthFirebase(FirebaseUser user) {
        user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(DetailProfilActivity.this, "Berhasil menghapus auth", Toast.LENGTH_SHORT).show();
                    mAuth.signOut();
                } else {
                    Toast.makeText(DetailProfilActivity.this, "Gagal menghapus auth", Toast.LENGTH_SHORT).show();
                    mAuth.signOut();
                }
            }
        });
    }
}
