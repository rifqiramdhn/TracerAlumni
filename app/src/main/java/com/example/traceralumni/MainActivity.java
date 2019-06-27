package com.example.traceralumni;

import android.graphics.PorterDuff;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ConstraintLayout cl_icon1, cl_icon2, cl_icon3, cl_icon4;
    ImageView imgIcon1, imgIcon2, imgIcon3, imgIcon4;
    TabLayout tabLayout;
    ViewPager viewPager;
    TextView tv_titleNavBar;

    private static String JENIS_USER = "";
    private static final String JENIS_USER_ALUMNI = "jenis_user_alumni";
    private static final String JENIS_USER_PIMPINAN = "jenis_user_pimpinan";
    private static final String JENIS_USER_OPERATOR = "jenis_user_operator";

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

        SimpleFragmentPagerAdapter adapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);

        //coba masuk sebagai alumni
        JENIS_USER = JENIS_USER_ALUMNI;
        getDataUser();
    }

    public void setIconNavBar(TabLayout.Tab tab) {
        cl_icon1.setVisibility(View.GONE);
        cl_icon2.setVisibility(View.GONE);
        cl_icon3.setVisibility(View.GONE);
        cl_icon4.setVisibility(View.GONE);

        if (JENIS_USER.equals(JENIS_USER_ALUMNI)){
            switch (tab.getPosition()){
                case 0:
                    imgIcon3.setImageResource(R.drawable.ic_search);
                    imgIcon4.setImageResource(R.drawable.ic_verticaldot);break;
                case 1:
                    imgIcon2.setImageResource(R.drawable.ic_chat);
                    imgIcon3.setImageResource(R.drawable.ic_search);
                    imgIcon4.setImageResource(R.drawable.ic_verticaldot);break;
                case 2:
                    imgIcon4.setImageResource(R.drawable.ic_verticaldot);break;
                case 3:
                    imgIcon2.setImageResource(R.drawable.ic_tambah_lowongan);
                    imgIcon3.setImageResource(R.drawable.ic_search);
                    imgIcon4.setImageResource(R.drawable.ic_verticaldot);break;
                case 4:
                    imgIcon3.setImageResource(R.drawable.ic_search);
                    imgIcon4.setImageResource(R.drawable.ic_verticaldot);break;
            }
        }

        cl_icon1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setIcon1Clicked();
            }
        });
        cl_icon2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setIcon2Clicked();
            }
        });
        cl_icon3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setIcon3Clicked();
            }
        });
        cl_icon4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setIcon4Clicked();
            }
        });
    }

    public void moveActivityToLogin() {
        //kode untuk pindah apabila jenis usernya kosong
    }


    public void getDataUser() {
        //method untuk mendapatkan data" user login atau belum
        //jika sudah login maka ambil data yang sudah tersimpan di sharepreference
        int user_log = 0;
        if (user_log == 0) {
            moveActivityToLogin();
        }

        //ngecheck kalau jenis usernya apa kemudian menginisialisasi tab layoutnya
        if (JENIS_USER.equals(JENIS_USER_ALUMNI)){
            int arrayDrawable[] = {R.drawable.ic_person,
                    R.drawable.ic_chat,
                    R.drawable.ic_home,
                    R.drawable.ic_lowongan,
                    R.drawable.ic_attach_money};
            String titleNavBar[] = {"DAFTAR ALUMNI",
                    "PESAN",
                    "BERANDA",
                    "LOWONGAN",
                    "DONASI"};
            int arrayIcon[] = {R.drawable.ic_search, R.drawable.ic_verticaldot};
            setTabLayout(tabLayout, arrayDrawable, titleNavBar);
        } else if (JENIS_USER.equals(JENIS_USER_OPERATOR)){
            int arrayDrawable[] = {R.drawable.ic_info,
                    R.drawable.ic_lowongan,
                    R.drawable.ic_attach_money};
            String titleNavBar[] = {"LOWONGAN",
                    "INFO",
                    "DONASI"};
            setTabLayout(tabLayout, arrayDrawable, titleNavBar);
        } else if (JENIS_USER.equals(JENIS_USER_PIMPINAN)){
            int arrayDrawable[] = {R.drawable.ic_person,
                    R.drawable.ic_info,
                    R.drawable.ic_lowongan,
                    R.drawable.ic_attach_money};
            String titleNavBar[] = {"DAFTAR ALUMNI",
                    "INFO",
                    "LOWONGAN",
                    "DONASI"};
            setTabLayout(tabLayout, arrayDrawable, titleNavBar);
        }
    }

    public void setIcon1Clicked() {
        Toast.makeText(MainActivity.this, "icon1 clicked", Toast.LENGTH_SHORT).show();
    }

    public void setIcon2Clicked() {
        Toast.makeText(MainActivity.this, "icon2 clicked", Toast.LENGTH_SHORT).show();
    }

    public void setIcon3Clicked() {
        Toast.makeText(MainActivity.this, "icon3 clicked", Toast.LENGTH_SHORT).show();
    }

    public void setIcon4Clicked() {
        Toast.makeText(MainActivity.this, "icon4 clicked", Toast.LENGTH_SHORT).show();
    }

    public void setIconVisibility(TabLayout.Tab tab){
        if (JENIS_USER.equals(JENIS_USER_ALUMNI)){
            switch (tab.getPosition()){
                case 0:
                    cl_icon1.setVisibility(View.GONE);
                    cl_icon2.setVisibility(View.GONE);
                    cl_icon3.setVisibility(View.VISIBLE);
                    cl_icon4.setVisibility(View.VISIBLE);break;
                case 1:
                    cl_icon1.setVisibility(View.GONE);
                    cl_icon2.setVisibility(View.VISIBLE);
                    cl_icon3.setVisibility(View.VISIBLE);
                    cl_icon4.setVisibility(View.VISIBLE); break;
                case 2:
                    cl_icon1.setVisibility(View.GONE);
                    cl_icon2.setVisibility(View.GONE);
                    cl_icon3.setVisibility(View.GONE);
                    cl_icon4.setVisibility(View.VISIBLE);break;
                case 3:
                    cl_icon1.setVisibility(View.GONE);
                    cl_icon2.setVisibility(View.VISIBLE);
                    cl_icon3.setVisibility(View.VISIBLE);
                    cl_icon4.setVisibility(View.VISIBLE); break;
                case 4:
                    cl_icon1.setVisibility(View.GONE);
                    cl_icon2.setVisibility(View.GONE);
                    cl_icon3.setVisibility(View.VISIBLE);
                    cl_icon4.setVisibility(View.VISIBLE); break;
            }
        }else if (JENIS_USER.equals(JENIS_USER_PIMPINAN)){
            switch (tab.getPosition()){
                case 0:
                    cl_icon1.setVisibility(View.GONE);
                    cl_icon2.setVisibility(View.GONE);
                    cl_icon3.setVisibility(View.GONE);
                    cl_icon4.setVisibility(View.VISIBLE);break;
                case 1:
                    cl_icon1.setVisibility(View.GONE);
                    cl_icon2.setVisibility(View.GONE);
                    cl_icon3.setVisibility(View.GONE);
                    cl_icon4.setVisibility(View.VISIBLE); break;
                case 2:
                    cl_icon1.setVisibility(View.GONE);
                    cl_icon2.setVisibility(View.GONE);
                    cl_icon3.setVisibility(View.GONE);
                    cl_icon4.setVisibility(View.VISIBLE);break;
                case 3:
                    cl_icon1.setVisibility(View.GONE);
                    cl_icon2.setVisibility(View.GONE);
                    cl_icon3.setVisibility(View.GONE);
                    cl_icon4.setVisibility(View.VISIBLE);break;
            }
        }else if (JENIS_USER.equals(JENIS_USER_PIMPINAN)){
            switch (tab.getPosition()){
                case 0:
                    cl_icon1.setVisibility(View.GONE);
                    cl_icon2.setVisibility(View.GONE);
                    cl_icon3.setVisibility(View.VISIBLE);
                    cl_icon4.setVisibility(View.VISIBLE);break;
                case 1:
                    cl_icon1.setVisibility(View.GONE);
                    cl_icon2.setVisibility(View.GONE);
                    cl_icon3.setVisibility(View.VISIBLE);
                    cl_icon4.setVisibility(View.VISIBLE); break;
                case 2:
                    cl_icon1.setVisibility(View.GONE);
                    cl_icon2.setVisibility(View.GONE);
                    cl_icon3.setVisibility(View.VISIBLE);
                    cl_icon4.setVisibility(View.VISIBLE);break;
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
            arrayImageView[i] = arrayCustomView[i].findViewById(R.id.img_gambar);
        }

        for (int i = 0; i < arrayDrawable.length; i++) {
            arrayImageView[i].setImageResource(arrayDrawable[i]);
            tabLayout.getTabAt(i).setCustomView(arrayCustomView[i]);
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ImageView img = tab.getCustomView().findViewById(R.id.img_gambar);
                img.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorTabSelected), PorterDuff.Mode.SRC_IN);
                tv_titleNavBar.setText(titleNavBar[tab.getPosition()]);
                setIconNavBar(tab);
                setIconVisibility(tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                ImageView img = tab.getCustomView().findViewById(R.id.img_gambar);
                img.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorTabUnselected), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });


        //langsung pilih ke beranda saat pertama buka aplikasi
        if (JENIS_USER.equals(JENIS_USER_ALUMNI)){
            TabLayout.Tab tab = tabLayout.getTabAt(2);
            tab.select();
        } else if (JENIS_USER.equals(JENIS_USER_OPERATOR)){
            TabLayout.Tab tab = tabLayout.getTabAt(1);
            tab.select();
        } else if (JENIS_USER.equals(JENIS_USER_PIMPINAN)){
            TabLayout.Tab tab = tabLayout.getTabAt(0);
            tab.select();
        } else {

        }
    }
}
