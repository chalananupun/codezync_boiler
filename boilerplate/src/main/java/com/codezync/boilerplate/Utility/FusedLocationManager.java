package com.codezync.boilerplate.Utility;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.lifecycle.MutableLiveData;

import com.codezync.boilerplate.Listeners.onGpsListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

public class FusedLocationManager {

    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private boolean isGpsEnabled = false;
    private Activity activity;
    public LatLng latLng;
    public MutableLiveData<LatLng> liveLocation;

    public FusedLocationManager(Activity activity) {
        this.activity = activity;

        liveLocation = new MutableLiveData<>();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(Constants.LOCATION_INTERVAL * 1000);
        locationRequest.setFastestInterval(Constants.LOCATION_FAST_INTERVAL * 1000);

        new GpsUtils(activity).turnGpsOn(new onGpsListener() {
            @Override
            public void gpsStatus(boolean isGPSEnable) {
                // turn on GPS
                isGpsEnabled = isGPSEnable;
            }
        });


        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    Log.d("ANEEF", "Invalid Location");
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        latLng = new LatLng(location.getLatitude(), location.getLongitude());
                        liveLocation.postValue(latLng);
                        mFusedLocationClient.removeLocationUpdates(locationCallback);
                    }
                }
            }
        };


    }


//    public void startFetchLocation(){
//        getLocation();
//    }


    public void getLocation() {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    Constants.LOCATION_REQUEST);

        } else {
            mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
            Log.d("getLocation", "On Demand worked");
        }
    }
}
