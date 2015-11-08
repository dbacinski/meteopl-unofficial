package pl.dariuszbacinski.meteo.diagram.model;

import lombok.AllArgsConstructor;
import pl.dariuszbacinski.meteo.location.Location;

@AllArgsConstructor(suppressConstructorProperties = true)
public class Diagram {

    Location location;

    public String getDiagramLink() {
        return DiagramLinkProvider.createDiagramLink(location);
    }
}
