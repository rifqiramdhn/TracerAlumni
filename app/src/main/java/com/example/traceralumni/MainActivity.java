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
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ConstraintLayout cl_icon1, cl_icon2, cl_icon3, cl_icon4;
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cl_icon1 = findViewById(R.id.cl_icon1);
        cl_icon2 = findViewById(R.id.cl_icon2);
        cl_icon3 = findViewById(R.id.cl_icon3);
        cl_icon4 = findViewById(R.id.cl_icon4);

//        cl_icon1.setVisibility(View.GONE);
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

        tabLayout = findViewById(R.id.tl_main_alumni);
        viewPager = findViewById(R.id.vp_main_alumni);

        SimpleFragmentPagerAdapter adapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);

        setTabLayout(tabLayout);
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

    public void setTabLayout(TabLayout tabLayout) {

        View arrayCustomView[] = new View[5];
        for (int i = 0; i < 5; i++) {
            arrayCustomView[i] = getLayoutInflater().inflate(R.layout.custom_view_tab_alumni, null);
        }

        final ImageView arrayImageView[] = new ImageView[5];
        for (int i = 0; i < 5; i++) {
            arrayImageView[i] = arrayCustomView[i].findViewById(R.id.img_gambar);
        }

        int arrayDrawable[] = {R.drawable.ic_person,
                R.drawable.ic_chat,
                R.drawable.ic_home,
                R.drawable.ic_lowongan,
                R.drawable.ic_attach_money};

        for (int i = 0; i < 5; i++) {
            arrayImageView[i].setImageResource(arrayDrawable[i]);
            tabLayout.getTabAt(i).setCustomView(arrayCustomView[i]);
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ImageView img = tab.getCustomView().findViewById(R.id.img_gambar);
                img.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorTabSelected), PorterDuff.Mode.SRC_IN);
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
        TabLayout.Tab tab = tabLayout.getTabAt(2);
        tab.select();
    }
}
