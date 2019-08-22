package com.example.traceralumni.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.traceralumni.APIService;
import com.example.traceralumni.Adapter.PesanAdapter;
import com.example.traceralumni.Model.Chat;
import com.example.traceralumni.Model.UserModel;
import com.example.traceralumni.Notification.Data;
import com.example.traceralumni.Notification.MyResponse;
import com.example.traceralumni.Notification.Sender;
import com.example.traceralumni.Notification.Token;
import com.example.traceralumni.R;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.traceralumni.Activity.MainActivity.INDEX_OPENED_TAB_KEY;
import static com.example.traceralumni.Activity.MainActivity.SHARE_PREFS;

public class PesanActivity extends AppCompatActivity {
    TextView tvNavbar;
    ConstraintLayout clBack, clProfil, clKirim;
    ImageView imgBack, imgProfil;

    FirebaseUser firebaseUser;
    DatabaseReference reference;

    Intent intent;
    EditText edtIsiPesan;

    PesanAdapter pesanAdapter;
    ArrayList<Chat> chats;
    RecyclerView recyclerView;

    ValueEventListener seenListener;

    String userId;
    String myid;

    APIService apiService;
    boolean notify = false;

    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesan);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        initView();

//        apiService = Client.getClientForMessage().create(APIService.class);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://fcm.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(APIService.class);

        recyclerView = findViewById(R.id.rv_pesan);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager lm = new LinearLayoutManager(getApplicationContext());
        lm.setStackFromEnd(true);
        recyclerView.setLayoutManager(lm);

        intent = getIntent();
        userId = intent.getStringExtra("userId");

        if (userId != null) {
            firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            myid = firebaseUser.getUid();
            if (firebaseUser == null) {
                startActivity(new Intent(PesanActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
            } else {
                reference = FirebaseDatabase.getInstance().getReference("Users").child(userId);

                clKirim.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        notify = true;
                        String message = edtIsiPesan.getText().toString().trim();
                        if (edtIsiPesan.getText().toString().equals("")) {
                            Toast.makeText(PesanActivity.this, "Can't send empty message", Toast.LENGTH_SHORT).show();
                        } else {
                            sendMessageToFirebase(firebaseUser.getUid(), userId, message);
                        }
                        edtIsiPesan.setText("");
                    }
                });

                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        UserModel userModel = dataSnapshot.getValue(UserModel.class);
                        if (userModel != null){
                            tvNavbar.setAllCaps(true);
                            tvNavbar.setText(userModel.getUsername());
                            readMessage(firebaseUser.getUid(), userModel.getId(), userModel.getImageUrl());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                seenMessage(userId);
            }
        }
    }

    private void seenMessage(final String userId) {
        reference = FirebaseDatabase.getInstance().getReference("Chats");
        seenListener = reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Chat chat = snapshot.getValue(Chat.class);
                    if (chat.getReceiver().equals(firebaseUser.getUid()) && chat.getSender().equals(userId)) {
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("isseen", true);
                        snapshot.getRef().updateChildren(hashMap);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void sendMessageToFirebase(String sender, final String receiver, String message) {
        Bundle params = new Bundle();
        params.putString("sender", sender);
        params.putString("receiver", receiver);
        mFirebaseAnalytics.logEvent("chatSent", params);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);
        hashMap.put("isseen", false);
        hashMap.put("hideforsender", false);
        hashMap.put("hideforreceiver", false);

        reference.child("Chats").push().setValue(hashMap);

        final DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference("Chatlist")
                .child(firebaseUser.getUid())
                .child(userId);

        chatRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    chatRef.child("id").setValue(userId);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final DatabaseReference chatRef2 = FirebaseDatabase.getInstance().getReference("Chatlist")
                .child(receiver)
                .child(firebaseUser.getUid());

        chatRef2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    chatRef2.child("id").setValue(firebaseUser.getUid());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final String msg = message;

        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserModel userModel = dataSnapshot.getValue(UserModel.class);
                if (notify)
                    sendNotification(receiver, userModel.getUsername(), msg);
                notify = false;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void sendNotification(String receiver, final String username, final String message) {
        DatabaseReference tokens = FirebaseDatabase.getInstance().getReference("Tokens");
        Query query = tokens.orderByKey().equalTo(receiver);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Token token = snapshot.getValue(Token.class);
                    Data data = new Data(firebaseUser.getUid(), R.mipmap.ic_launcher, username + ": " + message, "New Message", userId);

                    Sender sender = new Sender(data, token.getToken());
                    apiService.sendNotification(sender)
                            .enqueue(new Callback<MyResponse>() {
                                @Override
                                public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                                    if (!response.isSuccessful()) {
                                        Toast.makeText(PesanActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }

                                @Override
                                public void onFailure(Call<MyResponse> call, Throwable t) {

                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void readMessage(final String myId, final String userId, final String imageUrl) {
        chats = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chats.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Chat chat = snapshot.getValue(Chat.class);
                    if ((chat.getReceiver().equals(myId) && chat.getSender().equals(userId) && !chat.isHideforreceiver()) ||
                            (chat.getReceiver().equals(userId) && chat.getSender().equals(myId) && !chat.isHideforsender())) {
                        chats.add(chat);
                    }
                }

                pesanAdapter = new PesanAdapter(PesanActivity.this, chats, imageUrl);
                recyclerView.setAdapter(pesanAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void currentUser(String userId) {
        SharedPreferences.Editor editor = getSharedPreferences(SHARE_PREFS, MODE_PRIVATE).edit();
        editor.putString("currentuser", userId);
        editor.apply();
    }

    private void moveToDetailProfil() {
        String nim = intent.getStringExtra("nim");
        Intent intent = new Intent(PesanActivity.this, DetailProfilActivity.class);
        intent.putExtra("nim", nim);
        intent.putExtra("dariPesanActivity", true);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        currentUser(userId);
    }

    @Override
    protected void onPause() {
        super.onPause();
        reference.removeEventListener(seenListener);
        currentUser("none");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(PesanActivity.this, MainActivity.class);
        intent.putExtra(INDEX_OPENED_TAB_KEY, 1);
        startActivity(intent);
        finish();
    }

    private void initView() {
        tvNavbar = findViewById(R.id.tv_navbar_top);
        clBack = findViewById(R.id.cl_icon1);
        clBack.setVisibility(View.VISIBLE);
        imgBack = findViewById(R.id.img_icon1);
        imgBack.setImageResource(R.drawable.ic_arrow_back);
        clProfil = findViewById(R.id.cl_icon4);
        clProfil.setVisibility(View.VISIBLE);
        imgProfil = findViewById(R.id.img_icon4);
        imgProfil.setImageResource(R.drawable.ic_person);
        clKirim = findViewById(R.id.cl_btn_kirim_pesan);
        edtIsiPesan = findViewById(R.id.edt_isi_pesan);

        clBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        clProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToDetailProfil();
            }
        });
    }
}
