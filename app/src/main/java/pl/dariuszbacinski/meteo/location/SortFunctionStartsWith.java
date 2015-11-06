package pl.dariuszbacinski.meteo.location;

import pl.dariuszbacinski.meteo.rx.Indexed;
import rx.functions.Func2;

class SortFunctionStartsWith implements Func2<Indexed<Location>, Indexed<Location>, Integer> {
    private final String name;

    public SortFunctionStartsWith(String name) {
        this.name = name;
    }

    @Override
    public Integer call(Indexed<Location> locationIndexed, Indexed<Location> locationIndexed2) {
        if ("".equals(name)) {
            return 0;
        } else if (getName(locationIndexed).startsWith(name)) {
            return -1;
        } else if (getName(locationIndexed2).startsWith(name)) {
            return 1;
        } else {
            return 0;
        }
    }

    private String getName(Indexed<Location> locationIndexed) {
        return locationIndexed.getValue().getName().toLowerCase();
    }
}
