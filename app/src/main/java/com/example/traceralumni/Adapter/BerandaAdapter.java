package com.example.traceralumni.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.traceralumni.Activity.DetailDonasiActivity;
import com.example.traceralumni.Activity.DetailLowonganActivity;
import com.example.traceralumni.Model.BerandaModel;
import com.example.traceralumni.Model.DonasiModel;
import com.example.traceralumni.Model.InfoModel;
import com.example.traceralumni.Model.LowonganModel;
import com.example.traceralumni.R;

import java.util.ArrayList;

import static com.example.traceralumni.Activity.MainActivity.JENIS_USER;
import static com.example.traceralumni.Activity.MainActivity.JENIS_USER_ALUMNI;

public class BerandaAdapter extends RecyclerView.Adapter<BerandaAdapter.ViewHolder> {

    private Context context;
    private ArrayList<BerandaModel> berandaModels;

    //cardnya belum selesai

    public BerandaAdapter(Context context, ArrayList<BerandaModel> data) {
        this.context = context;
        this.berandaModels = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.card_beranda, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        BerandaModel list = berandaModels.get(i);
        if (list.getJenis_data().equals("data_donasi")) {
            DonasiModel donasiModel = list.getDonasiModel();
            viewHolder.d_namaKegiatan.setText(donasiModel.getNamaKegiatan());
//            viewHolder.d_tanggalDonasi.setText("" + donasiModel.getTanggal());
            viewHolder.d_totalBiaya.setText(donasiModel.getTotalAnggaran());
            viewHolder.container_donasi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, DetailDonasiActivity.class);
                    context.startActivity(intent);
                }
            });

            //visibility
            viewHolder.container_lowongan.setVisibility(View.GONE);
            viewHolder.container_info.setVisibility(View.GONE);
            viewHolder.container_donasi.setVisibility(View.VISIBLE);
        } else if (list.getJenis_data().equals("data_info")) {
            final InfoModel infoModel = list.getInfoModel();
            viewHolder.i_isi.setText(infoModel.getKeterangan());
            viewHolder.i_judul.setText(infoModel.getJudul());
            viewHolder.i_tanggal.setText("" + infoModel.getTanggal_info());
            viewHolder.container_info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String url = infoModel.getLink();
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    context.startActivity(intent);
                }
            });

            //visibility
            viewHolder.container_lowongan.setVisibility(View.GONE);
            viewHolder.container_info.setVisibility(View.VISIBLE);
            viewHolder.container_donasi.setVisibility(View.GONE);
        } else {
            LowonganModel lowonganModel = list.getLowonganModel();
            viewHolder.l_txtKisaranGaji.setText(lowonganModel.getKisaran_gaji());
            viewHolder.l_txtLokasi.setText(lowonganModel.getAlamat_perusahaan());
            viewHolder.l_txtPerusahaan.setText(lowonganModel.getNama_perusahaan());
            viewHolder.l_txtTitle.setText(lowonganModel.getNama_lowongan());
            viewHolder.container_lowongan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, DetailLowonganActivity.class);
                    context.startActivity(intent);
                }
            });

            //visibility
            viewHolder.container_lowongan.setVisibility(View.VISIBLE);
            viewHolder.container_info.setVisibility(View.GONE);
            viewHolder.container_donasi.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return berandaModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //untuk donasi
        private TextView d_namaKegiatan, d_totalBiaya, d_tanggalDonasi, d_tulisanDonasi;

        //untuk info
        private TextView i_judul, i_isi, i_tanggal;
        private ImageView i_iv_link;

        //untuk lowongan
        private TextView l_txtTitle, l_txtPerusahaan, l_txtLokasi, l_txtKisaranGaji;

        //Deklarasi ConstraintLayout container
        private ConstraintLayout container_donasi, container_info, container_lowongan;

        public ViewHolder(View itemView) {
            super(itemView);
            //untuk donasi
            d_namaKegiatan = itemView.findViewById(R.id.tv_card_donasi_judul_donasi);
            d_totalBiaya = itemView.findViewById(R.id.tv_card_donasi_jumlah_donasi);
            d_tanggalDonasi = itemView.findViewById(R.id.tv_card_donasi_tanggal);
            d_tulisanDonasi = itemView.findViewById(R.id.tv_card_donasi_tulisan_donasi);

            //buat diklik
            container_donasi = itemView.findViewById(R.id.card_beranda_donasi);

            if (JENIS_USER.equalsIgnoreCase(JENIS_USER_ALUMNI)) {
                d_tulisanDonasi.setVisibility(View.VISIBLE);
                d_tanggalDonasi.setVisibility(View.INVISIBLE);
            } else {
                d_tulisanDonasi.setVisibility(View.INVISIBLE);
                d_tanggalDonasi.setVisibility(View.VISIBLE);
            }

            //untuk info
            i_judul = itemView.findViewById(R.id.tv_card_info_judul);
            i_isi = itemView.findViewById(R.id.tv_card_info_isi);
            i_tanggal = itemView.findViewById(R.id.tv_card_info_tanggal);
            i_iv_link = itemView.findViewById(R.id.img_card_info_link);

            //buat diklik
            container_info = itemView.findViewById(R.id.card_beranda_info);

            if (JENIS_USER.equalsIgnoreCase(JENIS_USER_ALUMNI)) {
                i_iv_link.setVisibility(View.VISIBLE);
                i_tanggal.setVisibility(View.GONE);
            } else {
                i_iv_link.setVisibility(View.GONE);
                i_tanggal.setVisibility(View.VISIBLE);
            }

            //untuk lowongan
            l_txtTitle = itemView.findViewById(R.id.tv_nama_lowongan);
            l_txtPerusahaan = itemView.findViewById(R.id.tv_nama_perusahaan);
            l_txtLokasi = itemView.findViewById(R.id.tv_lokasi_perusahaan);
            l_txtKisaranGaji = itemView.findViewById(R.id.tv_kisaran_gaji);

            //buat diklik
            container_lowongan = itemView.findViewById(R.id.card_beranda_lowongan);
        }
    }
}
