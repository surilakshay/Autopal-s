package com.example.gautam.carnival2016;

/**
 * Created by gautam on 05-06-2016.
 */
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;

public class networkDetector {

    private Context context;

    public networkDetector(Context context){
        this.context = context;
    }

    public boolean isConnectingToInternet(){
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }
}