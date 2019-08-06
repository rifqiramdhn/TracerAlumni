package com.example.traceralumni.Activity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.traceralumni.Client;
import com.example.traceralumni.JsonApi;
import com.example.traceralumni.Model.DaftarModel;
import com.example.traceralumni.Model.PathModel;
import com.example.traceralumni.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.traceralumni.Activity.LocationPickerActivity.KODE_POS_EXTRA_KEY;
import static com.example.traceralumni.Activity.LocationPickerActivity.LOKASI_EXTRA_KEY;
import static com.example.traceralumni.Activity.MainActivity.BASE_URL;
import static com.example.traceralumni.Activity.MainActivity.EMAIL_PREF;
import static com.example.traceralumni.Activity.MainActivity.NIM;
import static com.example.traceralumni.Activity.MainActivity.PASS;
import static com.example.traceralumni.Activity.MainActivity.SHARE_PREFS;
import static com.example.traceralumni.Activity.MainActivity.TEXT_NO_INTERNET;

public class SuntingProfilActivity extends AppCompatActivity {

    ConstraintLayout cl_iconBack, cl_iconConfirm;
    ImageView img_iconBack, img_iconConfirm;
    TextView tv_titleBar;
    DatePickerDialog picker;
    EditText edt_email, edt_tempat_lahir, edt_tanggal_lahir, edt_alamat, edt_kode_pos, edt_angkatan,
            edt_tahun_lulus, edt_tanggal_yudisium, edt_negara, edt_no_hp, edt_no_telp, edt_facebook,
            edt_twitter;
    Spinner spn_kewarganegaraaan;
    DaftarModel daftarModel;
    CircleImageView img_foto_profil, img_edit_foto_profil;
    ProgressDialog progressDialog;

    static final int PICK_PHOTO_REQUEST = 1;
    static final int PICK_ADDRESS_REQUEST = 2;

    String oldPath = "";
    String newPath;

    private boolean dataSudahLengkap = false;
    private boolean dataBerubah = false;

    private FirebaseAuth mFirebaseAuth;

