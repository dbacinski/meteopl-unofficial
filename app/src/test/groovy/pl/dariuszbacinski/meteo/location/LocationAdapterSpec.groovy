package pl.dariuszbacinski.meteo.location

import com.bignerdranch.android.multiselector.MultiSelector
import pl.dariuszbacinski.meteo.rx.Indexed
import pl.dariuszbacinski.meteo.shadow.ShadowRoboSpecification
import rx.Observable

class LocationAdapterSpec extends ShadowRoboSpecification {

    def "handles empty list"() {
        given:
            LocationAdapter objectUnderTest = new LocationAdapter(new MultiSelector(), [], [])
        when:
            def count = objectUnderTest.getItemCount()
        then:
            count == 0
    }

    def "filters locations by name ignoring case"() {
        given:
            LocationAdapter objectUnderTest = new LocationAdapter(new MultiSelector(), [new Location("Warszawa", 0, 0), new Location("Lublin", 0, 0)], [])
        when:
            objectUnderTest.filterLocationsByName("lub")
        then:
            objectUnderTest.getItemCount() == 1
            getFirstLocation(objectUnderTest.getLocationObservable()).value.name == "Lublin"
    }

    def "filters locations by name and updates position"() {
        given:
            LocationAdapter objectUnderTest = new LocationAdapter(new MultiSelector(), [new Location("Warszawa", 0, 0), new Location("Lublin", 0, 0)], [])
        when:
            objectUnderTest.filterLocationsByName("Lublin")
        then:
            getFirstLocation(objectUnderTest.getLocationObservable()).index == 0
            getFirstLocation(objectUnderTest.getLocationObservable()).originalIndex == 1
    }

    def getFirstLocation(Observable<Indexed<Location>> indexedObservable) {
        indexedObservable.toList().toBlocking().single().get(0)
    }
}
