package pl.dariuszbacinski.meteo.location
import groovy.transform.CompileStatic
import pl.dariuszbacinski.meteo.R
import pl.dariuszbacinski.meteo.component.LocationListIdlingResource
import pl.dariuszbacinski.meteo.component.ReplaceHintAction
import pl.dariuszbacinski.meteo.location.model.FavoriteLocationRepository
import pl.dariuszbacinski.meteo.location.model.Location
import pl.dariuszbacinski.meteo.location.model.LocationRepository

import static android.support.test.espresso.Espresso.onView
import static android.support.test.espresso.action.ViewActions.actionWithAssertions
import static android.support.test.espresso.action.ViewActions.click
import static android.support.test.espresso.assertion.ViewAssertions.matches
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import static android.support.test.espresso.matcher.ViewMatchers.withId
import static android.support.test.espresso.matcher.ViewMatchers.withText

@CompileStatic
public class LocationListFeature {

    public static void filterLocationsWithQuery(String berlin_query, LocationListIdlingResource idlingResource) {
        onView withId(R.id.action_search) perform actionWithAssertions(new ReplaceHintAction(berlin_query))
        waitForFilterResult(idlingResource)
    }

    public static void listContainsLocation(String locationName) {
        onView withId(R.id.location_name) check matches(withText(locationName))
    }

    public static void selectFirstLocationAsFavorite() {
        onView withId(R.id.favorites_list) perform actionOnItemAtPosition(0, click())
    }

    public static void selectLocationWithName(String name, LocationListIdlingResource idlingResource) {
        filterLocationsWithQuery(name, idlingResource)
        selectFirstLocationAsFavorite()
    }

    public static void saveSelectedLocations() {
        onView withId(R.id.favorites_save) perform click()
    }

    public static void waitForFilterResult(LocationListIdlingResource idlingResource) {
        idlingResource.setIsIdle false
    }

    public static void removeFavoriteLocations() {
        new FavoriteLocationRepository().saveList([])
    }

    public static boolean firstSelectedLocationHasName(String name) {
        return new FavoriteLocationRepository().findAll().first().name == name
    }

    static void addFavoriteLocationsWithNames(List<String> locationNames) {
        List<Location> locations = new LocationRepository().findAll().findAll { it.name in locationNames}
        new FavoriteLocationRepository().saveList(locations);
    }
}
