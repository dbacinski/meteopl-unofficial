package pl.dariuszbacinski.meteo.location.view;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckedTextView;
import android.widget.SearchView;

import com.bignerdranch.android.multiselector.MultiSelector;
import com.jakewharton.rxbinding.widget.RxSearchView;

import org.parceler.Parcels;

import java.util.List;

import hugo.weaving.DebugLog;
import pl.dariuszbacinski.meteo.BuildConfig;
import pl.dariuszbacinski.meteo.R;
import pl.dariuszbacinski.meteo.WeatherApplication;
import pl.dariuszbacinski.meteo.component.rx.ReusableCompositeSubscription;
import pl.dariuszbacinski.meteo.component.view.SnackbarLightBuilder;
import pl.dariuszbacinski.meteo.databinding.FragmentLocationBinding;
import pl.dariuszbacinski.meteo.diagram.view.DiagramActivity;
import pl.dariuszbacinski.meteo.inject.ApplicationModule;
import pl.dariuszbacinski.meteo.inject.DaggerApplicationComponent;
import pl.dariuszbacinski.meteo.inject.DaggerLocationComponent;
import pl.dariuszbacinski.meteo.inject.LocationComponent;
import pl.dariuszbacinski.meteo.inject.LocationModule;
import pl.dariuszbacinski.meteo.location.model.FavoriteLocationRepository;
import pl.dariuszbacinski.meteo.location.model.Location;
import pl.dariuszbacinski.meteo.location.model.LocationRepository;
import pl.dariuszbacinski.meteo.location.viewmodel.CoarseLocationItemViewModel;
import pl.dariuszbacinski.meteo.location.viewmodel.CoarseLocationViewModelAdapter;
import pl.dariuszbacinski.meteo.location.viewmodel.LocationListViewModel;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

import static com.eccyan.optional.Optional.ofNullable;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

@DebugLog
public class LocationFragment extends Fragment {

