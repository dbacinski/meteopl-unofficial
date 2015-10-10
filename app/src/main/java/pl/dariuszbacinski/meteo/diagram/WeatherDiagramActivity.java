package pl.dariuszbacinski.meteo.diagram;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import pl.dariuszbacinski.meteo.R;
import pl.dariuszbacinski.meteo.location.FavoriteLocationRepository;
import pl.dariuszbacinski.meteo.location.LocationActivity;
import pl.dariuszbacinski.meteo.location.LocationTransformation;


public class WeatherDiagramActivity extends AppCompatActivity {

    private WeatherDiagramPagerAdapter weatherDiagramPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        weatherDiagramPagerAdapter = new WeatherDiagramPagerAdapter(getFragmentManager(), new LocationTransformation(new FavoriteLocationRepository().findAll()), new CurrentDateProvider());
        startLocationActivityWhenNoFavoriteLocations(weatherDiagramPagerAdapter.getCount());
        setContentView(R.layout.activity_weather);

        ViewPager viewPager = createViewPager(weatherDiagramPagerAdapter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setOnTabSelectedListener(new TabSeletedUpdater(viewPager));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        weatherDiagramPagerAdapter.setLocations(new LocationTransformation(new FavoriteLocationRepository().findAll()));
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        tabLayout.setupWithViewPager(viewPager);
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

    private ViewPager createViewPager(WeatherDiagramPagerAdapter weatherDiagramPagerAdapter) {
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(weatherDiagramPagerAdapter);
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

    private static class TabSeletedUpdater implements TabLayout.OnTabSelectedListener {
        private final ViewPager viewPager;

        public TabSeletedUpdater(ViewPager viewPager) {
            this.viewPager = viewPager;
        }

        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            viewPager.setCurrentItem(tab.getPosition());
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    }
}