    private static String TAG = SuntingProfilActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sunting_profil);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        mFirebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(SuntingProfilActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");

        setIcon();
        getView();

        daftarModel = getIntent().getParcelableExtra("daftarModel");
        if (daftarModel != null) {
            setView();
            customSpinner();
        } else {
            getDataFromNIM(NIM);
        }
        datePickerGetDate(edt_tanggal_lahir);
        datePickerGetDate(edt_tanggal_yudisium);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                moveToLocationPickerActivity();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PICK_PHOTO_REQUEST) {
            if (resultCode == RESULT_OK) {
                Uri imageUri = data.getData();
                uploadPhoto(imageUri);
            }
        } else if (requestCode == PICK_ADDRESS_REQUEST) {
            edt_alamat.setText(data.getStringExtra(LOKASI_EXTRA_KEY));
            edt_kode_pos.setText(data.getStringExtra(KODE_POS_EXTRA_KEY));
        }
    }

    private void setIcon() {
        cl_iconBack = findViewById(R.id.cl_icon1);
        cl_iconBack.setVisibility(View.VISIBLE);
        cl_iconConfirm = findViewById(R.id.cl_icon4);
        cl_iconConfirm.setVisibility(View.VISIBLE);
        cl_iconConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_email.getText().toString().equalsIgnoreCase("")) {
                    edt_email.setError("Wajib diisi!");
                } else if (edt_tempat_lahir.getText().toString().equalsIgnoreCase("")) {
                    edt_tempat_lahir.setError("Wajib diisi!");
                } else if (edt_tanggal_lahir.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(SuntingProfilActivity.this, "Anda belum mengisi tanggal lahir!", Toast.LENGTH_SHORT).show();
                } else if (edt_alamat.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(SuntingProfilActivity.this, "Anda belum mengisi alamat!", Toast.LENGTH_SHORT).show();
                } else if (edt_kode_pos.getText().toString().equalsIgnoreCase("")) {
                    edt_kode_pos.setError("Wajib diisi!");
                } else if (edt_angkatan.getText().toString().equalsIgnoreCase("")) {
                    edt_angkatan.setError("Wajib diisi!");
                } else if (Integer.valueOf(edt_angkatan.getText().toString()) > Integer.valueOf(edt_tahun_lulus.getText().toString())) {
                    edt_angkatan.setError("Tahun angkatan tidak valid!");
                } else if (edt_tahun_lulus.getText().toString().equalsIgnoreCase("")) {
                    edt_tahun_lulus.setError("Wajib diisi!");
                } else if (spn_kewarganegaraaan.getSelectedItem().toString().equalsIgnoreCase("Kewarganegaraan")) {
                    Toast.makeText(SuntingProfilActivity.this, "Anda belum memilih kewarganegaraan!", Toast.LENGTH_SHORT).show();
                } else if (edt_negara.getText().toString().equalsIgnoreCase("")) {
                    edt_negara.setError("Wajib diisi!");
                } else if (edt_no_hp.getText().toString().equalsIgnoreCase("")) {
                    edt_no_hp.setError("Wajib diisi!");
                } else if (edt_no_hp.getText().length() < 10) {
                    edt_no_hp.setError("No HP. tidak valid!");
                } else {
                    showSimpanPerubahanDialog();
                }
            }
        });
        img_iconBack = findViewById(R.id.img_icon1);
        img_iconBack.setImageResource(R.drawable.ic_arrow_back);
        img_iconConfirm = findViewById(R.id.img_icon4);
        img_iconConfirm.setImageResource(R.drawable.ic_check);
        tv_titleBar = findViewById(R.id.tv_navbar_top);
    }

    private void getView() {
        edt_email = findViewById(R.id.edt_sunting_profil_email);
        edt_tempat_lahir = findViewById(R.id.edt_sunting_profil_tempat_lahir);
        edt_alamat = findViewById(R.id.edt_sunting_profil_alamat);
        edt_alamat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(SuntingProfilActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(SuntingProfilActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                } else {
                    moveToLocationPickerActivity();
                }

            }
        });
        edt_tanggal_lahir = findViewById(R.id.edt_sunting_profil_tanggal_lahir);
        edt_kode_pos = findViewById(R.id.edt_sunting_profil_kode_pos);
        edt_angkatan = findViewById(R.id.edt_sunting_profil_angkatan);
        edt_tahun_lulus = findViewById(R.id.edt_sunting_profil_tahun_lulus);
        edt_tanggal_yudisium = findViewById(R.id.edt_sunting_profil_tanggal_yudisium);
        spn_kewarganegaraaan = findViewById(R.id.spn_sunting_profil_kewarganegaraan);
        edt_negara = findViewById(R.id.edt_sunting_profil_negara);
        edt_no_hp = findViewById(R.id.edt_sunting_profil_no_hp);
        edt_no_telp = findViewById(R.id.edt_sunting_profil_no_telepon);
        edt_facebook = findViewById(R.id.edt_sunting_profil_facebook);
        edt_twitter = findViewById(R.id.edt_sunting_profil_twitter);
        img_foto_profil = findViewById(R.id.img_sunting_profil_foto);
        img_edit_foto_profil = findViewById(R.id.img_sunting_profil_edit_foto);
        img_edit_foto_profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(SuntingProfilActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(SuntingProfilActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    getPhotoFromGallery();
                }
            }
        });
    }

    private void setView() {
        if (daftarModel.getEmail().contains("traceralumnifeb@gmail.com")) {
            edt_email.setText("");
            tv_titleBar.setText("ISI DATA");
            cl_iconBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MainActivity.showKeluarDialog(SuntingProfilActivity.this);
                }
            });
        } else {
            dataSudahLengkap = true;
            edt_email.setText(daftarModel.getEmail());
            tv_titleBar.setText("SUNTING PROFIL");
            cl_iconBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SuntingProfilActivity.super.onBackPressed();
                }
            });
        }
        edt_tanggal_lahir.setText(daftarModel.getTanggal_lahir());
        edt_tempat_lahir.setText(daftarModel.getTempat_lahir());
        edt_alamat.setText(daftarModel.getAlamat());
        edt_kode_pos.setText(daftarModel.getKode_pos());
        edt_angkatan.setText(daftarModel.getAngkatan());
        edt_tahun_lulus.setText(daftarModel.getTahun_lulus());
        edt_tanggal_yudisium.setText(daftarModel.getTanggal_yudisium());
        edt_negara.setText(daftarModel.getNama_negara());
        edt_no_hp.setText(daftarModel.getNomor_hp());
        edt_no_telp.setText(daftarModel.getNomor_telepon());
        edt_facebook.setText(daftarModel.getFacebook());
        edt_twitter.setText(daftarModel.getTwitter());
        oldPath = daftarModel.getFoto();
        if (oldPath != null) {
            Glide.with(SuntingProfilActivity.this)
                    .load(BASE_URL + oldPath)
                    .into(img_foto_profil);
        }
        cekPerubahan();
    }

    private void getPhotoFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_PHOTO_REQUEST);
    }

    private void uploadPhoto(Uri fileUri) {
        File file = new File(getRealPathFromURI(fileUri));

        File compressedFile = new File(getRealPathFromURI(fileUri));
        try {
            compressedFile = new Compressor(this).compressToFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse(getContentResolver().getType(fileUri)), compressedFile);
        MultipartBody.Part kirim = MultipartBody.Part.createFormData("image", file.getName(), requestBody);

        final JsonApi jsonApi = Client.getClient().create(JsonApi.class);
        Call<PathModel> call = jsonApi.uploadPhoto(kirim);
        call.enqueue(new Callback<PathModel>() {
            @Override
            public void onResponse(Call<PathModel> call, Response<PathModel> response) {
                if (!response.isSuccessful()) {
                    return;
                }

                PathModel pathModel = response.body();
                if (!pathModel.getPath().equals("invalid")) {
                    newPath = pathModel.getPath();
                    addPhotoPathToDatabase(jsonApi);
                    oldPath = pathModel.getPath();
                    Glide.with(SuntingProfilActivity.this)
                            .load(BASE_URL + pathModel.getPath())
                            .into(img_foto_profil);
                }
            }

            @Override
            public void onFailure(Call<PathModel> call, Throwable t) {
                if (t.getMessage().contains("Failed to connect")) {
                    Toast.makeText(SuntingProfilActivity.this, TEXT_NO_INTERNET, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void addPhotoPathToDatabase(JsonApi jsonApi) {
        Call<Void> call = jsonApi.updatePhotoPath(NIM, oldPath, newPath);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    return;
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                if (t.getMessage().contains("Failed to connect")) {
                    Toast.makeText(SuntingProfilActivity.this, TEXT_NO_INTERNET, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(this, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    private void moveToLocationPickerActivity() {
        Intent i = new Intent(SuntingProfilActivity.this, LocationPickerActivity.class);
        startActivityForResult(i, PICK_ADDRESS_REQUEST);
    }

    private void datePickerGetDate(final EditText editText) {
        editText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                picker = new DatePickerDialog(SuntingProfilActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                editText.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            }
                        }, year, month, day);
                picker.show();
            }
        });
    }

    private void customSpinner() {
        String[] kewarganegaraan = new String[]{
                "Kewarganegaraan",
                "WNI",
                "WNA"
        };

        final List<String> kewarganegaraanList = new ArrayList<>(Arrays.asList(kewarganegaraan));

        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this, R.layout.card_spinner, kewarganegaraanList) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
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
                    tv.setTextColor(getResources().getColor(R.color.colorIconBiru));
                }
                return view;
            }
        };
        spinnerArrayAdapter.setDropDownViewResource(R.layout.card_spinner);
        spn_kewarganegaraaan.setAdapter(spinnerArrayAdapter);
        if (daftarModel.getKewarganegaraan().equalsIgnoreCase("WNI")) {
            spn_kewarganegaraaan.setSelection(1);
        } else {
            spn_kewarganegaraaan.setSelection(2);
        }
    }

    private void suntingProfil() {
        progressDialog.show();
        JsonApi jsonApi = Client.getClient().create(JsonApi.class);
        String telepon;
        if (edt_no_hp.getText().toString().charAt(0) == '0') {
            telepon = "+62" + edt_no_hp.getText().toString().substring(1);
        } else if (edt_no_hp.getText().toString().charAt(0) != '+') {
            telepon = "+" + edt_no_hp.getText();
        } else {
            telepon = edt_no_hp.getText().toString();
        }
        Call<Void> call = jsonApi.suntingProfil(
                daftarModel.getNim(),
                edt_email.getText().toString(),
                edt_tempat_lahir.getText().toString(),
                edt_tanggal_lahir.getText().toString(),
                edt_alamat.getText().toString(),
                edt_kode_pos.getText().toString(),
                edt_angkatan.getText().toString(),
                edt_tahun_lulus.getText().toString(),
                edt_tanggal_yudisium.getText().toString(),
                spn_kewarganegaraaan.getSelectedItem().toString(),
                edt_negara.getText().toString(),
                telepon,
                edt_no_telp.getText().toString(),
                edt_facebook.getText().toString(),
                edt_twitter.getText().toString());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                AuthCredential credential = EmailAuthProvider.getCredential(daftarModel.getEmail(), PASS);
                mFirebaseAuth.getCurrentUser().reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            mFirebaseAuth.getCurrentUser().updateEmail(edt_email.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    progressDialog.dismiss();
                                    SharedPreferences sharedPreferences = getSharedPreferences(SHARE_PREFS, MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString(EMAIL_PREF, daftarModel.getEmail());
                                    editor.apply();
                                    SuntingProfilActivity.super.onBackPressed();
                                    Toast.makeText(SuntingProfilActivity.this, "Data telah diubah", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            Log.d(TAG, "Auth ganti password gagal");
                        }
                    }
                });

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                if (t.getMessage().contains("Failed to connect")) {
                    Toast.makeText(SuntingProfilActivity.this, TEXT_NO_INTERNET, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showSimpanPerubahanDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SuntingProfilActivity.this);
        builder.setMessage("Simpan perubahan?");
        builder.setTitle("Sunting Profil");
        builder.setCancelable(false);
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                suntingProfil();
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

    private void showKonfirmasiKembaliDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SuntingProfilActivity.this);

        builder.setMessage("Anda yakin ingin kembali?\nPerubahan yang anda buat tidak akan disimpan");

        builder.setTitle("Kembali");

        builder.setCancelable(false);

        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                SuntingProfilActivity.super.onBackPressed();
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

    private void setTextWatcher(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                dataBerubah = true;
                if (!dataSudahLengkap) {
                    cl_iconBack.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            MainActivity.showKeluarDialog(SuntingProfilActivity.this);
                        }
                    });
                } else {
                    cl_iconBack.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            showKonfirmasiKembaliDialog();
                        }
                    });
                }
            }
        });
    }

    private void cekPerubahan() {
        setTextWatcher(edt_email);
        setTextWatcher(edt_tempat_lahir);
        setTextWatcher(edt_tanggal_lahir);
        setTextWatcher(edt_alamat);
        setTextWatcher(edt_kode_pos);
        setTextWatcher(edt_angkatan);
        setTextWatcher(edt_tahun_lulus);
        setTextWatcher(edt_tanggal_yudisium);
        setTextWatcher(edt_negara);
        setTextWatcher(edt_no_hp);
        setTextWatcher(edt_no_telp);
        setTextWatcher(edt_facebook);
        setTextWatcher(edt_twitter);
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
                setView();
                customSpinner();
            }

            @Override
            public void onFailure(Call<DaftarModel> call, Throwable t) {
                if (t.getMessage().contains("Failed to connect")) {
                    Toast.makeText(SuntingProfilActivity.this, TEXT_NO_INTERNET, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (!dataSudahLengkap && dataBerubah) {
            MainActivity.showKeluarDialog(SuntingProfilActivity.this);
        } else if (!dataSudahLengkap) {
            MainActivity.showKeluarDialog(SuntingProfilActivity.this);
        } else if (dataBerubah) {
            showKonfirmasiKembaliDialog();
        } else {
            super.onBackPressed();
        }
    }
}
