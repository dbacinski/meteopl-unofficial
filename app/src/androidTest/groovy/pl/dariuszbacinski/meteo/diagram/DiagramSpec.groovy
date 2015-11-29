package pl.dariuszbacinski.meteo.diagram

import com.andrewreitz.spock.android.UseActivity
import pl.dariuszbacinski.meteo.diagram.view.DiagramActivity
import spock.lang.Specification

import static com.andrewreitz.spock.android.ActivityRunMode.METHOD
import static pl.dariuszbacinski.meteo.diagram.DiagramFeature.tabWithNameIsLoaded
import static pl.dariuszbacinski.meteo.location.LocationListFeature.addFavoriteLocationsWithNames
import static pl.dariuszbacinski.meteo.location.LocationListFeature.removeFavoriteLocations

public class DiagramSpec extends Specification {

    @UseActivity(value = DiagramActivity, runMode = METHOD)
    def diagramActivity

    def setupSpec() {
        removeFavoriteLocations()
        addFavoriteLocationsWithNames(["Berlin", "Mordor"])
    }

    def "verify activity"() {
        expect:
            diagramActivity instanceof DiagramActivity
    }

    def "loads favorite locations"() {
        expect:
            tabWithNameIsLoaded "Berlin"
            tabWithNameIsLoaded "Mordor"
    }
}
