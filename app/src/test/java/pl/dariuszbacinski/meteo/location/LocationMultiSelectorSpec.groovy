package pl.dariuszbacinski.meteo.location
import com.bignerdranch.android.multiselector.MultiSelector
import pl.dariuszbacinski.meteo.shadow.ShadowRoboSpecification

class LocationMultiSelectorSpec extends ShadowRoboSpecification {

    def "should not select positions for empty locations list"() {
        given:
            MultiSelector multiSelector = new MultiSelector()
            LocationMultiSelector objectUnderTest = new LocationMultiSelector(multiSelector)
            def selectedLocations = [new Location("first", 0,0,),new Location("second", 0,0,) ]
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
}
