package com.example.traceralumni.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.traceralumni.Activity.DetailDonasiActivity;
import com.example.traceralumni.Activity.OpDetailDonasiActivity;
import com.example.traceralumni.Model.DonasiModel;
import com.example.traceralumni.R;

import java.util.ArrayList;

import static com.example.traceralumni.Activity.MainActivity.JENIS_USER;
import static com.example.traceralumni.Activity.MainActivity.JENIS_USER_ALUMNI;
import static com.example.traceralumni.Activity.MainActivity.JENIS_USER_OPERATOR;

public class DonasiAdapter extends RecyclerView.Adapter<DonasiAdapter.ViewHolder> implements Filterable {

    private Context context;
    private ArrayList<DonasiModel> donasiModels;
    private ArrayList<DonasiModel> donasiModelsFull;
    private Filter donasiModelsFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<DonasiModel> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(donasiModelsFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (DonasiModel item : donasiModelsFull) {
                    if (item.getNamaKegiatan().toLowerCase().contains(filterPattern)) {
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
            donasiModels.clear();
            if (filterResults.values != null) {
                donasiModels.addAll((ArrayList<DonasiModel>) filterResults.values);
            }
            notifyDataSetChanged();
        }
    };

    public DonasiAdapter(Context context, ArrayList<DonasiModel> data) {
        this.context = context;
        this.donasiModels = data;
        donasiModelsFull = new ArrayList<>(data);
    }

    @Override
    public DonasiAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //Inflate layout card_lainnya
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_donasi, parent, false);
        return new DonasiAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final DonasiAdapter.ViewHolder holder, final int position) {

        //Instansiasi objek lainnyaModel yang isinya sama dengan lainnyaModels pada index position
        final DonasiModel donasiModel = donasiModels.get(position);

        //Mengisi item dari holder menjadi item dari objek lainnyaModel
        holder.namaKegiatan.setText(donasiModel.getNamaKegiatan());
        holder.totalBiaya.setText("Rp " + String.format("%.0f", donasiModel.getTotalAnggaran()));
        holder.tanggalDonasi.setText(donasiModel.getTanggal_opendonasi());
        if (JENIS_USER.equalsIgnoreCase(JENIS_USER_ALUMNI)) {
            holder.tulisanDonasi.setVisibility(View.VISIBLE);
            holder.tanggalDonasi.setVisibility(View.GONE);
        } else {
            holder.tulisanDonasi.setVisibility(View.GONE);
            holder.tanggalDonasi.setVisibility(View.VISIBLE);
        }

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (JENIS_USER.equals(JENIS_USER_OPERATOR)) {
                    Intent intent = new Intent(context, OpDetailDonasiActivity.class);
                    intent.putExtra("object_donasi", donasiModels.get(position));
                    context.startActivity(intent);
                } else {
                    Intent intent = new Intent(context, DetailDonasiActivity.class);
                    intent.putExtra("object_donasi", donasiModels.get(position));
                    context.startActivity(intent);
                }

            }
        });
    }

    @Override
    public int getItemCount() {

        //Mengembalikan ukuran dari ArrayList chatModels
        return donasiModels.size();
    }

    @Override
    public Filter getFilter() {
        return donasiModelsFilter;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        //Deklarasi TextView namaKegiatan, totalBiaya
        private TextView namaKegiatan, totalBiaya, tanggalDonasi, tulisanDonasi;

        //Deklarasi ConstraintLayout container
        private ConstraintLayout container;

        public ViewHolder(View itemView) {
            super(itemView);
            namaKegiatan = itemView.findViewById(R.id.tv_card_donasi_judul_donasi);
            totalBiaya = itemView.findViewById(R.id.tv_card_donasi_jumlah_donasi);
            tanggalDonasi = itemView.findViewById(R.id.tv_card_donasi_tanggal);
            tulisanDonasi = itemView.findViewById(R.id.tv_card_donasi_tulisan_donasi);
            container = itemView.findViewById(R.id.card_donasi_container);
        }
    }
}