package pl.dariuszbacinski.meteo.location;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import com.bignerdranch.android.multiselector.MultiSelector;

import java.util.List;

import hugo.weaving.DebugLog;
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
    private Observable<IndexedLocation> originalLocationObservable;
    private List<IndexedLocation> locations;

    public LocationAdapter(MultiSelector multiSelector, List<Location> locationList, List<Location> favoriteLocationList) {
        this.multiSelector = multiSelector;
        multiSelector.setSelectable(true);
        originalLocationObservable = createObservableWithIndexedLocations(locationList);
        locations = originalLocationObservable.toList().toBlocking().single();
        restoreSelectedItems(multiSelector, originalLocationObservable, favoriteLocationList);
    }

    private Observable<IndexedLocation> createObservableWithIndexedLocations(List<Location> locationList) {
        return Observable.from(locationList).zipWith(NaturalNumbers.instance(), new Func2<Location, Integer, IndexedLocation>() {
            @Override
            public IndexedLocation call(Location location, Integer index) {
                return new IndexedLocation(index, index, location);
            }
        });
    }

    private void restoreSelectedItems(MultiSelector multiSelector, Observable<IndexedLocation> locationObservable, List<Location> selectedLocations) {
        new LocationMultiSelector(multiSelector).restoreSelectedItems(locationObservable, selectedLocations);
    }

    @Override
    public LocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ListItemLocationBinding binding = ListItemLocationBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new LocationViewHolder((CheckedTextView) binding.getRoot());
    }

    @Override
    public void onBindViewHolder(LocationViewHolder holder, int position) {
        IndexedLocation locationIndexed = getLocationAtPosition(position);
        LocationListItem listItem = new LocationListItem(locationIndexed.getValue().getName(), getMultiSelector().isSelected(locationIndexed.getOriginalIndex(), -1));
        holder.getBinding().setItem(listItem);
        holder.getBinding().setListener(new LocationListItemOnClickListener(multiSelector, locationIndexed.getOriginalIndex()));
    }

    private IndexedLocation getLocationAtPosition(final int position) {
        return getLocations().get(position);
    }

    @Override
    public int getItemCount() {
        return getLocations().size();
    }

    public List<Location> getSelectedLocations() {
        final List<Integer> selectedPositions = getMultiSelector().getSelectedPositions();

        return getOriginalLocationObservable().filter(new Func1<Indexed<Location>, Boolean>() {
            @Override
            public Boolean call(Indexed<Location> locationIndexed) {
                return selectedPositions.contains(locationIndexed.getOriginalIndex());
            }
        }).map(new Func1<Indexed<Location>, Location>() {
            @Override
            public Location call(Indexed<Location> locationIndexed) {
                return locationIndexed.getValue();
            }
        }).toList().toBlocking().single();
    }

    @DebugLog
    public void filterLocationsByName(final String name) {
        setLocations(getOriginalLocationObservable().filter(new Func1<IndexedLocation, Boolean>() {
            @Override
            public Boolean call(IndexedLocation locationIndexed) {
                String lowerCaseName = name.toLowerCase();
                return locationIndexed.getLowerCaseName().contains(lowerCaseName) || locationIndexed.getNormalizedName().contains(lowerCaseName) ;
            }
        }).zipWith(new NaturalNumbers(), new Func2<IndexedLocation, Integer, IndexedLocation>() {
            @Override
            public IndexedLocation call(IndexedLocation locationIndexed, Integer index) {
                return locationIndexed.setIndex(index);
            }
        }).toSortedList(new SortFunctionStartsWith(name)).toBlocking().single());
        notifyDataSetChanged();
    }
}
