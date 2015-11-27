package pl.dariuszbacinski.meteo.location

import com.andrewreitz.spock.android.UseActivity
import pl.dariuszbacinski.meteo.location.view.LocationActivity
import spock.lang.Specification

import static com.andrewreitz.spock.android.ActivityRunMode.METHOD
import static pl.dariuszbacinski.meteo.location.LocationListFeature.filterLocationsWithQuery
import static pl.dariuszbacinski.meteo.location.LocationListFeature.listContainsLocation

public class LocationFilteringSpec extends Specification{

    @UseActivity(value=LocationActivity, runMode = METHOD)
    def locationActivity

    def "verify activity"() {
        expect:
            locationActivity instanceof LocationActivity
    }

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
