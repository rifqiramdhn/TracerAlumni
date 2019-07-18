package com.example.traceralumni.Adapter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

import com.example.traceralumni.Activity.MainActivity;
import com.example.traceralumni.Activity.OpDetailInfoActivity;
import com.example.traceralumni.Model.InfoModel;
import com.example.traceralumni.R;

import java.util.ArrayList;

import static com.example.traceralumni.Activity.MainActivity.JENIS_USER;
import static com.example.traceralumni.Activity.MainActivity.JENIS_USER_ALUMNI;
import static com.example.traceralumni.Activity.MainActivity.JENIS_USER_OPERATOR;

public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.ViewHolder> implements Filterable {

    private Context context;
    private ArrayList<InfoModel> infoModels;
    private ArrayList<InfoModel> infoModelsFull;

    public InfoAdapter(Context context, ArrayList<InfoModel> data) {
        this.context = context;
        this.infoModels = data;
        infoModelsFull = new ArrayList<>(data);
    }

    @Override
    public InfoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //Inflate layout card_lainnya
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_info, parent, false);
        return new InfoAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final InfoAdapter.ViewHolder holder, final int position) {
        //Instansiasi objek lainnyaModel yang isinya sama dengan lainnyaModels pada index position
        final InfoModel infoModel = infoModels.get(position);
        holder.judul.setText(infoModel.getJudul());
        holder.isi.setText(infoModel.getKeterangan());
        holder.tanggal.setText(infoModel.getTanggal_info());

        Log.e("aldy", "jalan please " + infoModel.getJudul());

        if (JENIS_USER.equalsIgnoreCase(JENIS_USER_OPERATOR)) {
            holder.cl_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(), OpDetailInfoActivity.class);
                    i.putExtra("object_info", infoModels.get(position));
                    context.startActivity(i);
                }
            });
        } else {
            holder.cl_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = infoModels.get(position).getLink();
                    try {
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        context.startActivity(i);
                    } catch (ActivityNotFoundException e){
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        String urlNew = "http://" + url;
                        i.setData(Uri.parse(urlNew));
                        context.startActivity(i);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {

        //Mengembalikan ukuran dari ArrayList infoModels
        return infoModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        //Deklarasi TextView nama, chat, waktu
        private TextView judul, isi, tanggal;

        private ConstraintLayout cl_card;

        private ImageView iv_link;


        public ViewHolder(View itemView) {
            super(itemView);
            judul = itemView.findViewById(R.id.tv_card_info_judul);
            isi = itemView.findViewById(R.id.tv_card_info_isi);
            tanggal = itemView.findViewById(R.id.tv_card_info_tanggal);
            iv_link = itemView.findViewById(R.id.img_card_info_link);
            cl_card = itemView.findViewById(R.id.cl_card_info);

            if (JENIS_USER.equalsIgnoreCase(JENIS_USER_ALUMNI)) {
                iv_link.setVisibility(View.VISIBLE);
                tanggal.setVisibility(View.GONE);
            } else {
                iv_link.setVisibility(View.GONE);
                tanggal.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public Filter getFilter() {
        return infoModelsFilter;
    }

    private Filter infoModelsFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<InfoModel> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(infoModelsFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (InfoModel item : infoModelsFull) {
                    if (item.getJudul().toLowerCase().contains(filterPattern)) {
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
            infoModels.clear();
            if (filterResults.values != null) {
                infoModels.addAll((ArrayList<InfoModel>) filterResults.values);
            }
            notifyDataSetChanged();
        }
    };
}