package com.shtainyky.weatherviewer.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonIgnore;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.bluelinelabs.logansquare.annotation.OnJsonParseComplete;
import com.shtainyky.weatherviewer.utils.Constants;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Bell on 19.06.2017.
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class DayWeatherItem implements Parcelable {
    public long dt;
    @JsonField(name = "main")
    public Temperature temperature;
    public Weather[] weather;


    @JsonIgnore
    public String date;
    @JsonIgnore
    public String iconUrl;

    @OnJsonParseComplete
    void onParseComplete() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy 'at' hh:mm a", Locale.getDefault());
        date = simpleDateFormat.format(new Date(dt*1000));
        iconUrl = Constants.BASE_URL_IMAGE + weather[0].icon;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.dt);
        dest.writeParcelable(this.temperature, flags);
        dest.writeTypedArray(this.weather, flags);
    }

    public DayWeatherItem() {
    }

    protected DayWeatherItem(Parcel in) {
        this.dt = in.readLong();
        this.temperature = in.readParcelable(Temperature.class.getClassLoader());
        this.weather = in.createTypedArray(Weather.CREATOR);
    }

    public static final Parcelable.Creator<DayWeatherItem> CREATOR = new Parcelable.Creator<DayWeatherItem>() {
        @Override
        public DayWeatherItem createFromParcel(Parcel source) {
            return new DayWeatherItem(source);
        }

        @Override
        public DayWeatherItem[] newArray(int size) {
            return new DayWeatherItem[size];
        }
    };
}
