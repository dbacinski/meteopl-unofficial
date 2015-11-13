package pl.dariuszbacinski.meteo.location.model;

import pl.dariuszbacinski.meteo.rx.Indexed;
import pl.dariuszbacinski.meteo.text.StringNormalizer;

public class IndexedLocation extends Indexed<Location> {

    public IndexedLocation(int index, int originalIndex, Location value) {
        super(index, originalIndex, value);
    }

    public String getLowerCaseName() {
        return getValue().getName().toLowerCase();
    }

    public String getNormalizedName() {
        return StringNormalizer.normalizePlLang(getLowerCaseName());
    }

    @Override
    public IndexedLocation setIndex(int index) {
        super.setIndex(index);
        return this;
    }
}
