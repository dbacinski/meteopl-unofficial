package pl.dariuszbacinski.meteo.location;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.android.multiselector.MultiSelector;
import com.jakewharton.rxbinding.widget.RxTextView;

import pl.dariuszbacinski.meteo.databinding.FragmentLocationBinding;
import pl.dariuszbacinski.meteo.diagram.DiagramActivity;
import rx.Subscription;
import rx.functions.Action1;

public class LocationFragment extends Fragment {

    public static final String MULTI_SELECTOR_STATE = "multiselector";
    //TODO try to replace with observable
    private MultiSelector multiSelector = new MultiSelector();
    private LocationAdapter locationAdapter;
    private FavoriteLocationRepository favoriteLocationRepository = new FavoriteLocationRepository();
    private LocationRepository locationRepository = new LocationRepository();
    private Subscription watcherSubscription;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentLocationBinding locationBinding = FragmentLocationBinding.inflate(inflater, container, false);
        locationAdapter = new LocationAdapter(multiSelector, locationRepository.findAll(), favoriteLocationRepository.findAll());
        locationBinding.favoritesList.setHasFixedSize(true);
        locationBinding.favoritesList.setAdapter(locationAdapter);
        locationBinding.favoritesList.setLayoutManager(new LinearLayoutManager(getActivity()));
        watcherSubscription = RxTextView.textChanges(locationBinding.favoritesFilter).subscribe(new Action1<CharSequence>() {
            @Override
            public void call(CharSequence charSequence) {
                locationAdapter.filterLocationsByName(charSequence.toString());
            }
        });
        locationBinding.setFragment(this);
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
        favoriteLocationRepository.saveList(locationAdapter.getFavoritePositions());
        startActivity(new Intent(getActivity(), DiagramActivity.class).setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        watcherSubscription.unsubscribe();
    }
}
