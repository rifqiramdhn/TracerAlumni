package com.example.traceralumni;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.traceralumni.Activity.MainActivity.NIM;
import static com.example.traceralumni.Activity.MainActivity.TEXT_NO_INTERNET;

public class ChangePasswordActivity extends AppCompatActivity {
    TextView tvNavBar;
    ConstraintLayout cl_iconback, cl_iconconfirm;
    ImageView img_iconback, img_iconconfirm;
    AlertDialog.Builder builder;
    EditText edtOldPass, edtNewPass, edtNewPassConfirm;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        builder = new AlertDialog.Builder(this);
        initView();
    }

    private void saveData(){
        if (edtOldPass.getText().toString().equals("")){
            edtOldPass.setError("Wajib diisi");
        } else if (edtNewPass.getText().toString().equals("")){
            edtNewPass.setError("Wajib diisi");
        } else if (edtNewPass.getText().length() < 8){
            edtNewPass.setError("Panjang kata sandi minimal 8 karakter");
        } else if (!edtNewPass.getText().toString().equals(edtNewPassConfirm.getText().toString())){
            edtNewPassConfirm.setError("Konfirmasi kata sandi tidak sesuai");
        } else {
            JsonApi jsonApi = Client.getClient().create(JsonApi.class);
            Call<String> call = jsonApi.changePassword(NIM, edtOldPass.getText().toString(), edtNewPass.getText().toString());
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (!response.isSuccessful()){
                        return;
                    }
                    String hasil = response.body();
                    if (hasil.equals("1")){
                        resetPasswordFirebase(edtNewPass.getText().toString());
                        onBackPressed();
                    } else {
                        Toast.makeText(ChangePasswordActivity.this, "Kata sandi lama anda salah", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    if (t.getMessage().contains("Failed to connect")){
                        Toast.makeText(ChangePasswordActivity.this, TEXT_NO_INTERNET, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void initView(){
        mAuth = FirebaseAuth.getInstance();

        edtNewPass = findViewById(R.id.edt_new_pass);
        edtNewPassConfirm = findViewById(R.id.edt_new_pass_confirm);
        edtOldPass = findViewById(R.id.edt_old_pass);
        tvNavBar = findViewById(R.id.tv_navbar_top);
        tvNavBar.setText("GANTI KATA SANDI");
        img_iconback = findViewById(R.id.img_icon1);
        img_iconback.setImageResource(R.drawable.ic_arrow_back);
        img_iconconfirm = findViewById(R.id.img_icon4);
        img_iconconfirm.setImageResource(R.drawable.ic_check);
        cl_iconback = findViewById(R.id.cl_icon1);
        cl_iconback.setVisibility(View.VISIBLE);
        cl_iconconfirm = findViewById(R.id.cl_icon4);
        cl_iconconfirm.setVisibility(View.VISIBLE);
        cl_iconback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        cl_iconconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showKonfirmasiDialog();
            }
        });
    }

    private void showKonfirmasiDialog(){
        builder.setMessage("Anda yakin ingin mengganti kata sandi")
                .setTitle("Ganti Kata Sandi")
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saveData();
                    }
                }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void resetPasswordFirebase(String pass){
        FirebaseUser user = mAuth.getCurrentUser();
        user.updatePassword(pass).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(ChangePasswordActivity.this, "Password berhasil diperbaharui", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ChangePasswordActivity.this, "Password gagal diubah", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ChangePasswordActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
