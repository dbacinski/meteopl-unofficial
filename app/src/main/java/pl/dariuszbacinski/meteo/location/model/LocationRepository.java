package pl.dariuszbacinski.meteo.location.model;

import com.activeandroid.query.Select;
import com.eccyan.optional.Optional;

import java.util.List;

public class LocationRepository {

    public List<Location> findAll() {
        return new Select().from(Location.class).orderBy("name COLLATE LOCALIZED ASC").execute();
    }

    public Optional<Location> findOne(long id){
        return Optional.ofNullable(Location.load(Location.class, id));
    }
}
