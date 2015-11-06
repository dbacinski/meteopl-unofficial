package pl.dariuszbacinski.meteo.location;

import pl.dariuszbacinski.meteo.rx.Indexed;

public class IndexedLocation extends Indexed<Location> {
    public IndexedLocation(int index, int originalIndex, Location value) {
        super(index, originalIndex, value);
    }

    private String getLowerCaseName() {
        return getValue().getName().toLowerCase();
    }
}
