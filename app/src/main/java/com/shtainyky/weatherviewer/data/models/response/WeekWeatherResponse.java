package com.shtainyky.weatherviewer.data.models.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.shtainyky.weatherviewer.data.models.DayWeatherItem;

import java.util.List;

/**
 * Created by Bell on 19.06.2017.
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class WeekWeatherResponse implements Parcelable {
    public List<DayWeatherItem> list;

    public WeekWeatherResponse() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.list);
    }

    protected WeekWeatherResponse(Parcel in) {
        this.list = in.createTypedArrayList(DayWeatherItem.CREATOR);
    }

    public static final Creator<WeekWeatherResponse> CREATOR = new Creator<WeekWeatherResponse>() {
        @Override
        public WeekWeatherResponse createFromParcel(Parcel source) {
            return new WeekWeatherResponse(source);
        }

        @Override
        public WeekWeatherResponse[] newArray(int size) {
            return new WeekWeatherResponse[size];
        }
    };
}
