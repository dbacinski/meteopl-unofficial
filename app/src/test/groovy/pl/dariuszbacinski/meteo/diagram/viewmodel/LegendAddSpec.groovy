package pl.dariuszbacinski.meteo.diagram.viewmodel
import pl.dariuszbacinski.meteo.location.model.Location
import pl.dariuszbacinski.meteo.shadow.ShadowRoboSpecification
import spock.lang.Subject

class LegendAddSpec extends ShadowRoboSpecification {

    @Subject
    DiagramPagerViewModel objectUnderTest

    def "adds legend to empty list"() {
        given:
            DiagramItemViewModel legend = new DiagramItemViewModel.Legend("Legenda")
            objectUnderTest = new DiagramPagerViewModel([], legend)
        when:
            boolean isAdded = objectUnderTest.addLegend()
        then:
            isAdded == true
            objectUnderTest.items.last() == legend
    }

    def "adds legend at list end"() {
        given:
            DiagramItemViewModel legend = createLegendItem()
            objectUnderTest = new DiagramPagerViewModel([createItem()], legend)
        when:
            boolean isAdded = objectUnderTest.addLegend()
        then:
            isAdded == true
            objectUnderTest.items.last() == legend
    }

    def "adds legend only once"() {
        given:
            DiagramItemViewModel legend = createLegendItem()
            objectUnderTest = new DiagramPagerViewModel([createItem()], legend)
        when:
            objectUnderTest.addLegend()
            boolean isAdded = objectUnderTest.addLegend()
        then:
            isAdded == false
            objectUnderTest.items.size() == 2
            objectUnderTest.items.last() == legend

    }

    DiagramItemViewModel.Legend createLegendItem() {
        return new DiagramItemViewModel.Legend("Legenda")
    }

    Location createItem() {
        return new Location.LocationBuilder().build()
    }
}
