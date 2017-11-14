package com.shtainyky.weatherviewer.presentation.week;

import android.util.Log;

import com.shtainyky.weatherviewer.data.ecxeptions.ConnectionException;
import com.shtainyky.weatherviewer.data.models.CurrentLocation;
import com.shtainyky.weatherviewer.presentation.week.adapter.WeatherDH;
import com.shtainyky.weatherviewer.utils.Constants;
import com.shtainyky.weatherviewer.utils.SignedLocationManager;

import java.util.ArrayList;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by Bell on 19.06.2017.
 */

public class WeekWeatherPresenter implements WeekWeatherContract.WeekWeatherPresenter {

    private WeekWeatherContract.WeekWeatherView mView;
    private WeekWeatherContract.WeekWeatherModel mModel;
    private CompositeSubscription mCompositeSubscription;
    private CurrentLocation mLocation;
    private ArrayList<WeatherDH> list;

    public WeekWeatherPresenter(WeekWeatherContract.WeekWeatherView view,
                                SignedLocationManager locationManager,
                                WeekWeatherContract.WeekWeatherModel model) {
        mView = view;
        mModel = model;
        mCompositeSubscription = new CompositeSubscription();
        mLocation = locationManager.getCurrentLocation();
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        if (mLocation != null) {
            mCompositeSubscription.add(mModel.getWeekWeather(mLocation.latitude, mLocation.longitude)
                    .subscribe(response -> {
                        list = new ArrayList<>();
                        for (int i = 0; i < response.list.size(); i++) {
                            list.add(new WeatherDH(response.list.get(i)));
                        }
                        mView.setList(list);
                    }, throwable -> {
                        mView.hideProgress();
                        if (throwable instanceof ConnectionException) {
                            if (list == null)
                                mView.showPlaceholder(Constants.PlaceholderType.NETWORK);
                            else
                                mView.showErrorMessage(Constants.MessageType.CONNECTION_PROBLEMS);
                        } else {
                            if (list == null)
                                mView.showPlaceholder(Constants.PlaceholderType.UNKNOWN);
                            else
                                mView.showErrorMessage(Constants.MessageType.UNKNOWN);
                        }
                        Log.e("myLog", "throwable makeSearch >>>" + throwable.getMessage());
                    }));
        } else
            mView.openLocationFragment();
    }

    @Override
    public void unsubscribe() {
        if (mCompositeSubscription.hasSubscriptions()) mCompositeSubscription.clear();
    }
}
