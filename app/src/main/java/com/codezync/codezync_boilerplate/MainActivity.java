package com.codezync.codezync_boilerplate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.codezync.boilerplate.CommonFragments.MapFragment;
import com.codezync.boilerplate.Listeners.MapViewListener;
import com.codezync.boilerplate.Utility.MapUtility;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MapFragment.initMap(false, getSupportFragmentManager().beginTransaction(), R.id.map, new MapViewListener<MapFragment, LatLng>() {
            @Override
            public void onMapInitialized(MapFragment fragment, GoogleMap googleMap) {
                Log.d("CN","init");
               fragment.addMarker(new LatLng(8.060034473895321, 80.50900001297619),R.drawable.img_ripple_image,false);
            }

            @Override
            public void onMapClickListener(LatLng location) {

            }

            @Override
            public void onCameraIdle() {

            }
        });

    }
}