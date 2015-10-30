package pl.dariuszbacinski.meteo.location;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import com.bignerdranch.android.multiselector.MultiSelector;

import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import pl.dariuszbacinski.meteo.databinding.ListItemLocationBinding;
import pl.dariuszbacinski.meteo.rx.Indexed;
import pl.dariuszbacinski.meteo.rx.NaturalNumbers;
import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;

@Getter(AccessLevel.PACKAGE)
@Setter(AccessLevel.PRIVATE)
public class LocationAdapter extends RecyclerView.Adapter<LocationViewHolder> {
    private MultiSelector multiSelector;
    private Observable<Indexed<Location>> originalLocationObservable;
    private List<Indexed<Location>> locationObservable;

    public LocationAdapter(MultiSelector multiSelector, List<Location> locationList, List<FavoriteLocation> favoriteLocationList) {
        this.multiSelector = multiSelector;
        multiSelector.setSelectable(true);
        originalLocationObservable = createObservableWithIndexedLocations(locationList);
        locationObservable = originalLocationObservable.toList().toBlocking().single();
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
    public LocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ListItemLocationBinding binding = ListItemLocationBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new LocationViewHolder((CheckedTextView) binding.getRoot());
    }

    @Override
    public void onBindViewHolder(LocationViewHolder holder, int position) {
        Indexed<Location> locationIndexed = getLocationAtPosition(position);
        LocationListItem listItem = new LocationListItem(locationIndexed.getValue().getName(), getMultiSelector().isSelected(locationIndexed.getOriginalIndex(), -1));
        holder.getBinding().setItem(listItem);
        holder.getBinding().setListener(new LocationListItemOnClickListener(multiSelector, locationIndexed.getOriginalIndex()));
    }

    private Indexed<Location> getLocationAtPosition(final int position) {
        return getLocationObservable().get(position);
    }

    @Override
    public int getItemCount() {
        return getLocationObservable().size();
    }

    public List<FavoriteLocation> getFavoriteLocations() {
        return new FavoriteLocationTransformation(getOriginalLocationObservable()).filter(getMultiSelector().getSelectedPositions());
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
        }).toList().toBlocking().single());
        notifyDataSetChanged();
    }
}
