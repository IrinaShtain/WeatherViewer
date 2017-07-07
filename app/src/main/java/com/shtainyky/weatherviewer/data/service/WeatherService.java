package com.shtainyky.weatherviewer.data.service;

import com.shtainyky.weatherviewer.data.models.response.WeatherResponse;
import com.shtainyky.weatherviewer.data.models.response.WeekWeatherResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Bell on 16.06.2017.
 */

public interface WeatherService {
    @GET("/data/2.5/weather")
    Observable<WeatherResponse> getDayWeather(@Query("lat") float lat,
                                              @Query("lon") float lon);
    @GET("/data/2.5/forecast")
    Observable<WeekWeatherResponse> getWeekWeather(@Query("lat") float lat,
                                                  @Query("lon") float lon);
}
