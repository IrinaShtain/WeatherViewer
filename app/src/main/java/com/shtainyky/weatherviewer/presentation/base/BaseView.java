package com.shtainyky.weatherviewer.presentation.base;

/**
 * Created by Bell on 13.06.2017.
 */


public interface BaseView<T extends BasePresenter> {
    void initPresenter();
    void setPresenter(T presenter);
}
