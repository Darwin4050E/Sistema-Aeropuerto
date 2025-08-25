package ec.edu.espol.flightcontrol.controllers;

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
    
    public void displayGraph(GraphAL<Airport, Flight> graph) {
        if (graph == null || graph.getVertexs().isEmpty()) {
            graphPane.setCenter(new Label("El grafo está vacío."));
            return;
        }

        StringBuilder sb = new StringBuilder("Representación del Grafo (Lista de Adyacencia):\n\n");

        for (Vertex<Airport, Flight> vertex : graph.getVertexs()) {
            sb.append("Aeropuerto: ").append(vertex.getContent().toString()).append("\n");
            if (vertex.getEdges().isEmpty()) {
                sb.append("\t -> (No tiene vuelos de salida)\n");
            } else {
                for (Edge<Airport, Flight> edge : vertex.getEdges()) {
                    Vertex<Airport, Flight> destination = edge.getTargetVertex();
                    sb.append("\t -> Vuelo hacia: ").append(destination.getContent().toString());
                    sb.append(" [Info: ").append(edge.getData().toString());
                    sb.append(", Peso: ").append(edge.getWeight()).append("]\n");
                }
            }
            sb.append("\n");
        }
        
        TextArea graphTextArea = new TextArea(sb.toString());
        graphTextArea.setEditable(false); 
        graphTextArea.setStyle("-fx-font-family: 'monospace';"); 

        graphPane.setCenter(graphTextArea);
    }
    
    @FXML
    private void switchToAirportView() throws IOException {
        App.setRoot("airport");
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
