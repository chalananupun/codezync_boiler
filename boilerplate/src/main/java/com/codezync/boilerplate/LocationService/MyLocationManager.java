package com.codezync.boilerplate.LocationService;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.codezync.boilerplate.AppConfigurations;
import com.codezync.boilerplate.Listeners.onGpsListener;
import com.codezync.boilerplate.Utility.ExtenstionMethods;
import com.codezync.boilerplate.Utility.LogUtil;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;
import java.util.Locale;

public class MyLocationManager {

    private Context mContext;
    private SettingsClient mSettingsClient;
    private LocationSettingsRequest mLocationSettingsRequest;
    private LocationManager locationManager;
    private LocationRequest locationRequest;
    private static final int GPS_REQUEST = 1001;

    public MyLocationManager(Context mContext) {
        this.mContext = mContext;

        locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        mSettingsClient = LocationServices.getSettingsClient(mContext);

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5 * 1000);
        locationRequest.setFastestInterval(2 * 1000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        mLocationSettingsRequest = builder.build();

        //**************************
        builder.setAlwaysShow(true); //this is the key ingredient
        //**************************
    }

    public String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";

        Geocoder geocoder = new Geocoder(mContext, AppConfigurations.getDefaultLocalForPlaceAutoComplete());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");
                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append(",");
                    String street = returnedAddress.getAddressLine(0);
                    String city = returnedAddress.getAddressLine(1);
                    String country = returnedAddress.getAddressLine(2);
                    String postalCode = "";
                    if (city != null) {
                        String[] names = city.split(" ");
                        for (int j = 0; j < names.length; j++) {
                            if (j == 0) {
                                city = names[j];
                            }
                            if (j == 1) {
                                postalCode = names[j];
                            }
                        }
                    }
                    if (postalCode == null) {
                        postalCode = "";
                    }
                }
                strAdd = strReturnedAddress.toString();
                Log.w("Current location", "" + strReturnedAddress.toString());
            } else {
                Log.w("Current location", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("Current location", "Cannot get Address!");
        }
        return strAdd;
    }

    public String getCityString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";

        Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                if(ExtenstionMethods.isNotEmptyString(returnedAddress.getLocality()))
                    strAdd = returnedAddress.getLocality();

                if(ExtenstionMethods.isNotEmptyString(returnedAddress.getSubLocality()))
                    strAdd = returnedAddress.getSubLocality();

                Log.w("Current location", "" + strAdd);
            } else {
                Log.w("Current location", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("Current location", "Cannot get Address!");
        }
        return strAdd;
    }


    // method for turn on GPS
    public void turnGpsOn(final onGpsListener onGpsListener) {

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            if (onGpsListener != null) {
                onGpsListener.gpsStatus(true);
            }
        } else {
            mSettingsClient
                    .checkLocationSettings(mLocationSettingsRequest)
                    .addOnSuccessListener((Activity) mContext, new OnSuccessListener<LocationSettingsResponse>() {
                        @SuppressLint("MissingPermission")
                        @Override
                        public void onSuccess(LocationSettingsResponse locationSettingsResponse) {

                            //  GPS is already enable, callback GPS status through listener
                            if (onGpsListener != null) {
                                onGpsListener.gpsStatus(true);
                            }
                        }
                    })
                    .addOnFailureListener((Activity) mContext, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            int statusCode = ((ApiException) e).getStatusCode();
                            switch (statusCode) {
                                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                                    try {
                                        // Show the dialog by calling startResolutionForResult(), and check the
                                        // result in onActivityResult().
                                        ResolvableApiException rae = (ResolvableApiException) e;
                                        rae.startResolutionForResult((Activity) mContext, GPS_REQUEST);
                                    } catch (IntentSender.SendIntentException sie) {
                                        Log.i(TAG, "PendingIntent unable to execute request.");
                                    }
                                    break;
                                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                    String errorMessage = "Location settings are inadequate, and cannot be " +
                                            "fixed here. Fix in Settings.";
                                    LogUtil.debug(TAG, errorMessage);

                                    Toast.makeText((Activity) mContext, errorMessage, Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }
}
