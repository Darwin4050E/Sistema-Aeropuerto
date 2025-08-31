package ec.edu.espol.flightcontrol.controllers;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import ec.edu.espol.flightcontrol.App;
import ec.edu.espol.flightcontrol.utils.*;
import ec.edu.espol.flightcontrol.models.*;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane; 
import javafx.scene.control.Label; 
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

// Imports para JGraphX y la integración con JavaFX
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import javafx.embed.swing.SwingNode;
import javax.swing.BorderFactory;
import javax.swing.SwingUtilities;
import javafx.scene.layout.StackPane;

public class MainViewController implements GraphSubscriber {

    @FXML
    private BorderPane graphPane;
    
    @FXML
    private Button saveChangesBtn;

    public void initialize() {
        GraphContext.addListener(this);
        saveChangesBtn.setDisable(true);
        
        GraphAL<Airport, Flight> mainGraph = GraphContext.getCurrentGraph();
        
        if (mainGraph == null) { // cada vez que se abre la aplicacion
            mainGraph = PersistenceController.graphDeserializer();
            if (mainGraph == null) { // si nunca se ha abierto la aplicacion
                mainGraph = PersistenceController.getInitialGraph();
                PersistenceController.graphSerializer(mainGraph);
            }
        }
        
        GraphContext.updateGraph(mainGraph);
    }
    
    @Override
    public void update() {
        refreshGraphView();
                
        if (App.hasUnsavedChanges()) {
            saveChangesBtn.setDisable(false);
        }
    }
    
    public void refreshGraphView() {
        GraphAL currentGraph = GraphContext.getCurrentGraph();
        
        if (currentGraph == null) {
            graphPane.setCenter(null);
            return;
        }
     
        displayGraph(currentGraph);
        
    }
    
    private void displayGraph(GraphAL<Airport, Flight> flightGraph) {
        final SwingNode swingNode = new SwingNode();
        graphPane.setStyle("-fx-background-color: white;");

        SwingUtilities.invokeLater(() -> {
            mxGraph graph = GraphAdapter.toJGraphX(flightGraph, false);

            graph.setCellsMovable(false);
            graph.setCellsResizable(false);
            graph.setCellsEditable(false);

            mxHierarchicalLayout layout = new mxHierarchicalLayout(graph);
            layout.execute(graph.getDefaultParent());

            mxGraphComponent graphComponent = new mxGraphComponent(graph);
            graphComponent.setConnectable(false);
            graphComponent.setBackground(java.awt.Color.WHITE);
            graphComponent.getViewport().setBackground(java.awt.Color.WHITE);
            graphComponent.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
            swingNode.setContent(graphComponent);
        });

        StackPane wrapper = new StackPane(swingNode);
        graphPane.setCenter(wrapper);
        
    }


    @FXML
    private void switchToAirportView() throws IOException {
        App.setRoot("airport");
    }
    
    @FXML
    private void switchToFlightsView() throws IOException {
        App.setRoot("flights");
    }
    
    @FXML
    private void switchToRoutesView() throws IOException {
        App.setRoot("routes");
    }
    
    @FXML
    private void switchToStatsView() throws IOException {
        App.setRoot("stats");
    }
    
    @FXML
    private void saveGraphChanges() {
        if (App.hasUnsavedChanges()) {
            PersistenceController.graphSerializer(GraphContext.getCurrentGraph());
            App.setUnsavedChanges(false);
            UtilController.showAlert(AlertType.INFORMATION, "Éxito", "Grafo guardado correctamente.");
            saveChangesBtn.setDisable(true);
        }
    }
    
}
