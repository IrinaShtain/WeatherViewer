package com.shtainyky.weatherviewer.presentation.today;

import android.util.Log;

import com.shtainyky.weatherviewer.data.models.CurrentLocation;
import com.shtainyky.weatherviewer.data.models.SunInfo;
import com.shtainyky.weatherviewer.data.models.Temperature;
import com.shtainyky.weatherviewer.data.models.Weather;
import com.shtainyky.weatherviewer.utils.SignedLocationManager;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by Bell on 14.06.2017.
 */

public class TodayWeatherPresenter implements TodayWeatherContract.TodayWeatherPresenter {

    private TodayWeatherContract.TodayWeatherView mView;
    private TodayWeatherContract.TodayWeatherModel mWeatherModel;
    private CurrentLocation mLocation;
    private CompositeSubscription mCompositeSubscription;


    public TodayWeatherPresenter(TodayWeatherContract.TodayWeatherView view,
                                 SignedLocationManager locationManager,
                                 TodayWeatherContract.TodayWeatherModel model) {
        mView = view;
        mWeatherModel = model;
        mLocation = locationManager.getCurrentLocation();
        mCompositeSubscription = new CompositeSubscription();
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        if (mLocation != null) {
            mView.setLastKnownLocation(mLocation.address);
            showWeather();
        } else
            mView.setLastKnownLocation("Choose location");
    }

    private void showWeather() {
        Log.e("myLog", "showWeather");
        mCompositeSubscription.add(mWeatherModel.getWeather(mLocation.latitude, mLocation.longitude)
                .subscribe(response -> {
                    mView.makeVisible();
                    Weather weather = response.weather[0];
                    if (weather.icon != null)
                        mView.setIcon(response.weather[0].icon);
                    if (weather.description != null)
                        mView.setDescription(response.weather[0].description);

                    SunInfo sun = response.sun;
                    if (sun.timeSunset != null && sun.timeSunrise != null)
                        mView.setSunInfo(sun.timeSunset, sun.timeSunrise);

                    Temperature temperature = response.temperature;
                    mView.setTemperature(temperature.temp_max,
                            temperature.temp_min,
                            temperature.temp);
                    mView.setHumidity(String.valueOf(temperature.humidity));
                    mView.setWindSpeed(String.valueOf(response.wind.speed));

                }, throwable -> {
                    Log.e("myLog", "throwable makeSearch >>>" + throwable.getMessage());
                }));
    }

    @Override
    public void unsubscribe() {
        if (mCompositeSubscription.hasSubscriptions()) mCompositeSubscription.clear();
    }

    @Override
    public void chooseLocationPressed() {
        mView.openChooseLocationFragment();
    }
}
