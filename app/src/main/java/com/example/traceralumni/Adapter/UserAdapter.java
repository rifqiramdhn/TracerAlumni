package com.example.traceralumni.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.traceralumni.Activity.PesanActivity;
import com.example.traceralumni.Model.Chat;
import com.example.traceralumni.Model.UserModel;
import com.example.traceralumni.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.traceralumni.Activity.MainActivity.BASE_URL;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private Context context;
    private ArrayList<UserModel> userModels;
    private boolean isChat;

    String theLastMessage;

    public UserAdapter(Context context, ArrayList<UserModel> userModels, boolean isChat) {
        this.context = context;
        this.userModels = userModels;
        this.isChat = isChat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_user, parent, false);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final UserModel userModel = userModels.get(position);
        holder.tvUsername.setText(userModel.getUsername());
        if (userModel.getImageUrl().equals("")) {
            holder.imgProfil.setImageResource(R.mipmap.ic_launcher);
        } else {
            Glide.with(context).load(BASE_URL + userModel.getImageUrl()).into(holder.imgProfil);
        }

        if (isChat) {
            lastMessage(userModel.getId(), holder.tvLastMessage);
        }

        holder.clContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PesanActivity.class);
                intent.putExtra("userId", userModel.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imgProfil;
        ConstraintLayout clContainer;
        TextView tvUsername, tvLastMessage;

        public ViewHolder(View v) {
            super(v);

            imgProfil = v.findViewById(R.id.img_c_user_profil);
            tvUsername = v.findViewById(R.id.tv_c_user_username);
            tvLastMessage = v.findViewById(R.id.tv_c_user_last_message);
            clContainer = v.findViewById(R.id.cl_card_user);
        }
    }

    private void lastMessage(final String userId, final TextView tvLastMessage) {
        theLastMessage = "default";
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Chats");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Chat chat = snapshot.getValue(Chat.class);
                    if (firebaseUser != null) {
                        if (chat.getReceiver().equals(firebaseUser.getUid()) && chat.getSender().equals(userId) ||
                                chat.getReceiver().equals(userId) && chat.getSender().equals(firebaseUser.getUid())) {
                            theLastMessage = chat.getMessage();
                        }
                    }
                }

                if (!theLastMessage.equals("default")) {
                    tvLastMessage.setVisibility(View.VISIBLE);
                    tvLastMessage.setText(theLastMessage);
                }

                theLastMessage = "default";
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}