package com.shtainyky.weatherviewer.presentation.location;

/**
 * Created by Bell on 15.06.2017.
 */

public class LocationPresenter implements LocationContract.LocationPresenter {
    private LocationContract.LocationView view;


    public LocationPresenter(LocationContract.LocationView view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void subscribe() {
        view.setLastKnownAddress("cou", "city");
    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void showMessage(String message) {
        view.showMessage(message);
    }

    @Override
    public void onSearchButtonClick() {
        view.requestPlaces();
    }

    @Override
    public void onShowButtonClick() {
        view.closeWithResult();
    }

    @Override
    public void onCurrentLocationClick() {
        view.requestGPS();
    }

    @Override
    public void requestPermissions() {
        view.requestPermission();
    }

    @Override
    public void showDialogForPermissions(boolean isFistDialog) {
        if (isFistDialog) {
            view.showDialogRequestPermissions();
        } else {
            view.showDialogWithExplanation();
        }
    }

    @Override
    public void showStandardRequestPermission() {
        view.showDialogRequestPermissions();
    }

    @Override
    public void showDialogForPermissionsAfterDontAskAgain() {
        view.showMessageAfterDontAskAgain();
    }

    @Override
    public void getLocationResult() {
        view.getLocationResult();
    }


    @Override
    public void getAddress(double latitude, double longitude) {
        view.getAddressFromGeoCoder(latitude, longitude);
    }

    @Override
    public void hadAddress() {
        view.makeVisibleAddress();
    }
}
