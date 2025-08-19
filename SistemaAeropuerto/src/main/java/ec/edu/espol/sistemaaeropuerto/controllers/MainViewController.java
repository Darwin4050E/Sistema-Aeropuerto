package ec.edu.espol.sistemaaeropuerto.controllers;

import ec.edu.espol.sistemaaeropuerto.utils.PersistenceController;
import javafx.fxml.FXML;
import ec.edu.espol.sistemaaeropuerto.utils.*;
import ec.edu.espol.sistemaaeropuerto.models.*;
import javafx.scene.layout.BorderPane; 
import org.graphstream.graph.Graph;
import org.graphstream.ui.fx_viewer.FxViewer;
import org.graphstream.ui.view.View;


public class MainViewController {

    @FXML
    private BorderPane graphPane;

    public void initialize() {
        System.setProperty("org.graphstream.ui", "javafx");
        GraphAL initial = PersistenceController.graphDeserializer();
        if (initial == null) return;
        Graph displayGraph = GraphAdapter.toGraphStream(initial);

        
        FxViewer viewer = new FxViewer(displayGraph, FxViewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
        
        viewer.enableAutoLayout();
        View view = viewer.addDefaultView(false);
        view.getCamera().setViewPercent(0.9);

        graphPane.setCenter(((javafx.scene.Node) view));
    }
}
