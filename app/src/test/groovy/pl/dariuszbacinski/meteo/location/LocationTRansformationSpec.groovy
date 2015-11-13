package pl.dariuszbacinski.meteo.location

import pl.dariuszbacinski.meteo.location.model.FavoriteLocation
import pl.dariuszbacinski.meteo.location.model.Location
import pl.dariuszbacinski.meteo.location.model.LocationTransformation
import pl.dariuszbacinski.meteo.shadow.ShadowRoboSpecification

class LocationTransformationSpec extends ShadowRoboSpecification {

     def "extracts all favorite locations"() {
        given:
            def expectedLocation = new Location("", 0, 0)
            LocationTransformation objectUnderTest = new LocationTransformation([new FavoriteLocation(expectedLocation), new FavoriteLocation(expectedLocation)]);
        when:
            def favoriteLocations = objectUnderTest.extractLocations()
        then:
            favoriteLocations == [expectedLocation, expectedLocation]
    }
}