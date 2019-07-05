package com.example.traceralumni.Adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.traceralumni.Model.ChatModel;
import com.example.traceralumni.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> implements Filterable {

    private Context context;
    private ArrayList<ChatModel> chatModels;
    private ArrayList<ChatModel> chatModelsFull;

    public ChatAdapter(Context context, ArrayList<ChatModel> data) {
        this.context = context;
        this.chatModels = data;
        chatModelsFull = new ArrayList<>(data);
    }

    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //Inflate layout card_lainnya
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_chat, parent, false);
        return new ChatAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ChatAdapter.ViewHolder holder, final int position) {

        //Instansiasi objek lainnyaModel yang isinya sama dengan lainnyaModels pada index position
        final ChatModel chatModel = chatModels.get(position);

        //Mengisi item dari holder menjadi item dari objek lainnyaModel
        holder.nama.setText(chatModel.getNama());
        holder.chat.setText(chatModel.getChat());
//        holder.waktu.setText(chatModel.getWaktu().toString());
//        holder.foto.setImageResource(chatModel.getFotoResId());
        holder.foto.setImageResource(R.color.colorIconBiru);

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, chatModel.getNama(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {

        //Mengembalikan ukuran dari ArrayList chatModels
        return chatModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        //Deklarasi TextView nama, chat, waktu
        private TextView nama, chat, waktu;

        private CircleImageView foto;

        //Deklarasi ConstraintLayout container
        private ConstraintLayout container;

        public ViewHolder(View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.card_chat_nama_text_view);
            chat = itemView.findViewById(R.id.card_chat_chat_text_view);
            waktu = itemView.findViewById(R.id.card_chat_waktu_text_view);
            foto = itemView.findViewById(R.id.iv_card_chat_foto);
            container = itemView.findViewById(R.id.card_chat_container);
        }
    }

    @Override
    public Filter getFilter() {
        return chatModelsFilter;
    }

    private Filter chatModelsFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<ChatModel> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0){
                filteredList.addAll(chatModelsFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (ChatModel item : chatModelsFull){
                    if (item.getNama().toLowerCase().contains(filterPattern)){
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
            chatModels.clear();
            chatModels.addAll((ArrayList<ChatModel>) filterResults.values);
            notifyDataSetChanged();
        }
    };
}