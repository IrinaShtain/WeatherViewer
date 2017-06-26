package com.shtainyky.weatherviewer.utils;

/**
 * Created by Bell on 15.06.2017.
 */

public abstract class Constants {
    public static final int CLICK_DELAY = 600;
    public static final long TIMEOUT = 30; //seconds
    public static final long TIMEOUT_READ = 30; //seconds
    public static final long TIMEOUT_WRITE = 60; //seconds

    public static final int REQUEST_PERMISSION_CALLBACK_CODE = 101;
    public static final int REQUEST_PERMISSION_SETTING = 102;
    public static final int REQUEST_PLACE_AUTOCOMPLETE_CODE = 103;
    public static final int REQUEST_CHECK_SETTINGS = 100;

    public static final String BASE_URL = "http://api.openweathermap.org";
    public static final String BASE_URL_IMAGE = "http://api.openweathermap.org/img/w/";
    public static final String KEY_API = "4e24b48366bd60da4d55a6271ed9e8bf";
}
