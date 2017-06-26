package com.shtainyky.weatherviewer.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by Bell on 15.06.2017.
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS)
public class CurrentLocation implements Parcelable, Cloneable {
    public String country;
    public String city;
    public float latitude;
    public float longitude;
    public String address;

    public CurrentLocation() {
    }

    public CurrentLocation clone() {
        CurrentLocation clone = new CurrentLocation();
        clone.city = this.city;
        clone.country = this.country;
        clone.latitude = this.latitude;
        clone.longitude = this.longitude;
        clone.address = this.address;

        return clone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.country);
        dest.writeString(this.city);
        dest.writeFloat(this.latitude);
        dest.writeFloat(this.longitude);
        dest.writeString(this.address);
    }

    protected CurrentLocation(Parcel in) {
        this.country = in.readString();
        this.city = in.readString();
        this.latitude = in.readFloat();
        this.longitude = in.readFloat();
        this.address = in.readString();
    }

    public static final Creator<CurrentLocation> CREATOR = new Creator<CurrentLocation>() {
        @Override
        public CurrentLocation createFromParcel(Parcel source) {
            return new CurrentLocation(source);
        }

        @Override
        public CurrentLocation[] newArray(int size) {
            return new CurrentLocation[size];
        }
    };
}
