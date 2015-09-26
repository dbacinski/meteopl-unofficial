package pl.dariuszbacinski.meteo.favorite;

import java.util.List;

import pl.dariuszbacinski.meteo.diagram.Location;
import rx.Observable;
import rx.functions.Func1;

public class FavoriteWeatherDataProvider {
    private Observable<Location> locations;

    public FavoriteWeatherDataProvider(FavoriteLocationRepository favoriteLocationRepository) {
        List<FavoriteLocation> favoriteLocations = favoriteLocationRepository.findAll();
        locations = rx.Observable.from(favoriteLocations).map(new Func1<FavoriteLocation, Location>() {
            @Override
            public Location call(FavoriteLocation favoriteLocation) {
                return favoriteLocation.getLocation();

            }
        });
    }

    public Location getFavoriteLocation(int position) {
        return locations.elementAt(position).toBlocking().single();
    }

    public int getFavoriteLocationCount() {
        return locations.count().toBlocking().single() ;
    }

}
