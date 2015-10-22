package pl.dariuszbacinski.meteo.location
import pl.dariuszbacinski.meteo.rx.Indexed
import pl.dariuszbacinski.meteo.shadow.ShadowRoboSpecification

class FavoriteLocationTransformationSpec extends ShadowRoboSpecification {

    public static final int SECOND_POSITION = 1
    public static final int THIRD_POSITION = 2
    public static final int ANY = 0

    def "transforms selected locations to favorite locations"() {
        given:
            FavoriteLocationTransformation objectUnderTest = new FavoriteLocationTransformation(rx.Observable.from(
            [new Indexed<Location> (ANY, ANY, new Location('first', ANY, ANY)),
             new Indexed<Location> (SECOND_POSITION, SECOND_POSITION, new Location('second', ANY, ANY)),
             new Indexed<Location> (THIRD_POSITION, THIRD_POSITION, new Location('third', ANY, ANY))]))
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
