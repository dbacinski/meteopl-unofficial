package pl.dariuszbacinski.meteo.location

import groovy.transform.CompileStatic
import pl.dariuszbacinski.meteo.R

import static android.support.test.espresso.Espresso.onView
import static android.support.test.espresso.action.ViewActions.click
import static android.support.test.espresso.action.ViewActions.typeText
import static android.support.test.espresso.assertion.ViewAssertions.matches
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import static android.support.test.espresso.matcher.ViewMatchers.withId
import static android.support.test.espresso.matcher.ViewMatchers.withText

@CompileStatic
public class LocationListFeature {

    public static void filterLocationsWithQuery(String berlin_query) {
        onView withId(R.id.action_search) perform typeText(berlin_query)
        waitForFilterResults()
    }

    public static void listContainsLocation(String locationName) {
        onView withId(R.id.location_name) check matches(withText(locationName))
    }

    public static void selectFirstLocationAsFavorite() {
        onView withId(R.id.favorites_list) perform actionOnItemAtPosition(0, click())
    }

    public static void saveSelectedLocations() {
        onView withId(R.id.favorites_save) perform click()
    }

    public static void waitForFilterResults(){
        sleep(500)
    }
}
