package pl.dariuszbacinski.meteo.location.model
import pl.dariuszbacinski.meteo.shadow.ShadowRoboSpecification
import rx.observers.TestSubscriber

class MeteoServiceSpec extends ShadowRoboSpecification {
    private TestSubscriber<Location> subscriber = new TestSubscriber<Location>()

    def "resolves grid location based on coarse location"() {
        given:
            MeteoService objectUnderTest = new MeteoService()
            def location = new android.location.Location("")
            location.setLatitude 52.2d
            location.setLongitude 21.0d
        when:
            objectUnderTest.getGridCoordinatedBasedOnLocation(location).subscribe(subscriber)
        then:
            subscriber.assertValue(new Location("", 409, 248))
    }
}
