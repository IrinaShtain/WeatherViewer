package com.shtainyky.weatherviewer.utils;

import org.androidannotations.annotations.sharedpreferences.DefaultBoolean;
import org.androidannotations.annotations.sharedpreferences.DefaultString;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

/**
 * Created by Bell on 15.06.2017.
 */

@SharedPref(value = SharedPref.Scope.UNIQUE)
public interface SharedPrefManager {
    String getLastLocation();
}

