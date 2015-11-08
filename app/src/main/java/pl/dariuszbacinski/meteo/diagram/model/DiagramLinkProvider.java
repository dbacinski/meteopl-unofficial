package pl.dariuszbacinski.meteo.diagram.model;

import pl.dariuszbacinski.meteo.location.Location;

public class DiagramLinkProvider {

    public static String createDiagramLink(Location location) {
        return "http://www.meteo.pl/um/metco/mgram_pict.php?ntype=0u"
                + "&row=" + location.getRow()
                + "&col=" + location.getCol()
                + "&lang=pl";
    }
}
