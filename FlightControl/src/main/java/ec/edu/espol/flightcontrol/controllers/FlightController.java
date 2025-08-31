/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.flightcontrol.controllers;

import ec.edu.espol.flightcontrol.models.*;
import ec.edu.espol.flightcontrol.utils.*;
import ec.edu.espol.flightcontrol.App;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author Grupo 1 - P1
 */
public class FlightController implements GraphSubscriber {
    
    @FXML
    private GridPane flightGrid;

    @FXML
    public void initialize() {
        populateGrid();
    }

    private void populateGrid() {
        
        List<Edge<Airport, Flight>> edges = getSortedEdgeList();
        if (edges.isEmpty()) return;
        
        // Limpiar el grid y solo dejar el encabezado de la tabla
        flightGrid.getChildren().removeIf(node -> {
            Integer rowIndex = GridPane.getRowIndex(node);
            return rowIndex != null && rowIndex > 0;
        });
        
        int rowIndex = 1; 

        for (Edge<Airport, Flight> edge: edges) { 

            RowConstraints rowConst = new RowConstraints();
            rowConst.setMinHeight(60);
            rowConst.setPrefHeight(80);
            flightGrid.getRowConstraints().add(rowConst);

            Label flightCode = new Label(edge.getData().getFlightNumber());
            Label flightAirline = new Label(edge.getData().getAirline());
            Label flightSourceTarget = new Label(edge.getSourceVertex().getContent().toString() + "\n" + edge.getTargetVertex().getContent().toString());
            flightSourceTarget.setWrapText(true);
            flightSourceTarget.setAlignment(Pos.CENTER);
            flightSourceTarget.setTextAlignment(TextAlignment.CENTER);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            Label flightDeparture = new Label(edge.getData().getDepartureTime().format(formatter));
            Label flightArrival = new Label(edge.getData().getArrivalTime().format(formatter));

            Button editBtn = new Button();
            Button deleteBtn = new Button();
            editBtn.setOnAction(event -> handleEdit(edge));
            deleteBtn.setOnAction(event -> handleDelete(edge));
            HBox actionsPane = new HBox(5, editBtn, deleteBtn);
            actionsPane.setAlignment(javafx.geometry.Pos.CENTER);

            Image editIconImage = new Image(getClass().getResourceAsStream("/icons/pencil.png"));
            ImageView editIconView = new ImageView(editIconImage);
            editIconView.setFitHeight(16); 
            editIconView.setFitWidth(16);
            editBtn.setGraphic(editIconView);
            editBtn.getStyleClass().addAll("button-icon", "button-edit");

            Image deleteIconImage = new Image(getClass().getResourceAsStream("/icons/trash.png"));
            ImageView deleteIconView = new ImageView(deleteIconImage);
            deleteIconView.setFitHeight(16);
            deleteIconView.setFitWidth(16);
            deleteBtn.setGraphic(deleteIconView);
            deleteBtn.getStyleClass().addAll("button-icon", "button-danger");

            flightGrid.add(new Label("" + rowIndex), 0, rowIndex); // (nodo, col, fila)
            flightGrid.add(flightCode, 1, rowIndex);    
            flightGrid.add(flightAirline, 2, rowIndex);
            flightGrid.add(flightSourceTarget, 3, rowIndex);
            flightGrid.add(flightDeparture, 4, rowIndex);
            flightGrid.add(flightArrival, 5, rowIndex);
            flightGrid.add(actionsPane, 6, rowIndex);
            rowIndex++;
        }
    }
    
    private List<Edge<Airport, Flight>> getSortedEdgeList() {
        List<Edge<Airport, Flight>> sortedEdges = new LinkedList<>();
        
        GraphAL<Airport, Flight> currentGraph = GraphContext.getCurrentGraph();
        if (currentGraph == null) return sortedEdges;

        Comparator<Edge<Airport, Flight>> flightComparator = (edge1, edge2) -> {
            LocalDateTime departure1 = edge1.getData().getDepartureTime();
            LocalDateTime departure2 = edge2.getData().getDepartureTime();

            return departure1.compareTo(departure2); // comparador implicito de LocalDateTime
        };

        PriorityQueue<Edge<Airport, Flight>> flightQueue = new PriorityQueue<>(flightComparator);

        for (Vertex<Airport, Flight> vertex : currentGraph.getVertexs()) {
            for(Edge<Airport, Flight> edge : vertex.getEdges()) {
                flightQueue.offer(edge);
            }
        }
        
        while (!flightQueue.isEmpty()) {
            sortedEdges.add(flightQueue.poll());
        }
        
        return sortedEdges;
    }

    private void handleEdit(Edge edge) {
        
        try {
           FXMLLoader loader = new FXMLLoader(App.class.getResource("flightsEditionView.fxml"));
           Parent root = loader.load();
           FlightEditionController editController = loader.getController();
           editController.initData(edge);
           App.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleDelete(Edge<Airport, Flight> edge){
        boolean confirmed = UtilController.showDeleteConfirmation(edge.getData().getFlightNumber());
        if (confirmed) {
            GraphAL<Airport, Flight> currentGraph = GraphContext.getCurrentGraph();
            if (currentGraph.removeEdge(edge.getData(), edge.getSourceVertex().getContent(), edge.getTargetVertex().getContent())) {
                UtilController.showAlert(Alert.AlertType.INFORMATION, "Éxito", "Vuelo '" + edge.getData().getFlightNumber() + "' eliminado correctamente.");
                App.setUnsavedChanges(true);
                GraphContext.updateGraph(currentGraph);
                try {
                    switchToMain();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                UtilController.showAlert(Alert.AlertType.ERROR, "Error al Eliminar", "Ocurrió un error durante la eliminación.");
            }
        }
    }
        
    @FXML
    private void switchToMain() throws IOException {
        App.setRoot("mainView");
    }
    
        
    @FXML
    private void switchToFlightCreation() throws IOException {
        App.setRoot("flightsCreationView");
    }
    
    @Override
    public void update() {
        populateGrid();
    }
}
