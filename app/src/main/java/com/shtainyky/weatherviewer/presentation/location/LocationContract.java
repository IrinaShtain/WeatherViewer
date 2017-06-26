package com.shtainyky.weatherviewer.presentation.location;

import com.shtainyky.weatherviewer.presentation.base.BasePresenter;
import com.shtainyky.weatherviewer.presentation.base.BaseView;

/**
 * Created by Bell on 15.06.2017.
 */

public interface LocationContract {
    interface LocationPresenter extends BasePresenter {

        void onShowButtonClick();
        void onSearchButtonClick();
        void onCurrentLocationClick();

        void getLocationResult();
        void requestPermissions();
        void hadAddress();
        void getAddress(double latitude, double longitude);
        void showMessage(String message);
        void showDialogForPermissions(boolean isFistDialog);
        void showDialogForPermissionsAfterDontAskAgain();
        void showStandardRequestPermission();
    }

    interface LocationView extends BaseView<LocationPresenter> {
        void setLastKnownAddress(String country, String city);
        void requestGPS();
        void requestPermission();
        void showDialogWithExplanation();
        void showMessageAfterDontAskAgain();
        void showDialogRequestPermissions();
        void requestPlaces();
        void getLocationResult();
        void getAddressFromGeoCoder(double latitude, double longitude);
        void makeVisibleAddress();
        void closeWithResult();
        void showMessage(String message);
    }
}
