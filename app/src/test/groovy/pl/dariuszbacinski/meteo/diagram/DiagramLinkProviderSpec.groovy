package pl.dariuszbacinski.meteo.diagram

import android.net.Uri
import org.robolectric.annotation.Config
import pl.dariuszbacinski.meteo.location.ShadowRoboSpecification

import static pl.dariuszbacinski.meteo.diagram.DiagramCoordinates.DiagramCoordinatesBuilder

@Config(manifest = "src/main/AndroidManifest.xml")
class DiagramLinkProviderSpec extends ShadowRoboSpecification {

    DiagramLinkProvider objectUnderTest = new DiagramLinkProvider()

    def "creates link with correct parameters"() {
        given:
            DiagramCoordinates coordinates = new DiagramCoordinatesBuilder().date("20150101").col(100).row(200).build()
        when:
            Uri diagramLink = Uri.parse(objectUnderTest.createDiagramLink(coordinates))
        then:
            diagramLink.getFirstParameter("fdate") == "20150101"
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
