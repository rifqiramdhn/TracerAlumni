package com.example.traceralumni.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.traceralumni.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PimInfoFragment extends Fragment {


    public PimInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pim_info, container, false);
    }

}
