package com.shtainyky.weatherviewer.presentation.week;

import com.shtainyky.weatherviewer.data.models.DayWeatherItem;
import com.shtainyky.weatherviewer.data.models.response.WeatherResponse;

/**
 * Created by Bell on 19.06.2017.
 */

public class WeatherDH {
    private final DayWeatherItem model;

    public WeatherDH(DayWeatherItem model) {
        this.model = model;
    }

    public DayWeatherItem getModel() {
        return model;
    }

    public String getCurrentWeatherDesc() {
        return model.date == null ? "No date" : model.date + " is " + model.weather[0].description;
    }

    public String getLowTemp() {
        return String.valueOf(model.temperature.temp_min);
    }

    public String getMaxTemp() {
        return String.valueOf(model.temperature.temp_max);
    }

    public String getIconUrl() {
        return model.iconUrl;
    }

    public String getHum() {
        return String.valueOf(model.temperature.humidity) + "%";
    }
}
