package pl.dariuszbacinski.meteo.diagram;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import pl.dariuszbacinski.meteo.R;
import pl.dariuszbacinski.meteo.location.FavoriteLocationRepository;
import pl.dariuszbacinski.meteo.location.LocationActivity;
import pl.dariuszbacinski.meteo.location.LocationTransformation;


public class WeatherDiagramActivity extends Activity {

    private WeatherDiagramPagerAdapter weatherDiagramPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        weatherDiagramPagerAdapter = new WeatherDiagramPagerAdapter(getFragmentManager(), new LocationTransformation(new FavoriteLocationRepository().findAll()), new CurrentDateProvider());
        startLocationActivityWhenNoFavoriteLocations(weatherDiagramPagerAdapter.getCount());
        setContentView(R.layout.activity_weather);
        ViewPager viewPager = createViewPager(weatherDiagramPagerAdapter, new OnPageChangeUpdater(getActionBar()));
        configureTabbedActionBar(getActionBar(), new OnTabChangeUpdater(viewPager), weatherDiagramPagerAdapter);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        weatherDiagramPagerAdapter.setLocations(new LocationTransformation(new FavoriteLocationRepository().findAll()));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void startLocationActivityWhenNoFavoriteLocations(int numberOfFavoriteLocations) {
        if (numberOfFavoriteLocations == 0) {
            startLocationActivity();
            finish();
        }
    }

    private void startLocationActivity() {
        startActivity(new Intent(this, LocationActivity.class).setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_diagram, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_favorite: {
                startLocationActivity();
                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }
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
