package ec.edu.espol.flightcontrol.controllers;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import ec.edu.espol.flightcontrol.App;
import ec.edu.espol.flightcontrol.utils.*;
import ec.edu.espol.flightcontrol.models.*;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane; 
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

// Imports para JGraphX y la integración con JavaFX
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import javafx.application.Platform;
import javafx.embed.swing.SwingNode;
import javax.swing.BorderFactory;
import javax.swing.SwingUtilities;
import javafx.scene.layout.StackPane;

/**
 *
 * @author Grupo 1 - P1
 */

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
            graph.setCellsBendable(false);  
            graph.setEdgeLabelsMovable(false);
            graph.setCellsDisconnectable(false);
            
            mxHierarchicalLayout layout = new mxHierarchicalLayout(graph);
            layout.execute(graph.getDefaultParent());

            mxGraphComponent graphComponent = new mxGraphComponent(graph);
            graphComponent.setConnectable(false);
            graphComponent.setBackground(java.awt.Color.WHITE);
            graphComponent.getViewport().setBackground(java.awt.Color.WHITE);
            graphComponent.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
            graphComponent.setDragEnabled(false);
            
            attachNodeClickHandler(graphComponent, graph, flightGraph);
                    
            swingNode.setContent(graphComponent);
        });

        StackPane wrapper = new StackPane(swingNode);
        graphPane.setCenter(wrapper);
        
    }
    
    private void attachNodeClickHandler(mxGraphComponent graphComponent, mxGraph graph, GraphAL<Airport, Flight> flightGraph) {
        graphComponent.getGraphControl().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                Object cell = graphComponent.getCellAt(e.getX(), e.getY());
                if (cell != null && graph.getModel().isVertex(cell)) {
                    String value = (String) graph.getModel().getValue(cell);
                    String airportCode = Util.extractAirportCode(value);
                    Airport airport = Util.findAirportByCode(airportCode);
                    Vertex<Airport, Flight> vertex = flightGraph.findVertex(airport);
                    if (vertex == null) return;
                    Platform.runLater(() -> {
                            UtilController.showAirportFlightInfo(airport, vertex.getEdges());
                    });
                }
            }
        });
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
