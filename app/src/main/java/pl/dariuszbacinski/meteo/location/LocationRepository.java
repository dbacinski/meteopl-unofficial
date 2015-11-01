package pl.dariuszbacinski.meteo.location;

import com.activeandroid.query.Select;

import java.util.List;

public class LocationRepository {

    public List<Location> findAll() {
        return new Select().from(Location.class).orderBy("name").execute();
    }
}
