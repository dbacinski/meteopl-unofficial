package pl.dariuszbacinski.meteo.location;

import com.activeandroid.query.Select;

import java.util.List;

import pl.dariuszbacinski.meteo.diagram.Location;

public class LocationRepository {

    public List<Location> findAll() {
        return new Select().from(Location.class).execute();
    }
}
