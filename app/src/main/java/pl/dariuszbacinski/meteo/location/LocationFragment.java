package pl.dariuszbacinski.meteo.location;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.android.multiselector.MultiSelector;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.List;

import pl.dariuszbacinski.meteo.R;
import pl.dariuszbacinski.meteo.WeatherApplication;
import pl.dariuszbacinski.meteo.databinding.FragmentLocationBinding;
import pl.dariuszbacinski.meteo.diagram.DiagramActivity;
import pl.dariuszbacinski.meteo.ui.SnackbarLightBuilder;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class LocationFragment extends Fragment {

    public static final String MULTI_SELECTOR_STATE = "multiselector";
    //TODO try to replace with observable
    private MultiSelector multiSelector = new MultiSelector();
    private LocationAdapter locationAdapter;
    private FavoriteLocationRepository favoriteLocationRepository = new FavoriteLocationRepository();
    private LocationRepository locationRepository = new LocationRepository();
    private Subscription watcherSubscription;
    private FragmentLocationBinding locationBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        locationBinding = FragmentLocationBinding.inflate(inflater, container, false);
        locationAdapter = new LocationAdapter(multiSelector, locationRepository.findAll(), favoriteLocationRepository.findAll());
        locationBinding.favoritesList.setHasFixedSize(true);
        locationBinding.favoritesList.setAdapter(locationAdapter);
        locationBinding.favoritesList.setLayoutManager(new LinearLayoutManager(getActivity()));
        locationBinding.setFragment(this);
        watcherSubscription = RxTextView.textChanges(locationBinding.favoritesFilter).throttleLast(100L, MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new FilterLocationByNameAction(locationAdapter));
        return locationBinding.getRoot();
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
        //TODO model should be responsible for saving favorites
        final List<Location> selectedLocations = locationAdapter.getSelectedLocations();
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
