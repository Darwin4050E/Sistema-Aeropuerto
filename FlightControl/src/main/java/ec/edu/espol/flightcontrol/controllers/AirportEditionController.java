/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.flightcontrol.controllers;

import ec.edu.espol.flightcontrol.App;
import ec.edu.espol.flightcontrol.models.*;
import ec.edu.espol.flightcontrol.utils.GraphContext;
import java.io.File;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
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
    
    @FXML
    Button pickImageBtn;
    
    private Airport airportToEdit;

    public void initData(Airport airport) {
        this.airportToEdit = airport;
        loadAirportInfo(airport);
        loadAndConfigureImage(airport);
    }
    
    private void loadAirportInfo(Airport airport) {
        codeInput.setText(airport.getCode());
        nameInput.setText(airport.getName());
        cityInput.setText(airport.getCity());
        countryInput.setText(airport.getCountry());
        codeInput.setDisable(true);
        pickImageBtn.setDisable(true);
    }
    
    private void loadAndConfigureImage(Airport airport) {
        String imagePath = airport.getImagePath();
        Image img = null;

        try {
            File file = new File(imagePath);
            if (file.exists() && file.isAbsolute()) {
                img = new Image("file:" + imagePath);
            } 
            else {
                img = new Image(getClass().getResourceAsStream(imagePath));
            }
        } catch (Exception e) {
            System.out.println("No se pudo cargar la imagen: " + imagePath);
            e.printStackTrace();
        }

        if (img != null) {
            airportImage.setImage(img);
        } else {
            airportImage.setImage(new Image(getClass().getResourceAsStream("/images/placeholder.jpg")));
        }

        airportImage.setFitWidth(200);
        airportImage.setPreserveRatio(true);
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
