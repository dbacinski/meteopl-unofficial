package pl.dariuszbacinski.meteo.location
import android.support.test.rule.ActivityTestRule
import org.junit.Rule
import pl.dariuszbacinski.meteo.location.view.LocationActivity
import spock.lang.Specification

import static pl.dariuszbacinski.meteo.location.LocationListFeature.filterLocationsWithQuery
import static pl.dariuszbacinski.meteo.location.LocationListFeature.listContainsLocation

public class LocationFilteringSpec extends Specification{

    @Rule
    ActivityTestRule<LocationActivity> locationActivityRule = new ActivityTestRule(LocationActivity)

    def "filter locations by prefix"() {
        when: "enter filter query Ber"
            filterLocationsWithQuery "Ber"

        then: "location list contains location Berlin"
            listContainsLocation "Berlin"
    }

    def "filter locations by containing string"() {
        when: "enter filter query arsza"
            filterLocationsWithQuery "arsza"

        then: "location list contains location Warszawa"
            listContainsLocation "Warszawa"
    }

    def "filter locations by suffix"() {
        when: "enter filter query belin"
            filterLocationsWithQuery "belin"

        then: "location list contains location Izabelin"
            listContainsLocation "Izabelin"
    }
}
