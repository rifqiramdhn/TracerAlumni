package com.example.traceralumni.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.traceralumni.Model.DaftarModel;
import com.example.traceralumni.Model.PengaturanModel;
import com.example.traceralumni.R;

import java.util.ArrayList;

public class DaftarAdapter extends RecyclerView.Adapter<DaftarAdapter.ViewHolder> {

    private Context context;
    private ArrayList<DaftarModel> daftarModels;

    public DaftarAdapter(Context context, ArrayList<DaftarModel> data) {
        this.context = context;
        this.daftarModels = data;
    }

    @Override
    public DaftarAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //Inflate layout daftar
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_daftar, parent, false);
        return new DaftarAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final DaftarAdapter.ViewHolder holder, int position) {

        //Instansiasi objek pengaturanModel yang isinya sama dengan daftarModels pada index position
        final DaftarModel daftarModel = daftarModels.get(position);

        //Mengisi item dari holder menjadi item dari objek daftarModel
        holder.nama.setText(daftarModel.getNama());

        //Mengisi item dari holder menjadi item dari objek daftarModel
        holder.prodi.setText(String.valueOf(daftarModel.getIdProdi()));

        //Mengisi item dari holder menjadi item dari objek daftarModel
        holder.angkatan.setText(daftarModel.getAngkatan());

        //Mengisi item dari holder menjadi item dari objek daftarModel
        holder.foto.setImageResource(R.mipmap.ic_launcher_round);

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, String.valueOf(daftarModel.getIdProdi()), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {

        //Mengembalikan ukuran dari ArrayList daftarModels
        return daftarModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        //Deklarasi TextView nama, prodi, dan angkatan
        private TextView nama, prodi, angkatan;

        //Deklarasi ImageView foto
        private ImageView foto;

        //Deklarasi ConstraintLayout container
        private ConstraintLayout container;

        public ViewHolder(View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.card_daftar_nama_text_view);
            prodi = itemView.findViewById(R.id.card_daftar_prodi_text_view);
            angkatan = itemView.findViewById(R.id.card_daftar_angkatan_text_view);
            foto = itemView.findViewById(R.id.card_daftar_image_view);
            container = itemView.findViewById(R.id.card_daftar_container);
        }
    }
}