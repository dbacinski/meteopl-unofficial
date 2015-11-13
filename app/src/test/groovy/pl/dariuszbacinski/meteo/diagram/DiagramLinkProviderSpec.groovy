package pl.dariuszbacinski.meteo.diagram
import android.net.Uri
import pl.dariuszbacinski.meteo.diagram.model.DiagramLinkProvider
import pl.dariuszbacinski.meteo.location.model.Location
import pl.dariuszbacinski.meteo.shadow.ShadowRoboSpecification

class DiagramLinkProviderSpec extends ShadowRoboSpecification {

    DiagramLinkProvider objectUnderTest = new DiagramLinkProvider()

    def "creates link with correct parameters"() {
        given:
            Location location = new Location("", 100, 200)
        when:
            Uri diagramLink = Uri.parse(objectUnderTest.createDiagramLink(location))
        then:
            diagramLink.getFirstParameter("row") == "100"
            diagramLink.getFirstParameter("col") == "200"
            diagramLink.getFirstParameter("lang") == "pl"
    }

    def setupSpec() {
        Uri.metaClass.getFirstParameter {
            delegate.getQueryParameters(it).first()
        }
    }
}
