package com.example.traceralumni.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.traceralumni.Activity.DetailDonasiActivity;
import com.example.traceralumni.Activity.MainActivity;
import com.example.traceralumni.Model.ChatModel;
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
        holder.totalBiaya.setText("Rp" + donasiModel.getTotalBiaya());
//        holder.tanggalDonasi.setText(donasiModel.getTanggal().toString());
        if (JENIS_USER.equalsIgnoreCase(JENIS_USER_ALUMNI)) {
            holder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), DetailDonasiActivity.class);
                    intent.putExtra("namaKegiatan", donasiModel.getNamaKegiatan());
                    intent.putExtra("totalBiaya", donasiModel.getTotalBiaya());
                    intent.putExtra("keterangan", donasiModel.getKeterangan());
                    intent.putExtra("fotoResId", String.valueOf(donasiModel.getFotoResId()));
                    v.getContext().startActivity(intent);
                }
            });
        } else if (JENIS_USER.equalsIgnoreCase(JENIS_USER_OPERATOR)) {
            holder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Detail donasi operator", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            holder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Detail donasi pimpinan", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {

        //Mengembalikan ukuran dari ArrayList chatModels
        return donasiModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        //Deklarasi TextView namaKegiatan, totalBiaya
        private TextView namaKegiatan, totalBiaya, tanggalDonasi, tulisanDonasi;

        //Deklarasi ConstraintLayout container
        private ConstraintLayout container;

        public ViewHolder(View itemView) {
            super(itemView);
            namaKegiatan = itemView.findViewById(R.id.card_donasi_judul_donasi_text_view);
            totalBiaya = itemView.findViewById(R.id.card_donasi_jumlah_donasi_text_view);
            tanggalDonasi = itemView.findViewById(R.id.tv_card_donasi_tanggal);
            tulisanDonasi = itemView.findViewById(R.id.tv_card_donasi_tulisan_donasi);
            container = itemView.findViewById(R.id.card_donasi_container);

            if (JENIS_USER.equalsIgnoreCase(JENIS_USER_ALUMNI)) {
                tulisanDonasi.setVisibility(View.VISIBLE);
                tanggalDonasi.setVisibility(View.INVISIBLE);
            } else {
                tulisanDonasi.setVisibility(View.INVISIBLE);
                tanggalDonasi.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public Filter getFilter() {
        return donasiModelsFilter;
    }

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
}