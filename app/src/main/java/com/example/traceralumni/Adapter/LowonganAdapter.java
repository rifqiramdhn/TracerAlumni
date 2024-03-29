package com.example.traceralumni.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.traceralumni.Activity.DetailLowonganActivity;
import com.example.traceralumni.Model.LowonganModel;
import com.example.traceralumni.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.traceralumni.Activity.MainActivity.BASE_URL;
import static com.example.traceralumni.Activity.MainActivity.JENIS_USER;
import static com.example.traceralumni.Activity.MainActivity.JENIS_USER_ALUMNI;
import static com.example.traceralumni.Activity.StatusPermintaanLowonganActivity.BUKA_STATUS_LOWONGAN;

public class LowonganAdapter extends RecyclerView.Adapter<LowonganAdapter.ListLowonganHolder> implements Filterable {
    Context context;
    String oldPath = "";
    private ArrayList<LowonganModel> listLowongan;
    private ArrayList<LowonganModel> listLowonganFull;
    private Filter listLowonganFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<LowonganModel> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(listLowonganFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (LowonganModel item : listLowonganFull) {
                    if (item.getNama_lowongan().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            listLowongan.clear();
            if (filterResults.values != null) {
                listLowongan.addAll((ArrayList<LowonganModel>) filterResults.values);
            }
            notifyDataSetChanged();
        }
    };

    public LowonganAdapter(Context context, ArrayList<LowonganModel> listLowongan) {
        this.context = context;
        this.listLowongan = listLowongan;
        listLowonganFull = new ArrayList<>(listLowongan);
    }

    @NonNull
    @Override
    public ListLowonganHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.card_lowongan, viewGroup, false);
        return new ListLowonganHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListLowonganHolder holder, final int position) {
        final LowonganModel list = listLowongan.get(position);
        holder.txtTitle.setText(list.getNama_lowongan());
        holder.txtPerusahaan.setText(list.getNama_perusahaan());
        holder.txtLokasi.setText(list.getAlamat_perusahaan());
        holder.txtKisaranGaji.setText("~Rp " + list.getKisaran_gaji() + " juta");
        holder.txtTanggal.setText(list.getTanggal_lowongan());
        oldPath = list.getLogo_perusahaan();

        if (BUKA_STATUS_LOWONGAN) {
            if (list.getStatus_lowongan().equals("Valid")) {
                holder.txtDetail.setText("DITERIMA");
            } else if (list.getStatus_lowongan().equals("BelumValid")) {
                holder.txtDetail.setText("MENUNGGU");
            } else {
                holder.txtDetail.setText("DITOLAK");
            }
        }

        if (!oldPath.equals("")) {
            Glide.with(context)
                    .load(BASE_URL + oldPath)
                    .into(holder.foto);
        }
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, DetailLowonganActivity.class);
                i.putExtra("object_lowongan", list);
                if (BUKA_STATUS_LOWONGAN) {
                    i.putExtra("status", list.getStatus_lowongan());
                }
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listLowongan.size();
    }

    @Override
    public Filter getFilter() {
        return listLowonganFilter;
    }

    public class ListLowonganHolder extends RecyclerView.ViewHolder {
        private TextView txtTitle, txtPerusahaan, txtLokasi, txtKisaranGaji, txtTanggal, txtDetail;
        private ConstraintLayout container;
        private CircleImageView foto;

        public ListLowonganHolder(View itemView) {
            super(itemView);
            txtDetail = itemView.findViewById(R.id.txt_detail);
            txtTitle = itemView.findViewById(R.id.tv_nama_lowongan);
            txtPerusahaan = itemView.findViewById(R.id.tv_nama_perusahaan);
            txtLokasi = itemView.findViewById(R.id.tv_lokasi_perusahaan);
            txtKisaranGaji = itemView.findViewById(R.id.tv_kisaran_gaji);
            txtTanggal = itemView.findViewById(R.id.tv_tanggal_lowongan);
            foto = itemView.findViewById(R.id.iv_tambah_lowongan_logo);
            container = itemView.findViewById(R.id.card_daftar_lowongan);
            if (JENIS_USER.equalsIgnoreCase(JENIS_USER_ALUMNI)) {
                txtTanggal.setVisibility(View.GONE);
                txtDetail.setVisibility(View.VISIBLE);
            } else {
                txtTanggal.setVisibility(View.VISIBLE);
                txtDetail.setVisibility(View.GONE);
            }
        }

    }
}
