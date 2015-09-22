package pl.dariuszbacinski.meteo;

import android.content.Context;

import com.activeandroid.Model;
import com.activeandroid.query.Select;
import com.google.common.base.Optional;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.google.common.base.Optional.fromNullable;

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
