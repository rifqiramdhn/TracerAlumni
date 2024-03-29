package com.example.traceralumni.Adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.traceralumni.Fragment.BerandaFragment;
import com.example.traceralumni.Fragment.ChatFragment;
import com.example.traceralumni.Fragment.DaftarFragment;
import com.example.traceralumni.Fragment.LainnyaFragment;
import com.example.traceralumni.Fragment.LowonganFragment;

public class AlumniFragPagerAdapter extends FragmentPagerAdapter {

    public AlumniFragPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new DaftarFragment();
            case 1:
                return new ChatFragment();
            case 2:
                return new BerandaFragment();
            case 3:
                return new LowonganFragment();
        }
        return new LainnyaFragment();
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }
}
