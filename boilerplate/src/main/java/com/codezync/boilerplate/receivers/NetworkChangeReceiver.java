package com.codezync.boilerplate.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.codezync.boilerplate.Network.OnInternetResponseListener;
import com.codezync.boilerplate.Utility.NetworkUtil;


//This class provide a service to detect network changes
public class NetworkChangeReceiver extends BroadcastReceiver {

    private static OnInternetResponseListener<Boolean> listener;
    private String TAG = "NetworkChangeReceiver";

    public NetworkChangeReceiver() {
    }

    //should pass OnInternetResponseListener to constructor and when change the network status will pass the current status through the listener
    public NetworkChangeReceiver(OnInternetResponseListener listener) {
        this.listener = listener;
    }

    @Override
    public void onReceive(final Context context, final Intent intent) {
       if(listener !=null){
           int status = NetworkUtil.getConnectivityStatusString(context);
           if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
               listener.onResponse(status != NetworkUtil.NETWORK_STATUS_NOT_CONNECTED);
           }
       }
    }
}