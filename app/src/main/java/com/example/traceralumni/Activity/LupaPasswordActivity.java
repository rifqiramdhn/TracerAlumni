package com.example.traceralumni.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.traceralumni.GMailSender;
import com.example.traceralumni.JsonPlaceHolderApi;
import com.example.traceralumni.Model.DaftarModel;
import com.example.traceralumni.R;

import java.io.DataOutputStream;
import java.net.PasswordAuthentication;
import java.util.Properties;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Multipart;

import static com.example.traceralumni.Activity.MainActivity.BASE_URL;

public class LupaPasswordActivity extends AppCompatActivity {

    EditText nim;
    ConstraintLayout back;
    Button lupaPassword;
    String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupa_password);
        nim = findViewById(R.id.edt_lupa_password_nim);
        back = findViewById(R.id.cl_lupa_password_back);
        lupaPassword = findViewById(R.id.btn_lupa_password_lupa);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LupaPasswordActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        lupaPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData(nim.getText().toString());
            }
        });
    }

    private void getData(String nim) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<DaftarModel> call = jsonPlaceHolderApi.getUserData(nim);
        call.enqueue(new Callback<DaftarModel>() {
            @Override
            public void onResponse(Call<DaftarModel> call, Response<DaftarModel> response) {
                if (!response.isSuccessful()) {
                    return;
                }

                DaftarModel daftarModel = response.body();
                if (daftarModel.getStatus_data().equalsIgnoreCase("n")) {
                    Toast.makeText(LupaPasswordActivity.this,
                            "NIM yang anda masukkan tidak terdaftar!", Toast.LENGTH_SHORT).show();
                } else if (!daftarModel.getJenis_user().equalsIgnoreCase("alumni")) {
                    Toast.makeText(LupaPasswordActivity.this,
                            "Fitur ini hanya untuk akun alumni, mohon hubungi operator!", Toast.LENGTH_SHORT).show();
                } else {
                    email = daftarModel.getEmail();
                    password = daftarModel.getPassword();
                    sendMessage();
                }
            }

            @Override
            public void onFailure(Call<DaftarModel> call, Throwable t) {
                Toast.makeText(LupaPasswordActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendMessage() {
        final ProgressDialog dialog = new ProgressDialog(LupaPasswordActivity.this);
        dialog.setTitle("Mengirimkan Email");
        dialog.setMessage("Mohon tunggu sebentar");
        dialog.setCancelable(false);
        dialog.show();
        Thread sender = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    GMailSender sender = new GMailSender("traceralumnifeb@gmail.com", "traceralumni2019");
                    sender.sendMail("Lupa password TracerAlumni",
                            "Password akun TracerAlumni anda adalah: "
                                    + password
                                    + "\nJangan tunjukkan kata sandi ini ke siapa pun!"
                                    + "\nApabila anda merasa tidak menggunakan fitur ini segera ubah kata sandi anda!",
                            "traceralumnifeb@gmail.com",
                            email);
                    dialog.dismiss();
                } catch (Exception e) {
                    Log.e("mylog", "Error: " + e.getMessage());
                }
            }
        });
        sender.start();
    }
}
