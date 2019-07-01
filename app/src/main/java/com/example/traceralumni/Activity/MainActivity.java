package com.example.traceralumni.Activity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.traceralumni.Adapter.OpFragPagerAdapter;
import com.example.traceralumni.Adapter.PimFragPagerAdapter;
import com.example.traceralumni.R;
import com.example.traceralumni.Adapter.AlumniFragPagerAdapter;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    ConstraintLayout cl_icon1, cl_icon2, cl_icon3, cl_icon4;
    ImageView imgIcon1, imgIcon2, imgIcon3, imgIcon4;
    TabLayout tabLayout;
    ViewPager viewPager;
    TextView tv_titleNavBar;

    public static String JENIS_USER = "";
    public static final String JENIS_USER_ALUMNI = "jenis_user_alumni";
    public static final String JENIS_USER_PIMPINAN = "jenis_user_pimpinan";
    public static final String JENIS_USER_OPERATOR = "jenis_user_operator";

    public static int INDEX_OPENED_TAB;

    private static int STATE_USER_LOGGED = 0; //0 berarti belum login, 1 berarti sudah login

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        //coba masuk sebagai alumni
        JENIS_USER = JENIS_USER_ALUMNI;
        STATE_USER_LOGGED = 0;
        getDataUser();
    }

    public void getDataUser() {
        //method untuk mendapatkan data" user login atau belum
        //jika sudah login maka ambil data yang sudah tersimpan di sharepreference
        int user_log = STATE_USER_LOGGED;
        if (user_log == 0) {
            moveActivityToLogin();
        }

        AlumniFragPagerAdapter adapterAlumni = new AlumniFragPagerAdapter(getSupportFragmentManager());
        OpFragPagerAdapter adapterOperator = new OpFragPagerAdapter(getSupportFragmentManager());
        PimFragPagerAdapter adapterPimpinan = new PimFragPagerAdapter(getSupportFragmentManager());

        //ngecheck kalau jenis usernya apa kemudian menginisialisasi tab layoutnya
        Log.e("aldy", JENIS_USER);
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
                    R.drawable.ic_attach_money};
            String titleNavBar[] = {"LOWONGAN",
                    "INFO",
                    "DONASI"};
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
                    imgIcon4.setImageResource(R.drawable.ic_search);
                    break;
            }
        } else if (JENIS_USER.equals(JENIS_USER_PIMPINAN)){
            switch (tab.getPosition()){
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
        } else if (JENIS_USER.equals(JENIS_USER_OPERATOR)){
            switch (tab.getPosition()){
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
                    cl_icon4.setVisibility(View.VISIBLE);
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
            }
        }
    }

    public void setTabLayout(TabLayout tabLayout, int arrayDrawable[], final String titleNavBar[]) {
        Log.e("aldy", Arrays.toString(titleNavBar));
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


        //langsung pilih ke beranda saat pertama buka aplikasi
        Log.e("aldy", JENIS_USER);
        if (JENIS_USER.equals(JENIS_USER_ALUMNI)) {
            if(getIntent() == null){
                TabLayout.Tab tab = tabLayout.getTabAt(2);
                tab.select();
                INDEX_OPENED_TAB = 2;
            } else {
                Intent i = getIntent();
                TabLayout.Tab tab = tabLayout.getTabAt(i.getIntExtra("Tambah", 2));
                tab.select();
                INDEX_OPENED_TAB = i.getIntExtra("Tambah", 2);
            }
        } else if (JENIS_USER.equals(JENIS_USER_OPERATOR)) {
            TabLayout.Tab tab = tabLayout.getTabAt(1);
            tab.select();
            INDEX_OPENED_TAB = 1;
        } else if (JENIS_USER.equals(JENIS_USER_PIMPINAN)) {
            TabLayout.Tab tab = tabLayout.getTabAt(1);
            tab.select();
            INDEX_OPENED_TAB = 1;
        } else {

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
                        //icon search
                        break;
                    default:
                        break;
                }
                break;
            case 1:
                //tab pesan
                switch (iconNumber) {
                    case 3:
                        //icon tambah pesan
                        break;
                    case 4:
                        //icon search
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
                        //icon search
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
                        break;
                    case 4:
                        //icon logout
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
                        break;
                    case 4:
                        //icon logout
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
                        break;
                    case 4:
                        //icon logout
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
                        break;
                    case 3:
                        //icon search
                        break;
                    case 4:
                        //icon logout
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
                        break;
                    case 3:
                        //icon search
                        break;
                    case 4:
                        //icon logout
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
                        break;
                    case 3:
                        //icon search
                        break;
                    case 4:
                        //icon logout
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }
}
