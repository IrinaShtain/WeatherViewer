package com.shtainyky.weatherviewer.presentation.splash;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import com.shtainyky.weatherviewer.R;
import com.shtainyky.weatherviewer.presentation.MainActivity_;
import com.shtainyky.weatherviewer.presentation.base.BaseActivity;
import com.shtainyky.weatherviewer.utils.AnimFactory;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Bell on 14.06.2017.
 */

@EActivity(R.layout.activity_splash)
public class SplashActivity extends BaseActivity implements SplashContract.SplashView {


    @ViewById
    protected ImageView iv_logo;
    @ViewById
    protected TextView tv_logo;

    private SplashContract.SplashPresenter mPresenter;

    @AfterViews
    protected void initUI() {
        mPresenter.subscribe();
    }


    @AfterInject
    @Override
    public void initPresenter() {
        new SplashPresenter(this);
    }

    @Override
    public void setPresenter(SplashContract.SplashPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void runSplashAnimation() {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(AnimFactory.transY(tv_logo)
                .setValues(50f, 0f, 0f)
                .get())
                .with(AnimFactory.alpha(tv_logo)
                        .setValues(0f, 0.3f, 0.7f, 1f)
                        .get())
                .with(AnimFactory.alpha(iv_logo)
                        .setValues(0f, 0.2f, 0.4f, 0.6f, 1f)
                        .get())
                .with(AnimFactory.scaleX(iv_logo)
                        .setValues(0.8f, 1.2f, 1f, 1.1f)
                        .get())
                .with(AnimFactory.scaleY(iv_logo)
                        .setValues(0.8f, 1.2f, 1f, 1.1f)
                        .get())
                .before(AnimFactory.values(0, 0)
                        .setDuration(2000)
                        .get());
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mPresenter.startNextScreen();
            }
        });
        animatorSet.setDuration(1000);
        animatorSet.start();

    }

    @Override
    public void startHomeScreen() {
        MainActivity_.intent(this)
                .flags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK)
                .start();
        finish();
    }

    @Override
    public void startLoginScreen() {

//nothing
    }

    @Override
    protected int getContainerId() {
        return 0;
    }
}
