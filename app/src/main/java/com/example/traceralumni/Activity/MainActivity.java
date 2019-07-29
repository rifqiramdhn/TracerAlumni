package com.example.traceralumni.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.traceralumni.Adapter.OpFragPagerAdapter;
import com.example.traceralumni.Adapter.PimFragPagerAdapter;
import com.example.traceralumni.Fragment.OpDonasiFragment;
import com.example.traceralumni.Fragment.OpLowonganFragment;
import com.example.traceralumni.JsonPlaceHolderApi;
import com.example.traceralumni.R;
import com.example.traceralumni.Adapter.AlumniFragPagerAdapter;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    ConstraintLayout cl_icon1, cl_icon2, cl_icon3, cl_icon4;
    ImageView imgIcon1, imgIcon2, imgIcon3, imgIcon4;
    TabLayout tabLayout;
    ViewPager viewPager;
    TextView tv_titleNavBar;
    View tambahDialogView;
    EditText nimTambahAlumni;
    AlertDialog dialog;
    Spinner spn_jurusan, spn_prodi;
    Integer id_jurusan, id_prodi;

    boolean doubleBackToExitPressedOnce = false;

    public static String JENIS_USER = "";
    public static final String JENIS_USER_ALUMNI = "alumni";
    public static final String JENIS_USER_PIMPINAN = "pimpinan";
    public static final String JENIS_USER_OPERATOR = "operator";

    //    public static final String BASE_URL = "http://psik.feb.ub.ac.id/apptracer/";
    public static final String BASE_URL = "http://10.22.255.18/tracer/";

    public static final String INDEX_OPENED_TAB_KEY = "index_opened_tab_key";

    public static final String SHARE_PREFS = "share_prefs";
    public static final String NIM_PREF = "nim_pref";
    public static final String JENIS_USER_PREF = "jenis_user_pref";
    public static final String STATE_USER_LOGGED_PREF = "state_user_logged_pref";

    public static int INDEX_OPENED_TAB;
    public static int STATE_USER_LOGGED; //0 berarti belum login, 1 berarti sudah login
    public static String NIM;

    public static final String TEXT_NO_INTERNET = "Koneksi internet tidak stabil";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loadData();
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        cl_icon1 = findViewById(R.id.cl_icon1);
        cl_icon2 = findViewById(R.id.cl_icon2);
        cl_icon3 = findViewById(R.id.cl_icon3);
        cl_icon4 = findViewById(R.id.cl_icon4);

        imgIcon1 = findViewById(R.id.img_icon1);
        imgIcon2 = findViewById(R.id.img_icon2);
        imgIcon3 = findViewById(R.id.img_icon3);
        imgIcon4 = findViewById(R.id.img_icon4);

        tabLayout = findViewById(R.id.tl_main_alumni);
        viewPager = findViewById(R.id.vp_main_alumni);

        tv_titleNavBar = findViewById(R.id.tv_navbar_top);

        KeyboardVisibilityEvent.setEventListener(this, new KeyboardVisibilityEventListener() {
            @Override
            public void onVisibilityChanged(boolean isOpen) {
                tabLayout.setVisibility(isOpen ? View.GONE : View.VISIBLE);
            }
        });

        getDataUser();
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

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARE_PREFS, MODE_PRIVATE);
        STATE_USER_LOGGED = sharedPreferences.getInt(STATE_USER_LOGGED_PREF, 0);
        if (STATE_USER_LOGGED != 0) {
            JENIS_USER = sharedPreferences.getString(JENIS_USER_PREF, "");
            if (JENIS_USER.equals(JENIS_USER_ALUMNI)) {
                NIM = sharedPreferences.getString(NIM_PREF, "");
            }
        } else {
            moveActivityToLogin();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (STATE_USER_LOGGED == 0) {
            moveActivityToLogin();
        }
    }

    public void getDataUser() {
        AlumniFragPagerAdapter adapterAlumni = new AlumniFragPagerAdapter(getSupportFragmentManager());
        OpFragPagerAdapter adapterOperator = new OpFragPagerAdapter(getSupportFragmentManager());
        PimFragPagerAdapter adapterPimpinan = new PimFragPagerAdapter(getSupportFragmentManager());

        //ngecheck kalau jenis usernya apa kemudian menginisialisasi tab layoutnya
        if (JENIS_USER.equals(JENIS_USER_ALUMNI)) {
            int arrayDrawable[] = {R.drawable.ic_person,
                    R.drawable.ic_chat,
                    R.drawable.ic_home,
                    R.drawable.ic_lowongan,
                    R.drawable.ic_dots_horizontal};
            String titleNavBar[] = {"DAFTAR ALUMNI",
                    "PESAN",
                    "BERANDA",
                    "LOWONGAN",
                    "LAINNYA"};
            viewPager.setAdapter(adapterAlumni);
            tabLayout.setupWithViewPager(viewPager);
            setTabLayout(tabLayout, arrayDrawable, titleNavBar);
        } else if (JENIS_USER.equals(JENIS_USER_OPERATOR)) {
            int arrayDrawable[] = {R.drawable.ic_lowongan,
                    R.drawable.ic_info,
                    R.drawable.ic_attach_money,
                    R.drawable.ic_person};
            String titleNavBar[] = {"LOWONGAN",
                    "INFO",
                    "DONASI",
                    "DAFTAR ALUMNI"};
            viewPager.setAdapter(adapterOperator);
            tabLayout.setupWithViewPager(viewPager);
            setTabLayout(tabLayout, arrayDrawable, titleNavBar);
        } else if (JENIS_USER.equals(JENIS_USER_PIMPINAN)) {
            int arrayDrawable[] = {R.drawable.ic_person,
                    R.drawable.ic_info,
                    R.drawable.ic_lowongan,
                    R.drawable.ic_attach_money};
            String titleNavBar[] = {"DATA ALUMNI",
                    "INFO",
                    "LOWONGAN",
                    "DONASI"};
            viewPager.setAdapter(adapterPimpinan);
            tabLayout.setupWithViewPager(viewPager);
            setTabLayout(tabLayout, arrayDrawable, titleNavBar);
        }
    }

    public void setIconNavBar(TabLayout.Tab tab) {
        if (JENIS_USER.equals(JENIS_USER_ALUMNI)) {
            switch (tab.getPosition()) {
                case 0:
                    imgIcon4.setImageResource(R.drawable.ic_search);
                    break;
                case 1:
                    imgIcon3.setImageResource(R.drawable.ic_chat);
                    imgIcon4.setImageResource(R.drawable.ic_search);
                    break;
                case 2:
                    break;
                case 3:
                    imgIcon3.setImageResource(R.drawable.ic_tambah_lowongan);
                    imgIcon4.setImageResource(R.drawable.ic_search);
                    break;
                case 4:
//                    imgIcon4.setImageResource(R.drawable.ic_search);
                    break;
            }
        } else if (JENIS_USER.equals(JENIS_USER_PIMPINAN)) {
            switch (tab.getPosition()) {
                case 0:
                    imgIcon4.setImageResource(R.drawable.ic_power_settings_new);
                    break;
                case 1:
                    imgIcon3.setImageResource(R.drawable.ic_search);
                    imgIcon4.setImageResource(R.drawable.ic_power_settings_new);
                    break;
                case 2:
                    imgIcon3.setImageResource(R.drawable.ic_search);
                    imgIcon4.setImageResource(R.drawable.ic_power_settings_new);
                    break;
                case 3:
                    imgIcon3.setImageResource(R.drawable.ic_search);
                    imgIcon4.setImageResource(R.drawable.ic_power_settings_new);
                    break;
            }
        } else if (JENIS_USER.equals(JENIS_USER_OPERATOR)) {
            switch (tab.getPosition()) {
                case 0:
                    imgIcon2.setImageResource(R.drawable.ic_add);
                    imgIcon3.setImageResource(R.drawable.ic_search);
                    imgIcon4.setImageResource(R.drawable.ic_power_settings_new);
                    break;
                case 1:
                    imgIcon2.setImageResource(R.drawable.ic_add);
                    imgIcon3.setImageResource(R.drawable.ic_search);
                    imgIcon4.setImageResource(R.drawable.ic_power_settings_new);
                    break;
                case 2:
                    imgIcon2.setImageResource(R.drawable.ic_add);
                    imgIcon3.setImageResource(R.drawable.ic_search);
                    imgIcon4.setImageResource(R.drawable.ic_power_settings_new);
                    break;
                case 3:
                    imgIcon3.setImageResource(R.drawable.ic_add);
                    imgIcon4.setImageResource(R.drawable.ic_search);
            }
        }

        cl_icon1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setIconClicked(JENIS_USER, INDEX_OPENED_TAB, 1);
            }
        });
        cl_icon2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setIconClicked(JENIS_USER, INDEX_OPENED_TAB, 2);
            }
        });
        cl_icon3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setIconClicked(JENIS_USER, INDEX_OPENED_TAB, 3);
            }
        });
        cl_icon4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setIconClicked(JENIS_USER, INDEX_OPENED_TAB, 4);
            }
        });
    }

    public void moveActivityToLogin() {
        //kode untuk pindah apabila jenis usernya kosong
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void setIconVisibility(TabLayout.Tab tab) {
        if (JENIS_USER.equals(JENIS_USER_ALUMNI)) {
            switch (tab.getPosition()) {
                case 0:
                    cl_icon1.setVisibility(View.GONE);
                    cl_icon2.setVisibility(View.GONE);
                    cl_icon3.setVisibility(View.GONE);
                    cl_icon4.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    cl_icon1.setVisibility(View.GONE);
                    cl_icon2.setVisibility(View.GONE);
                    cl_icon3.setVisibility(View.VISIBLE);
                    cl_icon4.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    cl_icon1.setVisibility(View.GONE);
                    cl_icon2.setVisibility(View.GONE);
                    cl_icon3.setVisibility(View.GONE);
                    cl_icon4.setVisibility(View.GONE);
                    break;
                case 3:
                    cl_icon1.setVisibility(View.GONE);
                    cl_icon2.setVisibility(View.GONE);
                    cl_icon3.setVisibility(View.VISIBLE);
                    cl_icon4.setVisibility(View.VISIBLE);
                    break;
                case 4:
                    cl_icon1.setVisibility(View.GONE);
                    cl_icon2.setVisibility(View.GONE);
                    cl_icon3.setVisibility(View.GONE);
                    cl_icon4.setVisibility(View.GONE);
                    break;
            }
        } else if (JENIS_USER.equals(JENIS_USER_PIMPINAN)) {
            switch (tab.getPosition()) {
                case 0:
                    cl_icon1.setVisibility(View.GONE);
                    cl_icon2.setVisibility(View.GONE);
                    cl_icon3.setVisibility(View.GONE);
                    cl_icon4.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    cl_icon1.setVisibility(View.GONE);
                    cl_icon2.setVisibility(View.GONE);
                    cl_icon3.setVisibility(View.VISIBLE);
                    cl_icon4.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    cl_icon1.setVisibility(View.GONE);
                    cl_icon2.setVisibility(View.GONE);
                    cl_icon3.setVisibility(View.VISIBLE);
                    cl_icon4.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    cl_icon1.setVisibility(View.GONE);
                    cl_icon2.setVisibility(View.GONE);
                    cl_icon3.setVisibility(View.VISIBLE);
                    cl_icon4.setVisibility(View.VISIBLE);
                    break;
            }
        } else if (JENIS_USER.equals(JENIS_USER_OPERATOR)) {
            switch (tab.getPosition()) {
                case 0:
                    cl_icon1.setVisibility(View.GONE);
                    cl_icon2.setVisibility(View.VISIBLE);
                    cl_icon3.setVisibility(View.VISIBLE);
                    cl_icon4.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    cl_icon1.setVisibility(View.GONE);
                    cl_icon2.setVisibility(View.VISIBLE);
                    cl_icon3.setVisibility(View.VISIBLE);
                    cl_icon4.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    cl_icon1.setVisibility(View.GONE);
                    cl_icon2.setVisibility(View.VISIBLE);
                    cl_icon3.setVisibility(View.VISIBLE);
                    cl_icon4.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    cl_icon1.setVisibility(View.GONE);
                    cl_icon2.setVisibility(View.GONE);
                    cl_icon3.setVisibility(View.VISIBLE);
                    cl_icon4.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }

    public void setTabLayout(TabLayout tabLayout, int arrayDrawable[], final String titleNavBar[]) {
        View arrayCustomView[] = new View[arrayDrawable.length];
        for (int i = 0; i < arrayDrawable.length; i++) {
            arrayCustomView[i] = getLayoutInflater().inflate(R.layout.custom_view_tab, null);
        }

        final ImageView arrayImageView[] = new ImageView[arrayDrawable.length];
        for (int i = 0; i < arrayDrawable.length; i++) {
            arrayImageView[i] = arrayCustomView[i].findViewById(R.id.img_custom_view_tab);
        }

        for (int i = 0; i < arrayDrawable.length; i++) {
            arrayImageView[i].setImageResource(arrayDrawable[i]);
            tabLayout.getTabAt(i).setCustomView(arrayCustomView[i]);
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ImageView img = tab.getCustomView().findViewById(R.id.img_custom_view_tab);
                img.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorTabSelected), PorterDuff.Mode.SRC_IN);
                tv_titleNavBar.setText(titleNavBar[tab.getPosition()]);
                INDEX_OPENED_TAB = tab.getPosition();
                setIconNavBar(tab);
                setIconVisibility(tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                ImageView img = tab.getCustomView().findViewById(R.id.img_custom_view_tab);
                img.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorTabUnselected), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        bukaTabAwal();
    }

    public void bukaTabAwal() { //true kalau ada intent
        Intent i = getIntent();
        if (i != null) {
            int tab_number;
            if (JENIS_USER.equals(JENIS_USER_ALUMNI)) {
                tab_number = i.getIntExtra(INDEX_OPENED_TAB_KEY, 2);
            } else if (JENIS_USER.equals(JENIS_USER_PIMPINAN)) {
                tab_number = i.getIntExtra(INDEX_OPENED_TAB_KEY, 0);
            } else {
                tab_number = i.getIntExtra(INDEX_OPENED_TAB_KEY, 1);
            }

            if (tab_number == 0) {
                tabLayout.getTabAt(1).select();
                tabLayout.getTabAt(0).select();
                INDEX_OPENED_TAB = 0;
            } else {
                tabLayout.getTabAt(tab_number).select();
                INDEX_OPENED_TAB = tab_number;
            }
        }
    }

    public void setIconClicked(String jenisUser, int indexOpenedTab, int iconNumber) {
        if (jenisUser.equals(JENIS_USER_ALUMNI)) {
            setIconClickedForAlumni(indexOpenedTab, iconNumber);
        } else if (jenisUser.equals(JENIS_USER_PIMPINAN)) {
            setIconClickedForPimpinan(indexOpenedTab, iconNumber);
        } else if (jenisUser.equals(JENIS_USER_OPERATOR)) {
            setIconClickedForOperator(indexOpenedTab, iconNumber);
        }
    }

    public void setIconClickedForAlumni(int indexOpenedTab, int iconNumber) {
        switch (indexOpenedTab) {
            case 0:
                //tab daftar alumni
                switch (iconNumber) {
                    case 4:
                        ConstraintLayout cl_fragment_daftar_search = findViewById(R.id.cl_fragment_daftar_search);
                        if (cl_fragment_daftar_search.getVisibility() == View.GONE) {
                            cl_fragment_daftar_search.setVisibility(View.VISIBLE);
                        } else {
                            cl_fragment_daftar_search.setVisibility(View.GONE);
                        }
                        break;
                    default:
                        break;
                }
                break;
            case 1:
                //tab pesan
                switch (iconNumber) {
                    case 3:
                        //icon chat baru
                        break;
                    case 4:
                        ConstraintLayout cl_fragment_chat_search = findViewById(R.id.cl_fragment_chat_search);
                        if (cl_fragment_chat_search.getVisibility() == View.GONE) {
                            cl_fragment_chat_search.setVisibility(View.VISIBLE);
                        } else {
                            cl_fragment_chat_search.setVisibility(View.GONE);
                        }
                        break;
                    default:
                        break;
                }
                break;
            case 2:
                //tab beranda
                break;
            case 3:
                //tab lowongan
                switch (iconNumber) {
                    case 3:
                        Intent intent = new Intent(MainActivity.this, TambahLowonganActivity.class);
                        startActivity(intent);
                        break;
                    case 4:
                        ConstraintLayout cl_fragment_lowongan_search = findViewById(R.id.cl_fragment_lowongan_search);
                        if (cl_fragment_lowongan_search.getVisibility() == View.GONE) {
                            cl_fragment_lowongan_search.setVisibility(View.VISIBLE);
                        } else {
                            cl_fragment_lowongan_search.setVisibility(View.GONE);
                        }
                        break;
                    default:
                        break;
                }
                break;
            case 4:
                //tab lainnya
                break;
            default:
                break;
        }
    }

    public void setIconClickedForPimpinan(int indexOpenedTab, int iconNumber) {
        switch (indexOpenedTab) {
            case 0:
                //tab data alumni
                switch (iconNumber) {
                    case 4:
                        //icon logout
                        showKeluarDialog(this);
                        break;
                    default:
                        break;
                }
                break;
            case 1:
                //tab info
                switch (iconNumber) {
                    case 3:
                        //icon search
                        ConstraintLayout cl_search_info = findViewById(R.id.cl_fragment_pim_info_search);
                        if (cl_search_info.getVisibility() == View.GONE) {
                            cl_search_info.setVisibility(View.VISIBLE);
                        } else {
                            cl_search_info.setVisibility(View.GONE);
                        }
                        break;
                    case 4:
                        //icon logout
                        showKeluarDialog(this);
                        break;
                    default:
                        break;
                }
                break;
            case 2:
                //tab lowongan
                switch (iconNumber) {
                    case 3:
                        //icon search
                        ConstraintLayout cl_search_lowongan = findViewById(R.id.cl_fragment_pim_lowongan_search);
                        if (cl_search_lowongan.getVisibility() == View.GONE) {
                            cl_search_lowongan.setVisibility(View.VISIBLE);
                        } else {
                            cl_search_lowongan.setVisibility(View.GONE);
                        }
                        break;
                    case 4:
                        //icon logout
                        showKeluarDialog(this);
                        break;
                    default:
                        break;
                }
                break;
            case 3:
                //tab donasi
                switch (iconNumber) {
                    case 3:
                        //icon search
                        ConstraintLayout cl_search_donasi = findViewById(R.id.cl_fragment_pim_donasi_search);
                        if (cl_search_donasi.getVisibility() == View.GONE) {
                            cl_search_donasi.setVisibility(View.VISIBLE);
                        } else {
                            cl_search_donasi.setVisibility(View.GONE);
                        }
                        break;
                    case 4:
                        //icon logout
                        showKeluarDialog(this);
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }

    public void setIconClickedForOperator(int indexOpenedTab, int iconNumber) {
        switch (indexOpenedTab) {
            case 0:
                //tab lowongan
                switch (iconNumber) {
                    case 2:
                        //icon tambah lowongan
                        Intent i = new Intent(MainActivity.this, TambahLowonganActivity.class);
                        startActivity(i);
                        break;
                    case 3:
                        //icon search
                        ConstraintLayout cl_search_lowongan = findViewById(R.id.cl_fragment_op_lowongan_search);
                        TextView tv_permintaan_lowongan = findViewById(R.id.tv_permintaan_lowongan);
                        if (cl_search_lowongan.getVisibility() == View.GONE) {
                            cl_search_lowongan.setVisibility(View.VISIBLE);
                            tv_permintaan_lowongan.setVisibility(View.GONE);
                        } else {
                            cl_search_lowongan.setVisibility(View.GONE);
                            if (!OpLowonganFragment.permintaanLowongan0())
                                tv_permintaan_lowongan.setVisibility(View.VISIBLE);
                        }
                        break;
                    case 4:
                        //icon logout
                        showKeluarDialog(this);
                        break;
                    default:
                        break;
                }
                break;
            case 1:
                //tab info
                switch (iconNumber) {
                    case 2:
                        //icon tambah info
                        Intent i = new Intent(MainActivity.this, OpDetailInfoActivity.class);
                        startActivity(i);
                        break;
                    case 3:
                        //icon search
                        ConstraintLayout cl_search_info = findViewById(R.id.cl_fragment_op_info_search);
                        if (cl_search_info.getVisibility() == View.GONE) {
                            cl_search_info.setVisibility(View.VISIBLE);
                        } else {
                            cl_search_info.setVisibility(View.GONE);
                        }
                        break;
                    case 4:
                        //icon logout
                        showKeluarDialog(this);
                        break;
                    default:
                        break;
                }
                break;
            case 2:
                //tab donasi
                switch (iconNumber) {
                    case 2:
                        //icon tambah donasi
                        Intent i = new Intent(MainActivity.this, OpDetailDonasiActivity.class);
                        startActivity(i);
                        break;
                    case 3:
                        //icon search
                        ConstraintLayout cl_search_donasi = findViewById(R.id.cl_fragment_op_donasi_search);
                        TextView tv_permintaan_donasi = findViewById(R.id.tv_fragment_op_donasi_permintaan);
                        if (cl_search_donasi.getVisibility() == View.GONE) {
                            cl_search_donasi.setVisibility(View.VISIBLE);
                            tv_permintaan_donasi.setVisibility(View.GONE);
                        } else {
                            cl_search_donasi.setVisibility(View.GONE);
                            if (!OpDonasiFragment.permintaanDonasi0())
                                tv_permintaan_donasi.setVisibility(View.VISIBLE);
                        }
                        break;
                    case 4:
                        //icon logout
                        showKeluarDialog(this);
                        break;
                    default:
                        break;
                }
                break;
            case 3:
                //tab daftar alumni
                switch (iconNumber) {
                    case 3:
                        //icon tambah alumni
                        showTambahDialog();
                        break;
                    case 4:
                        //icon search
                        ConstraintLayout cl_fragment_daftar_search = findViewById(R.id.cl_fragment_daftar_search);
                        if (cl_fragment_daftar_search.getVisibility() == View.GONE) {
                            cl_fragment_daftar_search.setVisibility(View.VISIBLE);
                        } else {
                            cl_fragment_daftar_search.setVisibility(View.GONE);
                        }
                        break;
                    default:
                        break;
                }
            default:
                break;
        }
    }

    public static void showKeluarDialog(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Apakah anda yakin ingin keluar?");
        builder.setTitle("Keluar");
        builder.setCancelable(false);
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences sharedPreferences = context.getSharedPreferences(SHARE_PREFS, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putInt(STATE_USER_LOGGED_PREF, 0);
                editor.apply();
                STATE_USER_LOGGED = 0;

                Intent intent = new Intent(context, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
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

    private void showTambahDialog() {
        tambahDialogView = getLayoutInflater().inflate(R.layout.dialog_tambah_alumni, null);
        nimTambahAlumni = tambahDialogView.findViewById(R.id.edt_dialog_tambah_alumni_nim);
        spn_jurusan = tambahDialogView.findViewById(R.id.spn_dialog_tambah_alumni_daftar_jurusan);
        spn_prodi = tambahDialogView.findViewById(R.id.spn_dialog_tambah_alumni_daftar_prodi);
        customSpinner();

        dialog = new AlertDialog.Builder(MainActivity.this)
                .setView(tambahDialogView)
                .setPositiveButton("Tambah", null)
                .setNegativeButton("Batal", null)
                .create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialogInterface) {

                Button tambah = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                tambah.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        if (nimTambahAlumni.length() == 0) {
                            nimTambahAlumni.setError("Wajib diisi");
                        } else if (nimTambahAlumni.length() < 10) {
                            nimTambahAlumni.setError("Panjang NIM minimal 10 digit");
                        } else if (spn_prodi.getSelectedItem().toString().equalsIgnoreCase("Prodi")) {
                            Toast.makeText(MainActivity.this, "Anda belum memilih prodi", Toast.LENGTH_SHORT).show();
                        } else {
                            tambahAlumni(nimTambahAlumni.getText().toString(), id_jurusan, id_prodi);
                        }
                    }
                });

                Button batal = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                batal.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });
            }
        });
        dialog.show();
    }

    private void tambahAlumni(String nim, Integer id_jurusan, Integer id_prodi) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<String> call = jsonPlaceHolderApi.createAlumni(nim, id_jurusan, id_prodi);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (!response.isSuccessful()) {
                    return;
                }

                String hasil = response.body();
                if (hasil.equals("0")) {
                    nimTambahAlumni.setError("NIM sudah digunakan");
                } else {
                    dialog.cancel();
                    Toast.makeText(MainActivity.this, "Alumni baru sudah ditambahkan", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (t.getMessage().contains("Failed to connect")) {
                    Toast.makeText(MainActivity.this, TEXT_NO_INTERNET, Toast.LENGTH_SHORT).show();
                }
            }
        });
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
                "S1 - Ekonomi Pembangunan",
                "S1 - Ekonomi Pembangunan (Internasional)",
                "S2 - Ilmu Ekonomi",
                "S3 - Ilmu Ekonomi",
        };

        String[] prodiManajemen = new String[]{
                "Prodi",
                "S1 - Ekonomi, Keuangan, dan Perbankan",
                "S1 - Kewirausahaan",
                "S1 - Manajemen",
                "S1 - Manajemen (Internasional)",
                "S2 - Manajemen",
                "S3 - Ilmu Manajemen",
        };

        final List<String> jurusanList = new ArrayList<>(Arrays.asList(jurusan));
        final List<String> prodiList = new ArrayList<>(Arrays.asList(prodi));
        final List<String> prodiListAkuntansi = new ArrayList<>(Arrays.asList(prodiAkuntansi));
        final List<String> prodiListIlmuEkonomi = new ArrayList<>(Arrays.asList(prodiIlmuEkonomi));
        final List<String> prodiListManajemen = new ArrayList<>(Arrays.asList(prodiManajemen));

        final ArrayAdapter<String> spinnerArrayAdapterJurusan = new ArrayAdapter<String>(
                MainActivity.this, R.layout.card_spinner, jurusanList) {
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
                MainActivity.this, R.layout.card_spinner, prodiList) {
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
                MainActivity.this, R.layout.card_spinner, prodiListAkuntansi) {
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
                MainActivity.this, R.layout.card_spinner, prodiListIlmuEkonomi) {
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
                MainActivity.this, R.layout.card_spinner, prodiListManajemen) {
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
                    id_jurusan = position;
                    spinnerArrayAdapterAkuntansi.setDropDownViewResource(R.layout.card_spinner);
                    spn_prodi.setAdapter(spinnerArrayAdapterAkuntansi);
                } else if (position == 2) {
                    id_jurusan = position;
                    spinnerArrayAdapterIlmuEkonomi.setDropDownViewResource(R.layout.card_spinner);
                    spn_prodi.setAdapter(spinnerArrayAdapterIlmuEkonomi);
                } else if (position == 3) {
                    id_jurusan = position;
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
        spn_prodi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
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
                        id_jurusan = 2;
                        id_prodi = 5;
                        break;
                    case "S3 Ilmu Akuntansi":
                        id_jurusan = 2;
                        id_prodi = 6;
                        break;
                    case "PPAk":
                        id_jurusan = 3;
                        id_prodi = 7;
                        break;
                    case "S1 Ekonomi Pembangunan":
                        id_jurusan = 3;
                        id_prodi = 8;
                        break;
                    case "S1 Ekonomi Pembangunan (Internasional)":
                        id_jurusan = 3;
                        id_prodi = 9;
                        break;
                    case "S2 Ilmu Ekonomi":
                        id_jurusan = 3;
                        id_prodi = 10;
                        break;
                    case "S3 Ilmu Ekonomi":
                        id_jurusan = 1;
                        id_prodi = 11;
                        break;
                    case "S1 Ekonomi, Keuangan, dan Perbankan":
                        id_jurusan = 3;
                        id_prodi = 12;
                        break;
                    case "S1 Kewirausahaan":
                        id_jurusan = 2;
                        id_prodi = 13;
                        break;
                    case "S1 Manajemen":
                        id_jurusan = 1;
                        id_prodi = 14;
                        break;
                    case "S1 Manajemen (Internasional)":
                        id_jurusan = 2;
                        id_prodi = 15;
                        break;
                    case "S2 Manajemen":
                        id_jurusan = 3;
                        id_prodi = 16;
                        break;
                    case "S3 Ilmu Manajemen":
                        id_jurusan = 1;
                        id_prodi = 17;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


}
