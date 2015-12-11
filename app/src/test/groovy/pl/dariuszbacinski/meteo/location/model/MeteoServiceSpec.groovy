package pl.dariuszbacinski.meteo.location.model
import pl.dariuszbacinski.meteo.shadow.ShadowRoboSpecification
import rx.Observable
import rx.Subscriber
import rx.schedulers.Schedulers
import spock.lang.Ignore

@Ignore
class MeteoServiceSpec extends ShadowRoboSpecification {
    def "GetGridCoordinatedBasedOnLocation"() {
        given:
            MeteoService objectUnderTest = new MeteoService()
            android.location.Location locationMock = Mock()
            locationMock.getLatitude() >> 52.2d
            locationMock.getLongitude() >> 21.0d
        when:
            Observable<Location> locationObservable = objectUnderTest.getGridCoordinatedBasedOnLocation(locationMock).subscribeOn(Schedulers.immediate())
        then:
            locationObservable.subscribe(new Subscriber<Location>() {
                @Override
                void onCompleted() {

                }

                @Override
                void onError(Throwable e) {
                    e.toString()
                }

                @Override
                void onNext(Location o) {
                    o.toString()
                }
            })
    }
}
