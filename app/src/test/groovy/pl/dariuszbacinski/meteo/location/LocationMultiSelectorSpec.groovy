package pl.dariuszbacinski.meteo.location

import com.bignerdranch.android.multiselector.MultiSelector
import pl.dariuszbacinski.meteo.rx.Indexed
import pl.dariuszbacinski.meteo.shadow.ShadowRoboSpecification
import rx.Observable

class LocationMultiSelectorSpec extends ShadowRoboSpecification {

    public static final int SECOND_POSITION = 1
    public static final int THIRD_POSITION = 2
    public static final int ANY = 0

    def "should not select positions for empty locations list"() {
        given:
            MultiSelector multiSelector = new MultiSelector()
            LocationMultiSelector objectUnderTest = new LocationMultiSelector(multiSelector)
            def selectedLocations = [new Location("first", 0, 0,), new Location("second", 0, 0,)]
        when:
            objectUnderTest.restoreSelectedItems(rx.Observable.empty(), selectedLocations);
        then:
            multiSelector.getSelectedPositions().empty
    }

    def "should not select positions for empty selected locations"() {
        given:
            MultiSelector multiSelector = new MultiSelector()
            LocationMultiSelector objectUnderTest = new LocationMultiSelector(multiSelector)
        when:
            objectUnderTest.restoreSelectedItems(rx.Observable.empty(), []);
        then:
            multiSelector.getSelectedPositions().empty
    }

    def "should select positions for selected locations"() {
        given:
            MultiSelector multiSelector = new MultiSelector()
            LocationMultiSelector objectUnderTest = new LocationMultiSelector(multiSelector)
            def observableLocations = Observable.from(
                    [[ANY, ANY, new Location('first', ANY, ANY)] as Indexed<Location>,
                     [SECOND_POSITION, SECOND_POSITION, new Location('second', ANY, ANY)] as Indexed<Location>,
                     [THIRD_POSITION, THIRD_POSITION, new Location('third', ANY, ANY)] as Indexed<Location>])
        when:
            objectUnderTest.restoreSelectedItems(observableLocations, [new Location('second', ANY, ANY), new Location('third', ANY, ANY) ]);
        then:
            multiSelector.getSelectedPositions() as Set == [THIRD_POSITION, SECOND_POSITION] as Set
    }
}
