package com.example.traceralumni.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.traceralumni.Model.PengaturanModel;
import com.example.traceralumni.R;

import java.util.ArrayList;

public class PengaturanAdapter extends RecyclerView.Adapter<PengaturanAdapter.ViewHolder> {

    private Context context;
    private ArrayList<PengaturanModel> pengaturanModels;

    public PengaturanAdapter(Context context, ArrayList<PengaturanModel> data) {
        this.context = context;
        this.pengaturanModels = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //Inflate layout card_pengaturan
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_pengaturan, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        //Instansiasi objek pengaturanModel yang isinya sama dengan pengaturanModels pada index position
        final PengaturanModel pengaturanModel = pengaturanModels.get(position);

        //Mengisi item dari holder menjadi item dari objek pengaturanModel
        holder.item.setText(pengaturanModel.getItem());

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (position) {
                    case 0:
                        Toast.makeText(context, "Kartu Identitas", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(context, "Legalisir Ijazah", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(context, "Tracer Studi", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(context, "Sunting Profil", Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        Toast.makeText(context, "Riwayat Pekerjaan", Toast.LENGTH_SHORT).show();
                        break;
                    case 5:
                        Toast.makeText(context, "Ganti Kata Sandi", Toast.LENGTH_SHORT).show();
                        break;
                    case 6:
                        Toast.makeText(context, "Hapus Semua Chat", Toast.LENGTH_SHORT).show();
                        break;
                    case 7:
                        Toast.makeText(context, "Tentang", Toast.LENGTH_SHORT).show();
                        break;
                    case 8:
                        Toast.makeText(context, "Keluar", Toast.LENGTH_SHORT).show();
                        break;

                }
            }
        });

        if (position == 8) {
            holder.item.setTextColor(Color.RED);
        }
    }

    @Override
    public int getItemCount() {

        //Mengembalikan ukuran dari ArrayList pengaturanModels
        return pengaturanModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        //Deklarasi TextView item
        private TextView item;

        //Deklarasi ConstraintLayout container
        private ConstraintLayout container;

        public ViewHolder(View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.card_pengaturan_nama_item_text_view);
            container = itemView.findViewById(R.id.card_pengaturan_container);
        }
    }
}