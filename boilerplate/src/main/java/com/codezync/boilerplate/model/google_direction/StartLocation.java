package com.codezync.boilerplate.model.google_direction;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StartLocation implements Parcelable {
    @SerializedName("lat")
    @Expose
    private Double lat;
    @SerializedName("lng")
    @Expose
    private Double lng;

    protected StartLocation(Parcel in) {
        if (in.readByte() == 0) {
            lat = null;
        } else {
            lat = in.readDouble();
        }
        if (in.readByte() == 0) {
            lng = null;
        } else {
            lng = in.readDouble();
        }
    }

    public static final Creator<StartLocation> CREATOR = new Creator<StartLocation>() {
        @Override
        public StartLocation createFromParcel(Parcel in) {
            return new StartLocation(in);
        }

        @Override
        public StartLocation[] newArray(int size) {
            return new StartLocation[size];
        }
    };

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (lat == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(lat);
        }
        if (lng == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(lng);
        }
    }
}
