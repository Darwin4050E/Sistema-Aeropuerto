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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/**
 *
 * @author gabriel
 */
public class AirportEditionController {
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
    
    private Airport airportToEdit;

    public void initData(Airport airport) {
        this.airportToEdit = airport;
        codeInput.setText(airport.getCode());
        nameInput.setText(airport.getName());
        cityInput.setText(airport.getCity());
        countryInput.setText(airport.getCountry());
        codeInput.setDisable(true);
    }
    
    @FXML
    private void switchToAirports() throws IOException {
        App.setRoot("airport");
    }
    
    @FXML
    private void selectImage() {
        System.out.println("Seleccionando imagen...");
    }
    
    
    @FXML
    private void updateAirport() throws IOException {
        String code = codeInput.getText().trim();
        String name = nameInput.getText().trim();
        String city = cityInput.getText().trim();
        String country = countryInput.getText().trim();
        
        if (code.isEmpty() || name.isEmpty() || city.isEmpty() || country.isEmpty()) {
            UtilController.showAlert(AlertType.ERROR, "Error de Validación", "Todos los campos son obligatorios.");
            return;
        }

        airportToEdit.setName(name);
        airportToEdit.setCity(city);
        airportToEdit.setCountry(country);

        GraphAL<Airport, Flight> currentGraph = GraphContext.getCurrentGraph();
        GraphContext.updateGraph(currentGraph);
        UtilController.showAlert(AlertType.INFORMATION, "Éxito", "Aeropuerto '" + name + "' actualizado correctamente.");
        App.setUnsavedChanges(true);
        switchToAirports();
    }
    
}
