package com.codezync.boilerplate.LocationService;

public class CurrentLocation {

    private double longitude,latitude;

    public CurrentLocation(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    protected void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    protected void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
