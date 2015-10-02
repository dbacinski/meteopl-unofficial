package pl.dariuszbacinski.meteo.location;

import com.activeandroid.query.Select;

import java.util.List;

public class FavoriteLocationRepository {

    public List<FavoriteLocation> findAll() {
        return new Select().from(FavoriteLocation.class).execute();
    }

    public void saveList(List<FavoriteLocation> favoritePositions) {
        for(FavoriteLocation favoriteLocation : favoritePositions) {
            favoriteLocation.save();
        }
    }
}
