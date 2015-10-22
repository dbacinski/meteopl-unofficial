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

    private void restoreSelectedItems(final MultiSelector multiSelector, List<FavoriteLocation> favoriteLocationList) {
        final List<Location> selectedLocations = new LocationTransformation(favoriteLocationList).extractLocations();
        new LocationMultiSelector(multiSelector).restoreSelectedItems(locationObservable, selectedLocations);
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
        return new FavoriteLocationTransformation(locationObservable).filter(multiSelector.getSelectedPositions());
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
