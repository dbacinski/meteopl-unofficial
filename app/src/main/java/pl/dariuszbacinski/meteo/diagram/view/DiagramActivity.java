package pl.dariuszbacinski.meteo.diagram.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.jakewharton.rxbinding.support.v4.view.RxViewPager;

import hugo.weaving.DebugLog;
import pl.dariuszbacinski.meteo.R;
import pl.dariuszbacinski.meteo.component.log.AnswersLogger;
import pl.dariuszbacinski.meteo.component.rx.ReusableCompositeSubscription;
import pl.dariuszbacinski.meteo.databinding.ActivityDiagramBinding;
import pl.dariuszbacinski.meteo.diagram.viewmodel.DiagramItemViewModel.Legend;
import pl.dariuszbacinski.meteo.diagram.viewmodel.DiagramPagerViewModel;
import pl.dariuszbacinski.meteo.diagram.viewmodel.DiagramViewModel;
import pl.dariuszbacinski.meteo.location.model.FavoriteLocationRepository;
import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


public class DiagramActivity extends AppCompatActivity {

    private ActivityDiagramBinding diagramBinding;
    private DiagramViewModel diagramViewModel;
    private ReusableCompositeSubscription subscriptions = new ReusableCompositeSubscription();

    @DebugLog
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        diagramViewModel = new DiagramViewModel(this);
        diagramBinding = ActivityDiagramBinding.inflate(getLayoutInflater());
        setContentView(diagramBinding.getRoot());
        setupToolbar(diagramBinding);
        setupViewPager(diagramBinding);
    }

    @DebugLog
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        loadFavoriteLocations(diagramBinding);
    }

    @DebugLog
    @Override
    protected void onResume() {
        super.onResume();
        restoreSelectedTab(diagramBinding.pager, diagramBinding.getPagerViewModel());
        Observable<Integer> pageSelectionObservable = RxViewPager.pageSelections(diagramBinding.pager).observeOn(Schedulers.io());
        subscriptions.add(pageSelectionObservable.subscribe(new SaveSelectedDiagramPositionAction(diagramBinding.getPagerViewModel())));
        subscriptions.add(pageSelectionObservable.subscribe(new TrackingPositionAction(new AnswersLogger(), diagramBinding.getPagerViewModel())));
    }

    @DebugLog
    @Override
    protected void onStop() {
        subscriptions.unsubscribe();
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
        loadFavoriteLocations(diagramBinding);
    }

    private void loadFavoriteLocations(ActivityDiagramBinding diagramBinding) {
        DiagramPagerViewModel diagramPagerViewModel = new DiagramPagerViewModel(new FavoriteLocationRepository().findAll(), new Legend(getString(R.string.legend)));
        diagramViewModel.startLocationActivityWhenNoFavoriteLocations(diagramPagerViewModel.getCount());
        setViewModel(diagramBinding, diagramPagerViewModel);
    }

    private void setViewModel(ActivityDiagramBinding diagramBinding, DiagramPagerViewModel diagramPagerViewModel) {
        diagramBinding.setPagerViewModel(diagramPagerViewModel);
        diagramBinding.setActivityViewModel(diagramViewModel);
        diagramBinding.executePendingBindings();
        diagramBinding.tabs.setTabsFromPagerAdapter(diagramBinding.pager.getAdapter());
    }

    private void restoreSelectedTab(final HackyViewPager pager, DiagramPagerViewModel pagerViewModel) {
        final int currentItem = pagerViewModel.loadSelectedDiagramPosition();
        pager.setCurrentItemDelayed(currentItem);

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

    public static class SaveSelectedDiagramPositionAction implements Action1<Integer> {
        private DiagramPagerViewModel diagramPagerViewModel;

        public SaveSelectedDiagramPositionAction(DiagramPagerViewModel diagramPagerViewModel) {
            this.diagramPagerViewModel = diagramPagerViewModel;
        }

        @Override
        public void call(Integer position) {
            diagramPagerViewModel.saveSelectedDiagramPosition(position);
        }
    }

    public static class TrackingPositionAction implements Action1<Integer> {

        private DiagramPagerViewModel diagramPagerViewModel;
        private AnswersLogger answersLogger;

        public TrackingPositionAction(AnswersLogger answersLogger, DiagramPagerViewModel diagramPagerViewModel) {
            this.answersLogger = answersLogger;
            this.diagramPagerViewModel = diagramPagerViewModel;
        }

        @Override
        public void call(Integer position) {
            answersLogger.logDiagramView(diagramPagerViewModel.items.get(position));
        }
    }
}
