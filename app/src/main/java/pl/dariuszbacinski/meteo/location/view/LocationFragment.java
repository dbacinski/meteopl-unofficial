package pl.dariuszbacinski.meteo.location.view;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.SearchView;

import com.bignerdranch.android.multiselector.MultiSelector;
import com.jakewharton.rxbinding.widget.RxSearchView;

import java.util.List;

import pl.dariuszbacinski.meteo.R;
import pl.dariuszbacinski.meteo.WeatherApplication;
import pl.dariuszbacinski.meteo.databinding.FragmentLocationBinding;
import pl.dariuszbacinski.meteo.diagram.view.DiagramActivity;
import pl.dariuszbacinski.meteo.location.model.FavoriteLocationRepository;
import pl.dariuszbacinski.meteo.location.model.Location;
import pl.dariuszbacinski.meteo.location.model.LocationRepository;
import pl.dariuszbacinski.meteo.location.viewmodel.LocationAdapter;
import pl.dariuszbacinski.meteo.location.viewmodel.LocationListViewModel;
import pl.dariuszbacinski.meteo.ui.SnackbarLightBuilder;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class LocationFragment extends Fragment {

    public static final String MULTI_SELECTOR_STATE = "multiselector";
    //TODO checked should be part of IndexedLocation
    //TODO move to viewmodel
    private MultiSelector multiSelector = new MultiSelector();
    private FavoriteLocationRepository favoriteLocationRepository = new FavoriteLocationRepository();
    private LocationRepository locationRepository = new LocationRepository();
    private Subscription watcherSubscription;
    private FragmentLocationBinding locationBinding;
    private LocationAdapter locationAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        locationBinding = FragmentLocationBinding.inflate(inflater, container, false);
        LocationListViewModel locationListViewModel = new LocationListViewModel(multiSelector, locationRepository.findAll(), favoriteLocationRepository.findAll());
        locationAdapter = new LocationAdapter(locationListViewModel);
        locationBinding.favoritesList.setHasFixedSize(true);
        locationBinding.favoritesList.setAdapter(locationAdapter);
        locationBinding.favoritesList.setLayoutManager(new LinearLayoutManager(getActivity()));
        locationBinding.setFragment(this);
        setHasOptionsMenu(true);
        //TODO show save only when data where changed
        return locationBinding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI | EditorInfo.IME_FLAG_NO_FULLSCREEN | EditorInfo.IME_ACTION_SEARCH);
        searchView.setIconified(false);
        searchView.clearFocus();
        //TODO add autocomplete to searchView
        watcherSubscription = RxSearchView.queryTextChanges(searchView).throttleLast(300L, MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new FilterLocationByNameAction(locationAdapter));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle(MULTI_SELECTOR_STATE, multiSelector.saveSelectionStates());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            multiSelector.restoreSelectionStates(savedInstanceState.getBundle(MULTI_SELECTOR_STATE));
        }
    }

    public void saveFavorites(View view) {
        final List<Location> selectedLocations = locationAdapter.getSelectedLocations();
        //TODO delegate to viewmodel
        favoriteLocationRepository.saveList(selectedLocations);
        if (selectedLocations.size() == 0) {
            new SnackbarLightBuilder().make(getView(), R.string.location_no_locations_selected, Snackbar.LENGTH_LONG).show();
        } else {
            startActivity(new Intent(getActivity(), DiagramActivity.class).setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
            getActivity().finish();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        watcherSubscription.unsubscribe();
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
}
