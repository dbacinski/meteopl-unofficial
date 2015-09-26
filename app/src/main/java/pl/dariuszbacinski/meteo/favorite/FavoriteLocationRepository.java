package pl.dariuszbacinski.meteo.favorite;

import com.activeandroid.query.Select;

import java.util.List;

public class FavoriteLocationRepository {

    public List<FavoriteLocation> findAll() {
        return new Select().from(FavoriteLocation.class).execute();
    }
}
