package com.example.traceralumni;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class ConnectivityHelper {
    public static boolean isConnectedToNetwork(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        boolean isConnected = false;
        if (connectivityManager != null) {
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            isConnected = (activeNetwork != null) && (activeNetwork.isConnectedOrConnecting());
        }

        return isConnected;
    }

    public static void checkConnection(Context context) {
        if (!ConnectivityHelper.isConnectedToNetwork(context)) {
            Toast.makeText(context, "Koneksi internet dibutuhkan!", Toast.LENGTH_SHORT).show();
        }
    }
}