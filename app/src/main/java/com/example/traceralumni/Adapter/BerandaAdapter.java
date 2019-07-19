package com.example.traceralumni.Adapter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.traceralumni.Activity.DetailDonasiActivity;
import com.example.traceralumni.Activity.DetailLowonganActivity;
import com.example.traceralumni.Model.BerandaModel;
import com.example.traceralumni.Model.DonasiModel;
import com.example.traceralumni.Model.InfoModel;
import com.example.traceralumni.Model.LowonganModel;
import com.example.traceralumni.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.traceralumni.Activity.MainActivity.BASE_URL;
import static com.example.traceralumni.Activity.MainActivity.JENIS_USER;
import static com.example.traceralumni.Activity.MainActivity.JENIS_USER_ALUMNI;

public class BerandaAdapter extends RecyclerView.Adapter<BerandaAdapter.ViewHolder> {

    private Context context;
    private ArrayList<BerandaModel> berandaModels;

    public BerandaAdapter(Context context, ArrayList<BerandaModel> data) {
        this.context = context;
        this.berandaModels = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.card_beranda, viewGroup, false);
        return new BerandaAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BerandaAdapter.ViewHolder holder, int i) {
        BerandaModel list = berandaModels.get(i);
        if (list.getJenis_data().equals("donasi")) {
            Integer idDonasi = list.getId_opendonasi();
            String namaKegiatan = list.getNama_kegiatan_d();
            String file = list.getFile_d();
            Integer noRekening = list.getNo_rekening_d();
            String keterangan = list.getKeterangan_d();
            String lokasi = list.getLokasi_d();
            String contactPerson = list.getContact_person_d();
            Double totalAnggaran = list.getTotal_anggaran_d();
            String tanggalDonasi = list.getTanggal_beranda();

            final DonasiModel donasiModel = new DonasiModel(idDonasi, namaKegiatan, file, noRekening, keterangan, lokasi, contactPerson, totalAnggaran, tanggalDonasi);

            holder.d_namaKegiatan.setText(donasiModel.getNamaKegiatan());
            holder.d_tanggalDonasi.setText("" + donasiModel.getTanggal_opendonasi());
            holder.d_totalBiaya.setText("Rp " + String.format("%.0f", donasiModel.getTotalAnggaran()));

            holder.container_donasi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, DetailDonasiActivity.class);
                    intent.putExtra("object_donasi", donasiModel);
                    context.startActivity(intent);
                }
            });

            //visibility
            holder.container_lowongan.setVisibility(View.GONE);
            holder.container_info.setVisibility(View.GONE);
            holder.container_donasi.setVisibility(View.VISIBLE);
        } else if (list.getJenis_data().equals("info")) {

            Integer idInfo = list.getId_info();
            String judul = list.getJudul_i();
            String keterangan = list.getKeterangan_i();
            String link = list.getLink_i();
            String tanggalInfo = list.getTanggal_beranda();

            final InfoModel infoModel = new InfoModel(idInfo, judul, keterangan, link, tanggalInfo);
            holder.i_isi.setText(infoModel.getKeterangan());
            holder.i_judul.setText(infoModel.getJudul());
            holder.container_info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String url = infoModel.getLink();
                    try {
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        context.startActivity(i);
                    } catch (ActivityNotFoundException e){
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        String urlNew = "http://" + url;
                        i.setData(Uri.parse(urlNew));
                        context.startActivity(i);
                    }
                }
            });

            //visibility
            holder.container_lowongan.setVisibility(View.GONE);
            holder.container_info.setVisibility(View.VISIBLE);
            holder.container_donasi.setVisibility(View.GONE);
        } else {

            Integer idLowongan = list.getId_lowongan();
            String username = list.getUsername_l();
            String namaLowongan = list.getNama_lowongan_l();
            String namaPer = list.getNama_perusahaan_l();
            String alamatPer = list.getAlamat_perusahaan_l();
            String kisaranGaji = list.getKisaran_gaji_l();
            String syaratPekerjaan = list.getSyarat_pekerjaan_l();
            Integer kuota = list.getPelamar_yang_dibutuhkan_l();
            String jabatan = list.getLowongan_jabatan_l();
            String website = list.getWebsite_perusahaan_l();
            String email = list.getEmail_perusahaan_l();
            String noTelp = list.getNo_telp_perusahaan_l();
            String cp = list.getContact_person_l();
            String statusLowongan = list.getStatus_l();
            String urlLogo = list.getLogo_perusahaan_l();
            String tanggalLowongan = list.getTanggal_beranda();
            String logoPath = list.getLogo_perusahaan_l();

            final LowonganModel lowonganModel = new LowonganModel(idLowongan, username, namaLowongan, namaPer, alamatPer, kisaranGaji, syaratPekerjaan, kuota, jabatan, website, email, noTelp, cp, statusLowongan, urlLogo, tanggalLowongan);

            holder.l_txtKisaranGaji.setText(lowonganModel.getKisaran_gaji());
            holder.l_txtLokasi.setText(lowonganModel.getAlamat_perusahaan());
            holder.l_txtPerusahaan.setText(lowonganModel.getNama_perusahaan());
            holder.l_txtTitle.setText(lowonganModel.getNama_lowongan());
            Glide.with(context)
                    .load(BASE_URL + logoPath)
                    .into(holder.l_logo);

            holder.container_lowongan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, DetailLowonganActivity.class);
                    intent.putExtra("object_lowongan", lowonganModel);
                    context.startActivity(intent);
                }
            });

            //visibility
            holder.container_lowongan.setVisibility(View.VISIBLE);
            holder.container_info.setVisibility(View.GONE);
            holder.container_donasi.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return berandaModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //untuk donasi
        private TextView d_namaKegiatan, d_totalBiaya, d_tanggalDonasi, d_tulisanDonasi;

        //untuk info
        private TextView i_judul, i_isi, i_tanggal;
        private ImageView i_iv_link;

        //untuk lowongan
        private TextView l_txtTitle, l_txtPerusahaan, l_txtLokasi, l_txtKisaranGaji, l_tanggal;
        private CircleImageView l_logo;

        //Deklarasi ConstraintLayout container
        private ConstraintLayout container_donasi, container_info, container_lowongan;

        public ViewHolder(View itemView) {
            super(itemView);
            //untuk donasi
            d_namaKegiatan = itemView.findViewById(R.id.tv_card_donasi_judul_donasi);
            d_totalBiaya = itemView.findViewById(R.id.tv_card_donasi_jumlah_donasi);
            d_tanggalDonasi = itemView.findViewById(R.id.tv_card_donasi_tanggal);
            d_tulisanDonasi = itemView.findViewById(R.id.tv_card_donasi_tulisan_donasi);
            d_tanggalDonasi.setVisibility(View.INVISIBLE);

            //buat diklik
            container_donasi = itemView.findViewById(R.id.card_beranda_donasi);

            //untuk info
            i_judul = itemView.findViewById(R.id.tv_card_info_judul);
            i_isi = itemView.findViewById(R.id.tv_card_info_isi);
            i_tanggal = itemView.findViewById(R.id.tv_card_info_tanggal);
            i_iv_link = itemView.findViewById(R.id.img_card_info_link);
            i_tanggal.setVisibility(View.GONE);

            //buat diklik
            container_info = itemView.findViewById(R.id.card_beranda_info);

            //untuk lowongan
            l_txtTitle = itemView.findViewById(R.id.tv_nama_lowongan);
            l_txtPerusahaan = itemView.findViewById(R.id.tv_nama_perusahaan);
            l_txtLokasi = itemView.findViewById(R.id.tv_lokasi_perusahaan);
            l_txtKisaranGaji = itemView.findViewById(R.id.tv_kisaran_gaji);
            l_tanggal = itemView.findViewById(R.id.tv_tanggal_lowongan);
            l_logo = itemView.findViewById(R.id.iv_tambah_lowongan_logo);
            l_tanggal.setVisibility(View.GONE);

            //buat diklik
            container_lowongan = itemView.findViewById(R.id.card_beranda_lowongan);
        }
    }
}
