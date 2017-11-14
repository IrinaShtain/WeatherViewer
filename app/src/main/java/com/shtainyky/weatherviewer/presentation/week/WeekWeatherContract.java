package com.shtainyky.weatherviewer.presentation.week;

import com.shtainyky.weatherviewer.data.models.response.WeekWeatherResponse;
import com.shtainyky.weatherviewer.presentation.base.BasePresenter;
import com.shtainyky.weatherviewer.presentation.base.BaseView;
import com.shtainyky.weatherviewer.presentation.base.content.ContentFragment;
import com.shtainyky.weatherviewer.presentation.base.content.ContentView;
import com.shtainyky.weatherviewer.presentation.week.adapter.WeatherDH;

import java.util.ArrayList;

import rx.Observable;

/**
 * Created by Bell on 14.06.2017.
 */

public class WeekWeatherContract {
    interface WeekWeatherView extends BaseView<WeekWeatherContract.WeekWeatherPresenter>, ContentView {
         void setList(ArrayList<WeatherDH> list);

        void openLocationFragment();
    }
    interface WeekWeatherPresenter extends BasePresenter {

    }

    public interface WeekWeatherModel {
        Observable<WeekWeatherResponse> getWeekWeather(float lat, float lon);
    }
}
