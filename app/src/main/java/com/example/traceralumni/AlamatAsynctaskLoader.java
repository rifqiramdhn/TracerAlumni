package com.example.traceralumni;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class AlamatAsynctaskLoader extends AsyncTaskLoader<String> {

    private String hasil;
    private Context context;
    private double lat, lng;
    private boolean HasResult = false;

    public AlamatAsynctaskLoader(Context context, double lat, double lng){
        super(context);
        this.context = context;
        this.lat = lat;
        this.lng = lng;
        onContentChanged();
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged())
            forceLoad();
        else if (HasResult)
            deliverResult(hasil);
        super.onStartLoading();
    }

    @Override
    public void deliverResult(@Nullable String data) {
        hasil = data;
        HasResult = true;
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (HasResult){
            hasil = "";
            HasResult = false;
        }
    }

    @Nullable
    @Override
    public String loadInBackground() {
        Log.e("aldy", "loadInbackground jalan");
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            Address obj = addresses.get(0);
            String add = obj.getAddressLine(0);
            return add;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
