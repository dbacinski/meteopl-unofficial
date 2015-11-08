package pl.dariuszbacinski.meteo.location;

import rx.functions.Func2;

class SortFunctionStartsWith implements Func2<IndexedLocation, IndexedLocation, Integer> {
    private final String prefix;

    public SortFunctionStartsWith(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public Integer call(IndexedLocation locationIndexed, IndexedLocation locationIndexedSecond) {

        if ("".equals(prefix)) {
            return 0;
        }

        boolean firstStartsWithName = startsWithName(locationIndexed, prefix);
        boolean secondStartsWithName = startsWithName(locationIndexedSecond, prefix);

        if (firstStartsWithName && secondStartsWithName) {
            return 0;
        } else if (firstStartsWithName) {
            return -1;
        } else if (secondStartsWithName) {
            return 1;
        } else {
            return 0;
        }
    }

    private boolean startsWithName(IndexedLocation locationIndexed, String name) {
        return locationIndexed.getLowerCaseName().startsWith(name) || locationIndexed.getNormalizedName().startsWith(name);
    }
}
