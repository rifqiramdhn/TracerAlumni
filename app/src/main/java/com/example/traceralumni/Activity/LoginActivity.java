package com.example.traceralumni.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.traceralumni.JsonPlaceHolderApi;
import com.example.traceralumni.Model.DaftarModel;
import com.example.traceralumni.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.traceralumni.Activity.MainActivity.BASE_URL;
import static com.example.traceralumni.Activity.MainActivity.JENIS_USER_ALUMNI;
import static com.example.traceralumni.Activity.MainActivity.JENIS_USER_PREF;
import static com.example.traceralumni.Activity.MainActivity.NIM_PREF;
import static com.example.traceralumni.Activity.MainActivity.SHARE_PREFS;
import static com.example.traceralumni.Activity.MainActivity.STATE_USER_LOGGED_PREF;
import static com.example.traceralumni.Activity.MainActivity.TEXT_NO_INTERNET;

public class LoginActivity extends AppCompatActivity {
    FrameLayout frameLayout;
    boolean doubleBackToExitPressedOnce = false;
    EditText edtUsername, edtPassword;
    ConstraintLayout cl_lupa_password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        edtUsername = findViewById(R.id.edt_login_username);
        edtPassword = findViewById(R.id.edt_login_password);

        frameLayout = findViewById(R.id.fl_login);
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        cl_lupa_password = findViewById(R.id.cl_lupa_password);
        cl_lupa_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, LupaPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void login() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<DaftarModel> call = jsonPlaceHolderApi.requestLogin(edtUsername.getText().toString().trim(), edtPassword.getText().toString().trim());
        call.enqueue(new Callback<DaftarModel>() {
            @Override
            public void onResponse(Call<DaftarModel> call, Response<DaftarModel> response) {
                if (!response.isSuccessful()) {
                    return;
                }

                DaftarModel daftarModel = response.body();
                if (daftarModel.getStatus_data().equals("y")) {
                    saveData(daftarModel);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Username dan Password tidak cocok", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DaftarModel> call, Throwable t) {
                if (t.getMessage().contains("Failed to connect")) {
                    Toast.makeText(LoginActivity.this, TEXT_NO_INTERNET, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void saveData(DaftarModel daftarModel) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARE_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(JENIS_USER_PREF, daftarModel.getJenis_user());
        editor.putInt(STATE_USER_LOGGED_PREF, 1);

        if (daftarModel.getJenis_user().equals(JENIS_USER_ALUMNI)) {
            editor.putString(NIM_PREF, daftarModel.getNim());
        }

        editor.apply();
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Tekan sekali lagi untuk keluar", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}
