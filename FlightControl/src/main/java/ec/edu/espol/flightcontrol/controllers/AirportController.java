/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.flightcontrol.controllers;

import ec.edu.espol.flightcontrol.models.*;
import ec.edu.espol.flightcontrol.utils.*;
import ec.edu.espol.flightcontrol.App;
import java.io.IOException;
import java.util.List;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;

/**
 *
 * @author grupo1
 */
public class AirportController implements GraphSubscriber {
    
    @FXML
    private GridPane airportGrid;

    @FXML
    public void initialize() {
        populateGrid();
    }

    private void populateGrid() {
        GraphAL<Airport, Flight> currentGraph = GraphContext.getCurrentGraph();
        if (currentGraph == null) return;

        List<Vertex<Airport, Flight>> vertexs = currentGraph.getVertexs();

        // Limpiar el grid y solo dejar el encabezado de la tabla
        airportGrid.getChildren().removeIf(node -> {
            Integer rowIndex = GridPane.getRowIndex(node);
            return rowIndex != null && rowIndex > 0;
        });

        int rowIndex = 1; 
        for (Vertex<Airport, Flight> vertex: vertexs) {
            
            // para cada aeropuerto en la lista de vertices
            RowConstraints rowConst = new RowConstraints();
            rowConst.setMinHeight(40);
            airportGrid.getRowConstraints().add(rowConst);
            
            Airport airport = vertex.getContent();
            Label codeLabel = new Label(airport.getCode());
            Label nameLabel = new Label(airport.getName());
            Label cityLabel = new Label(airport.getCity());
            Label countryLabel = new Label(airport.getCountry());
            
            Button editBtn = new Button("Editar");
            Button deleteBtn = new Button("Eliminar");
            editBtn.setOnAction(event -> handleEdit(airport));
            deleteBtn.setOnAction(event -> handleDelete(airport));
            HBox actionsPane = new HBox(5, editBtn, deleteBtn);
            actionsPane.setAlignment(javafx.geometry.Pos.CENTER);
            
            airportGrid.add(new Label("" + rowIndex), 0, rowIndex); // (nodo, col, fila)
            airportGrid.add(codeLabel, 1, rowIndex);    
            airportGrid.add(nameLabel, 2, rowIndex);
            airportGrid.add(cityLabel, 3, rowIndex);
            airportGrid.add(countryLabel, 4, rowIndex);
            airportGrid.add(actionsPane, 5, rowIndex);
            rowIndex++;
        }
    }

    private void handleEdit(Airport airport) {
        try {
           FXMLLoader loader = new FXMLLoader(App.class.getResource("airportEditionView.fxml"));
           Parent root = loader.load();
           AirportEditionController editController = loader.getController();
           editController.initData(airport);
           App.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleDelete(Airport airport){
        boolean confirmed = UtilController.showDeleteConfirmation(airport.getName());
        if (confirmed) {
            GraphAL<Airport, Flight> currentGraph = GraphContext.getCurrentGraph();
            if (currentGraph.removeVertex(airport)) {
                UtilController.showAlert(Alert.AlertType.INFORMATION, "Éxito", "Aeropuerto '" + airport.getName() + "' eliminado correctamente.");
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
    private void switchToAirportCreation() throws IOException {
        App.setRoot("airportCreationView");
    }
    
    @Override
    public void update() {
        populateGrid();
    }
}
