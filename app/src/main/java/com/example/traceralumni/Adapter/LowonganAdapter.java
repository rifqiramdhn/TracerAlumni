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

import com.example.traceralumni.Activity.DetailLowonganActivity;
import com.example.traceralumni.R;
import com.example.traceralumni.Model.LowonganModel;

import java.util.ArrayList;

public class LowonganAdapter extends RecyclerView.Adapter<LowonganAdapter.ListLowonganHolder>{
    Context context;
    private ArrayList<LowonganModel> listLowongan;

    public LowonganAdapter(ArrayList<LowonganModel> listLowongan){
        this.listLowongan = listLowongan;
    }

    @NonNull
    @Override
    public ListLowonganHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.card_lowongan, viewGroup, false);
        return new ListLowonganHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListLowonganHolder listLowonganHolder, int i) {
        LowonganModel list = listLowongan.get(i);
        listLowonganHolder.txtTitle.setText(list.getNama_lowongan());
        listLowonganHolder.txtPerusahaan.setText(list.getNama_perusahaan());
        listLowonganHolder.txtLokasi.setText(list.getLokasi_perusahaan());
        listLowonganHolder.txtKisaranGaji.setText("~Rp "+list.getKisaran_gaji());
        listLowonganHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(listLowonganHolder.container.getContext(), DetailLowonganActivity.class);
                listLowonganHolder.container.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listLowongan.size();
    }

    public class ListLowonganHolder extends RecyclerView.ViewHolder{
        private TextView txtTitle, txtPerusahaan, txtLokasi, txtKisaranGaji;
        private ConstraintLayout container;

        public ListLowonganHolder(View itemView){
            super(itemView);

            txtTitle = itemView.findViewById(R.id.txt_nama_lowongan);
            txtPerusahaan = itemView.findViewById(R.id.txt_nama_perusahaan);
            txtLokasi = itemView.findViewById(R.id.txt_lokasi_perusahaan);
            txtKisaranGaji = itemView.findViewById(R.id.txt_kisaran_gaji);
            container = itemView.findViewById(R.id.card_daftar_lowongan);
        }

    }
}
