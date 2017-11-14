package com.shtainyky.weatherviewer.presentation;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.widget.ProgressBar;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.holder.DimenHolder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.shtainyky.weatherviewer.R;
import com.shtainyky.weatherviewer.presentation.base.BaseActivity;
import com.shtainyky.weatherviewer.presentation.location.LocationFragment_;
import com.shtainyky.weatherviewer.presentation.today.TodayWeatherFragment_;
import com.shtainyky.weatherviewer.presentation.week.WeekWeatherFragment_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @ViewById(R.id.toolbar)
    protected Toolbar toolbar;

    private Drawer result;


    @AfterViews
    protected void initUI() {
        toolbar.setTitle(R.string.main_name);
        setSupportActionBar(toolbar);
        setupNavigationDrawerMenu();
        replaceFragment(TodayWeatherFragment_.builder().build());

    }

    private void setupNavigationDrawerMenu() {
        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withActionBarDrawerToggle(true)
                .withHeader(R.layout.activity_navigation_view_header)
                .withHeaderHeight(DimenHolder.fromDp(175))
                .withSelectedItem(-1)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.today_weather)
                                .withIcon(R.drawable.ic_today)
                                .withIdentifier(0)
                                .withSelectedColor(Color.WHITE)
                                .withSelectedTextColor(ContextCompat.getColor(this, R.color.colorAccent))
                                .withTextColor(Color.BLACK),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName(R.string.week_weather)
                                .withIcon(R.drawable.ic_week)
                                .withIdentifier(1)
                                .withSelectedColor(Color.WHITE)
                                .withSelectedTextColor(ContextCompat.getColor(this, R.color.colorAccent))
                                .withTextColor(Color.BLACK),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName(R.string.choose_location)
                                .withIcon(R.drawable.ic_map_point)
                                .withIdentifier(2)
                                .withSelectedColor(Color.WHITE)
                                .withSelectedTextColor(ContextCompat.getColor(this, R.color.colorAccent))
                                .withTextColor(Color.BLACK))
                .withOnDrawerItemClickListener((view, position, drawerItem) -> {
                    // do something with the clicked item :D
                    switch ((int) drawerItem.getIdentifier()) {
                        case 0:
                            replaceFragment(TodayWeatherFragment_.builder().build());
                            break;
                        case 1:
                            replaceFragment(WeekWeatherFragment_.builder().build());
                            break;
                        case 2:
                            replaceFragment(LocationFragment_.builder().build());
                            break;
                    }
                    return false;
                })
                .build();
    }

    @Override
    public void onBackPressed() {
        // Закрываем Navigation Drawer по нажатию системной кнопки "Назад" если он открыт
        if (result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }


    @Override
    protected int getContainerId() {
        return R.id.flContent;
    }


}
