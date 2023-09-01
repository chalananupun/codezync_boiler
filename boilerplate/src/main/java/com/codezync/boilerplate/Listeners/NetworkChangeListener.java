package com.codezync.boilerplate.Listeners;

import android.app.Activity;
import android.content.IntentFilter;

import androidx.lifecycle.MutableLiveData;

import com.codezync.boilerplate.Network.OnInternetResponseListener;
import com.codezync.boilerplate.R;
import com.codezync.boilerplate.Utility.LogUtil;
import com.codezync.boilerplate.Utility.NotificationUtil;
import com.codezync.boilerplate.receivers.NetworkChangeReceiver;


public class NetworkChangeListener {
    private static final String TAG = "NetworkChangeListener";
    private static OnInternetResponseListener<Boolean> listener;
    private static NetworkChangeReceiver networkChangeReceiver;
    protected static MutableLiveData<Boolean> isBackOnline = new MutableLiveData<>();
    protected static boolean shouldNotifyConnectivityChange;
    private static boolean isNetworkListenerRegistered, lastStatusIsOnline;
    protected static Activity currentActivity;
    private static boolean isCallFromOnCreate;

    public NetworkChangeListener(Activity activity, boolean shouldNotifyToUser) {
        currentActivity = activity;
        this.shouldNotifyConnectivityChange = shouldNotifyToUser;

    }


    public void startListening() {
        registerReceivers();
    }


    public void stopListening() {
        unregisterReceivers();
    }


    //register network callbacks
    private void registerReceivers() {
        try {
            setNetworkChangeListener();
            LogUtil.error(TAG, "registerReceivers ");

            if (networkChangeReceiver == null) {
                networkChangeReceiver = new NetworkChangeReceiver(listener);
            }

            IntentFilter filter = new IntentFilter();
            filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            currentActivity.registerReceiver(networkChangeReceiver, filter);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.error(TAG, "registerReceivers > FAILED");
        }
    }


    //when destroy the activity should unregister network callbacks
    private void unregisterReceivers() {
        try {
            if (networkChangeReceiver != null && isNetworkListenerRegistered) {
                isNetworkListenerRegistered = false;
                currentActivity.unregisterReceiver(networkChangeReceiver);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.error(TAG, "unRegisterReceivers > FAILED");
        }
    }


    // This listener will provide network state when changing the network
    private void setNetworkChangeListener() {

        if (isBackOnline == null) {
            isBackOnline = new MutableLiveData<>();
        }

        if (listener == null) {
            listener = new OnInternetResponseListener<Boolean>() {
                @Override
                public void onResponse(Boolean isOnline) {
                    isNetworkListenerRegistered = true;
                    isBackOnline.postValue(isOnline);


                    LogUtil.error(TAG, "Connectivity Changed Should show " + shouldNotifyConnectivityChange + " isFromOnCreate : " + isCallFromOnCreate);
                    if (shouldNotifyConnectivityChange && (!isCallFromOnCreate)) {

                        LogUtil.error(TAG, "Connectivity Changed isONLINE: " + isOnline + "  --- Last status " + lastStatusIsOnline);


                        if (isOnline && (lastStatusIsOnline != isOnline)) {
                            LogUtil.error(TAG, "Connectivity Changed SHOW ONLINE MESSAGE");
                            NotificationUtil.showBackOnlineMessage(currentActivity, currentActivity.getString(R.string.backOnlineSnakeBarMessage));

                        } else if (!isOnline) {
                            NotificationUtil.showNoInternetMessage(currentActivity, currentActivity.getString(R.string.noInternetSnakeBarMessage));
                            LogUtil.error(TAG, "Connectivity Changed SHOW OFF-ONLINE MESSAGE");
                        } else {
                            LogUtil.error(TAG, "Connectivity Changed ELSE");
                        }


                        lastStatusIsOnline = isOnline;
//                            if (isOnline) {
//                                NotificationUtil.showBackOnlineMessage(currentActivity);
//                            } else {
//                                NotificationUtil.showNoInternetMessage(currentActivity);
//                            }


                    }

                    isCallFromOnCreate = false;
                    LogUtil.error(TAG, "Connectivity Changed isONLINE: " + isOnline);
                }
            };
        }
    }


}
