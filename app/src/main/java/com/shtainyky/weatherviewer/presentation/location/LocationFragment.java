package com.shtainyky.weatherviewer.presentation.location;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.jakewharton.rxbinding.view.RxView;
import com.shtainyky.weatherviewer.R;
import com.shtainyky.weatherviewer.data.models.CurrentLocation;
import com.shtainyky.weatherviewer.presentation.base.BaseFragment;
import com.shtainyky.weatherviewer.presentation.today.TodayWeatherFragment_;
import com.shtainyky.weatherviewer.utils.Constants;
import com.shtainyky.weatherviewer.utils.SignedLocationManager;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * Created by Bell on 15.06.2017.
 */

@EFragment(R.layout.fragment_location)
public class LocationFragment extends BaseFragment implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener,
        LocationContract.LocationView {


    @ViewById
    protected RelativeLayout rlCurrentAddress;
    @ViewById
    protected TextView tvSearch;
    @ViewById
    protected TextView tvCurrentLocation;
    @ViewById
    protected TextView tvGetCurrentCity;

    @ViewById
    protected Button bt_show;


    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private LocationContract.LocationPresenter presenter;

    private LocationRequest mLocationRequest;
    private String mCountry;
    private String mCity;
    private boolean mIsFirstTime;
    private double mLatitude;
    private double mLongitude;

    @Bean
    protected SignedLocationManager mLocationManager;


    @AfterInject
    public void init() {
        if (mGoogleApiClient != null) return;
        try {
            mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .addApi(Places.GEO_DATA_API)
                    .addApi(Places.PLACE_DETECTION_API)
                    .enableAutoManage(getActivity(), this)
                    .build();
        } catch (Exception e) {
            Log.e("Location", "GoogleApiClient error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @AfterViews
    protected void initUI() {
        mIsFirstTime = true;
        presenter.subscribe();
        RxView.clicks(tvCurrentLocation)
                .throttleFirst(Constants.CLICK_DELAY, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> presenter.onCurrentLocationClick());


        RxView.clicks(tvSearch)
                .throttleFirst(Constants.CLICK_DELAY, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> presenter.onSearchButtonClick());

        RxView.clicks(bt_show)
                .throttleFirst(Constants.CLICK_DELAY, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> presenter.onShowButtonClick());
    }

    @AfterInject
    @Override
    public void initPresenter() {
        new LocationPresenter(this);
    }

    @Override
    public void setPresenter(LocationContract.LocationPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setLastKnownAddress(String country, String city) {
        if (mLocationManager.getCurrentLocation() != null) {
            CurrentLocation location = mLocationManager.getCurrentLocation();
            rlCurrentAddress.setVisibility(View.VISIBLE);
            tvGetCurrentCity.setText(location.address);
        }
    }

    @Override
    public void requestPermission() {
        if (hasNoPermissions()) {
            if (shouldShowRequestPermissionRationale()) {
                presenter.showDialogForPermissions(mIsFirstTime);
            } else {
                presenter.showStandardRequestPermission();
            }
            mIsFirstTime = false;
        } else {
            presenter.getLocationResult();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constants.REQUEST_PERMISSION_CALLBACK_CODE) {
            if (grantResults.length > 0) {
                boolean fineLocationPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                boolean coarseLocationPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                if (fineLocationPermission && coarseLocationPermission)
                    presenter.getLocationResult();
                else if (shouldShowRequestPermissionRationale()) {
                    presenter.showDialogForPermissions(mIsFirstTime);
                } else {
                    presenter.showDialogForPermissionsAfterDontAskAgain();
                }
            }
        }
    }

    private boolean shouldShowRequestPermissionRationale() {
        return ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION) ||
                ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                        Manifest.permission.ACCESS_FINE_LOCATION);
    }

    private boolean hasNoPermissions() {
        return ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED;
    }


    @Override
    public void showMessageAfterDontAskAgain() {
        Snackbar snackbar = Snackbar
                .make(getView(), R.string.permissions_are_denied, Snackbar.LENGTH_LONG)
                .setAction(R.string.setting, view -> {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                    intent.setData(uri);
                    startActivityForResult(intent, Constants.REQUEST_PERMISSION_SETTING);
                    presenter.showMessage(getString(R.string.message_path_to_permissions));
                });
        snackbar.show();
    }

    @Override
    public void showDialogWithExplanation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.message_need_permission);
        builder.setPositiveButton(R.string.answer_grant, (dialog, which) -> {
            dialog.cancel();
            presenter.showStandardRequestPermission();
        });
        builder.setNegativeButton(R.string.answer_deny, (dialog, which) ->
                presenter.showMessage(getString(R.string.message_no_permission)));

        builder.show();
    }

    @Override
    public void showDialogRequestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                    Constants.REQUEST_PERMISSION_CALLBACK_CODE);
        }
    }

    @Override
    public void requestGPS() {
        if (mGoogleApiClient == null || !mGoogleApiClient.isConnected()) {
            presenter.showMessage(getString(R.string.message_not_connected));
            return;
        }
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);
        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient,
                        builder.build());

        result.setResultCallback(locationSettingsResult -> {
            final Status status = locationSettingsResult.getStatus();
            switch (status.getStatusCode()) {
                case LocationSettingsStatusCodes.SUCCESS:
                    if (hasNoPermissions()) {
                        presenter.requestPermissions();
                    } else {
                        presenter.getLocationResult();
                    }
                    break;
                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                    try {
                        this.startIntentSenderForResult(status.getResolution().getIntentSender(),
                                Constants.REQUEST_CHECK_SETTINGS, null, 0, 0, 0, null);
                    } catch (IntentSender.SendIntentException e) {
                        // Ignore the error.
                    }
                    break;
                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                    presenter.showMessage(getString(R.string.message_advice));
                    break;
            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        mLastLocation = location;
        if (hasNoPermissions()) {
            presenter.requestPermissions();
        } else {
            presenter.getAddress(mLastLocation.getLatitude(),
                    mLastLocation.getLongitude());
        }
    }


    @Override
    public void getLocationResult() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if (!mGoogleApiClient.isConnected()) return;
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            presenter.getAddress(mLastLocation.getLatitude(),
                    mLastLocation.getLongitude());
        }
    }

    @Override
    public void makeVisibleAddress() {
        rlCurrentAddress.setVisibility(View.VISIBLE);
        tvGetCurrentCity.setText(mCity + ", " + mCountry);
    }

    private void addressValidation() {
        if (mCity == null) mCity = getString(R.string.validation_city); //need info
        if (mCountry == null) mCountry = getString(R.string.validation_country);//need info
    }


    @Override
    public void requestPlaces() {
        try {
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                            .setFilter(new AutocompleteFilter.Builder()
                                    .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                                    .build())
                            .build(getActivity());
            this.startActivityForResult(intent, Constants.REQUEST_PLACE_AUTOCOMPLETE_CODE);
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
            presenter.showMessage(getString(R.string.message_no_known_location));
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.REQUEST_PERMISSION_SETTING:
                if (hasNoPermissions()) {
                    presenter.showMessage(getString(R.string.message_no_permission));
                } else {
                    presenter.getLocationResult();
                }
                break;
            case Constants.REQUEST_CHECK_SETTINGS:
                if (resultCode == RESULT_OK)
                    checkPermissionsAndGetLocation();
                else
                    presenter.showMessage(getString(R.string.message_advice));
                break;
            case Constants.REQUEST_PLACE_AUTOCOMPLETE_CODE:
                if (resultCode == RESULT_OK) {
                    Place place = PlaceAutocomplete.getPlace(getActivity(), data);
                    presenter.getAddress(place.getLatLng().latitude,
                            place.getLatLng().longitude);
                } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                    presenter.showMessage(getString(R.string.message_no_known_location));
                } else if (resultCode == RESULT_CANCELED) {
                    // The user canceled the operation.
                }

                break;
        }
    }

    private void checkPermissionsAndGetLocation() {
        if (hasNoPermissions()) {
            presenter.requestPermissions();
        } else {
            presenter.getLocationResult();
        }
    }

    @Override
    public void getAddressFromGeoCoder(double latitude, double longitude) {
        mLatitude = latitude;
        mLongitude = longitude;
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
        } catch (IOException ioException) {
            presenter.showMessage(getString(R.string.message_no_known_location));
        }
        if (addresses == null || addresses.size() == 0) {
            presenter.showMessage(getString(R.string.message_no_known_location));
        } else {
            Address address = addresses.get(0);
            mCountry = address.getCountryName();
            mCity = address.getLocality();
            addressValidation();
            presenter.hadAddress();
        }
    }

    @Override
    public void showMessage(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void closeWithResult() {
        String address = "";
        if (mCity == null && mCountry == null) return;
        if (!mCity.equals(getString(R.string.validation_city))
                && !mCountry.equals(getString(R.string.validation_country)))
            address = String.format("%s, %s", mCity, mCountry);
        if (mCity.equals(getString(R.string.validation_city))
                && !mCountry.equals(getString(R.string.validation_country)))
            address = String.format("%s", mCountry);
        if (!mCity.equals(getString(R.string.validation_city))
                && mCountry.equals(getString(R.string.validation_country)))
            address = String.format("%s", mCity);
        CurrentLocation currentLocation = new CurrentLocation();
        currentLocation.address = address;
        currentLocation.city = mCity;
        currentLocation.country = mCountry;
        currentLocation.latitude = (float) mLatitude;
        currentLocation.longitude = (float) mLongitude;
        mLocationManager.updateLocation(currentLocation);
        mActivity.replaceFragment(TodayWeatherFragment_.builder().build());
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {
        presenter.showMessage(getString(R.string.message_suspended_connection));
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        presenter.showMessage(getString(R.string.message_failed_connection));
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        } else
            presenter.showMessage(getString(R.string.message_not_connected));
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
            mGoogleApiClient.stopAutoManage(getActivity());
        }
    }

}
