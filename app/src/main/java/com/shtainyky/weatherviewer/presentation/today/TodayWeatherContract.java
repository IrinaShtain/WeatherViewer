package com.shtainyky.weatherviewer.presentation.today;

import com.shtainyky.weatherviewer.data.models.response.WeatherResponse;
import com.shtainyky.weatherviewer.presentation.base.BasePresenter;
import com.shtainyky.weatherviewer.presentation.base.BaseView;
import com.shtainyky.weatherviewer.presentation.base.content.ContentView;

import rx.Observable;


/**
 * Created by Bell on 14.06.2017.
 */

public class TodayWeatherContract {
    interface TodayWeatherView extends BaseView<TodayWeatherContract.TodayWeatherPresenter>, ContentView {
        void setLastKnownLocation(String address);

        void makeVisible();

        void setIcon(String url);

        void setDescription(String description);

        void setSunInfo(String sunRise, String sunSet);

        void setTemperature(float max, float min, float mid);

        void setHumidity(String humidity);

        void setWindSpeed(String speed);

        void openChooseLocationFragment();


    }

    interface TodayWeatherPresenter extends BasePresenter {
        void chooseLocationPressed();
    }

    public interface TodayWeatherModel {
        Observable<WeatherResponse> getWeather(float lat, float lon);
    }
}
