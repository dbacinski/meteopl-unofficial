package pl.dariuszbacinski.meteo.location

import android.support.test.espresso.Espresso
import android.support.test.rule.ActivityTestRule
import org.junit.Rule
import pl.dariuszbacinski.meteo.component.LocationListIdlingResource
import pl.dariuszbacinski.meteo.location.view.LocationActivity
import spock.lang.Specification

import static pl.dariuszbacinski.meteo.location.LocationListFeature.*

public class LocationSaveSpec extends Specification {

    @Rule
    ActivityTestRule<LocationActivity> locationActivityRule = new ActivityTestRule(LocationActivity)
    LocationListIdlingResource listIdlingResource

    def setupSpec() {
        removeFavoriteLocations()
    }

    def setup() {
        listIdlingResource = new LocationListIdlingResource(getLocationListAdapter(locationActivityRule.getActivity()).getLoading())
        Espresso.registerIdlingResources listIdlingResource
    }
    def cleanup() {
        Espresso.unregisterIdlingResources listIdlingResource
    }

    def "save location as favorite"() {
        given: "select Berlin as favorite location"
            selectLocationWithName "Berlin", listIdlingResource
        when: "save selected location"
            saveSelectedLocations()
        then: "Berlin is saved"
            firstSelectedLocationHasName "Berlin"
    }
}
