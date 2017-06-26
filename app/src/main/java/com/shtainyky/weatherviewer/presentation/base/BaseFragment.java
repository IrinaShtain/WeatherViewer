package com.shtainyky.weatherviewer.presentation.base;

import android.content.Context;
import android.support.v4.app.Fragment;

import org.androidannotations.annotations.EFragment;

/**
 * Created by Bell on 13.06.2017.
 */

@EFragment
public abstract class BaseFragment extends Fragment {

    protected BaseActivity mActivity;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (BaseActivity) context;
    }



}