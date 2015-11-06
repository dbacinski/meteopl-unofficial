package pl.dariuszbacinski.meteo.location;

import rx.functions.Func2;

class SortFunctionStartsWith implements Func2<IndexedLocation, IndexedLocation, Integer> {
    private final String name;

    public SortFunctionStartsWith(String name) {
        this.name = name;
    }

    @Override
    public Integer call(IndexedLocation locationIndexed, IndexedLocation locationIndexed2) {
        if ("".equals(name)) {
            return 0;
        } else if (locationIndexed.getLowerCaseName().startsWith(name)|| locationIndexed.getNormalizedName().startsWith(name)) {
            return -1;
        } else if (locationIndexed2.getLowerCaseName().startsWith(name)|| locationIndexed2.getNormalizedName().startsWith(name)) {
            return 1;
        } else {
            return 0;
        }
    }
}
