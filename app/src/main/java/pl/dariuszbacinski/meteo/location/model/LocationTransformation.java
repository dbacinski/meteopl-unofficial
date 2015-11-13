package pl.dariuszbacinski.meteo.location.model;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

public class LocationTransformation {
    private Observable<Location> locations;

    public LocationTransformation(List<FavoriteLocation> favoriteLocations) {
        locations = rx.Observable.from(favoriteLocations).map(new Func1<FavoriteLocation, Location>() {
            @Override
            public Location call(FavoriteLocation favoriteLocation) {
                return favoriteLocation.getLocation();
            }
        });
    }

    public List<Location> extractLocations(){
        return locations.toList().toBlocking().single();
    }
}
