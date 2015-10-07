package pl.dariuszbacinski.meteo.location
import org.robolectric.annotation.Config

@Config(manifest = "src/main/AndroidManifest.xml")
class LocationTransformationSpec extends ShadowRoboSpecification {

    public static final int SECOND_ELEMENT = 1

    def "returns number of favorite locations"() {
        given:
            LocationTransformation objectUnderTest = new LocationTransformation([new FavoriteLocation(), new FavoriteLocation()]);
        when:
            def count = objectUnderTest.favoriteLocationCount
        then:
            count == 2
    }

    def "extracts requested favorite location"() {
        given:
            def expectedLocation = new Location("", 0, 0)
            LocationTransformation objectUnderTest = new LocationTransformation([new FavoriteLocation(), new FavoriteLocation(expectedLocation)]);
        when:
            def favoriteLocation = objectUnderTest.extractLocationAtPosition(SECOND_ELEMENT)
        then:
            favoriteLocation == expectedLocation
    }

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