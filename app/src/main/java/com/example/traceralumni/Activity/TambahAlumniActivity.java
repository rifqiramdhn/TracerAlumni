package com.example.traceralumni.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.traceralumni.Client;
import com.example.traceralumni.JsonApi;
import com.example.traceralumni.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.traceralumni.Activity.MainActivity.TEXT_NO_INTERNET;

public class TambahAlumniActivity extends AppCompatActivity {
    private ConstraintLayout clIconBack, clIconOk;
    private ImageView imgIconBack, imgIconOk;
    private TextView tvNavbar;

    int CAN_CLICK_BUTTON_SAVE = 0;

    AlertDialog.Builder builder;
    Integer id_prodi, id_jurusan;
    Spinner spn_jurusan, spn_prodi;
    EditText edtNim, edtNama;

    ProgressDialog pd;

    FirebaseAuth auth;
    FirebaseAuth auth2;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_alumni);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        builder = new AlertDialog.Builder(this);

        pd = new ProgressDialog(TambahAlumniActivity.this);
        pd.setCancelable(false);
        pd.setMessage("Loading...");
        auth = FirebaseAuth.getInstance();
        auth2 = FirebaseAuth.getInstance();

        initView();
    }

    private void initView() {
        tvNavbar = findViewById(R.id.tv_navbar_top);
        tvNavbar.setText("TAMBAH ALUMNI");
        spn_jurusan = findViewById(R.id.spn_tambah_alumni_daftar_jurusan);
        spn_prodi = findViewById(R.id.spn_tambah_alumni_daftar_prodi);
        edtNim = findViewById(R.id.edt_tambah_alumni_nim);
        edtNama = findViewById(R.id.edt_tambah_alumni_nama);
        clIconBack = findViewById(R.id.cl_icon1);
        clIconBack.setVisibility(View.VISIBLE);
        clIconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        clIconOk = findViewById(R.id.cl_icon4);
        clIconOk.setVisibility(View.VISIBLE);
        clIconOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtNim.length() == 0) {
                    edtNim.setError("Wajib diisi");
                } else if (edtNim.length() < 10) {
                    edtNim.setError("Panjang NIM minimal 10 digit");
                } else if (edtNama.length() == 0) {
                    edtNama.setError("Wajib diisi");
                } else if (spn_prodi.getSelectedItem().toString().equalsIgnoreCase("Prodi")) {
                    Toast.makeText(TambahAlumniActivity.this, "Anda belum memilih prodi", Toast.LENGTH_SHORT).show();
                } else {
                    showKonfirmasiTambah();

                }

            }
        });
        imgIconBack = findViewById(R.id.img_icon1);
        imgIconBack.setImageResource(R.drawable.ic_arrow_back);
        imgIconOk = findViewById(R.id.img_icon4);
        imgIconOk.setImageResource(R.drawable.ic_check);

        customSpinner();
    }

    private void showKonfirmasiTambah() {
        switch (spn_prodi.getSelectedItem().toString()) {
            case "S1 Akuntansi (Internasional)":
                id_jurusan = 1;
                id_prodi = 2;
                break;
            case "S1 Ekonomi, Keuangan, dan Perbankan (Internasional)":
                id_jurusan = 1;
                id_prodi = 3;
                break;
            case "S2 Akuntansi":
                id_jurusan = 1;
                id_prodi = 11;
                break;
            case "S3 Ilmu Akuntansi":
                id_jurusan = 1;
                id_prodi = 14;
                break;
            case "PPAk":
                id_jurusan = 1;
                id_prodi = 17;
                break;
            case "S1 Ekonomi Pembangunan":
                id_jurusan = 2;
                id_prodi = 5;
                break;
            case "S1 Ekonomi Pembangunan (Internasional)":
                id_jurusan = 2;
                id_prodi = 6;
                break;
            case "S2 Ilmu Ekonomi":
                id_jurusan = 2;
                id_prodi = 13;
                break;
            case "S3 Ilmu Ekonomi":
                id_jurusan = 2;
                id_prodi = 15;
                break;
            case "S1 Ekonomi, Keuangan, dan Perbankan":
                id_jurusan = 3;
                id_prodi = 7;
                break;
            case "S1 Kewirausahaan":
                id_jurusan = 3;
                id_prodi = 8;
                break;
            case "S1 Manajemen":
                id_jurusan = 3;
                id_prodi = 9;
                break;
            case "S1 Manajemen (Internasional)":
                id_jurusan = 3;
                id_prodi = 10;
                break;
            case "S2 Manajemen":
                id_jurusan = 3;
                id_prodi = 12;
                break;
            case "S3 Ilmu Manajemen":
                id_jurusan = 3;
                id_prodi = 16;
                break;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(TambahAlumniActivity.this);
        builder.setMessage("Apakah anda yakin ingin menambah alumni?");
        builder.setTitle("Tambah Alumni");
        builder.setCancelable(false);
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
//                if (CAN_CLICK_BUTTON_SAVE == 0){
//                    CAN_CLICK_BUTTON_SAVE = 1;
                pd.show();
                    registerAlumni();
//                }
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

    private void registerAlumni() {
        final String nim = edtNim.getText().toString().trim();
        String nama = edtNama.getText().toString().trim();
        Integer idJurusan = id_jurusan;
        Integer idProdi = id_prodi;

        final String email = nim + "traceralumnifeb@gmail.com";
        final String password = getRandomPassword(8);

        JsonApi jsonApi = Client.getClient().create(JsonApi.class);
        Call<String> call = jsonApi.createAlumni(nama, nim, email, password, idJurusan, idProdi);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (!response.isSuccessful()) {
//                    CAN_CLICK_BUTTON_SAVE = 0;
                    return;
                }
//                CAN_CLICK_BUTTON_SAVE = 0;
                String hasil = response.body();
                if (hasil.equals("0")) {
                    edtNim.setError("NIM sudah digunakan");
                } else {
                    registerToFirebase(email, password, nim);
//                    onBackPressed();
//                    Toast.makeText(TambahAlumniActivity.this, "Alumni baru sudah ditambahkan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
//                CAN_CLICK_BUTTON_SAVE = 0;
                if (t.getMessage().contains("Failed to connect")) {
                    Toast.makeText(TambahAlumniActivity.this, TEXT_NO_INTERNET, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void registerToFirebase(String email, String password, final String nim){
        auth2.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser firebaseUser = auth2.getCurrentUser();
                            assert firebaseUser != null;
                            String userId = firebaseUser.getUid();

                            reference = FirebaseDatabase.getInstance().getReference("Users").child(userId);

                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id", userId);
                            hashMap.put("edtEmail", nim);

                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    auth2.signOut();
                                    pd.dismiss();
                                    Toast.makeText(TambahAlumniActivity.this, "Alumni berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                                    onBackPressed();
                                }
                            });
                        } else {
                            Toast.makeText(TambahAlumniActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            pd.dismiss();
                        }
                    }
                });
    }

    private String getRandomPassword(int n) {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            int index
                    = (int) (AlphaNumericString.length()
                    * Math.random());
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
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
                TambahAlumniActivity.this, R.layout.card_spinner, jurusanList) {
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
                TambahAlumniActivity.this, R.layout.card_spinner, prodiList) {
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
                TambahAlumniActivity.this, R.layout.card_spinner, prodiListAkuntansi) {
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
                TambahAlumniActivity.this, R.layout.card_spinner, prodiListIlmuEkonomi) {
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
                TambahAlumniActivity.this, R.layout.card_spinner, prodiListManajemen) {
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
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
