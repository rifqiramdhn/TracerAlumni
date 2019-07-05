package com.example.traceralumni.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.traceralumni.Activity.DetailProfilActivity;
import com.example.traceralumni.Model.ListDonaturModel;
import com.example.traceralumni.R;

import java.util.ArrayList;

public class ListDonaturAdapter extends RecyclerView.Adapter<ListDonaturAdapter.ViewHolder> {
    private Context context;
    private ArrayList<ListDonaturModel> listDonaturModels;

    public ListDonaturAdapter(Context context, ArrayList<ListDonaturModel> listDonaturModels) {
        this.context = context;
        this.listDonaturModels = listDonaturModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_list_donatur, parent, false);
        return new ListDonaturAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        ListDonaturModel listDonaturModel = listDonaturModels.get(i);
        holder.namaDonatur.setText(listDonaturModel.getNamaDonatur());
        holder.totalDonasi.setText("Jumlah Donasi : Rp "+listDonaturModel.getJumlahDonasi());
        holder.viewProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), DetailProfilActivity.class);
                v.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listDonaturModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView namaDonatur, totalDonasi, viewProfil;

        public ViewHolder(View itemView) {
            super(itemView);
            namaDonatur = itemView.findViewById(R.id.tv_nama_donatur);
            totalDonasi = itemView.findViewById(R.id.tv_jumlah_donasi);
            viewProfil = itemView.findViewById(R.id.tv_profil);
        }
    }
}
