package com.shtainyky.weatherviewer.presentation.today;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.shtainyky.weatherviewer.R;
import com.shtainyky.weatherviewer.domains.WeatherRepository;
import com.shtainyky.weatherviewer.presentation.base.BasePresenter;
import com.shtainyky.weatherviewer.presentation.base.content.ContentFragment;
import com.shtainyky.weatherviewer.presentation.location.LocationFragment_;
import com.shtainyky.weatherviewer.utils.Constants;
import com.shtainyky.weatherviewer.utils.SignedLocationManager;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.concurrent.TimeUnit;

/**
 * Created by Bell on 14.06.2017.
 */
@EFragment
public class TodayWeatherFragment extends ContentFragment implements TodayWeatherContract.TodayWeatherView {

    @ViewById
    protected ImageView iv_Weather;
    @ViewById
    protected TextView tv_currentDay_desc;
    @ViewById
    protected TextView tv_sunRise;
    @ViewById
    protected TextView tv_sunSet;
    @ViewById
    protected TextView tv_mainTemp;
    @ViewById
    protected TextView tv_maxTemp;
    @ViewById
    protected TextView tv_minTemp;
    @ViewById
    protected TextView tv_humidity_value;
    @ViewById
    protected TextView tv_wind_value;
    @ViewById
    protected TextView tvCurrentCity;
    @ViewById
    protected RelativeLayout weather_container;

    @Bean
    protected SignedLocationManager mLocationManager;
    private TodayWeatherContract.TodayWeatherPresenter mWeatherPresenter;
    @Bean
    protected WeatherRepository mRepository;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_today;
    }

    @Override
    protected BasePresenter getPresenter() {
        return mWeatherPresenter;
    }

    @AfterInject
    @Override
    public void initPresenter() {
        new TodayWeatherPresenter(this, mLocationManager, mRepository);
    }

    @Override
    public void setPresenter(TodayWeatherContract.TodayWeatherPresenter presenter) {
        mWeatherPresenter = presenter;
    }

    @AfterViews
    public void initUI() {
        mWeatherPresenter.subscribe();
        RxView.clicks(tvCurrentCity)
                .throttleFirst(Constants.CLICK_DELAY, TimeUnit.MILLISECONDS)
                .subscribe(aVoid ->{
                    if (tvCurrentCity.getText().equals("Choose location"))
                        mWeatherPresenter.chooseLocationPressed();}
                        );
    }

    @Override
    public void setLastKnownLocation(String address) {
        tvCurrentCity.setText(address);
    }

    @Override
    public void makeVisible() {
        weather_container.setVisibility(View.VISIBLE);
    }

    @Override
    public void setIcon(String url) {
        Picasso.with(getContext())
                .load(Constants.BASE_URL_IMAGE + url)
                .error(R.drawable.ic_today)
                .into(iv_Weather);
    }

    @Override
    public void setDescription(String description) {
        tv_currentDay_desc.setText(getString(R.string.today, description));
    }

    @Override
    public void setSunInfo(String sunRise, String sunSet) {
        tv_sunRise.setText(getString(R.string.sunrise_s, sunRise));
        tv_sunSet.setText(getString(R.string.sunset_s, sunSet));
    }

    @Override
    public void setTemperature(float max, float min, float mid) {
        tv_mainTemp.setText(getString(R.string.temp_s_c, String.valueOf(mid)));
        tv_maxTemp.setText(getString(R.string.max_s_c, String.valueOf(max)));
        tv_minTemp.setText(getString(R.string.min_s_c, String.valueOf(min)));
    }

    @Override
    public void setHumidity(String humidity) {
        tv_humidity_value.setText(getString(R.string.humidity_s, humidity + "%"));
    }

    @Override
    public void setWindSpeed(String speed) {
        tv_wind_value.setText(getString(R.string.wind_speed_s, speed));
    }

    @Override
    public void openChooseLocationFragment() {
        mActivity.replaceFragment(LocationFragment_.builder().build());
    }

    @Override
    public void onDestroy() {
        mWeatherPresenter.unsubscribe();
        super.onDestroy();
    }
}
