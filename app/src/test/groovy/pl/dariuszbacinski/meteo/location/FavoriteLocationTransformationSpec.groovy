package pl.dariuszbacinski.meteo.location

import org.robolectric.annotation.Config
import pl.dariuszbacinski.meteo.diagram.Location

@Config(manifest = "src/main/AndroidManifest.xml")
class FavoriteLocationTransformationSpec extends ShadowRoboSpecification {

    public static final int SECOND_POSITION = 1
    public static final int THIRD_POSITION = 2
    public static final int ANY = 1

    def "transforms selected locations to favorite locations"() {
        given:
            FavoriteLocationTransformation objectUnderTest = [new Location('first', ANY, ANY), new Location('second', ANY, ANY), new Location('third', ANY, ANY)]
        when:
            def favoriteLocations = objectUnderTest.filter([SECOND_POSITION, THIRD_POSITION])
        then:
            favoriteLocations.getLocationNameAtPosition(0) == 'second'
            favoriteLocations.getLocationNameAtPosition(1) == 'third'
    }

    def setupSpec() {
        List.metaClass.getLocationNameAtPosition {
            delegate.get(it).location.name
        }
    }
}
