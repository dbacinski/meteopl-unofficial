package pl.dariuszbacinski.meteo.location;

import java.util.ArrayList;
import java.util.List;

import pl.dariuszbacinski.meteo.diagram.Location;

class FavoriteLocationTransformation {
    private List<Location> locationList;

    public FavoriteLocationTransformation(List<Location> locationList) {
        this.locationList = locationList;
    }

    public List<FavoriteLocation> filter(List<Integer> selectedPositions) {
        List<FavoriteLocation> favoriteLocations = new ArrayList<>();

        for (Integer position : selectedPositions) {
            favoriteLocations.add(new FavoriteLocation(locationList.get(position)));
        }
        return favoriteLocations;
    }
}
