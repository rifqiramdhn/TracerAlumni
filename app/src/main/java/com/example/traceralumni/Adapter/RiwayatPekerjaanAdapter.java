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

import com.example.traceralumni.Activity.TambahRiwayatPekerjaanActivity;
import com.example.traceralumni.Model.RiwayatPekerjaanModel;
import com.example.traceralumni.R;

import java.util.ArrayList;

import static com.example.traceralumni.Activity.MainActivity.NIM;

public class RiwayatPekerjaanAdapter extends RecyclerView.Adapter<RiwayatPekerjaanAdapter.ViewHolder> {
    private Context context;
    private ArrayList<RiwayatPekerjaanModel> listRiwayat;

    public RiwayatPekerjaanAdapter(Context context, ArrayList<RiwayatPekerjaanModel> listRiwayat){
        this.listRiwayat = listRiwayat;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.card_riwayat_pekerjaan, viewGroup, false);
        return new RiwayatPekerjaanAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RiwayatPekerjaanAdapter.ViewHolder holder, int i) {
        final RiwayatPekerjaanModel list = listRiwayat.get(i);
        holder.tvJabatan.setText(list.getPekerjaan());
        holder.tvNmPerusahaan.setText(list.getNamaPerusahaan());
        holder.tvLokPerusahaan.setText(list.getLokasi());
        holder.tvPendapatan.setText("Rp. "+list.getGaji()+" / bulan");
        holder.tvThn.setText(list.getTahunAwal() + " - " + list.getTahunAkhir());
        holder.clContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (list.getNim().equals(NIM)){
                    Intent intent = new Intent(context, TambahRiwayatPekerjaanActivity.class);
                    intent.putExtra("object_riwayat", list);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listRiwayat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvJabatan, tvNmPerusahaan, tvLokPerusahaan, tvPendapatan, tvThn;
        private ConstraintLayout clContainer;
        public ViewHolder(View itemView){
            super(itemView);
            clContainer = itemView.findViewById(R.id.cl_riwayat_container);
            tvJabatan = itemView.findViewById(R.id.tv_jabatan);
            tvNmPerusahaan = itemView.findViewById(R.id.tv_nama_perusahaan);
            tvLokPerusahaan = itemView.findViewById(R.id.tv_lokasi_perusahaan);
            tvPendapatan = itemView.findViewById(R.id.tv_pendapatan);
            tvThn = itemView.findViewById(R.id.tv_tahun_riwayat);
        }
    }
}
