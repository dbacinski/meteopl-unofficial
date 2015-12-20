package pl.dariuszbacinski.meteo.location.model
import pl.dariuszbacinski.meteo.shadow.ShadowRoboSpecification
import rx.Observable
import rx.observers.TestSubscriber
import spock.lang.Subject

class CoarseLocationToLocationFuncSpec extends ShadowRoboSpecification {

    @Subject
    CoarseLocation.CoarseLocationToLocationFunc objectUnderTest;
    MeteoService meteoServiceStub = Stub(MeteoService)
    LocationNameResolver locationNameResolverStub = Stub(LocationNameResolver)
    TestSubscriber<Location> subscriber = new TestSubscriber<>()

    def "converts coarse location to location model"() {
        given:
            meteoServiceStub.getGridCoordinatedBasedOnLocation(_ as android.location.Location) >> Observable.just(new Location.LocationBuilder().name(null).row(1).col(2).build())
            locationNameResolverStub.getLocationName(_ as android.location.Location) >> Observable.just("Name")
            objectUnderTest = new CoarseLocation.CoarseLocationToLocationFunc(meteoServiceStub, locationNameResolverStub)
        when:
            objectUnderTest.call(new android.location.Location("")).subscribe(subscriber)
        then:
            subscriber.assertValue(new Location.LocationBuilder().name("Name").row(1).col(2).build())
    }
}
