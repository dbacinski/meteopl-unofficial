package pl.dariuszbacinski.meteo.location;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import com.bignerdranch.android.multiselector.MultiSelector;

import java.util.List;

import pl.dariuszbacinski.meteo.rx.Indexed;
import pl.dariuszbacinski.meteo.rx.NaturalNumbers;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;

public class LocationAdapter extends RecyclerView.Adapter {
    private MultiSelector multiSelector;
    private Observable<Indexed<Location>> originalLocationObservable;
    private Observable<Indexed<Location>> locationObservable;

    public LocationAdapter(LocationRepository locationRepository, MultiSelector multiSelector, FavoriteLocationRepository favoriteLocationRepository) {
        this.locationObservable = Observable.from(locationRepository.findAll()).zipWith(NaturalNumbers.instance(), new Func2<Location, Integer, Indexed<Location>>() {
            @Override
            public Indexed<Location> call(Location location, Integer index) {
                return new Indexed<Location>(index, index, location);
            }
        });
        this.originalLocationObservable = this.locationObservable;
        this.multiSelector = multiSelector;
        multiSelector.setSelectable(true);
        restoreSelectedItems(multiSelector, favoriteLocationRepository.findAll());
    }

    //TODO can be extracted
    private void restoreSelectedItems(final MultiSelector multiSelector, List<FavoriteLocation> favoriteLocationList) {
        final List<Location> selectedLocations = new LocationTransformation(favoriteLocationList).extractLocations();

        locationObservable.filter(new Func1<Indexed<Location>, Boolean>() {
            @Override
            public Boolean call(Indexed<Location> locationIndexed) {
                return selectedLocations.contains(locationIndexed.getValue());
            }
        }).forEach(new Action1<Indexed<Location>>() {
            @Override
            public void call(Indexed<Location> locationIndexed) {
                multiSelector.setSelected(locationIndexed.getOriginalIndex(), -1, true);
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_multiple_choice, parent, false);
        return new LocationViewHolder((CheckedTextView) view, multiSelector);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Indexed<Location> locationIndexed = locationObservable.filter(new Func1<Indexed<Location>, Boolean>() {
            @Override
            public Boolean call(Indexed<Location> locationIndexed) {
                return locationIndexed.getIndex() == position;
            }
        }).distinct().toBlocking().single();
        LocationViewHolder locationViewHolder = (LocationViewHolder) holder;
        locationViewHolder.bindName(locationIndexed.getValue().getName());
        locationViewHolder.bindSelected(multiSelector.isSelected(locationIndexed.getOriginalIndex(), -1));
    }

    @Override
    public int getItemCount() {
        return locationObservable.count().toBlocking().single();
    }

    public List<FavoriteLocation> getFavoritePositions() {

        final List<Integer> selectedPositions = multiSelector.getSelectedPositions();

        return locationObservable.filter(new Func1<Indexed<Location>, Boolean>() {
            @Override
            public Boolean call(Indexed<Location> locationIndexed) {
                return selectedPositions.contains(locationIndexed.getOriginalIndex());
            }
        }).map(new Func1<Indexed<Location>, FavoriteLocation>() {
            @Override
            public FavoriteLocation call(Indexed<Location> locationIndexed) {
                return new FavoriteLocation(locationIndexed.getValue());
            }
        }).toList().toBlocking().single();
    }

    public void filterLocationsByName(final String name) {
        locationObservable = originalLocationObservable.filter(new Func1<Indexed<Location>, Boolean>() {
            @Override
            public Boolean call(Indexed<Location> locationIndexed) {
                return locationIndexed.getValue().getName().contains(name);
            }
        }).zipWith(new NaturalNumbers(), new Func2<Indexed<Location>, Integer, Indexed<Location>>() {
            @Override
            public Indexed<Location> call(Indexed<Location> locationIndexed, Integer index) {
                return locationIndexed.setIndex(index);
            }
        });
        notifyDataSetChanged();
    }
}
