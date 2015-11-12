package pl.dariuszbacinski.meteo.diagram.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import pl.dariuszbacinski.meteo.R;
import pl.dariuszbacinski.meteo.databinding.ActivityDiagramBinding;
import pl.dariuszbacinski.meteo.diagram.viewmodel.DiagramItemViewModel.Legend;
import pl.dariuszbacinski.meteo.diagram.viewmodel.DiagramPagerViewModel;
import pl.dariuszbacinski.meteo.info.InfoActivity;
import pl.dariuszbacinski.meteo.location.FavoriteLocationRepository;
import pl.dariuszbacinski.meteo.location.LocationActivity;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;


public class DiagramActivity extends AppCompatActivity {

    private ActivityDiagramBinding diagramBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        diagramBinding = ActivityDiagramBinding.inflate(getLayoutInflater());
        setContentView(diagramBinding.getRoot());
        setupToolbar(diagramBinding);
        setupViewPager(diagramBinding);
        loadFavoriteLocations(diagramBinding);
    }

    private void setupToolbar(ActivityDiagramBinding diagramBinding) {
        setSupportActionBar(diagramBinding.toolbar);
        diagramBinding.toolbar.setVisibility(getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE ? View.GONE : View.VISIBLE);
    }

    private void setupViewPager(ActivityDiagramBinding diagramBinding) {
        diagramBinding.tabs.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(diagramBinding.pager));
        diagramBinding.pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(diagramBinding.tabs));
    }

    private void loadFavoriteLocations(ActivityDiagramBinding diagramBinding) {
        DiagramPagerViewModel diagramPagerViewModel = new DiagramPagerViewModel(new FavoriteLocationRepository().findAll(), new Legend(getString(R.string.legend)));
        startLocationActivityWhenNoFavoriteLocations(diagramPagerViewModel.getCount());
        setViewModel(diagramBinding, diagramPagerViewModel);
    }

    private void setViewModel(ActivityDiagramBinding diagramBinding, DiagramPagerViewModel diagramPagerViewModel) {
        diagramBinding.setViewModel(diagramPagerViewModel);
        diagramBinding.executePendingBindings();
        diagramBinding.tabs.setTabsFromPagerAdapter(diagramBinding.pager.getAdapter());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        loadFavoriteLocations(diagramBinding);
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
        getMenuInflater().inflate(R.menu.menu_diagram, menu);
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
            case R.id.action_show_legend: {
                if (diagramBinding.getViewModel().addLegend()) {
                    diagramBinding.tabs.setTabsFromPagerAdapter(diagramBinding.pager.getAdapter());
                }
                diagramBinding.pager.scrollToLastElement();
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
}
