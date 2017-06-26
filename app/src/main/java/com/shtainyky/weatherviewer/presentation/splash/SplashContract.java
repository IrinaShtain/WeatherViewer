package com.shtainyky.weatherviewer.presentation.splash;

import com.shtainyky.weatherviewer.presentation.base.BasePresenter;
import com.shtainyky.weatherviewer.presentation.base.BaseView;

/**
 * Created by Bell on 14.06.2017.
 */

public interface SplashContract {
    interface SplashView extends BaseView<SplashPresenter> {
        void runSplashAnimation();

        void startHomeScreen();

        void startLoginScreen();
    }
    interface SplashPresenter extends BasePresenter {
        void startNextScreen();
    }
}
