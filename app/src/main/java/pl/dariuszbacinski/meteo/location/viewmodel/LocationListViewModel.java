package pl.dariuszbacinski.meteo.location.viewmodel;

import com.bignerdranch.android.multiselector.MultiSelector;

import java.util.List;

import hugo.weaving.DebugLog;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import pl.dariuszbacinski.meteo.component.rx.Indexed;
import pl.dariuszbacinski.meteo.component.rx.NaturalNumbers;
import pl.dariuszbacinski.meteo.component.text.StringJoiner;
import pl.dariuszbacinski.meteo.location.model.IndexedLocation;
import pl.dariuszbacinski.meteo.location.model.Location;
import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;

import static pl.dariuszbacinski.meteo.component.text.StringNormalizer.normalizePlLang;
import static pl.dariuszbacinski.meteo.location.viewmodel.CoarseLocationViewModelAdapter.COARSE_LOCATION_ID;

@Getter(AccessLevel.PACKAGE)
@Setter(AccessLevel.PRIVATE)
public class LocationListViewModel {

    private MultiSelector multiSelector;
    private Observable<IndexedLocation> originalLocationObservable;
    private List<IndexedLocation> locations;
    private StringJoiner stringJoiner = new StringJoiner(" - ");

    public LocationListViewModel(MultiSelector multiSelector, List<Location> locationList, List<Location> favoriteLocationList ) {
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
        }).filter(new Func1<IndexedLocation, Boolean>() {
            @Override
            public Boolean call(IndexedLocation indexedLocation) {
                return !COARSE_LOCATION_ID.equals(indexedLocation.getValue().getId());
            }
        });
    }

    private void restoreSelectedItems(MultiSelector multiSelector, Observable<IndexedLocation> locationObservable, List<Location> selectedLocations) {
        new LocationMultiSelector(multiSelector).restoreSelectedItems(locationObservable, selectedLocations);
    }

    private  IndexedLocation getLocationAtPosition(final int position) {
        return getLocations().get(position);
    }

    public LocationItemViewModel getItemViewModel(int position){
        IndexedLocation locationIndexed = getLocationAtPosition(position);
        return new LocationItemViewModel(getLocationName(locationIndexed.getValue().getName(), locationIndexed.getValue().getPoviat()), getMultiSelector().isSelected(locationIndexed.getOriginalIndex(), -1));

    }

    private String getLocationName(String... names) {
        return stringJoiner.join(names);
    }

    public LocationItemOnClickListener getOnClickListener(int position){
        int originalPosition = getLocationAtPosition(position).getOriginalIndex();
        return new LocationItemOnClickListener(getMultiSelector(), originalPosition);
    }

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
                return  locationIndexed.getNormalizedName().contains(normalizePlLang(lowerCaseName)) ;
            }
        }).zipWith(new NaturalNumbers(), new Func2<IndexedLocation, Integer, IndexedLocation>() {
            @Override
            public IndexedLocation call(IndexedLocation locationIndexed, Integer index) {
                return locationIndexed.setIndex(index);
            }
        }).toSortedList(new SortFunctionStartsWith(name)).toBlocking().single());
    }
}
