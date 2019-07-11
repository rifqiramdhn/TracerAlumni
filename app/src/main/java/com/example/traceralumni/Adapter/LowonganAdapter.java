package com.example.traceralumni.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.traceralumni.Activity.DetailLowonganActivity;
import com.example.traceralumni.Model.LowonganModel;
import com.example.traceralumni.R;

import java.util.ArrayList;

import static com.example.traceralumni.Activity.MainActivity.JENIS_USER;
import static com.example.traceralumni.Activity.MainActivity.JENIS_USER_ALUMNI;

public class LowonganAdapter extends RecyclerView.Adapter<LowonganAdapter.ListLowonganHolder> implements Filterable {
    Context context;
    private ArrayList<LowonganModel> listLowongan;
    private ArrayList<LowonganModel> listLowonganFull;

    public LowonganAdapter(Context context, ArrayList<LowonganModel> listLowongan){
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
    public void onBindViewHolder(@NonNull final ListLowonganHolder listLowonganHolder, int i) {
        LowonganModel list = listLowongan.get(i);
        listLowonganHolder.txtTitle.setText(list.getNama_lowongan());
        listLowonganHolder.txtPerusahaan.setText(list.getNama_perusahaan());
        listLowonganHolder.txtLokasi.setText(list.getAlamat_perusahaan());
        listLowonganHolder.txtKisaranGaji.setText("~Rp "+list.getKisaran_gaji());
        if (JENIS_USER.equalsIgnoreCase(JENIS_USER_ALUMNI)){
            listLowonganHolder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(), DetailLowonganActivity.class);
                }
            });
        }

        listLowonganHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(listLowonganHolder.container.getContext(), DetailLowonganActivity.class);
                listLowonganHolder.container.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listLowongan.size();
    }

    public class ListLowonganHolder extends RecyclerView.ViewHolder{
        private TextView txtTitle, txtPerusahaan, txtLokasi, txtKisaranGaji;
        private ConstraintLayout container;

        public ListLowonganHolder(View itemView){
            super(itemView);

            txtTitle = itemView.findViewById(R.id.tv_nama_lowongan);
            txtPerusahaan = itemView.findViewById(R.id.tv_nama_perusahaan);
            txtLokasi = itemView.findViewById(R.id.tv_lokasi_perusahaan);
            txtKisaranGaji = itemView.findViewById(R.id.tv_kisaran_gaji);
            container = itemView.findViewById(R.id.card_daftar_lowongan);
        }

    }

    @Override
    public Filter getFilter() {
        return listLowonganFilter;
    }

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
}
