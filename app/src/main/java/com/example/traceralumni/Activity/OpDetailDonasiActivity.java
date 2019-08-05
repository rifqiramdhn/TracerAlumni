package com.example.traceralumni.Activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ParseException;
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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.traceralumni.Client;
import com.example.traceralumni.JsonApi;
import com.example.traceralumni.Model.DonasiModel;
import com.example.traceralumni.Model.PathModel;
import com.example.traceralumni.Model.PermintaanDonasiModel;
import com.example.traceralumni.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.traceralumni.Activity.MainActivity.INDEX_OPENED_TAB;
import static com.example.traceralumni.Activity.MainActivity.INDEX_OPENED_TAB_KEY;
import static com.example.traceralumni.Activity.MainActivity.TEXT_NO_INTERNET;

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
    Uri mImageUri;
    String telepon;

    int CAN_CLICK_BUTTON_SAVE = 0; //0 bisa diklik, 1 tidak bisa diklik
    int EDIT = 0; //0 berarti tidak mengubah foto, 1 berarti mengubah

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_op_detail_donasi);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        builder = new AlertDialog.Builder(this);
        initView();
        getData();

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
                } else if (edt_noTelp.getText().length() < 9) {
                    edt_noTelp.setError("No telepon tidak valid!");
                } else if (tvFile.getText().toString().equalsIgnoreCase(".jpg")) {
                    Toast.makeText(OpDetailDonasiActivity.this, "Anda belum memilih foto!", Toast.LENGTH_SHORT).show();
                } else {
                    totalAnggaran = Double.valueOf(edt_donasi.getText().toString().trim());
                    namaKegiatan = edt_judul.getText().toString().trim();
                    noTelepon = edt_noTelp.getText().toString().trim();
                    deskripsi = edt_deskripsi.getText().toString().trim();
                    getTanggalHariIni();

                    if (noTelepon.charAt(0) == '0') {
                        telepon = "+62" + noTelepon.substring(1);
                    } else if (noTelepon.charAt(0) != '+') {
                        telepon = "+" + noTelepon;
                    } else {
                        telepon = noTelepon;
                    }

                    if (CAN_CLICK_BUTTON_SAVE == 0) {
                        CAN_CLICK_BUTTON_SAVE = 1;
                        if (oldPath != null && EDIT == 0) {
                            saveData(idDonasi, namaKegiatan, deskripsi, telepon, totalAnggaran, tanggal_opendonasi, oldPath);
                        } else {
                            uploadPhoto(mImageUri);
                        }
                    }
                    onBackPressed();
                }
            }
        });
        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(OpDetailDonasiActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(OpDetailDonasiActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    getPhotoFromGallery();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getPhotoFromGallery();
                }
                return;
            }
        }
    }

    private void initView() {
        btn_list_donatur = findViewById(R.id.btn_list_donatur);
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
        btn_simpan = findViewById(R.id.btn_simpan);
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
        JsonApi jsonApi = Client.getClient().create(JsonApi.class);

        Call<Void> call = jsonApi.deleteDonasi(idDonasi);
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
                if (t.getMessage().contains("Failed to connect")) {
                    Toast.makeText(OpDetailDonasiActivity.this, TEXT_NO_INTERNET, Toast.LENGTH_SHORT).show();
                }
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
            oldPath = donasiModel.getFile();
            getJumlahDuit();
        } else {
            cl_iconHapus.setVisibility(View.GONE);
            btn_list_donatur.setVisibility(View.GONE);
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
        JsonApi jsonApi = Client.getClient().create(JsonApi.class);

        Call<Void> call = jsonApi.createDonasi(idDonasi, namaKegiatan, noTelepon, keterangan, totalAnggaran, tanggal_donasi, file);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    CAN_CLICK_BUTTON_SAVE = 0;
                    return;
                }
//                uploadPhoto(mImageUri);
                Toast.makeText(OpDetailDonasiActivity.this, "Data tersimpan", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                CAN_CLICK_BUTTON_SAVE = 0;
                if (t.getMessage().contains("Failed to connect")) {
                    Toast.makeText(OpDetailDonasiActivity.this, TEXT_NO_INTERNET, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getJumlahDuit() {
        JsonApi jsonApi = Client.getClient().create(JsonApi.class);

        Call<PermintaanDonasiModel> call = jsonApi.getJumlahDuit(donasiModel.getIdDonasi());
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
                if (t.getMessage().contains("Failed to connect")) {
                    Toast.makeText(OpDetailDonasiActivity.this, TEXT_NO_INTERNET, Toast.LENGTH_SHORT).show();
                }
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
                mImageUri = imageUri;
                tvFile.setText(new File(getRealPathFromURI(imageUri)).getName());
                EDIT = 1;
            }
        }
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
                    CAN_CLICK_BUTTON_SAVE = 0;
                    return;
                }
                PathModel pathModel = response.body();
                if (!pathModel.getPath().equals("invalid")) {
                    oldPath = pathModel.getPath();
                    saveData(idDonasi, namaKegiatan, deskripsi, telepon, totalAnggaran, tanggal_opendonasi, oldPath);
                    CAN_CLICK_BUTTON_SAVE = 0;
                } else {
                    CAN_CLICK_BUTTON_SAVE = 0;
                    Toast.makeText(OpDetailDonasiActivity.this, "Gagal Upload Photo", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PathModel> call, Throwable t) {
                CAN_CLICK_BUTTON_SAVE = 0;
                if (t.getMessage().contains("Failed to connect")) {
                    Toast.makeText(OpDetailDonasiActivity.this, TEXT_NO_INTERNET, Toast.LENGTH_SHORT).show();
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

}
