package com.shtainyky.weatherviewer.data.models.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.shtainyky.weatherviewer.data.models.SunInfo;
import com.shtainyky.weatherviewer.data.models.Temperature;
import com.shtainyky.weatherviewer.data.models.Weather;
import com.shtainyky.weatherviewer.data.models.Wind;

/**
 * Created by Bell on 15.06.2017.
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class WeatherResponse implements Parcelable {
    public Weather[] weather;
    @JsonField(name = "main")
    public Temperature temperature;
    public Wind wind;
    public long dt;
    @JsonField(name = "sys")
    public SunInfo sun;


    public WeatherResponse() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedArray(this.weather, flags);
        dest.writeParcelable(this.temperature, flags);
        dest.writeParcelable(this.wind, flags);
        dest.writeLong(this.dt);
        dest.writeParcelable(this.sun, flags);
    }

    protected WeatherResponse(Parcel in) {
        this.weather = in.createTypedArray(Weather.CREATOR);
        this.temperature = in.readParcelable(Temperature.class.getClassLoader());
        this.wind = in.readParcelable(Wind.class.getClassLoader());
        this.dt = in.readLong();
        this.sun = in.readParcelable(SunInfo.class.getClassLoader());
    }

    public static final Creator<WeatherResponse> CREATOR = new Creator<WeatherResponse>() {
        @Override
        public WeatherResponse createFromParcel(Parcel source) {
            return new WeatherResponse(source);
        }

        @Override
        public WeatherResponse[] newArray(int size) {
            return new WeatherResponse[size];
        }
    };
}
