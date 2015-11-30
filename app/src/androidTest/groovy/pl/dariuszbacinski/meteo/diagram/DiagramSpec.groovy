package pl.dariuszbacinski.meteo.diagram
import android.support.test.rule.ActivityTestRule
import org.junit.Rule
import pl.dariuszbacinski.meteo.diagram.view.DiagramActivity
import spock.lang.Specification

import static pl.dariuszbacinski.meteo.diagram.DiagramFeature.tabWithNameIsLoaded
import static pl.dariuszbacinski.meteo.location.LocationListFeature.addFavoriteLocationsWithNames
import static pl.dariuszbacinski.meteo.location.LocationListFeature.removeFavoriteLocations

public class DiagramSpec extends Specification {
    @Rule
    ActivityTestRule<DiagramActivity> diagramActivityRule = new ActivityTestRule(DiagramActivity)

    def setupSpec() {
        removeFavoriteLocations()
        addFavoriteLocationsWithNames(["Berlin", "Mordor"])
    }

    def "loads favorite locations"() {
        expect:
            tabWithNameIsLoaded "Berlin"
            tabWithNameIsLoaded "Mordor"
    }
}
