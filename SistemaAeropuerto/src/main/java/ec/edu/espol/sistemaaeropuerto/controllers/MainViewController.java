package ec.edu.espol.sistemaaeropuerto.controllers;

import ec.edu.espol.sistemaaeropuerto.utils.PersistenceController;
import javafx.fxml.FXML;
import ec.edu.espol.sistemaaeropuerto.utils.*;
import ec.edu.espol.sistemaaeropuerto.models.*;
import javafx.scene.layout.BorderPane; 
import org.graphstream.graph.Graph;
import org.graphstream.ui.fx_viewer.FxViewer;
import org.graphstream.ui.view.View;
import org.graphstream.ui.layout.springbox.implementations.LinLog;
import javafx.application.Platform;


public class MainViewController {

    @FXML
    private BorderPane graphPane;
    
    private FxViewer viewer;

    public void initialize() {
        System.setProperty("org.graphstream.ui", "javafx");
        
        // Load or create initial graph
        GraphAL initial = PersistenceController.graphDeserializer();
        if (initial == null) {
            // If no serialized data exists, create and save initial graph
            initial = PersistenceController.getInitialGraph();
            PersistenceController.graphSerializer(initial);
        }
        
        Graph displayGraph = GraphAdapter.toGraphStream(initial);
        
        // Create viewer with specific threading model
        viewer = new FxViewer(displayGraph, FxViewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
        
        // Configure layout algorithm with stability settings
        LinLog layout = new LinLog(false);
        layout.configure(0.9, 0, true, 0.9);
        layout.setStabilizationLimit(0.9);
        layout.setQuality(0.9);
        viewer.enableAutoLayout(layout);
        
        // Create view and configure camera
        View view = viewer.addDefaultView(false);
        view.getCamera().setViewPercent(0.8);
        view.getCamera().setViewCenter(0, 0, 0);
        
        // Add the view to the pane
        graphPane.setCenter(((javafx.scene.Node) view));
        
        // Stabilize the layout after a short delay
        Platform.runLater(() -> {
            try {
                Thread.sleep(2000); // Let layout stabilize
                viewer.disableAutoLayout(); // Stop automatic movement
                view.getCamera().resetView(); // Reset camera to center
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }
    
    // Method to reset camera to center (can be called by buttons later)
    public void centerGraph() {
        if (viewer != null) {
            View view = viewer.getDefaultView();
            if (view != null) {
                view.getCamera().resetView();
                view.getCamera().setViewCenter(0, 0, 0);
            }
        }
    }
}
