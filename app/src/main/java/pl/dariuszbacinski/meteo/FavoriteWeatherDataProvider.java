package pl.dariuszbacinski.meteo;

import com.activeandroid.query.Select;

import java.util.List;

public class FavoriteWeatherDataProvider {
    private List<Location> locations;

    public FavoriteWeatherDataProvider() {
        locations = new Select().from(Location.class).execute();
    }

    public Location getFavoriteLocation(int position) {
        return locations.get(position);
    }

    public int getFavoriteLocationCount() {
        return locations.size();
    }
}