    private static final String MULTI_SELECTOR_STATE = "multiselector";
    private static final String COARSE_LOCATION_STATE = "location";
    //TODO move to viewmodel, checked should be part of IndexedLocation
    private MultiSelector multiSelector = new MultiSelector();
    private FavoriteLocationRepository favoriteLocationRepository = new FavoriteLocationRepository();
    private LocationRepository locationRepository = new LocationRepository();
    private FragmentLocationBinding locationBinding;
    private LocationAdapter locationAdapter;
    private ReusableCompositeSubscription subscriptions = new ReusableCompositeSubscription();
    private CoarseLocationViewModelAdapter coarseLocationViewModelAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        locationBinding = FragmentLocationBinding.inflate(inflater, container, false);
        locationAdapter = bindLocationList(locationBinding);
        coarseLocationViewModelAdapter = restoreInstanceState(savedInstanceState, multiSelector);
        requestCoarseLocation();
        bindCoarseLocationView(locationBinding);
        setHasOptionsMenu(true);
        return locationBinding.getRoot();
    }

    private void requestCoarseLocation() {
        if (coarseLocationViewModelAdapter.getLocation() == null) {
            //TODO move location request to on click
            LocationComponent locationComponent = createLocationComponent();
            subscriptions.add(coarseLocationViewModelAdapter.requestLocation(locationComponent.coarseLocation(), getActivity().getApplicationContext().getString(R.string.location_gps_error)));
            if (BuildConfig.DEBUG && isEmulator()) {
                subscriptions.add(coarseLocationViewModelAdapter.mockLocation(getActivity().getApplicationContext()));
            }
        }
    }

    private LocationComponent createLocationComponent() {
        return LocationComponentProvider.get(getActivity().getApplicationContext());
    }

    private boolean isEmulator() {
        return "goldfish".equals(Build.HARDWARE) || "vbox86".equals(Build.HARDWARE);
    }

    private LocationAdapter bindLocationList(FragmentLocationBinding locationBinding) {
        LocationListViewModel locationListViewModel = new LocationListViewModel(multiSelector, locationRepository.findAll(), favoriteLocationRepository.findAll());
        LocationAdapter locationAdapter = new LocationAdapter(locationListViewModel);
        locationBinding.favoritesList.setHasFixedSize(true);
        locationBinding.favoritesList.setAdapter(locationAdapter);
        locationBinding.favoritesList.setLayoutManager(new LinearLayoutManager(getActivity()));
        locationBinding.setFragment(this);
        return locationAdapter;
    }

    private void bindCoarseLocationView(FragmentLocationBinding locationBinding) {
        CoarseLocationItemViewModel locationItemViewModel = coarseLocationViewModelAdapter.getLocationItemViewModel();
        locationBinding.setLocation(locationItemViewModel);
        locationBinding.locationGpsName.setItem(locationItemViewModel);
        CoarseLocationClickListener listener = new CoarseLocationClickListener(locationItemViewModel);
        locationBinding.setListener(listener);
        locationBinding.locationGpsName.setListener(listener);
    }

    private CoarseLocationViewModelAdapter restoreInstanceState(Bundle savedInstanceState, MultiSelector multiSelector) {
        if (savedInstanceState != null) {
            multiSelector.restoreSelectionStates(ofNullable(savedInstanceState.getBundle(MULTI_SELECTOR_STATE)).orElse(new Bundle()));
            return Parcels.unwrap(savedInstanceState.getParcelable(COARSE_LOCATION_STATE));
        } else {
            CoarseLocationViewModelAdapter coarseLocationViewModelAdapter = new CoarseLocationViewModelAdapter();
            coarseLocationViewModelAdapter.setLocationItemViewModel(new CoarseLocationItemViewModel(getString(R.string.location_gps_loading), false, R.drawable.ic_gps_not_fixed));
            return coarseLocationViewModelAdapter;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle(MULTI_SELECTOR_STATE, multiSelector.saveSelectionStates());
        outState.putParcelable(COARSE_LOCATION_STATE, Parcels.wrap(coarseLocationViewModelAdapter));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI | EditorInfo.IME_FLAG_NO_FULLSCREEN | EditorInfo.IME_ACTION_SEARCH);
        searchView.setIconified(false);
        searchView.clearFocus();
        searchView.setQueryHint(getString(R.string.location_choose_favorite));
        subscribeToSearchViewQueries(searchView);
    }

    private void subscribeToSearchViewQueries(SearchView searchView) {
        subscriptions.add(RxSearchView.queryTextChanges(searchView).throttleLast(300L, MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new FilterLocationByNameAction(locationAdapter)));
    }

    public void saveFavorites(View view) {
        final List<Location> selectedLocations = locationAdapter.getSelectedLocations();
        List<Location> selectedCoarseLocation = coarseLocationViewModelAdapter.getSelectedCoarseLocation();
        saveCoarseLocation(selectedCoarseLocation);
        selectedLocations.addAll(selectedCoarseLocation);

        //TODO delegate to viewmodel
        favoriteLocationRepository.saveList(selectedLocations);
        if (selectedLocations.size() == 0) {
            new SnackbarLightBuilder().make(getView(), R.string.location_no_locations_selected, Snackbar.LENGTH_LONG).show();
        } else {
            startActivity(new Intent(getActivity(), DiagramActivity.class).setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
            getActivity().finish();
        }
    }

    private void saveCoarseLocation(List<Location> selectedCoarseLocation) {
        for (Location coarseLocation : selectedCoarseLocation) {
            coarseLocation.save();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        subscriptions.unsubscribe();
        locationBinding = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        WeatherApplication.getRefWatcher(getActivity()).watch(this);
    }

    private static class FilterLocationByNameAction implements Action1<CharSequence> {

        private LocationAdapter locationAdapter;

        public FilterLocationByNameAction(LocationAdapter locationAdapter) {
            this.locationAdapter = locationAdapter;
        }

        @Override
        public void call(CharSequence charSequence) {
            locationAdapter.filterLocationsByName(charSequence.toString());
        }
    }

    private static class CoarseLocationClickListener implements View.OnClickListener {

        private CoarseLocationItemViewModel locationItemViewModel;

        public CoarseLocationClickListener(CoarseLocationItemViewModel locationItemViewModel) {
            this.locationItemViewModel = locationItemViewModel;
        }

        @Override
        public void onClick(View view) {
            CheckedTextView checkedTextView = (CheckedTextView) view.findViewById(R.id.location_gps_name);
            checkedTextView.toggle();
            locationItemViewModel.setChecked(checkedTextView.isChecked());
        }
    }

    public static class LocationComponentProvider {

        @VisibleForTesting
        public static void setTestLocationComponent(LocationComponent testLocationComponent) {
            LocationComponentProvider.testLocationComponent = testLocationComponent;
        }

        private static LocationComponent testLocationComponent;

        static LocationComponent get(Context applicationContext) {
            if (testLocationComponent != null) {
                return testLocationComponent;
            }
            return DaggerLocationComponent.builder().applicationComponent(DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(applicationContext)).build()).locationModule(new LocationModule()).build();
        }
    }
}
