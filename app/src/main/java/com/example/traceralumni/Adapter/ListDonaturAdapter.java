package com.example.traceralumni.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.traceralumni.Activity.DetailProfilActivity;
import com.example.traceralumni.Model.PermintaanDonasiModel;
import com.example.traceralumni.R;

import java.util.ArrayList;

public class ListDonaturAdapter extends RecyclerView.Adapter<ListDonaturAdapter.ViewHolder> {
    private Context context;
    private ArrayList<PermintaanDonasiModel> permintaanDonasiModels;

    public ListDonaturAdapter(Context context, ArrayList<PermintaanDonasiModel> permintaanDonasiModels) {
        this.context = context;
        this.permintaanDonasiModels = permintaanDonasiModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_list_donatur, parent, false);
        return new ListDonaturAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        final PermintaanDonasiModel permintaanDonasiModel = permintaanDonasiModels.get(i);
        holder.namaDonatur.setText(permintaanDonasiModel.getNamaDonatur());
        holder.totalDonasi.setText("Jumlah Donasi : Rp " + String.format("%.0f", permintaanDonasiModel.getBantuan()));
        holder.tanggal.setText(permintaanDonasiModel.getTanggal_daftar_donatur());
        holder.viewProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, DetailProfilActivity.class);
                i.putExtra("nim", permintaanDonasiModel.getNim());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return permintaanDonasiModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView namaDonatur, totalDonasi, viewProfil, tanggal;

        public ViewHolder(View itemView) {
            super(itemView);
            namaDonatur = itemView.findViewById(R.id.tv_nama_donatur);
            totalDonasi = itemView.findViewById(R.id.tv_jumlah_donasi);
            viewProfil = itemView.findViewById(R.id.tv_profil);
            tanggal = itemView.findViewById(R.id.tv_card_list_donatur_tanggal);
        }
    }
}
