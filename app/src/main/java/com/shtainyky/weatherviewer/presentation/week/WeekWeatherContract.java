package com.shtainyky.weatherviewer.presentation.week;

import com.shtainyky.weatherviewer.data.models.response.WeatherResponse;
import com.shtainyky.weatherviewer.data.models.response.WeekWeatherResponse;
import com.shtainyky.weatherviewer.presentation.base.BasePresenter;
import com.shtainyky.weatherviewer.presentation.base.BaseView;
import com.shtainyky.weatherviewer.presentation.today.TodayWeatherContract;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by Bell on 14.06.2017.
 */

public class WeekWeatherContract {
    interface WeekWeatherView extends BaseView<WeekWeatherContract.WeekWeatherPresenter> {
         void setList(ArrayList<WeatherDH> list);

        void openLocationFragment();
    }
    interface WeekWeatherPresenter extends BasePresenter {

    }

    public interface WeekWeatherModel {
        Observable<WeekWeatherResponse> getWeekWeather(float lat, float lon);
    }
}
