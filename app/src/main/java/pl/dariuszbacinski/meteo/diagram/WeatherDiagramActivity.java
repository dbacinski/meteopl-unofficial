package pl.dariuszbacinski.meteo.diagram;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import pl.dariuszbacinski.meteo.R;
import pl.dariuszbacinski.meteo.favorite.FavoriteWeatherDataProvider;


public class WeatherDiagramActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        WeatherDiagramPagerAdapter weatherDiagramPagerAdapter = new WeatherDiagramPagerAdapter(getFragmentManager(), new FavoriteWeatherDataProvider(), new CurrentDateProvider());
        ViewPager viewPager = createViewPager(weatherDiagramPagerAdapter, new OnPageChangeUpdater(getActionBar()));
        configureTabbedActionBar(getActionBar(), new OnTabChangeUpdater(viewPager), weatherDiagramPagerAdapter);
    }

    private ViewPager createViewPager(WeatherDiagramPagerAdapter weatherDiagramPagerAdapter, OnPageChangeUpdater listener) {
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(weatherDiagramPagerAdapter);
        viewPager.addOnPageChangeListener(listener);
        return viewPager;
    }

    private ActionBar configureTabbedActionBar(ActionBar actionBar, ActionBar.TabListener listener, WeatherDiagramPagerAdapter weatherDiagramPagerAdapter) {
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        for (int i = 0; i < weatherDiagramPagerAdapter.getCount(); i++) {
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(weatherDiagramPagerAdapter.getPageTitle(i))
                            .setTabListener(listener));
        }

        return actionBar;
    }

    public static class OnPageChangeUpdater extends ViewPager.SimpleOnPageChangeListener {

        private ActionBar actionBar;

        public OnPageChangeUpdater(ActionBar actionBar) {
            this.actionBar = actionBar;
        }

        @Override
        public void onPageSelected(int position) {
            actionBar.setSelectedNavigationItem(position);
        }
    }

    public static class OnTabChangeUpdater implements ActionBar.TabListener {

        private ViewPager viewPager;

        public OnTabChangeUpdater(ViewPager viewPager) {
            this.viewPager = viewPager;
        }

        @Override
        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
            viewPager.setCurrentItem(tab.getPosition());
        }

        @Override
        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

        }

        @Override
        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

        }
    }
}
