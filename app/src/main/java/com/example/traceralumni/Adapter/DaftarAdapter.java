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

import com.example.traceralumni.Activity.DetailProfilActivity;
import com.example.traceralumni.Model.DaftarModel;
import com.example.traceralumni.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.traceralumni.Fragment.DaftarFragment.TEXT_SEARCH_DAFTAR_USE_ANGKATAN;
import static com.example.traceralumni.Fragment.DaftarFragment.TEXT_SEARCH_DAFTAR_USE_NAMA;
import static com.example.traceralumni.Fragment.DaftarFragment.SPINNER_SEARCH_DAFTAR_USE_PRODI;

public class DaftarAdapter extends RecyclerView.Adapter<DaftarAdapter.ViewHolder> implements Filterable {

    private Context context;
    private ArrayList<DaftarModel> daftarModels;
    private ArrayList<DaftarModel> daftarModelsFull;

    public DaftarAdapter(Context context, ArrayList<DaftarModel> data) {
        this.context = context;
        this.daftarModels = data;
        daftarModelsFull = new ArrayList<>(data);
    }

    @Override
    public DaftarAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //Inflate layout daftar
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_daftar, parent, false);
        return new DaftarAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final DaftarAdapter.ViewHolder holder, final int position) {

        final DaftarModel daftarModel = daftarModels.get(position);

        holder.nama.setText(daftarModel.getNama());

        holder.prodi.setText(daftarModel.getProdi());

        holder.angkatan.setText(daftarModel.getAngkatan());

        holder.foto.setImageResource(R.color.colorIconBiru);

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, DetailProfilActivity.class);
                i.putExtra("daftarModel", daftarModels.get(position));
                context.startActivity(i);
            }
        });

    }


    @Override
    public int getItemCount() {

        //Mengembalikan ukuran dari ArrayList daftarModels
        return daftarModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        //Deklarasi TextView nama, prodi, dan angkatan
        private TextView nama, prodi, angkatan;

        //Deklarasi ImageView foto
        private CircleImageView foto;

        //Deklarasi ConstraintLayout container
        private ConstraintLayout container;

        public ViewHolder(View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.tv_card_daftar_nama);
            prodi = itemView.findViewById(R.id.tv_card_daftar_prodi);
            angkatan = itemView.findViewById(R.id.tv_card_daftar_angkatan);
            foto = itemView.findViewById(R.id.img_card_daftar);
            container = itemView.findViewById(R.id.card_daftar_container);
        }
    }

    @Override
    public Filter getFilter() {
        return daftarModelsFilterNamaTahun;
    }

    private Filter daftarModelsFilterNamaTahun = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<DaftarModel> filteredList = new ArrayList<>();
            String filterAngkatan = TEXT_SEARCH_DAFTAR_USE_ANGKATAN;
            String filterNama = TEXT_SEARCH_DAFTAR_USE_NAMA;
            String filterProdi = SPINNER_SEARCH_DAFTAR_USE_PRODI;
            if ((TEXT_SEARCH_DAFTAR_USE_NAMA == null
                    || TEXT_SEARCH_DAFTAR_USE_NAMA.length() == 0)
                    && (TEXT_SEARCH_DAFTAR_USE_ANGKATAN == null
                    || TEXT_SEARCH_DAFTAR_USE_ANGKATAN.length() == 0)
                    && (SPINNER_SEARCH_DAFTAR_USE_PRODI.equalsIgnoreCase("prodi")
                    || SPINNER_SEARCH_DAFTAR_USE_PRODI.equalsIgnoreCase(""))) {
                filteredList.addAll(daftarModelsFull);
            } else if ((SPINNER_SEARCH_DAFTAR_USE_PRODI.equalsIgnoreCase("prodi")
                    || SPINNER_SEARCH_DAFTAR_USE_PRODI.equalsIgnoreCase(""))) {
                for (DaftarModel item : daftarModelsFull) {
                    if (item.getAngkatan().toLowerCase().contains(filterAngkatan)
                            && item.getNama().toLowerCase().contains(filterNama)) {
                        filteredList.add(item);
                    }
                }
            } else {
                for (DaftarModel item : daftarModelsFull) {
                    if (item.getAngkatan().toLowerCase().contains(filterAngkatan)
                            && item.getNama().toLowerCase().contains(filterNama)
                            && item.getProdi().equalsIgnoreCase(filterProdi)) {
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
            daftarModels.clear();
            if (filterResults.values != null) {
                daftarModels.addAll((ArrayList<DaftarModel>) filterResults.values);
            }
            notifyDataSetChanged();
        }
    };

}