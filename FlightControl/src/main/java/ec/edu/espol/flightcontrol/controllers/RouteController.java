/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.flightcontrol.controllers;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import ec.edu.espol.flightcontrol.App;
import ec.edu.espol.flightcontrol.models.*;
import ec.edu.espol.flightcontrol.utils.AirportComparator;
import ec.edu.espol.flightcontrol.utils.GraphContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

// Imports para JGraphX y la integración con JavaFX
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import ec.edu.espol.flightcontrol.utils.GraphAdapter;
import javafx.embed.swing.SwingNode;
import javax.swing.BorderFactory;
import javax.swing.SwingUtilities;
import javafx.scene.layout.StackPane;
import javax.swing.SwingConstants;

/**
 *
 * @author Grupo 1 - P1
 */
public class RouteController {
    
    @FXML
    ComboBox<String> originCombo;
    
    @FXML
    ComboBox<String> targetCombo;
    
    @FXML
    Label resultLabel;
    
    @FXML
    private BorderPane graphPane;
    
    @FXML
    private void switchToMain() throws IOException {
        App.setRoot("mainView");
    }
    
    @FXML
    public void initialize() {
        setupComboBoxes();
    }
    
    private void setupComboBoxes() {
        GraphAL<Airport, Flight> graph = GraphContext.getCurrentGraph();
        List<String> airportCodes = new ArrayList<>();

        for (Vertex<Airport, Flight> vertex : graph.getVertexs()) {
            airportCodes.add(vertex.getContent().toString());
        }

        originCombo.getItems().setAll(airportCodes);
        targetCombo.getItems().setAll(airportCodes);
    }
    
    @FXML
    private void findRoute() throws IOException {
        if (!validateRequiredFields()) return;

        String originCode = extractAirportCode(originCombo.getValue());
        String targetCode = extractAirportCode(targetCombo.getValue());

        if (!validateDifferentAirports(originCode, targetCode)) return;
        
        Airport origin = findAirportByCode(originCode);
        Airport target = findAirportByCode(targetCode);
        
        GraphAL<Airport, Flight> currentGraph = GraphContext.getCurrentGraph();
        List<Vertex<Airport, Flight>> path = currentGraph.runDijkstra(origin, target);
        displayPath(path);
    }
    
    private boolean validateRequiredFields() {
        String origin = originCombo.getValue();
        String target = targetCombo.getValue();

        if (origin == null || target == null) {
            UtilController.showAlert(AlertType.ERROR, "Error de Validación", "Todos los campos son obligatorios.");
            return false;
        }
        return true;
    }

    private String extractAirportCode(String airportStr) {
        return airportStr.split("\\(")[1].replace(")", "");
    }

    private boolean validateDifferentAirports(String originCode, String targetCode) {
        if (originCode.equals(targetCode)) {
            UtilController.showAlert(AlertType.ERROR, "Error de Validación", "El aeropuerto de origen y destino no pueden ser el mismo.");
            return false;
        }
        return true;
    }
    
    private Airport findAirportByCode(String code) {
        BSTree<Airport, String> searchTree = GraphContext.getAirportSearchTree();

        if (searchTree != null) {
            return searchTree.find(code);
        }

        return null; 
    }
    
    private void displayPath(List<Vertex<Airport, Flight>> path) {
        graphPane.setCenter(null);

        if (path.isEmpty()) {
            UtilController.showAlert(Alert.AlertType.INFORMATION, "Sin Ruta", "No se encontró una ruta.");
            resultLabel.setText("No se encontró una ruta entre los aeropuertos seleccionados.");
            return;
        }
        
        updateResultLabel(path);
        GraphAL<Airport, Flight> subgraph = new GraphAL<>(new AirportComparator(), true);
        
        for (Vertex<Airport, Flight> vertex : path) {
            subgraph.addVertex(vertex.getContent());
        }

        for (int i = 0; i < path.size() - 1; i++) {
            Vertex<Airport, Flight> sourceStep = path.get(i);
            Vertex<Airport, Flight> targetStep = path.get(i + 1);
            
            int expectedWeight = targetStep.getCumulativeDistance() - sourceStep.getCumulativeDistance();
            Edge<Airport, Flight> edge = findEdgeBetween(sourceStep, targetStep, expectedWeight);

            if (edge != null) {
                subgraph.addEdge(edge.getData(), sourceStep.getContent(), targetStep.getContent(), edge.getWeight());
            }
        }
        
        displayGraph(subgraph);
    }
    
    private void displayGraph(GraphAL<Airport, Flight> flightGraph) {
        final SwingNode swingNode = new SwingNode();
        graphPane.setStyle("-fx-background-color: white;");

        SwingUtilities.invokeLater(() -> {
            mxGraph graph = GraphAdapter.toJGraphX(flightGraph, true);

            graph.setCellsMovable(false);
            graph.setCellsResizable(false);
            graph.setCellsEditable(false);

            mxHierarchicalLayout layout = new mxHierarchicalLayout(graph);
            layout.setOrientation(SwingConstants.WEST);
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
    
    private void updateResultLabel(List<Vertex<Airport, Flight>> path) {
        Vertex<Airport, Flight> startVertex = path.get(0);
        Vertex<Airport, Flight> endVertex = path.get(path.size() - 1);
        int totalDistance = endVertex.getCumulativeDistance();

        String resultText = String.format("Ruta más corta de %s a %s (%d km)", startVertex.getContent().getCode(), endVertex.getContent().getCode(), totalDistance);
        resultLabel.setText(resultText);
    }
    
    private Edge<Airport, Flight> findEdgeBetween(Vertex<Airport, Flight> source, Vertex<Airport, Flight> target, int weight) {
        for (Edge<Airport, Flight> edge : source.getEdges()) {
            if (edge.getTargetVertex().equals(target) && edge.getWeight() == weight) {
                return edge; // Devuelve la arista exacta que usó Dijkstra
            }
        }
        return null;
    }
}
