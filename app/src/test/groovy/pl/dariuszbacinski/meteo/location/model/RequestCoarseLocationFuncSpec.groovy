package pl.dariuszbacinski.meteo.location.model
import com.google.android.gms.location.LocationRequest
import pl.charmas.android.reactivelocation.ReactiveLocationProvider
import pl.dariuszbacinski.meteo.shadow.ShadowRoboSpecification
import rx.observers.TestSubscriber
import spock.lang.Subject

class RequestCoarseLocationFuncSpec extends ShadowRoboSpecification {

    @Subject
    CoarseLocation.RequestCoarseLocationFunc objectUnderTest
    ReactiveLocationProvider reactiveLocationProviderStub = Stub(ReactiveLocationProvider)
    TestSubscriber<android.location.Location> subscriber = new TestSubscriber<>()

    def "requests coarse location when permission is granted"() {
        given:
            def expectedLocation = new android.location.Location("")
            reactiveLocationProviderStub.getUpdatedLocation(_ as LocationRequest) >> rx.Observable.just(expectedLocation)
            objectUnderTest = new CoarseLocation.RequestCoarseLocationFunc(reactiveLocationProviderStub)
        when:
            objectUnderTest.call(true).subscribe(subscriber)
        then:
            subscriber.assertValue(expectedLocation)
    }

    def "throws error when permission is not granted"() {
        given:
            objectUnderTest = new CoarseLocation.RequestCoarseLocationFunc(reactiveLocationProviderStub)
        when:
            objectUnderTest.call(false).subscribe(subscriber)
        then:
            subscriber.assertError(SecurityException)
    }
}
