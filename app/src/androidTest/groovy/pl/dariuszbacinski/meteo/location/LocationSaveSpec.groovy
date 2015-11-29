package pl.dariuszbacinski.meteo.location
import com.andrewreitz.spock.android.UseActivity
import pl.dariuszbacinski.meteo.location.view.LocationActivity
import spock.lang.Specification

import static com.andrewreitz.spock.android.ActivityRunMode.METHOD
import static pl.dariuszbacinski.meteo.location.LocationListFeature.*

public class LocationSaveSpec extends Specification {

    @UseActivity(value = LocationActivity, runMode = METHOD)
    def locationActivity

    def setupSpec() {
        removeFavoriteLocations()
    }

    def "verify activity"() {
        expect:
            locationActivity instanceof LocationActivity
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
