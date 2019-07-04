package com.example.traceralumni.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.traceralumni.AlamatAsynctaskLoader;
import com.example.traceralumni.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static com.example.traceralumni.Activity.LocationPickerActivity.LOKASI_EXTRA;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapsFragment extends Fragment implements LoaderManager.LoaderCallbacks<String>, OnMapReadyCallback {

    GoogleMap mMap;

    LatLng user_touch;
    private int ID_LOADER = 0;

    MarkerOptions markerOptions = new MarkerOptions();

    public MapsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_maps, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.frag_map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
        if (location != null) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(location.getLatitude(), location.getLongitude()))
                    .zoom(17)
                    .build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                markerOptions.position(latLng);

                Bundle bundle = new Bundle();
                bundle.putDouble("lat", latLng.latitude);
                bundle.putDouble("lng", latLng.longitude);

                getLoaderManager().initLoader(ID_LOADER, bundle, MapsFragment.this);
                ID_LOADER++;

                user_touch = latLng;
                mMap.clear();

                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(latLng.latitude, latLng.longitude))
                        .zoom(mMap.getCameraPosition().zoom)
                        .build();
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int i, @Nullable Bundle bundle) {
        double lat, lng;
        Log.e("aldy", "oncreateloader jalan");
        if (bundle != null){
            lat = bundle.getDouble("lat");
            lng = bundle.getDouble("lng");
            Log.e("aldy", "lat : " + lat + "long : " + lng);
            return new AlamatAsynctaskLoader(getActivity(), lat, lng);
        } else {
            Log.e("aldy", "bundle null");
            return null;
        }
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String s) {
        Log.e("aldy", "onloadfinished jalan - lokasi : " + s);
        markerOptions.title(s);
        LOKASI_EXTRA = s;
        Marker marker = mMap.addMarker(markerOptions);
        marker.showInfoWindow();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}
