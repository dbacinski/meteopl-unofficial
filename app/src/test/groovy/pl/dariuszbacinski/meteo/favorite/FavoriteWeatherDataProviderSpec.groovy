package pl.dariuszbacinski.meteo.favorite

import org.robolectric.annotation.Config
import pl.dariuszbacinski.meteo.diagram.Location
import pl.polidea.robospock.RoboSpecification;

@Config(manifest = "src/main/AndroidManifest.xml")
class FavoriteWeatherDataProviderSpec extends RoboSpecification {

    public static final int SECOND_ELEMENT = 1
    FavoriteLocationRepository favoriteLocationRepository = Mock()

    def "returns number of favorite locations"() {
        given:
            this.favoriteLocationRepository.findAll() >> [new FavoriteLocation(), new FavoriteLocation()]
            FavoriteWeatherDataProvider objectUnderTest = new FavoriteWeatherDataProvider(this.favoriteLocationRepository);
        when:
            def count = objectUnderTest.favoriteLocationCount
        then:
            count == 2
    }

    def "returns correct favorite location"() {
        given:
            def expectedLocation = new Location("", 0, 0)
            favoriteLocationRepository.findAll() >> [new FavoriteLocation(), new FavoriteLocation(expectedLocation)]
            FavoriteWeatherDataProvider objectUnderTest = new FavoriteWeatherDataProvider(favoriteLocationRepository);
        when:
            def favoriteLocation = objectUnderTest.getFavoriteLocation(SECOND_ELEMENT)
        then:
            favoriteLocation == expectedLocation
    }
}