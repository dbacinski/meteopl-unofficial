package pl.dariuszbacinski.meteo.location.model

import pl.dariuszbacinski.meteo.shadow.ShadowRoboSpecification

class LocationTransformationSpec extends ShadowRoboSpecification {

    def "extracts all favorite locations"() {
        given:
        def expectedLocation = new Location("", 0, 0, "")
        LocationTransformation objectUnderTest = new LocationTransformation([new FavoriteLocation(expectedLocation), new FavoriteLocation(expectedLocation)]);
        when:
        def favoriteLocations = objectUnderTest.extractLocations()
        then:
        favoriteLocations == [expectedLocation, expectedLocation]
    }
}