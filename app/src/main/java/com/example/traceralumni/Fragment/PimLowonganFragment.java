   package com.example.traceralumni.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.traceralumni.Activity.OpPermintaanLowonganActivity;
import com.example.traceralumni.Adapter.LowonganAdapter;
import com.example.traceralumni.JsonPlaceHolderApi;
import com.example.traceralumni.Model.LowonganModel;
import com.example.traceralumni.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.traceralumni.Activity.MainActivity.BASE_URL;

   /**
 * A simple {@link Fragment} subclass.
 */
public class PimLowonganFragment extends Fragment {

       View rootview;

       RecyclerView lowonganRecycler;
       LowonganAdapter lowonganAdapter;
       ArrayList<LowonganModel> arrayLowongan;

       EditText edt_lowongan_cari;

    public PimLowonganFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_pim_lowongan,  container,false);
        edt_lowongan_cari = rootview.findViewById(R.id.edt_fragment_pim_lowongan_search);

        arrayLowongan = new ArrayList<>();

        lowonganRecycler = rootview.findViewById(R.id.rv_fragment_pim_lowongan);

        lowonganRecycler.setLayoutManager(new LinearLayoutManager(rootview.getContext(), LinearLayoutManager.VERTICAL, false));
        lowonganAdapter = new LowonganAdapter(rootview.getContext(), arrayLowongan);
        lowonganRecycler.setAdapter(lowonganAdapter);

        return rootview;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getAllLowongan();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void getAllLowongan(){
       Retrofit retrofit = new Retrofit.Builder()
               .baseUrl(BASE_URL)
               .addConverterFactory(GsonConverterFactory.create())
               .build();

       JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

       Call<ArrayList<LowonganModel>> lowongan = jsonPlaceHolderApi.getAllLowongan();
       lowongan.enqueue(new Callback<ArrayList<LowonganModel>>() {
           @Override
           public void onResponse(Call<ArrayList<LowonganModel>> call, Response<ArrayList<LowonganModel>> response) {
               if(!response.isSuccessful()){
                   return;
               }

               arrayLowongan.clear();
               ArrayList<LowonganModel> lowonganResponse = response.body();
               arrayLowongan.addAll(lowonganResponse);

               final LowonganAdapter lowonganAdapterNew = new LowonganAdapter(getActivity(), arrayLowongan);
               lowonganRecycler.setAdapter(lowonganAdapterNew);

               setSearch(lowonganAdapterNew);
           }

           @Override
           public void onFailure(Call<ArrayList<LowonganModel>> call, Throwable t) {
               Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
           }
       });
       lowonganAdapter.notifyDataSetChanged();
   }
   private void setSearch(final LowonganAdapter lowonganAdapter){
       edt_lowongan_cari.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

           }

           @Override
           public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
               lowonganAdapter.getFilter().filter(charSequence);
           }

           @Override
           public void afterTextChanged(Editable editable) {

           }
       });
   }
}
