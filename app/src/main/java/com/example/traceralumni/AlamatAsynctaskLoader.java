package com.example.traceralumni;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;
import android.widget.Toast;

import com.example.traceralumni.Activity.LocationPickerActivity;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static com.example.traceralumni.Activity.LocationPickerActivity.KODE_POS_EXTRA;

public class AlamatAsynctaskLoader extends AsyncTaskLoader<String> {

    private String hasil;
    private Context context;
    private double lat, lng;
    private boolean HasResult = false;

    public AlamatAsynctaskLoader(Context context, double lat, double lng) {
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
        if (HasResult) {
            hasil = "";
            HasResult = false;
        }
    }

    @Nullable
    @Override
    public String loadInBackground() {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            Address obj = addresses.get(0);
            String add = "";

            if (obj.getThoroughfare() != null)
                add += obj.getThoroughfare() + " ";
            if (obj.getSubThoroughfare() != null)
                add += "No. " + obj.getSubThoroughfare() + ", ";
            if (obj.getSubLocality() != null)
                add += obj.getSubLocality() + ", ";
            if (obj.getLocality() != null)
                add += obj.getLocality() + ", ";
            if (obj.getSubAdminArea() != null)
                add += obj.getSubAdminArea() + ", ";
            if (obj.getAdminArea() != null)
                add += obj.getAdminArea() + ", ";
            if (obj.getCountryName() != null)
                add += obj.getCountryName();

            if (obj.getPostalCode() != null)
                KODE_POS_EXTRA = obj.getPostalCode();
            else
                KODE_POS_EXTRA = "";

            return add;
        } catch (IOException e) {
            e.printStackTrace();
//            Toast.makeText(context, "Check Your Network Connection", Toast.LENGTH_SHORT).show();
            return "";
        }
    }
}
