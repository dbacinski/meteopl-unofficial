package pl.dariuszbacinski.meteo.location
import com.andrewreitz.spock.android.UseActivity
import groovy.transform.CompileStatic
import pl.dariuszbacinski.meteo.location.view.LocationActivity
import spock.lang.Specification

import static com.andrewreitz.spock.android.RunMode.METHOD
import static pl.dariuszbacinski.meteo.location.LocationListFeature.*

@CompileStatic
public class LocationSaveSpec extends Specification{

    @UseActivity(value=LocationActivity, runMode = METHOD)
    def locationActivity

    def "verify activity"() {
        expect:
            locationActivity instanceof LocationActivity
    }

    def "save location as favorite"() {
        when: "select Berlin as favorite location"
        //TODO uncheck all locations
            filterLocationsWithQuery "Berlin"
            selectFirstLocationAsFavorite()
            saveSelectedLocations()
        then: "save selected locations"
        //TODO assert
    }
}
