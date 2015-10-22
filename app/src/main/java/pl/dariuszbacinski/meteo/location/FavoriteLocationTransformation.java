package pl.dariuszbacinski.meteo.location;

import java.util.List;

import pl.dariuszbacinski.meteo.rx.Indexed;
import rx.Observable;
import rx.functions.Func1;

class FavoriteLocationTransformation {
    private Observable<Indexed<Location>> locationObservable;

    public FavoriteLocationTransformation(Observable<Indexed<Location>> locationObservable) {
        this.locationObservable = locationObservable;
    }

    public List<FavoriteLocation> filter(final List<Integer> selectedPositions) {
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
}
