package pl.dariuszbacinski.meteo.diagram.viewmodel

import pl.dariuszbacinski.meteo.diagram.model.SelectedDiagram
import pl.dariuszbacinski.meteo.shadow.ShadowRoboSpecification
import spock.lang.Subject

import static pl.dariuszbacinski.meteo.diagram.viewmodel.DiagramItemViewModel.DiagramItemViewModelBuilder
import static pl.dariuszbacinski.meteo.diagram.viewmodel.DiagramItemViewModel.Legend

class DiagramPositionFunctionSpec extends ShadowRoboSpecification {

    @Subject
    DiagramPagerViewModel.DiagramPositionFunction objectUnderTest

    def "gets first position for empty list"() {
        given:
            objectUnderTest = new DiagramPagerViewModel.DiagramPositionFunction([])
        when:
            Integer position = objectUnderTest.call(new SelectedDiagram(1L))
        then:
            position == 0
    }

    def "gets item position"() {
        given:
            objectUnderTest = new DiagramPagerViewModel.DiagramPositionFunction([createItemWithId(2L), createItemWithId(1L)])
        when:
            Integer position = objectUnderTest.call(new SelectedDiagram(1L))
        then:
            position == 1
    }

    def "gets legend position"() {
        given:
            objectUnderTest = new DiagramPagerViewModel.DiagramPositionFunction([createItemWithId(2L), createLegend()])
        when:
            Integer position = objectUnderTest.call(new SelectedDiagram(Legend.DIAGRAM_ID))
        then:
            position == 1
    }

    private DiagramItemViewModel createItemWithId(Long id) {
        return new DiagramItemViewModelBuilder().id(id).build()
    }

    private DiagramItemViewModel createLegend() {
        return new Legend("");
    }
}
