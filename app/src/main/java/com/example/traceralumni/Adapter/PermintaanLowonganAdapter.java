package com.example.traceralumni.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.traceralumni.Activity.DetailLowonganActivity;
import com.example.traceralumni.Client;
import com.example.traceralumni.JsonApi;
import com.example.traceralumni.Model.PermintaanLowonganModel;
import com.example.traceralumni.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.traceralumni.Activity.MainActivity.BASE_URL;
import static com.example.traceralumni.Activity.MainActivity.TEXT_NO_INTERNET;

public class PermintaanLowonganAdapter extends RecyclerView.Adapter<PermintaanLowonganAdapter.ViewHolder> {
    private Context context;
    private ArrayList<PermintaanLowonganModel> permintaanLowonganModels;
    AlertDialog.Builder builder;
    String oldPath = "";

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
        holder.namaPelowong.setText(permintaanLowonganModel.getNama());
        holder.namaLowongan.setText(permintaanLowonganModel.getNamaLowongan());
        holder.namaPerusahaan.setText(permintaanLowonganModel.getNamaPerusahaan());
        holder.lokasiPerusahaan.setText(permintaanLowonganModel.getAlamatPerusahaan());
        holder.gaji.setText("Rp~ " + permintaanLowonganModel.getKisaranGaji() + " juta");
        holder.tanggal.setText(permintaanLowonganModel.getTanggal_lowongan());
        oldPath = permintaanLowonganModel.getLogoPerusahaan();
        if (!oldPath.equals("")) {
            Glide.with(context)
                    .load(BASE_URL + oldPath)
                    .into(holder.foto);
        }
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailLowonganActivity.class);
                intent.putExtra("object_permintaan_lowongan", permintaanLowonganModel.getIdLowongan());
                context.startActivity(intent);
            }
        });
        holder.clKonfirmasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showKonfirmasiDialog(position);
            }
        });
        holder.clTolak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTolakDialog(position);
            }
        });
    }

    @Override
    public int getItemCount() {

        //Mengembalikan ukuran dari ArrayList permintaanLowonganModels
        return permintaanLowonganModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView namaPelowong, namaLowongan, namaPerusahaan, lokasiPerusahaan, gaji, tanggal;

        private CircleImageView foto;

        private ConstraintLayout container, clKonfirmasi, clTolak;

        public ViewHolder(View itemView) {
            super(itemView);
            namaPelowong = itemView.findViewById(R.id.tv_card_permintaan_lowongan_nama_pelowong);
            namaLowongan = itemView.findViewById(R.id.tv_card_permintaan_lowongan_jabatan);
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

    private void showKonfirmasiDialog(final int position) {
        builder.setMessage("Konfirmasi permintaan lowongan?");
        builder.setTitle("Konfirmasi");
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                confirmLowongan(position, permintaanLowonganModels.get(position).getIdLowongan(), "y");
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

    private void showTolakDialog(final int position) {
        builder.setMessage("Tolak permintaan lowongan?");
        builder.setTitle("Tolak");
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                confirmLowongan(position, permintaanLowonganModels.get(position).getIdLowongan(), "n");
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

    private void confirmLowongan(final int position, int idLowongan, final String confirm) {
        JsonApi jsonApi = Client.getClient().create(JsonApi.class);
        Call<Void> call = jsonApi.confirmLowongan(idLowongan, confirm);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                if (confirm.equals("y")) {
                    Toast.makeText(context, "Permintaan lowongan telah diterima", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Permintaan lowongan telah ditolak", Toast.LENGTH_SHORT).show();
                }

                permintaanLowonganModels.remove(position);
                notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                if (t.getMessage().contains("Failed to connect")) {
                    Toast.makeText(context, TEXT_NO_INTERNET, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
