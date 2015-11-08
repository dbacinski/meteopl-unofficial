package pl.dariuszbacinski.meteo.diagram
import android.net.Uri
import pl.dariuszbacinski.meteo.diagram.model.DiagramLinkProvider
import pl.dariuszbacinski.meteo.shadow.ShadowRoboSpecification

import static pl.dariuszbacinski.meteo.diagram.model.DiagramCoordinates.DiagramCoordinatesBuilder

class DiagramLinkProviderSpec extends ShadowRoboSpecification {

    DiagramLinkProvider objectUnderTest = new DiagramLinkProvider()

    def "creates link with correct parameters"() {
        given:
            DiagramCoordinates coordinates = new DiagramCoordinatesBuilder().col(100).row(200).build()
        when:
            Uri diagramLink = Uri.parse(objectUnderTest.createDiagramLink(coordinates))
        then:
            diagramLink.getFirstParameter("col") == "100"
            diagramLink.getFirstParameter("row") == "200"
            diagramLink.getFirstParameter("lang") == "pl"
    }

    def setupSpec() {
        Uri.metaClass.getFirstParameter {
            delegate.getQueryParameters(it).first()
        }
    }
}
