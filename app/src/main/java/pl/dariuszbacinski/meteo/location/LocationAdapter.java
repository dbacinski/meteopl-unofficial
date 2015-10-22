package pl.dariuszbacinski.meteo.location;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import com.bignerdranch.android.multiselector.MultiSelector;

import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import pl.dariuszbacinski.meteo.rx.Indexed;
import pl.dariuszbacinski.meteo.rx.NaturalNumbers;
import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;

@Getter(AccessLevel.PACKAGE)
@Setter(AccessLevel.PRIVATE)
public class LocationAdapter extends RecyclerView.Adapter {
    private MultiSelector multiSelector;
    private Observable<Indexed<Location>> originalLocationObservable;
    private Observable<Indexed<Location>> locationObservable;

    public LocationAdapter(MultiSelector multiSelector, List<Location> locationList, List<FavoriteLocation> favoriteLocationList) {
        this.multiSelector = multiSelector;
        multiSelector.setSelectable(true);
        locationObservable = createObservableWithIndexedLocations(locationList);
        originalLocationObservable = locationObservable;
        restoreSelectedItems(multiSelector, originalLocationObservable, favoriteLocationList);
    }

    private Observable<Indexed<Location>> createObservableWithIndexedLocations(List<Location> locationList) {
        return Observable.from(locationList).zipWith(NaturalNumbers.instance(), new Func2<Location, Integer, Indexed<Location>>() {
            @Override
            public Indexed<Location> call(Location location, Integer index) {
                return new Indexed<>(index, index, location);
            }
        });
    }

    private void restoreSelectedItems(MultiSelector multiSelector, Observable<Indexed<Location>> locationObservable, List<FavoriteLocation> favoriteLocationList) {
        List<Location> selectedLocations = new LocationTransformation(favoriteLocationList).extractLocations();
        new LocationMultiSelector(multiSelector).restoreSelectedItems(locationObservable, selectedLocations);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_multiple_choice, parent, false);
        return new LocationViewHolder((CheckedTextView) view, getMultiSelector());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Indexed<Location> locationIndexed = getLocationAtPosition(position);
        LocationViewHolder locationViewHolder = (LocationViewHolder) holder;
        locationViewHolder.bindName(locationIndexed.getValue().getName());
        locationViewHolder.bindSelected(getMultiSelector().isSelected(locationIndexed.getOriginalIndex(), -1));
    }

    private Indexed<Location> getLocationAtPosition(final int position) {
        return getLocationObservable().filter(new Func1<Indexed<Location>, Boolean>() {
                @Override
                public Boolean call(Indexed<Location> locationIndexed) {
                    return locationIndexed.getIndex() == position;
                }
            }).toBlocking().single();
    }

    @Override
    public int getItemCount() {
        return getLocationObservable().count().toBlocking().single();
    }

    public List<FavoriteLocation> getFavoritePositions() {
        return new FavoriteLocationTransformation(getLocationObservable()).filter(getMultiSelector().getSelectedPositions());
    }

    public void filterLocationsByName(final String name) {
        setLocationObservable(getOriginalLocationObservable().filter(new Func1<Indexed<Location>, Boolean>() {
            @Override
            public Boolean call(Indexed<Location> locationIndexed) {
                return locationIndexed.getValue().getName().toLowerCase().contains(name.toLowerCase());
            }
        }).zipWith(new NaturalNumbers(), new Func2<Indexed<Location>, Integer, Indexed<Location>>() {
            @Override
            public Indexed<Location> call(Indexed<Location> locationIndexed, Integer index) {
                return locationIndexed.setIndex(index);
            }
        }));
        notifyDataSetChanged();
    }
}
