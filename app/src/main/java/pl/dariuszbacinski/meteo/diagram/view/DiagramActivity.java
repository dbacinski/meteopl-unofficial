package pl.dariuszbacinski.meteo.diagram.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.jakewharton.rxbinding.support.v4.view.RxViewPager;

import pl.dariuszbacinski.meteo.R;
import pl.dariuszbacinski.meteo.databinding.ActivityDiagramBinding;
import pl.dariuszbacinski.meteo.diagram.viewmodel.DiagramItemViewModel.Legend;
import pl.dariuszbacinski.meteo.diagram.viewmodel.DiagramPagerViewModel;
import pl.dariuszbacinski.meteo.diagram.viewmodel.DiagramViewModel;
import pl.dariuszbacinski.meteo.location.model.FavoriteLocationRepository;
import rx.Subscription;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


public class DiagramActivity extends AppCompatActivity {

    private ActivityDiagramBinding diagramBinding;
    private DiagramViewModel diagramViewModel;
    private Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        diagramViewModel = new DiagramViewModel(this);
        diagramBinding = ActivityDiagramBinding.inflate(getLayoutInflater());
        setContentView(diagramBinding.getRoot());
        setupToolbar(diagramBinding);
        setupViewPager(diagramBinding);
    }

    @Override
    protected void onStop() {
        subscription.unsubscribe();
        super.onStop();
    }

    private void setupToolbar(ActivityDiagramBinding diagramBinding) {
        setSupportActionBar(diagramBinding.toolbar);
        //TODO scroll toolbar off screen in landscape mode
    }

    private void setupViewPager(final ActivityDiagramBinding diagramBinding) {
        diagramBinding.tabs.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(diagramBinding.pager));
        //TODO reset zoom on page change
        diagramBinding.pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(diagramBinding.tabs));
        DiagramPagerViewModel diagramPagerViewModel = loadFavoriteLocations(diagramBinding);
        restoreSelectedTab(diagramBinding.pager, diagramBinding.getPagerViewModel());
        subscription = RxViewPager.pageSelections(diagramBinding.pager).observeOn(Schedulers.io()).subscribe(new SaveSelectedDiagramPositionAction(diagramPagerViewModel));
    }

    private DiagramPagerViewModel loadFavoriteLocations(ActivityDiagramBinding diagramBinding) {
        DiagramPagerViewModel diagramPagerViewModel = new DiagramPagerViewModel(new FavoriteLocationRepository().findAll(), new Legend(getString(R.string.legend)));
        diagramViewModel.startLocationActivityWhenNoFavoriteLocations(diagramPagerViewModel.getCount());
        setViewModel(diagramBinding, diagramPagerViewModel);
        return  diagramPagerViewModel;
    }

    private void setViewModel(ActivityDiagramBinding diagramBinding, DiagramPagerViewModel diagramPagerViewModel) {
        diagramBinding.setPagerViewModel(diagramPagerViewModel);
        diagramBinding.setActivityViewModel(diagramViewModel);
        diagramBinding.executePendingBindings();
        diagramBinding.tabs.setTabsFromPagerAdapter(diagramBinding.pager.getAdapter());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        loadFavoriteLocations(diagramBinding);
        restoreSelectedTab(diagramBinding.pager, diagramBinding.getPagerViewModel());
    }

    private void restoreSelectedTab(HackyViewPager pager, DiagramPagerViewModel pagerViewModel) {
        pager.setCurrentItem(pagerViewModel.loadSelectedDiagramPosition());
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
                diagramViewModel.startLocationActivity();
                return true;
            }
            case R.id.action_info: {
                diagramViewModel.startInfoActivity();
                return true;
            }
            case R.id.action_show_legend: {
                if (diagramBinding.getPagerViewModel().addLegend()) {
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

    private static class SaveSelectedDiagramPositionAction implements Action1<Integer> {

        private DiagramPagerViewModel diagramPagerViewModel;

        public SaveSelectedDiagramPositionAction(DiagramPagerViewModel diagramPagerViewModel) {
            this.diagramPagerViewModel = diagramPagerViewModel;
        }

        @Override
        public void call(Integer integer) {
            diagramPagerViewModel.saveSelectedDiagramPosition(integer);
        }
    }
}
