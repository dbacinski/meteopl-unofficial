package pl.dariuszbacinski.meteo.location.model;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.activeandroid.sqlbrite.BriteDatabase;

import java.util.List;

public class FavoriteLocationRepository {

    public List<Location> findAll() {
        List<FavoriteLocation> favoriteLocations = new Select().from(FavoriteLocation.class).execute();
        return new LocationTransformation(favoriteLocations).extractLocations();
    }

    public void saveList(List<Location> favoriteLocations) {
        BriteDatabase.Transaction transaction = ActiveAndroid.beginTransaction();

        try {
            new Delete().from(FavoriteLocation.class).execute();
            for (Location favoriteLocation : favoriteLocations) {
                new FavoriteLocation(favoriteLocation).save();
            }
            ActiveAndroid.setTransactionSuccessful(transaction);
        } finally {
            ActiveAndroid.endTransaction(transaction);
        }
    }
}
