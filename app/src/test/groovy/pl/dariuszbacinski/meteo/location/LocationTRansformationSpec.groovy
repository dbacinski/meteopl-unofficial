package pl.dariuszbacinski.meteo.location
import org.robolectric.annotation.Config

@Config(manifest = "src/main/AndroidManifest.xml")
class LocationTransformationSpec extends ShadowRoboSpecification {

    public static final int SECOND_ELEMENT = 1
    FavoriteLocationRepository favoriteLocationRepository = Mock()

    def "returns number of favorite locations"() {
        given:
            favoriteLocationRepository.findAll() >> [new FavoriteLocation(), new FavoriteLocation()]
            LocationTransformation objectUnderTest = new LocationTransformation(favoriteLocationRepository.findAll());
        when:
            def count = objectUnderTest.favoriteLocationCount
        then:
            count == 2
    }

    def "returns correct favorite location"() {
        given:
            def expectedLocation = new Location("", 0, 0)
            favoriteLocationRepository.findAll() >> [new FavoriteLocation(), new FavoriteLocation(expectedLocation)]
            LocationTransformation objectUnderTest = new LocationTransformation(favoriteLocationRepository.findAll());
        when:
            def favoriteLocation = objectUnderTest.extractLocationAtPosition(SECOND_ELEMENT)
        then:
            favoriteLocation == expectedLocation
    }
}