package pl.dariuszbacinski.meteo.diagram.model;

import lombok.AllArgsConstructor;
import pl.dariuszbacinski.meteo.location.model.Location;

@AllArgsConstructor(suppressConstructorProperties = true)
public class Diagram {

    Location location;

    public String getDiagramLink() {
        return DiagramLinkProvider.createDiagramLink(location);
    }

    public String getTitle() {
        return location.getName();
    }

    public Long getId() {
        return location.getId();
    }
}
