package ec.edu.espol.sistemaaeropuerto.controllers;

import ec.edu.espol.sistemaaeropuerto.App;
import ec.edu.espol.sistemaaeropuerto.utils.*;
import ec.edu.espol.sistemaaeropuerto.models.*;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane; 
import javafx.scene.control.Label; 

/*
import org.graphstream.graph.Graph;
import org.graphstream.ui.fx_viewer.FxViewer;
import org.graphstream.ui.view.View;
*/

public class MainViewController implements GraphSubscriber {

    @FXML
    private BorderPane graphPane;

    public void initialize() {
        //System.setProperty("org.graphstream.ui", "javafx");
        GraphContext.addListener(this);
        GraphAL initial = PersistenceController.graphDeserializer();
        GraphContext.updateGraph(initial);
    }
    
    @Override
    public void update() {
        refreshGraphView();
    }
    
    public void refreshGraphView() {
        GraphAL currentGraph = GraphContext.getCurrentGraph();
        if (currentGraph == null) {
            graphPane.setCenter(null);
            return;
        }
        displayGraph(currentGraph);
    }
    
    public void displayGraph(GraphAL<Airport, Flight> graph) {
        /*
        Graph displayGraph = GraphAdapter.toGraphStream(graph);
        FxViewer viewer = new FxViewer(displayGraph, FxViewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
        viewer.enableAutoLayout();
        View view = viewer.addDefaultView(false);
        view.getCamera().setViewPercent(1.2);
        graphPane.setCenter(((javafx.scene.Node) view));
        */
        graphPane.setCenter(new Label("Hola mundo"));
    }
    
    @FXML
    private void switchToAirportView() throws IOException {
        App.setRoot("airport");
    }
}
