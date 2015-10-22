package pl.dariuszbacinski.meteo.location;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.bignerdranch.android.multiselector.MultiSelector;
import com.jakewharton.rxbinding.widget.RxTextView;

import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.dariuszbacinski.meteo.R;
import pl.dariuszbacinski.meteo.diagram.WeatherDiagramActivity;
import rx.functions.Action1;

import static android.support.v7.widget.RecyclerView.LayoutManager;

public class LocationFragment extends Fragment {

    public static final String MULTI_SELECTOR_STATE = "multiselector";
    private MultiSelector multiSelector = new MultiSelector();
    private LocationAdapter locationAdapter;
    private FavoriteLocationRepository favoriteLocationRepository = new FavoriteLocationRepository();
    private LocationRepository locationRepository = new LocationRepository();

    public LocationFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        ButterKnife.bind(this, view);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.favorites_list);
        recyclerView.setHasFixedSize(true);
        LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        locationAdapter = new LocationAdapter(multiSelector, locationRepository.findAll(), favoriteLocationRepository.findAll());
        recyclerView.setAdapter(locationAdapter);
        EditText filterInput = (EditText) view.findViewById(R.id.favorites_filter);
        RxTextView.textChanges(filterInput).subscribe(new Action1<CharSequence>() {
            @Override
            public void call(CharSequence charSequence) {
                locationAdapter.filterLocationsByName(charSequence.toString());
            }
        });
        return view;
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

    @OnClick(R.id.favorites_save)
    public void saveFavorites() {
        favoriteLocationRepository.saveList(locationAdapter.getFavoritePositions());
        startActivity(new Intent(getActivity(), WeatherDiagramActivity.class).setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
    }
}
