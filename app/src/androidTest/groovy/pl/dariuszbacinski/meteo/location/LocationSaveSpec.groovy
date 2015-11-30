package pl.dariuszbacinski.meteo.location
import android.support.test.rule.ActivityTestRule
import org.junit.Rule
import pl.dariuszbacinski.meteo.location.view.LocationActivity
import spock.lang.Specification

import static pl.dariuszbacinski.meteo.location.LocationListFeature.*

public class LocationSaveSpec extends Specification {

    @Rule
    ActivityTestRule<LocationActivity> locationActivityRule = new ActivityTestRule(LocationActivity)

    def setupSpec() {
        removeFavoriteLocations()
    }

    def "save location as favorite"() {
        given: "select Berlin as favorite location"
            selectLocationWithName "Berlin"
        when: "save selected location"
            saveSelectedLocations()
        then: "Berlin is saved"
            firstSelectedLocationHasName "Berlin"
    }
}
