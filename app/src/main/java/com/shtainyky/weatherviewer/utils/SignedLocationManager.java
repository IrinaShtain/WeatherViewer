package com.shtainyky.weatherviewer.utils;

/**
 * Created by Bell on 15.06.2017.
 */

import com.bluelinelabs.logansquare.LoganSquare;
import com.shtainyky.weatherviewer.data.models.CurrentLocation;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.io.IOException;


/**
 * Created by Lynx on 4/14/2017.
 */

@EBean(scope = EBean.Scope.Singleton)
public class SignedLocationManager {

    @Pref
    protected SharedPrefManager_ userStorePrefs;

    private CurrentLocation mCurrentLocation;

    private void storeLocation() {
        userStorePrefs
                .edit()
                .getLastLocation()
                .put(mCurrentLocation == null ? null : serializeLocation())
                .apply();
    }

    public CurrentLocation getCurrentLocation() {
        return mCurrentLocation == null ? mCurrentLocation = parseUser() : mCurrentLocation.clone();
    }

    private CurrentLocation parseUser() {
        CurrentLocation user = null;
        try {
            user = LoganSquare.parse(userStorePrefs.getLastLocation().get(), CurrentLocation.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }

    private String serializeLocation() {
        String location = null;
        try {
            location = LoganSquare.serialize(mCurrentLocation);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return location;
    }

    public void updateLocation(final CurrentLocation location) {
        mCurrentLocation = location;
        storeLocation();
    }

    public void clearLocation() {
        userStorePrefs
                .edit()
                .getLastLocation()
                .remove()
                .apply();

    }
}
