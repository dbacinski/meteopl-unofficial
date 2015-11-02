package pl.dariuszbacinski.meteo.location

import com.bignerdranch.android.multiselector.MultiSelector
import pl.dariuszbacinski.meteo.shadow.ShadowRoboSpecification

class LocationAdapterSpec extends ShadowRoboSpecification {

    def "handles empty list"() {
        given:
            LocationAdapter objectUnderTest = new LocationAdapter(new MultiSelector(), [], [])
        when:
            def count = objectUnderTest.getItemCount()
        then:
            count == 0
    }

    def "filters locations by name ignoring case"() {
        given:
            LocationAdapter objectUnderTest = new LocationAdapter(new MultiSelector(), [new Location("Warszawa", 0, 0), new Location("Lublin", 0, 0)], [])
        when:
            objectUnderTest.filterLocationsByName("lub")
        then:
            objectUnderTest.getItemCount() == 1
            objectUnderTest.getLocations().first().value.name == "Lublin"
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


    def "returns selected locations "() {
        given:
            LocationAdapter objectUnderTest =
                    new LocationAdapter(new MultiSelector(),
                            [new Location('BerlinNotSelected', 0, 0), new Location('Warszawa', 0, 0), new Location('Lublin', 0, 0)],
                            [new Location('Warszawa', 0, 0), new Location('Lublin', 0, 0)])
        when:
            def favoriteLocations = objectUnderTest.getSelectedLocations()
        then:
            favoriteLocations.getLocationNameAtPosition(0) == 'Warszawa'
            favoriteLocations.getLocationNameAtPosition(1) == 'Lublin'
    }

    def setupSpec() {
        List.metaClass.getLocationNameAtPosition {
            delegate.get(it).name
        }
    }

}
