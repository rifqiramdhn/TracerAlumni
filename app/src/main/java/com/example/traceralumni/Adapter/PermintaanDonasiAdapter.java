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
import com.example.traceralumni.Client;
import com.example.traceralumni.JsonApi;
import com.example.traceralumni.Model.PermintaanDonasiModel;
import com.example.traceralumni.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.traceralumni.Activity.MainActivity.JENIS_USER;
import static com.example.traceralumni.Activity.MainActivity.JENIS_USER_ALUMNI;
import static com.example.traceralumni.Activity.MainActivity.TEXT_NO_INTERNET;

public class PermintaanDonasiAdapter extends RecyclerView.Adapter<PermintaanDonasiAdapter.ViewHolder> {
    AlertDialog.Builder builder;
    private Context context;
    private ArrayList<PermintaanDonasiModel> permintaanDonasiModels;

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
        holder.tanggalDonasi.setText(permintaanDonasiModel.getTanggal_daftar_donatur());

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


        if (JENIS_USER.equalsIgnoreCase(JENIS_USER_ALUMNI)) {
            holder.clContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailDonasiActivity.class);
                    intent.putExtra("id_donasi", permintaanDonasiModel.getIdDonasi());
                    intent.putExtra("statusDonasi", true);
                    context.startActivity(intent);
                }
            });
            if (permintaanDonasiModel.getStatus().equalsIgnoreCase("VALID")) {
                holder.tulisanDonasi.setText("DITERIMA");
            } else if (permintaanDonasiModel.getStatus().equalsIgnoreCase("BELUMVALID")) {
                holder.tulisanDonasi.setText("MENUNGGU");
            } else {
                holder.tulisanDonasi.setText("DITOLAK");
            }
        }
    }

    @Override
    public int getItemCount() {

        //Mengembalikan ukuran dari ArrayList permintaanDonasiModels
        return permintaanDonasiModels.size();
    }

    private void showKonfirmasiDialog(final int position) {
        builder.setMessage("Konfirmasi permintaan donasi?");
        builder.setTitle("Konfirmasi");
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
        JsonApi jsonApi = Client.getClient().create(JsonApi.class);

        Call<Void> call = jsonApi.confirmDonasi(idDonatur, confirm);
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
                if (t.getMessage().contains("Failed to connect")) {
                    Toast.makeText(context, TEXT_NO_INTERNET, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView namaDonatur, namaKegiatan, totalDonasi, tanggalDonasi, tulisanDonasi;

        private ConstraintLayout clDetailDonasi, clProfilDonatur, clKonfirmasi, clTolak, clContainer;
        private View vVertical, vHorizontal;

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
            tanggalDonasi = itemView.findViewById(R.id.tv_card_permintaan_donasi_tanggal);
            vVertical = itemView.findViewById(R.id.v_card_permintaan_donasi2);
            vHorizontal = itemView.findViewById(R.id.v_card_permintaan_donasi3);
            tulisanDonasi = itemView.findViewById(R.id.tv_card_permintaan_donasi_tulisan_donasi);
            clContainer = itemView.findViewById(R.id.cl_card_permintaan_donasi_container);

            if (JENIS_USER.equals(JENIS_USER_ALUMNI)) {
                clDetailDonasi.setVisibility(View.GONE);
                clProfilDonatur.setVisibility(View.GONE);
                clKonfirmasi.setVisibility(View.INVISIBLE);
                clTolak.setVisibility(View.INVISIBLE);
                vVertical.setVisibility(View.GONE);
                vHorizontal.setVisibility(View.GONE);
                tulisanDonasi.setVisibility(View.VISIBLE);
                tanggalDonasi.setVisibility(View.GONE);
            } else {
                clDetailDonasi.setVisibility(View.VISIBLE);
                clProfilDonatur.setVisibility(View.VISIBLE);
                clKonfirmasi.setVisibility(View.VISIBLE);
                clTolak.setVisibility(View.VISIBLE);
                vVertical.setVisibility(View.VISIBLE);
                vHorizontal.setVisibility(View.VISIBLE);
                tulisanDonasi.setVisibility(View.GONE);
                tanggalDonasi.setVisibility(View.VISIBLE);
            }
        }
    }
}
