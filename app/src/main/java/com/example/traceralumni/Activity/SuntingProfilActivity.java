package com.example.traceralumni.Activity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.example.traceralumni.JsonPlaceHolderApi;
import com.example.traceralumni.Model.DaftarModel;
import com.example.traceralumni.Model.PathModel;
import com.example.traceralumni.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.traceralumni.Activity.LocationPickerActivity.KODE_POS_EXTRA_KEY;
import static com.example.traceralumni.Activity.LocationPickerActivity.LOKASI_EXTRA_KEY;
import static com.example.traceralumni.Activity.MainActivity.BASE_URL;
import static com.example.traceralumni.Activity.MainActivity.NIM;

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

    static final int PICK_PHOTO_REQUEST = 1;
    static final int PICK_ADDRESS_REQUEST = 2;

    String oldPath = "";
    String newPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sunting_profil);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        daftarModel = getIntent().getParcelableExtra("daftarModel");

        setIcon();
        getView();
        customSpinner();
        cekPerubahan();

        edt_alamat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToLocationPickerActivity();
            }
        });

        datePickerGetDate(edt_tanggal_lahir);
        datePickerGetDate(edt_tanggal_yudisium);

        img_edit_foto_profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPhotoFromGallery();
            }
        });
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
        cl_iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
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
                } else if (edt_tahun_lulus.getText().toString().equalsIgnoreCase("")) {
                    edt_tahun_lulus.setError("Wajib diisi!");
                } else if (spn_kewarganegaraaan.getSelectedItem().toString().equalsIgnoreCase("Kewarganegaraan")) {
                    Toast.makeText(SuntingProfilActivity.this, "Anda belum memilih kewarganegaraan!", Toast.LENGTH_SHORT).show();
                } else if (edt_negara.getText().toString().equalsIgnoreCase("")) {
                    edt_negara.setError("Wajib diisi!");
                } else if (edt_no_hp.getText().toString().equalsIgnoreCase("")) {
                    edt_no_hp.setError("Wajib diisi!");
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
        tv_titleBar.setText("SUNTING PROFIL");
    }

    private void getView() {
        edt_email = findViewById(R.id.edt_sunting_profil_email);
        edt_email.setText(daftarModel.getEmail());
        edt_tempat_lahir = findViewById(R.id.edt_sunting_profil_tempat_lahir);
        edt_tempat_lahir.setText(daftarModel.getTempat_lahir());
        edt_tanggal_lahir = findViewById(R.id.edt_sunting_profil_tanggal_lahir);
        edt_tanggal_lahir.setText(daftarModel.getTanggal_lahir());
        edt_alamat = findViewById(R.id.edt_sunting_profil_alamat);
        edt_alamat.setText(daftarModel.getAlamat());
        edt_kode_pos = findViewById(R.id.edt_sunting_profil_kode_pos);
        edt_kode_pos.setText(daftarModel.getKode_pos());
        edt_angkatan = findViewById(R.id.edt_sunting_profil_angkatan);
        edt_angkatan.setText(daftarModel.getAngkatan());
        edt_tahun_lulus = findViewById(R.id.edt_sunting_profil_tahun_lulus);
        edt_tahun_lulus.setText(daftarModel.getTahun_lulus());
        edt_tanggal_yudisium = findViewById(R.id.edt_sunting_profil_tanggal_yudisium);
        edt_tanggal_yudisium.setText(daftarModel.getTanggal_yudisium());
        spn_kewarganegaraaan = findViewById(R.id.spn_sunting_profil_kewarganegaraan);
        edt_negara = findViewById(R.id.edt_sunting_profil_negara);
        edt_negara.setText(daftarModel.getNama_negara());
        edt_no_hp = findViewById(R.id.edt_sunting_profil_no_hp);
        edt_no_hp.setText(daftarModel.getNomor_hp());
        edt_no_telp = findViewById(R.id.edt_sunting_profil_no_telepon);
        edt_no_telp.setText(daftarModel.getNomor_telepon());
        edt_facebook = findViewById(R.id.edt_sunting_profil_facebook);
        edt_facebook.setText(daftarModel.getFacebook());
        edt_twitter = findViewById(R.id.edt_sunting_profil_twitter);
        edt_twitter.setText(daftarModel.getTwitter());
        img_foto_profil = findViewById(R.id.img_sunting_profil_foto);
        img_edit_foto_profil = findViewById(R.id.img_sunting_profil_edit_foto);

        oldPath = daftarModel.getFoto();
        if (!oldPath.equals("")){
            Glide.with(SuntingProfilActivity.this)
                    .load(BASE_URL + oldPath)
                    .into(img_foto_profil);
        }
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

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<PathModel> call = jsonPlaceHolderApi.uploadPhoto(kirim);
        call.enqueue(new Callback<PathModel>() {
            @Override
            public void onResponse(Call<PathModel> call, Response<PathModel> response) {
                if (!response.isSuccessful()){
                    return;
                }

                PathModel pathModel = response.body();
                if (!pathModel.getPath().equals("invalid")){
                    newPath = pathModel.getPath();
                    addPhotoPathToDatabase(jsonPlaceHolderApi);
                    oldPath = pathModel.getPath();
                    Glide.with(SuntingProfilActivity.this)
                            .load(BASE_URL + pathModel.getPath())
                            .into(img_foto_profil);
                }
            }

            @Override
            public void onFailure(Call<PathModel> call, Throwable t) {
                Toast.makeText(SuntingProfilActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addPhotoPathToDatabase(JsonPlaceHolderApi jsonPlaceHolderApi){
        Call<Void> call = jsonPlaceHolderApi.updatePhotoPath(NIM, oldPath, newPath);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()){
                    return;
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(SuntingProfilActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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
                if (position == 0) {
                    return false;
                } else {
                    return true;
                }
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
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        
        Call<Void> call = jsonPlaceHolderApi.suntingProfil(
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
                edt_no_hp.getText().toString(),
                edt_no_telp.getText().toString(),
                edt_facebook.getText().toString(),
                edt_twitter.getText().toString());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()){
                    return;
                }
                onBackPressed();
                Toast.makeText(SuntingProfilActivity.this, "Data telah diubah", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(SuntingProfilActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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
                onBackPressed();
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
                cl_iconBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showKonfirmasiKembaliDialog();
                    }
                });

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

}
