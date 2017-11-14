package com.shtainyky.weatherviewer.presentation.week.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shtainyky.weatherviewer.R;
import com.shtainyky.weatherviewer.presentation.week.adapter.WeatherDH;
import com.squareup.picasso.Picasso;

/**
 * Created by Bell on 19.06.2017.
 */

public class WeatherVH extends RecyclerView.ViewHolder {
    private final TextView tv_currentDay_desc;
    private final TextView tv_minTemp;
    private final TextView tv_maxTemp;
    private final TextView tv_humidity;
    private final ImageView iv_Weather;

    private Context mContext;

    public WeatherVH(View itemView) {
        super(itemView);
        mContext = itemView.getContext();
        tv_currentDay_desc = (TextView) itemView.findViewById(R.id.tv_currentDay_desc);
        tv_minTemp = (TextView) itemView.findViewById(R.id.tv_minTemp);
        tv_maxTemp = (TextView) itemView.findViewById(R.id.tv_maxTemp);
        tv_humidity = (TextView) itemView.findViewById(R.id.tv_humidity);
        iv_Weather = (ImageView) itemView.findViewById(R.id.iv_Weather);
    }

    public void bindData(WeatherDH weatherDH) {
        tv_currentDay_desc.setText(weatherDH.getCurrentWeatherDesc());
        tv_minTemp.setText(mContext.getResources().getString(R.string.low_s, weatherDH.getLowTemp()));
        tv_maxTemp.setText(mContext.getResources().getString(R.string.max_s, weatherDH.getMaxTemp()));
        tv_humidity.setText(mContext.getResources().getString(R.string.humidity_s, weatherDH.getHum()));

        Picasso.with(mContext)
                .load(weatherDH.getIconUrl())
                .error(R.drawable.ic_today)
                .into(iv_Weather);

    }
}
