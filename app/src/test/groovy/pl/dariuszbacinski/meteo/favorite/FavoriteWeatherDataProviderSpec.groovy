package pl.dariuszbacinski.meteo.favorite

import org.robolectric.annotation.Config
import pl.polidea.robospock.RoboSpecification;

@Config(manifest = "src/main/AndroidManifest.xml")
class FavoriteWeatherDataProviderSpec extends RoboSpecification {


    def "returns number of elements in repository"() {
        given:
            FavoriteLocationRepository favoriteLocationRepository = Mock()
            favoriteLocationRepository.findAll() >> [new FavoriteLocation(), new FavoriteLocation()]
            FavoriteWeatherDataProvider objectUnderTest = new FavoriteWeatherDataProvider(favoriteLocationRepository);
        when:
            def count = objectUnderTest.favoriteLocationCount
        then:
            count == 2
    }
}