package pl.dariuszbacinski.meteo.location
import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.rule.ActivityTestRule
import org.junit.Rule
import pl.dariuszbacinski.meteo.location.view.LocationActivity
import spock.lang.AutoCleanup
import spock.lang.Specification

import static pl.dariuszbacinski.meteo.location.LocationListFeature.filterLocationsWithQuery
import static pl.dariuszbacinski.meteo.location.LocationListFeature.listContainsLocation

public class LocationFilteringSpec extends Specification {

    @Rule
    ActivityTestRule<LocationActivity> locationActivityRule = new ActivityTestRule(LocationActivity)
    @AutoCleanup("cleanup")
    LocationSetup locationSetup = new LocationSetup()

    def "filter locations by prefix"() {
        when: "enter filter query Ber"
            filterLocationsWithQuery "Ber", locationSetup.listIdlingResource
        then: "location list contains location Berlin"
            listContainsLocation "Berlin"
    }

    def "filter locations by containing string"() {
        when: "enter filter query arsza"
            filterLocationsWithQuery "arsza", locationSetup.listIdlingResource
        then: "location list contains location Warszawa"
            listContainsLocation "Warszawa"
    }

    def "filter locations by suffix"() {
        when: "enter filter query belin"
            filterLocationsWithQuery "belin", locationSetup.listIdlingResource
        then: "location list contains location Izabelin"
            listContainsLocation "Izabelin"
    }

    void setup() {
        Context applicationContext = InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext()
        locationSetup.setupStubs(applicationContext)
        locationSetup.setupIdlingResource(locationActivityRule.getActivity())
    }
}
