package com.shtainyky.weatherviewer.presentation.week.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shtainyky.weatherviewer.R;
import com.shtainyky.weatherviewer.presentation.week.adapter.WeatherDH;
import com.shtainyky.weatherviewer.presentation.week.adapter.WeatherVH;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bell on 19.06.2017.
 */
@EBean
public class WeekWeatherAdapter extends RecyclerView.Adapter<WeatherVH> {
    private List<WeatherDH> items;

    public WeekWeatherAdapter() {
        items = new ArrayList<>();
    }

    public void setListDH(List<WeatherDH> listsDHs) {
        items = listsDHs;
        notifyDataSetChanged();
    }


    @Override
    public WeatherVH onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_weather, parent, false);
        return new WeatherVH(view);

    }

    @Override
    public void onBindViewHolder(WeatherVH holder, int position) {
        holder.bindData(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
