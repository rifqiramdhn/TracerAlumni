package com.example.traceralumni.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.traceralumni.Model.RiwayatPekerjaanModel;
import com.example.traceralumni.R;

import java.util.ArrayList;

public class RiwayatPekerjaanAdapter extends RecyclerView.Adapter<RiwayatPekerjaanAdapter.RiwayatPekerjaanHolder> {
    private ArrayList<RiwayatPekerjaanModel> listRiwayat;

    public RiwayatPekerjaanAdapter(ArrayList<RiwayatPekerjaanModel> listRiwayat){
        this.listRiwayat = listRiwayat;
    }

    @NonNull
    @Override
    public RiwayatPekerjaanHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.card_riwayat_pekerjaan, viewGroup, false);
        return new RiwayatPekerjaanHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RiwayatPekerjaanHolder holder, int i) {
        RiwayatPekerjaanModel list = listRiwayat.get(i);
        holder.tvJabatan.setText(list.getJabatan());
        holder.tvBidangKerja.setText(list.getBidangKerja());
        holder.tvNmPerusahaan.setText(list.getNamaPerusahaan());
        holder.tvLokPerusahaan.setText(list.getLokasiKerja());
        holder.tvPendapatan.setText("Rp. "+list.getPendapatan()+" / bulan");
        holder.tvThnMasuk.setText(list.getThnMulai());
        holder.tvThnSelesai.setText(list.getThnSelesai());
    }

    @Override
    public int getItemCount() {
        return listRiwayat.size();
    }

    public class RiwayatPekerjaanHolder extends RecyclerView.ViewHolder{
        private TextView tvJabatan, tvBidangKerja, tvNmPerusahaan, tvLokPerusahaan, tvPendapatan, tvThnMasuk, tvThnSelesai;

        public RiwayatPekerjaanHolder(View itemView){
            super(itemView);
            tvJabatan = itemView.findViewById(R.id.tv_jabatan);
            tvBidangKerja = itemView.findViewById(R.id.tv_bidang_kerja);
            tvNmPerusahaan = itemView.findViewById(R.id.tv_nama_perusahaan);
            tvLokPerusahaan = itemView.findViewById(R.id.tv_lokasi_perusahaan);
            tvPendapatan = itemView.findViewById(R.id.tv_pendapatan);
            tvThnMasuk = itemView.findViewById(R.id.tv_tahun_masuk);
            tvThnSelesai = itemView.findViewById(R.id.tv_tahun_selesai);
        }
    }
}
