package com.example.traceralumni.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.traceralumni.Activity.DonasiActivity;
import com.example.traceralumni.Model.LainnyaModel;
import com.example.traceralumni.R;

import java.util.ArrayList;

public class LainnyaAdapter extends RecyclerView.Adapter<LainnyaAdapter.ViewHolder> {

    private Context context;
    private ArrayList<LainnyaModel> lainnyaModels;

    public LainnyaAdapter(Context context, ArrayList<LainnyaModel> data) {
        this.context = context;
        this.lainnyaModels = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //Inflate layout card_lainnya
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_lainnya, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        //Instansiasi objek lainnyaModel yang isinya sama dengan lainnyaModels pada index position
        final LainnyaModel lainnyaModel = lainnyaModels.get(position);

        //Mengisi item dari holder menjadi item dari objek lainnyaModel
        holder.item.setText(lainnyaModel.getItem());

        holder.icon.setImageResource(lainnyaModel.getIconResId());

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (position) {
                    case 1:
                        Intent intent = new Intent(holder.container.getContext(), DonasiActivity.class);
                        holder.container.getContext().startActivity(intent);
                        break;
                    default:
                        Toast.makeText(context, lainnyaModel.getItem(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (lainnyaModel.getItem().equalsIgnoreCase("Keluar")) {
            holder.item.setTextColor(Color.RED);
        }
    }

    @Override
    public int getItemCount() {

        //Mengembalikan ukuran dari ArrayList lainnyaModels
        return lainnyaModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        //Deklarasi TextView item
        private TextView item;

        private ImageView icon;

        //Deklarasi ConstraintLayout container
        private ConstraintLayout container;

        public ViewHolder(View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.card_pengaturan_nama_item_text_view);
            icon = itemView.findViewById(R.id.card_pengaturan_icon_image_view);
            container = itemView.findViewById(R.id.card_pengaturan_container);
        }
    }
}