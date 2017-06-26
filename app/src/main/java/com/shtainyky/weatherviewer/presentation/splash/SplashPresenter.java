package com.shtainyky.weatherviewer.presentation.splash;

/**
 * Created by Bell on 14.06.2017.
 */
public class SplashPresenter implements SplashContract.SplashPresenter {

    private SplashContract.SplashView view;



    public SplashPresenter(SplashContract.SplashView view) {
        this.view = view;

        view.setPresenter(this);
    }


    @Override
    public void subscribe() {
        view.runSplashAnimation();
    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void startNextScreen() {
            view.startHomeScreen();
    }
}
