package com.example.traceralumni.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.traceralumni.Activity.BantuanActivity;
import com.example.traceralumni.Activity.ChangePasswordActivity;
import com.example.traceralumni.Activity.DonasiActivity;
import com.example.traceralumni.Activity.KartuAlumniActivity;
import com.example.traceralumni.Activity.LoginActivity;
import com.example.traceralumni.Activity.MainActivity;
import com.example.traceralumni.Activity.RiwayatPekerjaanActivity;
import com.example.traceralumni.Activity.SuntingProfilActivity;
import com.example.traceralumni.JsonPlaceHolderApi;
import com.example.traceralumni.Model.DaftarModel;
import com.example.traceralumni.Model.LainnyaModel;
import com.example.traceralumni.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;
import static com.example.traceralumni.Activity.MainActivity.BASE_URL;
import static com.example.traceralumni.Activity.MainActivity.NIM;
import static com.example.traceralumni.Activity.MainActivity.SHARE_PREFS;
import static com.example.traceralumni.Activity.MainActivity.STATE_USER_LOGGED_PREF;
import static com.example.traceralumni.Activity.MainActivity.TEXT_NO_INTERNET;

public class LainnyaAdapter extends RecyclerView.Adapter<LainnyaAdapter.ViewHolder> {

    private Context context;
    private ArrayList<LainnyaModel> lainnyaModels;
    private DaftarModel daftarModel;
    AlertDialog.Builder builder;

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
                    case 2:
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
                        getData(4);
                        break;
                    case 5:
                        Intent riwayat = new Intent(context, RiwayatPekerjaanActivity.class);
                        context.startActivity(riwayat);
                        break;
                    case 6:
                        Intent permintaanDonasi = new Intent(context, RiwayatPekerjaanActivity.class);
                        context.startActivity(permintaanDonasi);
                        break;
                    case 7:
                        Intent permintaanLowongan = new Intent(context, RiwayatPekerjaanActivity.class);
                        context.startActivity(permintaanLowongan);
                        break;
                    case 8:
                        Intent gantiPass = new Intent(context, ChangePasswordActivity.class);
                        context.startActivity(gantiPass);
                        break;
                    case 9:
                        showHapusSemuaChatDialog();
                        break;
                    case 10:
                        Intent tentang = new Intent(context, AboutActivity.class);
                        context.startActivity(tentang);
                        break;
                    case 11:
                        Intent bantuan = new Intent(context, BantuanActivity.class);
                        context.startActivity(bantuan);
                        break;
                    case 12:
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

    public void getData(final int index) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<DaftarModel> call = jsonPlaceHolderApi.getUserData(NIM);
        call.enqueue(new Callback<DaftarModel>() {
            @Override
            public void onResponse(Call<DaftarModel> call, Response<DaftarModel> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                daftarModel = response.body();
                Intent intent;
                if (index == 1) {
                    intent = new Intent(context, KartuAlumniActivity.class);
                } else {
                    intent = new Intent(context, SuntingProfilActivity.class);
                }
                intent.putExtra("daftarModel", daftarModel);
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
}