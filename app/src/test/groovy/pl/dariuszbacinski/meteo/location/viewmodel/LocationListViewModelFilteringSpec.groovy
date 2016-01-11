package pl.dariuszbacinski.meteo.location.viewmodel

import com.bignerdranch.android.multiselector.MultiSelector
import pl.dariuszbacinski.meteo.location.model.Location
import pl.dariuszbacinski.meteo.shadow.ShadowRoboSpecification

class LocationListViewModelFilteringSpec extends ShadowRoboSpecification {

    def "filters locations by name ignoring case"() {
        given:
        LocationListViewModel objectUnderTest = new LocationListViewModel(new MultiSelector(), [new Location("Warszawa", 0, 0, ""), new Location("Lublin", 0, 0, "")], [])
        when:
        objectUnderTest.filterLocationsByName("lub")
        then:
        objectUnderTest.getItemCount() == 1
        objectUnderTest.getLocations().first().value.name == "Lublin"
    }

    def "filters locations by name with normalized letters"() {
        given:
        LocationListViewModel objectUnderTest = new LocationListViewModel(new MultiSelector(), [new Location("Warszawa", 0, 0, ""), new Location("Łódź", 0, 0, "")], [])
        when:
        objectUnderTest.filterLocationsByName("lodz")
        then:
        objectUnderTest.getItemCount() == 1
        objectUnderTest.getLocations().first().value.name == "Łódź"
    }

    def "filters locations by name with polish letters"() {
        given:
        LocationListViewModel objectUnderTest = new LocationListViewModel(new MultiSelector(), [new Location("Warszawa", 0, 0, ""), new Location("Łódź", 0, 0, "")], [])
        when:
        objectUnderTest.filterLocationsByName("łódź")
        then:
        objectUnderTest.getItemCount() == 1
        objectUnderTest.getLocations().first().value.name == "Łódź"
    }

    def "filters locations by name with mixed polish and normalized letters"() {
        given:
        LocationListViewModel objectUnderTest = new LocationListViewModel(new MultiSelector(), [new Location("Warszawa", 0, 0,""), new Location("Łódź", 0, 0,"")], [])
        when:
        objectUnderTest.filterLocationsByName("łodź")
        then:
        objectUnderTest.getItemCount() == 1
        objectUnderTest.getLocations().first().value.name == "Łódź"
    }

    def "filters locations by name and updates position"() {
        given:
        LocationListViewModel objectUnderTest = new LocationListViewModel(new MultiSelector(), [new Location("Warszawa", 0, 0,""), new Location("Lublin", 0, 0,"")], [])
        when:
        objectUnderTest.filterLocationsByName("Lublin")
        then:
        objectUnderTest.getLocations().first().index == 0
        objectUnderTest.getLocations().first().originalIndex == 1
    }
}
