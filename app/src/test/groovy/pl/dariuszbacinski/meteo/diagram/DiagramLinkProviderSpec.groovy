package pl.dariuszbacinski.meteo.diagram
import android.net.Uri
import pl.polidea.robospock.RoboSpecification

import static pl.dariuszbacinski.meteo.diagram.DiagramCoordinates.*

class DiagramLinkProviderSpec extends RoboSpecification {

    DiagramLinkProvider objectUnderTest = new DiagramLinkProvider()

    def "creates link with correct parameters"() {
        given:
            DiagramCoordinates coordinates = new DiagramCoordinatesBuilder().date("20150101").col(100).row(200).build()
        when:
            Uri diagramLink = Uri.parse(objectUnderTest.createDiagramLink(coordinates))
        then:
            diagramLink.getQueryParameters("fdate").first() == "20150101"
            diagramLink.getQueryParameters("col").first() == "100"
            diagramLink.getQueryParameters("row").first() == "200"
    }
}
