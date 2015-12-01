package pl.dariuszbacinski.meteo.location
import android.support.test.espresso.Espresso
import android.support.test.rule.ActivityTestRule
import org.junit.Rule
import pl.dariuszbacinski.meteo.component.LocationListIdlingResource
import pl.dariuszbacinski.meteo.location.view.LocationActivity
import spock.lang.Specification

import static pl.dariuszbacinski.meteo.location.LocationListFeature.*

public class LocationFilteringSpec extends Specification {

    @Rule
    ActivityTestRule<LocationActivity> locationActivityRule = new ActivityTestRule(LocationActivity)
    LocationListIdlingResource listIdlingResource

    def setup() {
        listIdlingResource = new LocationListIdlingResource(getLocationListAdapter(locationActivityRule.getActivity()).getLoading())
        Espresso.registerIdlingResources listIdlingResource
    }

    def cleanup() {
        Espresso.unregisterIdlingResources listIdlingResource
    }

    def "filter locations by prefix"() {
        when: "enter filter query Ber"
            filterLocationsWithQuery "Ber", listIdlingResource
        then: "location list contains location Berlin"
            listContainsLocation "Berlin"
    }

    def "filter locations by containing string"() {
        when: "enter filter query arsza"
            filterLocationsWithQuery "arsza", listIdlingResource
        then: "location list contains location Warszawa"
            listContainsLocation "Warszawa"
    }

    def "filter locations by suffix"() {
        when: "enter filter query belin"
            filterLocationsWithQuery "belin", listIdlingResource
        then: "location list contains location Izabelin"
            listContainsLocation "Izabelin"
    }
}
