package pl.dariuszbacinski.meteo;

class DiagramLinkProvider {

    public String createDiagramLink(DiagramCoordinates diagramCoordinates) {
        return "http://www.meteo.pl/um/metco/mgram_pict.php?ntype=0u"
                + "&fdate=" + diagramCoordinates.getDate()
                + "&row=" + diagramCoordinates.getRow()
                + "&col=" + diagramCoordinates.getCol()
                + "&lang=pl";
    }
}
