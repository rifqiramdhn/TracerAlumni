package com.example.traceralumni.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.traceralumni.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static com.example.traceralumni.Activity.LocationPickerActivity.KODE_POS_EXTRA_KEY;
import static com.example.traceralumni.Activity.LocationPickerActivity.LOKASI_EXTRA_KEY;

public class SuntingProfilActivity extends AppCompatActivity {

    ConstraintLayout cl_iconBack, cl_iconConfirm;
    ImageView img_iconBack, img_iconConfirm;
    TextView tv_titleBar;

    Calendar myCalendar;

    DatePickerDialog.OnDateSetListener tanggalLahir, tanggalYudisium;

    ImageView img_foto_profil, img_edit_foto_profil;

    EditText edt_email, edt_tempat_lahir, edt_tanggal_lahir, edt_alamat, edt_kode_pos, edt_angkatan,
            edt_tahun_lulus, edt_tanggal_yudisium, edt_negara, edt_no_hp, edt_no_telp, edt_facebook,
            edt_twitter;

    Spinner spn_kewarganegaraaan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sunting_profil);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        setIcon();
        getView();

        edt_alamat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToLocationPickerActivity();
            }
        });

        myCalendar = Calendar.getInstance();

        datePickerGetDate(edt_tanggal_lahir);
        datePickerGetDate(edt_tanggal_yudisium);
        datePickerSetDate();

        customSpinner();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        if (intent != null){
            edt_alamat.setText(intent.getStringExtra(LOKASI_EXTRA_KEY));
            edt_kode_pos.setText(intent.getStringExtra(KODE_POS_EXTRA_KEY));
        }
    }

    private void moveToLocationPickerActivity(){
        Intent i = new Intent(SuntingProfilActivity.this, LocationPickerActivity.class);
        startActivity(i);
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
                Toast.makeText(SuntingProfilActivity.this, "Mantaap", Toast.LENGTH_SHORT).show();
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
        edt_tempat_lahir = findViewById(R.id.edt_sunting_profil_tempat_lahir);
        edt_tanggal_lahir = findViewById(R.id.edt_sunting_profil_tanggal_lahir);
        edt_alamat = findViewById(R.id.edt_sunting_profil_alamat);
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
    }

    private void datePickerGetDate(final EditText editText) {
        editText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (editText == edt_tanggal_lahir) {
                    new DatePickerDialog(SuntingProfilActivity.this, tanggalLahir, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                } else {
                    new DatePickerDialog(SuntingProfilActivity.this, tanggalYudisium, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }

            }
        });
    }

    private void datePickerSetDate() {
        tanggalLahir = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(edt_tanggal_lahir);
            }

        };
        tanggalYudisium = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(edt_tanggal_yudisium);
            }

        };

    }

    private void updateLabel(EditText editText) {
        String myFormat = "dd/MM/YYYY"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editText.setText(sdf.format(myCalendar.getTime()));
    }

    private void customSpinner() {
        String[] kewarganegaraan = new String[]{
                "Kewarganegaraan*",
                "WNI",
                "WNA"
        };

        final List<String> kewarganegaraanList = new ArrayList<>(Arrays.asList(kewarganegaraan));

        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this, R.layout.card_spinner, kewarganegaraanList) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
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
    }
}
