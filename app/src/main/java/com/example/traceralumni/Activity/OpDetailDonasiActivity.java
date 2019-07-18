package com.example.traceralumni.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Path;
import android.net.ParseException;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.traceralumni.JsonPlaceHolderApi;
import com.example.traceralumni.Model.DonasiModel;
import com.example.traceralumni.Model.PathModel;
import com.example.traceralumni.Model.PermintaanDonasiModel;
import com.example.traceralumni.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.traceralumni.Activity.MainActivity.BASE_URL;
import static com.example.traceralumni.Activity.MainActivity.INDEX_OPENED_TAB;
import static com.example.traceralumni.Activity.MainActivity.INDEX_OPENED_TAB_KEY;

public class OpDetailDonasiActivity extends AppCompatActivity {
    TextView tvNavBar, tvTotalDonasi, tvFile;
    ConstraintLayout cl_iconBack, cl_iconHapus;
    ImageView img_iconBack, img_iconHapus;
    EditText edt_judul, edt_donasi, edt_deskripsi, edt_noTelp;
    Button btn_list_donatur, btn_simpan, btn_upload;
    AlertDialog.Builder builder;
    DonasiModel donasiModel;
    Integer idDonasi;
    Double totalAnggaran;
    String namaKegiatan, noTelepon, deskripsi, tanggal_opendonasi, oldPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_op_detail_donasi);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        builder = new AlertDialog.Builder(this);
        initView();
        getData();

        btn_list_donatur = findViewById(R.id.btn_list_donatur);
        btn_list_donatur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (donasiModel != null) {
                    Intent i = new Intent(OpDetailDonasiActivity.this, OpListDonaturActivity.class);
                    i.putExtra("id_donasi", donasiModel.getIdDonasi());
                    startActivity(i);
                }
            }
        });

        btn_simpan = findViewById(R.id.btn_simpan);
        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_judul.getText().toString().equalsIgnoreCase("")) {
                    edt_judul.setError("Wajib diisi!");
                } else if (edt_donasi.getText().toString().equalsIgnoreCase("")) {
                    edt_donasi.setError("Wajib diisi!");
                } else if (edt_deskripsi.getText().toString().equalsIgnoreCase("")) {
                    edt_deskripsi.setError("Wajib diisi!");
                } else if (edt_noTelp.getText().toString().equalsIgnoreCase("")) {
                    edt_noTelp.setError("Wajib diisi!");
                } else if (tvFile.getText().toString().equalsIgnoreCase(".jpg")) {
                    Toast.makeText(OpDetailDonasiActivity.this, "Anda belum memilih foto!", Toast.LENGTH_SHORT).show();
                } else {
                    totalAnggaran = Double.valueOf(edt_donasi.getText().toString().trim());
                    namaKegiatan = edt_judul.getText().toString().trim();
                    noTelepon = edt_noTelp.getText().toString().trim();
                    deskripsi = edt_deskripsi.getText().toString().trim();
                    getTanggalHariIni();
                    saveData(idDonasi, namaKegiatan, deskripsi, noTelepon, totalAnggaran, tanggal_opendonasi, oldPath);
                    onBackPressed();
                }
            }
        });
        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPhotoFromGallery();
            }
        });

    }

    private void initView() {
        tvNavBar = findViewById(R.id.tv_navbar_top);
        tvNavBar.setText("DETAIL DONASI");

        img_iconBack = findViewById(R.id.img_icon1);
        img_iconBack.setImageResource(R.drawable.ic_arrow_back);

        img_iconHapus = findViewById(R.id.img_icon4);
        img_iconHapus.setImageResource(R.drawable.ic_delete);

        cl_iconBack = findViewById(R.id.cl_icon1);
        cl_iconBack.setVisibility(View.VISIBLE);

        cl_iconHapus = findViewById(R.id.cl_icon4);
        cl_iconHapus.setVisibility(View.VISIBLE);

        cl_iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        cl_iconHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hapusDonasi();
            }
        });

        edt_judul = findViewById(R.id.edt_judul_donasi);
        edt_donasi = findViewById(R.id.edt_total_donasi);
        edt_deskripsi = findViewById(R.id.edt_deskripsi);
        edt_noTelp = findViewById(R.id.edt_notelp_op_donasi);
        tvTotalDonasi = findViewById(R.id.tv_total_donasi);
        tvFile = findViewById(R.id.tv_file);
        btn_upload = findViewById(R.id.btn_upload);
    }

    private void hapusDonasi() {
        builder.setMessage("Anda yakin ingin menghapus donasi ini?");
        builder.setCancelable(false);
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteDonasiID(idDonasi);
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

    private void deleteDonasiID(Integer idDonasi) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<Void> call = jsonPlaceHolderApi.deleteDonasi(idDonasi);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    return;
                }

                Intent i = new Intent(OpDetailDonasiActivity.this, MainActivity.class);
                i.putExtra(INDEX_OPENED_TAB_KEY, INDEX_OPENED_TAB);
                startActivity(i);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(OpDetailDonasiActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getData() {
        Intent intent = getIntent();
        donasiModel = intent.getParcelableExtra("object_donasi");
        if (donasiModel != null) {
            edt_judul.setText(donasiModel.getNamaKegiatan());
            edt_donasi.setText(String.format("%.0f", donasiModel.getTotalAnggaran()));
            edt_deskripsi.setText(donasiModel.getKeterangan());
            edt_noTelp.setText(donasiModel.getContactPerson());
            tvFile.setText(donasiModel.getFile().substring(8));
            idDonasi = donasiModel.getIdDonasi();
            getJumlahDuit();
        }
    }

    private void getTanggalHariIni() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = new Date();
            tanggal_opendonasi = dateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void saveData(Integer idDonasi, String namaKegiatan, String keterangan, String noTelepon, Double totalAnggaran, String tanggal_donasi, String file) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<Void> call = jsonPlaceHolderApi.createDonasi(idDonasi, namaKegiatan, noTelepon, keterangan, totalAnggaran, tanggal_donasi, file);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                Toast.makeText(OpDetailDonasiActivity.this, "Data tersimpan", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(OpDetailDonasiActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getJumlahDuit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<PermintaanDonasiModel> call = jsonPlaceHolderApi.getJumlahDuit(donasiModel.getIdDonasi());
        call.enqueue(new Callback<PermintaanDonasiModel>() {
            @Override
            public void onResponse(Call<PermintaanDonasiModel> call, Response<PermintaanDonasiModel> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                PermintaanDonasiModel donaturModelResponse = response.body();
                if (donaturModelResponse.getTotal() != null) {
                    tvTotalDonasi.setText("Rp " + String.format("%.0f", donaturModelResponse.getTotal()));
                } else {
                    tvTotalDonasi.setText("Rp 0");
                }
            }

            @Override
            public void onFailure(Call<PermintaanDonasiModel> call, Throwable t) {
                Toast.makeText(OpDetailDonasiActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getPhotoFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Uri imageUri = data.getData();
                uploadPhoto(imageUri);
                tvFile.setText(new File(getRealPathFromURI(imageUri)).getName());
            }
        }
    }

    private void uploadPhoto(Uri fileUri) {
        File file = new File(getRealPathFromURI(fileUri));
        RequestBody requestBody = RequestBody.create(MediaType.parse(getContentResolver().getType(fileUri)), file);
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
                if (!response.isSuccessful()) {
                    return;
                }
                PathModel pathModel = response.body();
                if (!pathModel.getPath().equals("invalid")) {
                    oldPath = pathModel.getPath();
                }
            }

            @Override
            public void onFailure(Call<PathModel> call, Throwable t) {
                Toast.makeText(OpDetailDonasiActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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

}
