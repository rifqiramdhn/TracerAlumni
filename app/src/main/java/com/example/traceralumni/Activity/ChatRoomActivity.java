package com.example.traceralumni.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.traceralumni.Adapter.ChatRoomAdapter;
import com.example.traceralumni.R;

public class ChatRoomActivity extends AppCompatActivity {
    ConstraintLayout cl_back, cl_avatar;
    ImageView img_back;
    TextView tvNavBar;

    RecyclerView recyclerView;
    ChatRoomAdapter chatRoomAdapter;
    ImageButton imgSend;
    EditText edtChat;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        initView();
    }

    private void initView(){
        cl_back = findViewById(R.id.cl_icon1);
        cl_avatar = findViewById(R.id.cl_icon4);
        img_back = findViewById(R.id.img_icon1);
        tvNavBar = findViewById(R.id.tv_navbar_top);
        recyclerView = findViewById(R.id.rv_chat);
        imgSend = findViewById(R.id.img_send);
        edtChat = findViewById(R.id.edt_chat);

        tvNavBar.setText("Budi Fauzan");

        imgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kirimPesan();
            }
        });
    }

    private void kirimPesan(){
        String pesan = edtChat.getText().toString().trim();
        edtChat.setText("");

    }
}
