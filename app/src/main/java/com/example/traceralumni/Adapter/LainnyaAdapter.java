package com.example.traceralumni.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
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
import com.example.traceralumni.Activity.ChangePasswordActivity;
import com.example.traceralumni.Activity.DonasiActivity;
import com.example.traceralumni.Activity.KartuAlumniActivity;
import com.example.traceralumni.Activity.LoginActivity;
import com.example.traceralumni.Activity.RiwayatPekerjaanActivity;
import com.example.traceralumni.Activity.SuntingProfilActivity;
import com.example.traceralumni.JsonPlaceHolderApi;
import com.example.traceralumni.Model.DonasiModel;
import com.example.traceralumni.Model.LainnyaModel;
import com.example.traceralumni.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LainnyaAdapter extends RecyclerView.Adapter<LainnyaAdapter.ViewHolder> {

    private Context context;
    private ArrayList<LainnyaModel> lainnyaModels;
    AlertDialog.Builder builder;

    public LainnyaAdapter(Context context, ArrayList<LainnyaModel> data) {
        this.context = context;
        this.lainnyaModels = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //Inflate layout card_lainnya
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_lainnya, parent, false);
        builder = new AlertDialog.Builder(context);
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
                    case 0:
                        Intent kartu = new Intent(context, KartuAlumniActivity.class);
                        context.startActivity(kartu);
                        break;
                    case 1:
                        Intent donasi = new Intent(context, DonasiActivity.class);
                        context.startActivity(donasi);
                        break;
                    case 3:
                        String url = "http://www.google.com";
                        Intent tracerStudi = new Intent(Intent.ACTION_VIEW);
                        tracerStudi.setData(Uri.parse(url));
                        context.startActivity(tracerStudi);
                        break;
                    case 4:
                        Intent suntingProfil = new Intent(context, SuntingProfilActivity.class);
                        context.startActivity(suntingProfil);
                        break;
                    case 5:
                        Intent riwayat = new Intent(context, RiwayatPekerjaanActivity.class);
                        context.startActivity(riwayat);
                        break;
                    case 6:
                        Intent gantiPass = new Intent(context, ChangePasswordActivity.class);
                        context.startActivity(gantiPass);
                        break;
                    case 7:
                        showHapusSemuaChatDialog();
                        break;
                    case 8:
                        Intent tentang = new Intent(context, AboutActivity.class);
                        context.startActivity(tentang);
                        break;
                    case 9:
                        showKeluarDialog();
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

    private void showKeluarDialog() {

        builder.setMessage("Apakah anda yakin ingin keluar?");

        builder.setTitle("Keluar");

        builder.setCancelable(false);

        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent(context, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
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

    private void showHapusSemuaChatDialog() {
        builder.setMessage("Apakah anda yakin ingin menghapus semua chat?");

        builder.setTitle("Hapus Semua Chat");

        builder.setCancelable(false);

        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                Toast.makeText(context, "Semua chat telah dihapus", Toast.LENGTH_SHORT).show();
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

}