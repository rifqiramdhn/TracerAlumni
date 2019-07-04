package com.example.traceralumni.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.traceralumni.Model.PermintaanLowonganModel;
import com.example.traceralumni.R;

import java.util.ArrayList;

public class PermintaanLowonganAdapter extends RecyclerView.Adapter<PermintaanLowonganAdapter.ViewHolder> {
    private Context context;
    private ArrayList<PermintaanLowonganModel> permintaanLowonganModels;
    AlertDialog.Builder builder;

    public PermintaanLowonganAdapter(Context context, ArrayList<PermintaanLowonganModel> data) {
        this.context = context;
        this.permintaanLowonganModels = data;
    }

    @Override
    public PermintaanLowonganAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //Inflate layout card_lainnya
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_permintaan_lowongan, parent, false);
        builder = new AlertDialog.Builder(context);
        return new PermintaanLowonganAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final PermintaanLowonganAdapter.ViewHolder holder, final int position) {

        final PermintaanLowonganModel permintaanLowonganModel = permintaanLowonganModels.get(position);
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Masuk ke detail lowongan", Toast.LENGTH_SHORT).show();
            }
        });
        holder.clKonfirmasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showKonfirmasiDialog();
            }
        });
        holder.clTolak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTolakDialog();
            }
        });
    }

    @Override
    public int getItemCount() {

        //Mengembalikan ukuran dari ArrayList permintaanLowonganModels
        return permintaanLowonganModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView namaPelowong, jabatan, namaPerusahaan, lokasiPerusahaan, gaji, tanggal;

        private ImageView foto;

        private ConstraintLayout container, clKonfirmasi, clTolak;

        public ViewHolder(View itemView) {
            super(itemView);
            namaPelowong = itemView.findViewById(R.id.tv_card_permintaan_lowongan_nama_pelowong);
            jabatan = itemView.findViewById(R.id.tv_card_permintaan_lowongan_jabatan);
            namaPerusahaan = itemView.findViewById(R.id.tv_card_permintaan_lowongan_nama_perusahaan);
            lokasiPerusahaan = itemView.findViewById(R.id.tv_card_permintaan_lowongan_lokasi_perusahaan);
            gaji = itemView.findViewById(R.id.tv_card_permintaan_lowongan_gaji);
            tanggal = itemView.findViewById(R.id.tv_card_permintaan_lowongan_tanggal);
            foto = itemView.findViewById(R.id.iv_card_permintaan_lowongan_foto);
            container = itemView.findViewById(R.id.cl_card_permintaan_lowongan_container);
            clKonfirmasi = itemView.findViewById(R.id.cl_card_permintaan_lowongan_konfirmasi);
            clTolak = itemView.findViewById(R.id.cl_card_permintaan_lowongan_tolak);
        }
    }

    private void showKonfirmasiDialog() {

        builder.setMessage("Konfirmasi permintaan lowongan?");

        builder.setTitle("Konfirmasi");

        builder.setCancelable(false);

        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "Permintaan lowongan telah dikonfirmasi!", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();

        alertDialog.show();
    }

    private void showTolakDialog() {
        builder.setMessage("Tolak permintaan lowongan?");

        builder.setTitle("Tolak");

        builder.setCancelable(false);

        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                Toast.makeText(context, "Permintaan lowongan telah ditolak!", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();

        alertDialog.show();
    }
}
