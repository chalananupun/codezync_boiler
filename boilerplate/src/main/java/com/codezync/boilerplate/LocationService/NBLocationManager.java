package com.codezync.boilerplate.LocationService;

import android.content.Context;

public class NBLocationManager {

    private Context context;

    public NBLocationManager(Context context) {
        this.context = context;
    }


    public GPSTracker getGPSTracker() {
        GPSTracker gpsTracker = new GPSTracker(context);

        if (gpsTracker.getIsGPSTrackingEnabled()) {
            return gpsTracker;
        } else {
            return null;
        }

    }

}
