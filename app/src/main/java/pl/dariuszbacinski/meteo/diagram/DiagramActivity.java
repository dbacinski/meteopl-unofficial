package pl.dariuszbacinski.meteo.diagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import pl.dariuszbacinski.meteo.R;
import pl.dariuszbacinski.meteo.databinding.ActivityDiagramBinding;
import pl.dariuszbacinski.meteo.info.InfoActivity;
import pl.dariuszbacinski.meteo.location.FavoriteLocationRepository;
import pl.dariuszbacinski.meteo.location.LocationActivity;
import pl.dariuszbacinski.meteo.location.LocationTransformation;


public class DiagramActivity extends AppCompatActivity {

    private DiagramPagerAdapter diagramPagerAdapter;
    private ActivityDiagramBinding diagramBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        diagramPagerAdapter = new DiagramPagerAdapter(getFragmentManager(), new LocationTransformation(new FavoriteLocationRepository().findAll()));
        startLocationActivityWhenNoFavoriteLocations(diagramPagerAdapter.getCount());
        diagramBinding = ActivityDiagramBinding.inflate(getLayoutInflater());
        setContentView(diagramBinding.getRoot());
        setSupportActionBar(diagramBinding.toolbar);

        diagramBinding.pager.setAdapter(diagramPagerAdapter);
        diagramBinding.tabs.setupWithViewPager(diagramBinding.pager);
        diagramBinding.tabs.setOnTabSelectedListener(new TabSelectedUpdater(diagramBinding.pager));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        diagramPagerAdapter.setLocations(new LocationTransformation(new FavoriteLocationRepository().findAll()));
        diagramBinding.tabs.setupWithViewPager(diagramBinding.pager);
        startLocationActivityWhenNoFavoriteLocations(diagramPagerAdapter.getCount());
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
            case R.id.action_info: {
                startInfoActivity();
                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }

    private void startInfoActivity() {
        startActivity(new Intent(this, InfoActivity.class));
    }

    private static class TabSelectedUpdater implements TabLayout.OnTabSelectedListener {
        private final ViewPager viewPager;

        public TabSelectedUpdater(ViewPager viewPager) {
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
