package com.shtainyky.weatherviewer.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.bluelinelabs.logansquare.annotation.JsonIgnore;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.bluelinelabs.logansquare.annotation.OnJsonParseComplete;
import com.shtainyky.weatherviewer.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Bell on 15.06.2017.
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class SunInfo implements Parcelable {
    public long sunrise;
    public long sunset;

    @JsonIgnore
    public String timeSunset;
    @JsonIgnore
    public String timeSunrise;
    @OnJsonParseComplete
    void onParseComplete() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        timeSunset = simpleDateFormat.format(new Date(sunrise*1000));
        timeSunrise = simpleDateFormat.format(new Date(sunset*1000));
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.sunrise);
        dest.writeLong(this.sunset);
    }

    public SunInfo() {
    }

    protected SunInfo(Parcel in) {
        this.sunrise = in.readLong();
        this.sunset = in.readLong();
    }

    public static final Parcelable.Creator<SunInfo> CREATOR = new Parcelable.Creator<SunInfo>() {
        @Override
        public SunInfo createFromParcel(Parcel source) {
            return new SunInfo(source);
        }

        @Override
        public SunInfo[] newArray(int size) {
            return new SunInfo[size];
        }
    };
}
