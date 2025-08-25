/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.flightcontrol.controllers;

import ec.edu.espol.flightcontrol.App;
import ec.edu.espol.flightcontrol.models.*;
import ec.edu.espol.flightcontrol.utils.GraphContext;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/**
 *
 * @author gabriel
 */
public class AirportCreationController {
    @FXML
    ImageView airportImage;
    
    @FXML
    TextField codeInput;
    
    @FXML
    TextField nameInput;
    
    @FXML
    TextField cityInput;
    
    @FXML
    TextField countryInput;
    
    @FXML
    private void selectImage() {
        System.out.println("Seleccionando imagen...");
    }
    
    @FXML
    private void switchToAirports() throws IOException {
        App.setRoot("airport");
    }
    
    @FXML
    private void saveAirport() throws IOException {
        String code = codeInput.getText().trim();
        String name = nameInput.getText().trim();
        String city = cityInput.getText().trim();
        String country = countryInput.getText().trim();
        
        if (code.isEmpty() || name.isEmpty() || city.isEmpty() || country.isEmpty()) {
            UtilController.showAlert(AlertType.ERROR, "Error de Validación", "Todos los campos son obligatorios.");
            return;
        }
        
        try {
            
            Airport newAirport = new Airport(code, name, city, country);
            GraphAL<Airport, Flight> currentGraph = GraphContext.getCurrentGraph();
            currentGraph.addVertex(newAirport);
            GraphContext.updateGraph(currentGraph);
            UtilController.showAlert(AlertType.INFORMATION, "Éxito", "Aeropuerto '" + name + "' guardado correctamente.");
            App.setUnsavedChanges(true);
            clearFields();
            switchToAirports();
            
        } catch (Exception e) {
            UtilController.showAlert(AlertType.ERROR, "Error al Guardar", "Ocurrió un error inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void clearFields() {
        codeInput.clear();
        nameInput.clear();
        cityInput.clear();
        countryInput.clear();
        //airportImage.setImage(null);
    }
    
}
