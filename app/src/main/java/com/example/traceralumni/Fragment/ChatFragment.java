package com.example.traceralumni.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.traceralumni.Adapter.ChatAdapter;
import com.example.traceralumni.Model.ChatModel;
import com.example.traceralumni.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment {

    RecyclerView chatRecycler;
    ChatAdapter chatAdapter;
    ArrayList<ChatModel> chatModels;

    View rootView;

    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_chat, container, false);

        chatModels = new ArrayList<>();
        chatModels.add(new ChatModel("Budi Fauzan", "Halo"));
        chatModels.add(new ChatModel("Rizaldy Firman T.", "Apasi"));
        chatModels.add(new ChatModel("Rifqi Imam", "WTF"));
        chatModels.add(new ChatModel("Budi Fauzan", "Halo"));
        chatModels.add(new ChatModel("Rizaldy Firman T.", "Apasi"));
        chatModels.add(new ChatModel("Rifqi Imam", "WTF"));
        chatModels.add(new ChatModel("Budi Fauzan", "Halo"));
        chatModels.add(new ChatModel("Rizaldy Firman T.", "Apasi"));
        chatModels.add(new ChatModel("Rifqi Imam", "WTF"));
        chatModels.add(new ChatModel("Budi Fauzan", "Halo"));
        chatModels.add(new ChatModel("Rizaldy Firman T.", "Apasi"));
        chatModels.add(new ChatModel("Rifqi Imam", "WTF"));

        chatRecycler = rootView.findViewById(R.id.rv_fragment_chat);

        //Mengatur LayoutManager dari Recycler daftar
        chatRecycler.setLayoutManager(new LinearLayoutManager(rootView.getContext(), LinearLayoutManager.VERTICAL, false));

        chatAdapter = new ChatAdapter(rootView.getContext(), chatModels);
        chatRecycler.setAdapter(chatAdapter);

        return rootView;
    }

}
