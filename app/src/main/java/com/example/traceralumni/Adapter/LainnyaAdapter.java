package com.example.traceralumni.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.traceralumni.Activity.AboutActivity;
import com.example.traceralumni.Activity.BantuanActivity;
import com.example.traceralumni.Activity.ChangePasswordActivity;
import com.example.traceralumni.Activity.KartuAlumniActivity;
import com.example.traceralumni.Activity.MainActivity;
import com.example.traceralumni.Activity.RiwayatPekerjaanActivity;
import com.example.traceralumni.Activity.StatusPermintaanLowonganActivity;
import com.example.traceralumni.Activity.SuntingProfilActivity;
import com.example.traceralumni.Client;
import com.example.traceralumni.JsonApi;
import com.example.traceralumni.Model.DaftarModel;
import com.example.traceralumni.Model.LainnyaModel;
import com.example.traceralumni.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.traceralumni.Activity.MainActivity.NIM;
import static com.example.traceralumni.Activity.MainActivity.TEXT_NO_INTERNET;

public class LainnyaAdapter extends RecyclerView.Adapter<LainnyaAdapter.ViewHolder> {

    AlertDialog.Builder builder;
    DatabaseReference mDatabase;
    FirebaseAuth mAuth;
    private Context context;
    private ArrayList<LainnyaModel> lainnyaModels;
    private DaftarModel daftarModel;

    public LainnyaAdapter(Context context) {
        this.context = context;
    }

    public LainnyaAdapter(Context context, ArrayList<LainnyaModel> data) {
        this.context = context;
        this.lainnyaModels = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_lainnya, parent, false);
        builder = new AlertDialog.Builder(context);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final LainnyaModel lainnyaModel = lainnyaModels.get(position);
        holder.item.setText(lainnyaModel.getItem());
        holder.icon.setImageResource(lainnyaModel.getIconResId());
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (position) {
                    case 0:
                        Toast.makeText(context, lainnyaModel.getItem(), Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        getData(1);
                        break;
//                    case 2:
//                        Intent donasi = new Intent(context, DonasiActivity.class);
//                        context.startActivity(donasi);
//                        break;
                    case 2:
                        String url = "https://sinatra.ub.ac.id/";
                        Intent tracerStudi = new Intent(Intent.ACTION_VIEW);
                        tracerStudi.setData(Uri.parse(url));
                        context.startActivity(tracerStudi);
                        break;
                    case 3:
                        getData(3);
                        break;
                    case 4:
                        Intent riwayat = new Intent(context, RiwayatPekerjaanActivity.class);
                        context.startActivity(riwayat);
                        break;
//                    case 5:
//                        Intent statusDonasi = new Intent(context, StatusPermintaanDonasiActivity.class);
//                        context.startActivity(statusDonasi);
//                        break;
                    case 5:
                        Intent statusLowongan = new Intent(context, StatusPermintaanLowonganActivity.class);
                        context.startActivity(statusLowongan);
                        break;
                    case 6:
                        Intent gantiPassword = new Intent(context, ChangePasswordActivity.class);
                        context.startActivity(gantiPassword);
                        break;
                    case 7:
                        showHapusSemuaChatDialog();
                        break;
                    case 8:
                        Intent tentang = new Intent(context, AboutActivity.class);
                        context.startActivity(tentang);
                        break;
                    case 9:
                        Intent bantuan = new Intent(context, BantuanActivity.class);
                        context.startActivity(bantuan);
                        break;
                    case 10:
                        MainActivity.showKeluarDialog(context);
                        break;
                }
            }
        });

        if (lainnyaModel.getItem().equalsIgnoreCase("Keluar")) {
            holder.item.setTextColor(Color.RED);
        } else {
            holder.item.setTextColor(Color.BLACK);
        }
    }

    @Override
    public int getItemCount() {
        return lainnyaModels.size();
    }

    private void showHapusSemuaChatDialog() {
        builder.setMessage("Apakah anda yakin ingin menghapus semua chat?");
        builder.setTitle("Hapus Semua Chat");
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                hapusChat();
            }
        });
        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void getData(final int index) {
        JsonApi jsonApi = Client.getClient().create(JsonApi.class);

        Call<DaftarModel> call = jsonApi.getUserData(NIM);
        call.enqueue(new Callback<DaftarModel>() {
            @Override
            public void onResponse(Call<DaftarModel> call, Response<DaftarModel> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                daftarModel = response.body();
                Intent intent = null;
                switch (index) {
                    case 1:
                        intent = new Intent(context, KartuAlumniActivity.class);
                        intent.putExtra("daftarModel", daftarModel);
                        break;
                    case 3:
                        intent = new Intent(context, SuntingProfilActivity.class);
                        intent.putExtra("daftarModel", daftarModel);
                        break;
                }
                context.startActivity(intent);
            }

            @Override
            public void onFailure(Call<DaftarModel> call, Throwable t) {
                if (t.getMessage().contains("Failed to connect")) {
                    Toast.makeText(context, TEXT_NO_INTERNET, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void hapusChat() {
        DatabaseReference mDatabaseChat = mDatabase.child("Chatlist");
        final String myId = mAuth.getCurrentUser().getUid();

        mDatabaseChat.child(myId).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(context, "Semua chat telah dihapus", Toast.LENGTH_SHORT).show();
                }
//                else {
//                    Toast.makeText(context, "Chat gagal dihapus", Toast.LENGTH_SHORT).show();
//                }
            }
        });

        final DatabaseReference mDatabaseChat2 = mDatabase.child("Chats");
        mDatabaseChat2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.child("receiver").getValue().toString().equals(myId)) {
                        DatabaseReference mDatabaseChat3 = mDatabase.child("Chats")
                                .child(snapshot.getKey())
                                .child("hideforreceiver");
                        mDatabaseChat3.setValue(true);
                    } else if (snapshot.child("sender").getValue().toString().equals(myId)) {
                        DatabaseReference mDatabaseChat4 = mDatabase.child("Chats")
                                .child(snapshot.getKey())
                                .child("hideforsender");
                        mDatabaseChat4.setValue(true);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView item;
        private ImageView icon;
        private ConstraintLayout container;

        public ViewHolder(View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.card_pengaturan_nama_item_text_view);
            icon = itemView.findViewById(R.id.card_pengaturan_icon_image_view);
            container = itemView.findViewById(R.id.card_pengaturan_container);
        }
    }
}