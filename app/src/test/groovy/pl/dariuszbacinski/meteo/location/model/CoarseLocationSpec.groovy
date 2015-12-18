package pl.dariuszbacinski.meteo.location.model

import com.tbruyelle.rxpermissions.RxPermissions
import org.junit.Rule
import pl.charmas.android.reactivelocation.ReactiveLocationProvider
import pl.dariuszbacinski.meteo.shadow.ShadowRoboSpecification
import rx.Observable
import rx.observers.TestSubscriber
import rx.plugins.RxImmediateSchedulersRule
import spock.lang.Ignore

import static android.Manifest.permission.ACCESS_COARSE_LOCATION
//@CompileStatic
//@Timeout(5)
class CoarseLocationSpec extends ShadowRoboSpecification {
    RxPermissions rxPermissionsStub = Stub(RxPermissions)
    MeteoService meteoServiceStub = Stub(MeteoService)
    ReactiveLocationProvider rxLocationStub = Stub(ReactiveLocationProvider)

    @Rule
    RxImmediateSchedulersRule immediateSchedulersRule = new RxImmediateSchedulersRule()

//    void setup() {
//        rxPermissionsStub.request(ACCESS_COARSE_LOCATION) >> Observable.just(Boolean.valueOf(true))
//        rxLocationStub.getUpdatedLocation(_) >> Observable.just(new MockLocation(null).getMockNetworkProviderLocation())
//        rxLocationStub.getReverseGeocodeObservable(_, _, _) >> Observable.just([new Address(null)])
//        meteoServiceStub.getGridCoordinatedBasedOnLocation(_) >> Observable.just(new Location.LocationBuilder().name(null).row(1).col(2).build())
//    }

    def "error when permission where not granted"() {
        given:
            CoarseLocation objectUnderTest = new CoarseLocation(rxLocationStub, rxPermissionsStub, meteoServiceStub)
            rxPermissionsStub.request(ACCESS_COARSE_LOCATION) >> Observable.just(Boolean.valueOf(false))
            TestSubscriber<Location> subscriber = new TestSubscriber<>()
        when:
            objectUnderTest.requestLocation().subscribe(subscriber)
        then:
            subscriber.assertError(SecurityException)
    }

    @Ignore
    def "resolves location with name and coordinates"() {
        given:
            CoarseLocation objectUnderTest = new CoarseLocation(rxLocationStub, rxPermissionsStub, meteoServiceStub)
            TestSubscriber<Location> subscriber = new TestSubscriber<>()
        when:
            objectUnderTest.requestLocation().subscribe(subscriber)
        then:
            subscriber.assertValue(new Location.LocationBuilder().name("N/A").row(1).col(2).build())
    }
}
