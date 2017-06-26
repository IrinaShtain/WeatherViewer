package com.shtainyky.weatherviewer.data.service;

import android.util.Log;

import com.github.aurae.retrofit2.LoganSquareConverterFactory;
import com.shtainyky.weatherviewer.WeatherApplication;
import com.shtainyky.weatherviewer.data.ecxeptions.ConnectionException;
import com.shtainyky.weatherviewer.data.ecxeptions.TimeoutException;
import com.shtainyky.weatherviewer.utils.Constants;

import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EBean;

import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Created by Bell on 15.06.2017.
 */
@EBean(scope = EBean.Scope.Singleton)
public class Rest {

    @App
    protected WeatherApplication application;


    private Retrofit retrofit;

    private WeatherService mService;


    public Rest() {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder()
                .connectTimeout(Constants.TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(Constants.TIMEOUT_READ, TimeUnit.SECONDS)
                .writeTimeout(Constants.TIMEOUT_WRITE, TimeUnit.SECONDS)
                .addInterceptor(chain -> {
                    try {
                        Log.e("myLog","chain called " + chain.request().url());
                        if (!application.hasInternetConnection()) {
                            throw new ConnectionException();
                        } else {
                            Request request = chain.request().newBuilder()
                                    .build();
                            return chain.proceed(request);
                        }
                    } catch (SocketTimeoutException e) {
                        throw new TimeoutException();
                    }
                });

        Log.e("myLog","Rest called ");
        retrofit = new Retrofit.Builder()
                .addConverterFactory(LoganSquareConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(clientBuilder.build())
                .baseUrl(Constants.BASE_URL)
                .build();

    }


    public WeatherService getWeatherService() {
        return mService == null ? mService = retrofit.create(WeatherService.class) : mService;
    }
}
