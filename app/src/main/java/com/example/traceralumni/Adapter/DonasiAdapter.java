package com.example.traceralumni.Adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.traceralumni.Model.ChatModel;
import com.example.traceralumni.Model.DonasiModel;
import com.example.traceralumni.R;

import java.util.ArrayList;

public class DonasiAdapter extends RecyclerView.Adapter<DonasiAdapter.ViewHolder> {

    private Context context;
    private ArrayList<DonasiModel> donasiModels;

    public DonasiAdapter(Context context, ArrayList<DonasiModel> data) {
        this.context = context;
        this.donasiModels = data;
    }

    @Override
    public DonasiAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //Inflate layout card_lainnya
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_donasi, parent, false);
        return new DonasiAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final DonasiAdapter.ViewHolder holder, final int position) {

        //Instansiasi objek lainnyaModel yang isinya sama dengan lainnyaModels pada index position
        final DonasiModel donasiModel = donasiModels.get(position);

        //Mengisi item dari holder menjadi item dari objek lainnyaModel
        holder.namaKegiatan.setText(donasiModel.getNamaKegiatan());
        holder.totalBiaya.setText(donasiModel.getTotalBiaya());

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, donasiModel.getNamaKegiatan(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {

        //Mengembalikan ukuran dari ArrayList chatModels
        return donasiModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        //Deklarasi TextView namaKegiatan, totalBiaya
        private TextView namaKegiatan, totalBiaya;

        //Deklarasi ConstraintLayout container
        private ConstraintLayout container;

        public ViewHolder(View itemView) {
            super(itemView);
            namaKegiatan = itemView.findViewById(R.id.card_donasi_judul_donasi_text_view);
            totalBiaya = itemView.findViewById(R.id.card_donasi_jumlah_donasi_text_view);
            container = itemView.findViewById(R.id.card_donasi_container);
        }
    }
}