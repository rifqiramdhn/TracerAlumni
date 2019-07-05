package com.example.traceralumni.Activity;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.traceralumni.Adapter.ListDonaturAdapter;
import com.example.traceralumni.Model.ListDonaturModel;
import com.example.traceralumni.R;

import java.util.ArrayList;

public class OpListDonaturActivity extends AppCompatActivity {
    ConstraintLayout cl_iconBack;
    ImageView img_iconBack;
    TextView tvNavBar;

    RecyclerView listDonaturRecycler;
    ListDonaturAdapter listDonaturAdapter;
    ArrayList<ListDonaturModel> listDonaturModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_op_list_donatur);

        tvNavBar = findViewById(R.id.tv_navbar_top);
        tvNavBar.setText("LIST DONATUR");

        cl_iconBack = findViewById(R.id.cl_icon1);
        cl_iconBack.setVisibility(View.VISIBLE);

        img_iconBack = findViewById(R.id.img_icon1);
        img_iconBack.setImageResource(R.drawable.ic_arrow_back);

        cl_iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        listDonaturModels = new ArrayList<>();
        listDonaturModels.add(new ListDonaturModel("Paidi Sugiono", 20000000));
        listDonaturModels.add(new ListDonaturModel("Paidi Sugiono", 20000000));
        listDonaturModels.add(new ListDonaturModel("Paidi Sugiono", 20000000));
        listDonaturModels.add(new ListDonaturModel("Paidi Sugiono", 20000000));

        listDonaturAdapter = new ListDonaturAdapter(OpListDonaturActivity.this, listDonaturModels);
        listDonaturRecycler = findViewById(R.id.rv_list_donatur);
        listDonaturRecycler.setLayoutManager(new LinearLayoutManager(OpListDonaturActivity.this, LinearLayoutManager.VERTICAL, false));
        listDonaturRecycler.setAdapter(listDonaturAdapter);
    }
}
