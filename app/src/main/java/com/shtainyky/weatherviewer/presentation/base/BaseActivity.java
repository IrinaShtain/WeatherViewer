package com.shtainyky.weatherviewer.presentation.base;

import android.os.Build;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.androidannotations.annotations.EActivity;

import java.util.Collections;
import java.util.List;

/**
 * Created by Bell on 13.06.2017.
 */

@EActivity
public abstract class BaseActivity extends AppCompatActivity {

    @IdRes
    protected abstract int getContainerId();

    public void replaceFragment(BaseFragment fragment) {
        replaceFragment(fragment, Collections.<View>emptyList());
    }

    public void replaceFragment(BaseFragment fragment, List<View> sharedViews) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            for (View view : sharedViews) {
                transaction.addSharedElement(view, view.getTransitionName());
            }
        }

        transaction.replace(getContainerId(), fragment)
                .addToBackStack(null)
                .commit();
    }

}
