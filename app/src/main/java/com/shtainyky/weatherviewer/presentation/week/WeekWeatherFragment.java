package com.shtainyky.weatherviewer.presentation.week;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.shtainyky.weatherviewer.R;
import com.shtainyky.weatherviewer.domains.WeatherRepository;
import com.shtainyky.weatherviewer.presentation.base.BaseFragment;
import com.shtainyky.weatherviewer.presentation.location.LocationFragment_;
import com.shtainyky.weatherviewer.utils.SignedLocationManager;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

/**
 * Created by Bell on 14.06.2017.
 */
@EFragment(R.layout.fragment_week)
public class WeekWeatherFragment extends BaseFragment implements WeekWeatherContract.WeekWeatherView {

    @ViewById
    protected RecyclerView rvLists;
    @Bean
    protected WeekWeatherAdapter listAdapter;
    @Bean
    protected WeatherRepository model;
    @Bean
    protected SignedLocationManager mLocationManager;

    private WeekWeatherContract.WeekWeatherPresenter mPresenter;

    @AfterInject
    @Override
    public void initPresenter() {
        new WeekWeatherPresenter(this, mLocationManager, model);
    }

    @Override
    public void setPresenter(WeekWeatherContract.WeekWeatherPresenter presenter) {
        mPresenter = presenter;
    }

    @AfterViews
    protected void initUI() {
        mPresenter.subscribe();
        setupRecyclerView();
    }


    private void setupRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvLists.setLayoutManager(layoutManager);
        rvLists.setAdapter(listAdapter);
    }


    @Override
    public void setList(ArrayList<WeatherDH> list) {
        listAdapter.setListDH(list);
    }

    @Override
    public void openLocationFragment() {
        mActivity.replaceFragment(LocationFragment_.builder().build());
    }

    @Override
    public void onDestroy() {
        mPresenter.unsubscribe();
        super.onDestroy();
    }
}