package com.example.traceralumni.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.traceralumni.R;

import java.util.ArrayList;

public class ChatRoomAdapter extends RecyclerView.Adapter<ChatRoomAdapter.ViewHolder> {
    private Context context;
    private ArrayList<ChatRoomAdapter> chat;
    private ArrayList<ChatRoomAdapter> sender;

    public ChatRoomAdapter(ArrayList<ChatRoomAdapter> chat, ArrayList<ChatRoomAdapter> sender){
        this.chat = chat;
        this.sender = sender;
    }
    @NonNull
    @Override
    public ChatRoomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatRoomAdapter.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View v){
            super(v);

        }
    }
}
