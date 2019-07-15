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

import com.example.traceralumni.Activity.DetailDonasiActivity;
import com.example.traceralumni.Activity.DetailProfilActivity;
import com.example.traceralumni.Activity.MainActivity;
import com.example.traceralumni.JsonPlaceHolderApi;
import com.example.traceralumni.Model.PermintaanDonasiModel;
import com.example.traceralumni.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PermintaanDonasiAdapter extends RecyclerView.Adapter<PermintaanDonasiAdapter.ViewHolder> {
    private Context context;
    private ArrayList<PermintaanDonasiModel> permintaanDonasiModels;
    AlertDialog.Builder builder;

    public PermintaanDonasiAdapter(Context context, ArrayList<PermintaanDonasiModel> data) {
        this.context = context;
        this.permintaanDonasiModels = data;
    }

    @Override
    public PermintaanDonasiAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //Inflate layout card_lainnya
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_permintaan_donasi, parent, false);
        builder = new AlertDialog.Builder(context);
        return new PermintaanDonasiAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final PermintaanDonasiAdapter.ViewHolder holder, final int position) {

        final PermintaanDonasiModel permintaanDonasiModel = permintaanDonasiModels.get(position);
        holder.namaDonatur.setText(permintaanDonasiModel.getNamaDonatur());
        holder.namaKegiatan.setText(permintaanDonasiModel.getNamaDonasi());
        holder.totalDonasi.setText("Jumlah Donasi : Rp " + String.format("%.0f", permintaanDonasiModel.getBantuan()));

        holder.clDetailDonasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailDonasiActivity.class);
                intent.putExtra("id_donasi", permintaanDonasiModel.getIdDonasi());
                context.startActivity(intent);
            }
        });
        holder.clProfilDonatur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailProfilActivity.class);
                intent.putExtra("nim", permintaanDonasiModel.getNim());
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

        //Mengembalikan ukuran dari ArrayList permintaanDonasiModels
        return permintaanDonasiModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView namaDonatur, namaKegiatan, totalDonasi, tanggalDonasi;

        private ConstraintLayout clDetailDonasi, clProfilDonatur, clKonfirmasi, clTolak;

        public ViewHolder(View itemView) {
            super(itemView);
            namaDonatur = itemView.findViewById(R.id.tv_card_permintaan_donasi_nama_donatur);
            namaKegiatan = itemView.findViewById(R.id.tv_card_permintaan_donasi_nama_donasi);
            totalDonasi = itemView.findViewById(R.id.tv_card_permintaan_donasi_jumlah);
            tanggalDonasi = itemView.findViewById(R.id.tv_card_permintaan_donasi_tanggal);
            clDetailDonasi = itemView.findViewById(R.id.cl_card_permintaan_donasi_detail_donasi);
            clProfilDonatur = itemView.findViewById(R.id.cl_card_permintaan_donasi_profil_donatur);
            clKonfirmasi = itemView.findViewById(R.id.cl_card_permintaan_donasi_ok);
            clTolak = itemView.findViewById(R.id.cl_card_permintaan_donasi_no);
        }
    }

    private void showKonfirmasiDialog(final int position) {
        builder.setMessage("Konfirmasi permintaan donasi?");
        builder.setTitle("Konfirmasi");
        builder.setCancelable(false);
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                confirmDonation(position, permintaanDonasiModels.get(position).getIdDonatur(), "y");
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
        builder.setMessage("Tolak permintaan donasi?");
        builder.setTitle("Tolak");
        builder.setCancelable(false);
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                confirmDonation(position, permintaanDonasiModels.get(position).getIdDonatur(), "n");
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

    private void confirmDonation(final int position, int idDonatur, final String confirm) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MainActivity.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<Void> call = jsonPlaceHolderApi.confirmDonasi(idDonatur, confirm);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    return;
                }

                if (confirm.equals("y")) {
                    Toast.makeText(context, "Donasi diterima", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Donasi ditolak", Toast.LENGTH_SHORT).show();
                }

                permintaanDonasiModels.remove(position);
                notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
