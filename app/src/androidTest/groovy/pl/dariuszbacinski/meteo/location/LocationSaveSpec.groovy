package pl.dariuszbacinski.meteo.location

import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.rule.ActivityTestRule
import org.junit.Rule
import pl.dariuszbacinski.meteo.location.view.LocationActivity
import spock.lang.AutoCleanup
import spock.lang.Specification

import static pl.dariuszbacinski.meteo.location.LocationListFeature.*

public class LocationSaveSpec extends Specification {

    @Rule
    ActivityTestRule<LocationActivity> locationActivityRule = new ActivityTestRule(LocationActivity)
    @AutoCleanup("cleanup")
    LocationSetup locationSetup = new LocationSetup()

    void setupSpec() {
        removeFavoriteLocations()
    }

    def "save location as favorite"() {
        given: "select Berlin as favorite location"
            selectLocationWithName "Berlin", locationSetup.listIdlingResource
        when: "save selected location"
            saveSelectedLocations()
        then: "Berlin is saved"
            firstSelectedLocationHasName "Berlin"
    }

    void setup() {
        Context applicationContext = InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext()
        locationSetup.setupStubs(applicationContext)
        locationSetup.setupIdlingResource(locationActivityRule.getActivity())
    }
}
