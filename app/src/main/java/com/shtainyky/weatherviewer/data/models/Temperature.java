package com.shtainyky.weatherviewer.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by Bell on 15.06.2017.
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class Temperature implements Parcelable {
    public float temp;
    public int humidity;
    public float temp_min;
    public float temp_max;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(this.temp);
        dest.writeInt(this.humidity);
        dest.writeFloat(this.temp_min);
        dest.writeFloat(this.temp_max);
    }

    public Temperature() {
    }

    protected Temperature(Parcel in) {
        this.temp = in.readFloat();
        this.humidity = in.readInt();
        this.temp_min = in.readFloat();
        this.temp_max = in.readFloat();
    }

    public static final Creator<Temperature> CREATOR = new Creator<Temperature>() {
        @Override
        public Temperature createFromParcel(Parcel source) {
            return new Temperature(source);
        }

        @Override
        public Temperature[] newArray(int size) {
            return new Temperature[size];
        }
    };
}
