package com.shtainyky.weatherviewer.presentation.base.content;

import com.shtainyky.weatherviewer.utils.Constants;

/**
 * Created by Irina Shtain on 14.11.2017.
 */

public interface ContentView {
    void showProgressMain();
    void hideProgress();
    void showErrorMessage(Constants.MessageType messageType);
    void showCustomMessage(String msg, boolean isDangerous);
    void showPlaceholder(Constants.PlaceholderType placeholderType);
}
