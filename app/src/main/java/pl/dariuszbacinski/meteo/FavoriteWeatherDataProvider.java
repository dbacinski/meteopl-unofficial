package pl.dariuszbacinski.meteo;

import com.activeandroid.query.Select;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

public class FavoriteWeatherDataProvider {
    private Observable<Location> locations;

    public FavoriteWeatherDataProvider() {
        List<FavoriteLocation> favoriteLocations = new Select().from(FavoriteLocation.class).execute();
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
