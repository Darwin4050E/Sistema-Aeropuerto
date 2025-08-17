package ec.edu.espol.sistemaaeropuerto.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane; 
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.fx_viewer.FxViewer;
import org.graphstream.ui.view.View;


public class MainViewController {

    @FXML
    private BorderPane graphPane;

    public void initialize() {
        System.setProperty("org.graphstream.ui", "javafx");

        Graph displayGraph = new SingleGraph("Red de Vuelos");
        
        displayGraph.setAttribute("ui.stylesheet", 
            "node { size: 15px; fill-color: #1E90FF; text-size: 12px; text-alignment: under; } " +
            "edge { shape: line; size: 2px; fill-color: #777; arrow-size: 10px, 3px; }");

        displayGraph.addNode("PKX").setAttribute("ui.label", "Daxing");
        displayGraph.addNode("JFK").setAttribute("ui.label", "J.F. Kennedy");
        displayGraph.addNode("LHR").setAttribute("ui.label", "Heathrow");
        displayGraph.addEdge("PKX_JFK", "PKX", "JFK", true);
        displayGraph.addEdge("JFK_LHR", "JFK", "LHR", true);
        displayGraph.addEdge("LHR_PKX", "LHR", "PKX", true);
        
        PersistenceController.graphSerializer(displayGraph);
        
        FxViewer viewer = new FxViewer(displayGraph, FxViewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
        
        viewer.enableAutoLayout();
        View view = viewer.addDefaultView(false);
        view.getCamera().setViewPercent(1);

        graphPane.setCenter((javafx.scene.Node) view);
    }
}
