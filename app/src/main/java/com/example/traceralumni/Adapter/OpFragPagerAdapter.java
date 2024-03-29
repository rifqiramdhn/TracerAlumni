package com.example.traceralumni.Adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.traceralumni.Fragment.DaftarFragment;
import com.example.traceralumni.Fragment.OpDonasiFragment;
import com.example.traceralumni.Fragment.OpInfoFragment;
import com.example.traceralumni.Fragment.OpLowonganFragment;

public class OpFragPagerAdapter extends FragmentPagerAdapter {

    public OpFragPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new OpLowonganFragment();
            case 1:
                return new OpInfoFragment();
            case 2:
                return new OpDonasiFragment();
        }
        return new DaftarFragment();
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }
}
