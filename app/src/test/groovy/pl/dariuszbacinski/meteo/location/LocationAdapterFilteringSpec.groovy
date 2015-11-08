package pl.dariuszbacinski.meteo.location
import com.bignerdranch.android.multiselector.MultiSelector
import pl.dariuszbacinski.meteo.shadow.ShadowRoboSpecification

class LocationAdapterFilteringSpec extends ShadowRoboSpecification {

    def "filters locations by name ignoring case"() {
        given:
            LocationAdapter objectUnderTest = new LocationAdapter(new MultiSelector(), [new Location("Warszawa", 0, 0), new Location("Lublin", 0, 0)], [])
        when:
            objectUnderTest.filterLocationsByName("lub")
        then:
            objectUnderTest.getItemCount() == 1
            objectUnderTest.getLocations().first().value.name == "Lublin"
    }

    def "filters locations by name with normalized letters"() {
        given:
            LocationAdapter objectUnderTest = new LocationAdapter(new MultiSelector(), [new Location("Warszawa", 0, 0), new Location("Łódź", 0, 0)], [])
        when:
            objectUnderTest.filterLocationsByName("lodz")
        then:
            objectUnderTest.getItemCount() == 1
            objectUnderTest.getLocations().first().value.name == "Łódź"
    }

    def "filters locations by name with polish letters"() {
        given:
            LocationAdapter objectUnderTest = new LocationAdapter(new MultiSelector(), [new Location("Warszawa", 0, 0), new Location("Łódź", 0, 0)], [])
        when:
            objectUnderTest.filterLocationsByName("łódź")
        then:
            objectUnderTest.getItemCount() == 1
            objectUnderTest.getLocations().first().value.name == "Łódź"
    }

    def "filters locations by name with mixed polish and normalized letters"() {
        given:
            LocationAdapter objectUnderTest = new LocationAdapter(new MultiSelector(), [new Location("Warszawa", 0, 0), new Location("Łódź", 0, 0)], [])
        when:
            objectUnderTest.filterLocationsByName("łodź")
        then:
            objectUnderTest.getItemCount() == 1
            objectUnderTest.getLocations().first().value.name == "Łódź"
    }

    def "filters locations by name and updates position"() {
        given:
            LocationAdapter objectUnderTest = new LocationAdapter(new MultiSelector(), [new Location("Warszawa", 0, 0), new Location("Lublin", 0, 0)], [])
        when:
            objectUnderTest.filterLocationsByName("Lublin")
        then:
            objectUnderTest.getLocations().first().index == 0
            objectUnderTest.getLocations().first().originalIndex == 1
    }
}
