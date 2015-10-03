package pl.dariuszbacinski.meteo.location;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.List;

public class FavoriteLocationRepository {

    public List<FavoriteLocation> findAll() {
        return new Select().from(FavoriteLocation.class).execute();
    }

    public void saveList(List<FavoriteLocation> favoritePositions) {
        ActiveAndroid.beginTransaction();

        try {
            new Delete().from(FavoriteLocation.class).execute();
            for (FavoriteLocation favoriteLocation : favoritePositions) {
                favoriteLocation.save();
            }
            ActiveAndroid.setTransactionSuccessful();
        } finally {
            ActiveAndroid.endTransaction();
        }
    }
}
