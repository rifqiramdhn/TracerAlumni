package com.example.traceralumni.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.traceralumni.Model.Chat;
import com.example.traceralumni.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class PesanAdapter extends RecyclerView.Adapter<PesanAdapter.ViewHolder> {

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;

    private Context context;
    private ArrayList<Chat> chats;
    private String imageURL;

    private int TYPE_PESAN;

    FirebaseUser firebaseUser;

    public PesanAdapter(Context context, ArrayList<Chat> chats, String imageURL) {
        this.context = context;
        this.chats = chats;
        this.imageURL = imageURL;
    }

    @NonNull
    @Override
    public PesanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TYPE_PESAN = viewType;
        if (viewType == MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(context).inflate(R.layout.card_chat_right, parent, false);
            return new PesanAdapter.ViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.card_chat_left, parent, false);
            return new PesanAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull PesanAdapter.ViewHolder holder, int position) {
        Chat chat = chats.get(position);

        holder.tvPesan.setText(chat.getMessage());

//        if (TYPE_PESAN != 1) {
//            if (!imageURL.equals("")) {
//                Glide.with(context).load(BASE_URL + imageURL).into(holder.imgProfil);
//            }
//        } else {
        if (position == chats.size() - 1) {
            if (chat.isIsseen()) {
                holder.tvIsseen.setVisibility(View.VISIBLE);
                holder.tvIsseen.setText("dilihat");
            } else {
                holder.tvIsseen.setVisibility(View.VISIBLE);
                holder.tvIsseen.setText("terkirim");
            }
        } else {
            holder.tvIsseen.setVisibility(View.GONE);
        }
//        }
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imgProfil;
        TextView tvPesan, tvIsseen;

        public ViewHolder(View v) {
            super(v);

//            imgProfil = v.findViewById(R.id.img_c_chat_profil);
            tvPesan = v.findViewById(R.id.tv_c_chat_pesan);
            tvIsseen = v.findViewById(R.id.tv_c_chat_seen);
        }
    }

    @Override
    public int getItemViewType(int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (chats.get(position).getSender().equals(firebaseUser.getUid())) {
            return MSG_TYPE_RIGHT;
        } else {
            return MSG_TYPE_LEFT;
        }
    }
}