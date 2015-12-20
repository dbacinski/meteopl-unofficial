package pl.dariuszbacinski.meteo.location.model
import android.location.Address
import pl.charmas.android.reactivelocation.ReactiveLocationProvider
import pl.dariuszbacinski.meteo.shadow.ShadowRoboSpecification
import rx.Observable
import rx.observers.TestSubscriber
import spock.lang.Subject

class LocationNameResolverSpec extends ShadowRoboSpecification {

    @Subject
    LocationNameResolver objectUnderTest;
    ReactiveLocationProvider reactiveLocationProviderStub = Stub(ReactiveLocationProvider)
    TestSubscriber<String> subscriber = new TestSubscriber<>()

    def "resolves blank address"() {
        given:
            objectUnderTest = new LocationNameResolver(reactiveLocationProviderStub)
            reactiveLocationProviderStub.getReverseGeocodeObservable(_, _, 1) >> Observable.just([new Address(null)])
        when:
            objectUnderTest.getLocationName(new android.location.Location("")).subscribe(subscriber)
        then:
            subscriber.assertValue("N/A")
    }

    def "resolves empty address list"() {
        given:
            objectUnderTest = new LocationNameResolver(reactiveLocationProviderStub)
            reactiveLocationProviderStub.getReverseGeocodeObservable(_, _, 1) >> Observable.just([])
        when:
            objectUnderTest.getLocationName(new android.location.Location("")).subscribe(subscriber)
        then:
            subscriber.assertValue("N/A")
    }

    def "resolves address with locality"() {
        given:
            objectUnderTest = new LocationNameResolver(reactiveLocationProviderStub)
            Address address = new Address(null)
            address.setLocality("Warszawa")
            reactiveLocationProviderStub.getReverseGeocodeObservable(_, _, 1) >> Observable.just([address])
        when:
            objectUnderTest.getLocationName(new android.location.Location("")).subscribe(subscriber)
        then:
            subscriber.assertValue("Warszawa")
    }

    def "resolves address with feature name"() {
        given:
            objectUnderTest = new LocationNameResolver(reactiveLocationProviderStub)
            Address address = new Address(null)
            address.setFeatureName("Warszawa")
            reactiveLocationProviderStub.getReverseGeocodeObservable(_, _, 1) >> Observable.just([address])
        when:
            objectUnderTest.getLocationName(new android.location.Location("")).subscribe(subscriber)
        then:
            subscriber.assertValue("Warszawa")
    }
}
