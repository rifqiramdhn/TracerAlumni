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
import com.example.traceralumni.Fragment.PimDataAlumniFragment;
import com.example.traceralumni.Fragment.PimDonasiFragment;
import com.example.traceralumni.Fragment.PimInfoFragment;
import com.example.traceralumni.Fragment.PimLowonganFragment;

public class PimFragPagerAdapter extends FragmentPagerAdapter {

    public PimFragPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new PimDataAlumniFragment();
            case 1:
                return new PimInfoFragment();
            case 2:
                return new PimLowonganFragment();
        }
        return new PimDonasiFragment();
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
