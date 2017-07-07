package com.shtainyky.weatherviewer.domains;

import com.shtainyky.weatherviewer.data.models.response.WeatherResponse;
import com.shtainyky.weatherviewer.data.models.response.WeekWeatherResponse;
import com.shtainyky.weatherviewer.data.service.Rest;
import com.shtainyky.weatherviewer.data.service.WeatherService;
import com.shtainyky.weatherviewer.presentation.base.NetworkRepository;
import com.shtainyky.weatherviewer.presentation.today.TodayWeatherContract;
import com.shtainyky.weatherviewer.presentation.week.WeekWeatherContract;
import com.shtainyky.weatherviewer.utils.Constants;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import rx.Observable;

/**
 * Created by Bell on 16.06.2017.
 */
@EBean(scope = EBean.Scope.Singleton)
public class WeatherRepository extends NetworkRepository implements TodayWeatherContract.TodayWeatherModel,
        WeekWeatherContract.WeekWeatherModel {

    @Bean
    protected Rest rest;

    private WeatherService mWeatherService;

    @AfterInject
    protected void initServices() {
        mWeatherService = rest.getWeatherService();
    }

    @Override
    public Observable<WeatherResponse> getWeather(float lat, float lon) {
        return getNetworkObservable(mWeatherService.getDayWeather(lat, lon));
    }

    @Override
    public Observable<WeekWeatherResponse> getWeekWeather(float lat, float lon) {
        return getNetworkObservable(mWeatherService.getWeekWeather(lat, lon));
    }
}
