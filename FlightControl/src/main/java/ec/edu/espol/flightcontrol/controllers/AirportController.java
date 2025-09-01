/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.flightcontrol.controllers;

import ec.edu.espol.flightcontrol.models.*;
import ec.edu.espol.flightcontrol.utils.*;
import ec.edu.espol.flightcontrol.App;
import java.io.IOException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;

/**
 *
 * @author Grupo 1 - P1
 */
public class AirportController implements GraphSubscriber {
    
    @FXML
    GridPane airportGrid;
    
    @FXML
    ComboBox<String> orderCombo;

    @FXML
    public void initialize() {
        setupComboBoxes();
        populateGrid();
    }
    
    private void setupComboBoxes() {
        orderCombo.getItems().setAll("Código", "Nombre", "Ciudad", "Pais");
        orderCombo.setValue("Código");
        orderCombo.setOnAction(event -> populateGrid());
    }
    
    private void populateGrid() {
        List<Airport> sortedAirports = getSortedAirportList();
        if (sortedAirports.isEmpty()) return;

        // Limpiar el grid y solo dejar el encabezado de la tabla
        airportGrid.getChildren().removeIf(node -> {
            Integer rowIndex = GridPane.getRowIndex(node);
            return rowIndex != null && rowIndex > 0;
        });
        
        airportGrid.getRowConstraints().clear();
        
        RowConstraints headerRowConst = new RowConstraints();
        headerRowConst.setMinHeight(50);
        airportGrid.getRowConstraints().add(headerRowConst);

        int rowIndex = 1; 
        for (Airport airport : sortedAirports) {
            
            RowConstraints rowConst = new RowConstraints();
            rowConst.setMinHeight(45);
            airportGrid.getRowConstraints().add(rowConst);
            
            Label codeLabel = new Label(airport.getCode());
            Label nameLabel = new Label(airport.getName());
            Label cityLabel = new Label(airport.getCity());
            Label countryLabel = new Label(airport.getCountry());
            
            Button editBtn = new Button();
            Button deleteBtn = new Button();
            
            editBtn.setOnAction(event -> handleEdit(airport));
            deleteBtn.setOnAction(event -> handleDelete(airport));
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
            
            airportGrid.add(new Label("" + rowIndex), 0, rowIndex); // (nodo, col, fila)
            airportGrid.add(codeLabel, 1, rowIndex);    
            airportGrid.add(nameLabel, 2, rowIndex);
            airportGrid.add(cityLabel, 3, rowIndex);
            airportGrid.add(countryLabel, 4, rowIndex);
            airportGrid.add(actionsPane, 5, rowIndex);
            rowIndex++;
        }
    }
    
    private List<Airport> getSortedAirportList() {
        List<Airport> sortedAirports = new LinkedList<>();
        Comparator<Airport> cmp = getAirportComparator();
        
        GraphAL<Airport, Flight> currentGraph = GraphContext.getCurrentGraph();
        if (currentGraph == null) return sortedAirports;

        Heap<Airport> heap = new Heap<>(cmp, true, currentGraph.getVertexContents());
        
        while (!heap.isEmpty()) {
            sortedAirports.add(heap.poll());
        }
        
        return sortedAirports;
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
    
    private Comparator<Airport> getAirportComparator() {
        String order = orderCombo.getValue();
        if (order == null) order = "";
        
        Comparator<Airport> cmpByCode = (Airport a1, Airport a2) -> {
            return a2.getCode().compareTo(a1.getCode());
        };
        
        Comparator<Airport> cmpByName = (Airport a1, Airport a2) -> {
            return a2.getName().compareTo(a1.getName());
        };
        
        Comparator<Airport> cmpByCity = (Airport a1, Airport a2) -> {
            return a2.getCity().compareTo(a1.getCity());
        };
        
        Comparator<Airport> cmpByCountry = (Airport a1, Airport a2) -> {
            return a2.getCountry().compareTo(a1.getCountry());
        };
        
        Comparator<Airport> cmp = cmpByCode;
        
        switch (order) {
            case "Código":
                cmp = cmpByCode;
                break;
            case "Nombre":
                cmp = cmpByName;
                break;
            case "Ciudad":
                cmp = cmpByCity;
                break;
            case "Pais":
                cmp = cmpByCountry;
                break;
            default:
                cmp = cmpByCode;
                break;                
        }
        
        return cmp;
    }

}
