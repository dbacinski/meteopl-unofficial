package pl.dariuszbacinski.meteo.location

import com.bignerdranch.android.multiselector.MultiSelector
import pl.dariuszbacinski.meteo.location.model.Location
import pl.dariuszbacinski.meteo.location.view.LocationAdapter
import pl.dariuszbacinski.meteo.location.viewmodel.LocationListViewModel
import pl.dariuszbacinski.meteo.shadow.ShadowRoboSpecification

class LocationAdapterSpec extends ShadowRoboSpecification {

    def "handles empty list"() {
        given:
            LocationAdapter objectUnderTest = new LocationAdapter(new LocationListViewModel(new MultiSelector(), [], []))
        when:
            def count = objectUnderTest.getItemCount()
        then:
            count == 0
    }

    def "returns selected locations"() {
        given:
            LocationAdapter objectUnderTest =
                    new LocationAdapter(new LocationListViewModel(
                            new MultiSelector(),
                            [new Location('BerlinNotSelected', 0, 0), new Location('Warszawa', 0, 0), new Location('Lublin', 0, 0)],
                            [new Location('Warszawa', 0, 0), new Location('Lublin', 0, 0)]))
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
