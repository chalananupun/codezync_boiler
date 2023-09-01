package com.codezync.boilerplate.Listeners;

import com.google.android.gms.maps.GoogleMap;

public interface MapViewListener<X, T> {
    void onMapInitialized(X fragment, GoogleMap googleMap);

    void onMapClickListener(T location);

    void onCameraIdle();
}
