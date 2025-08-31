/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.flightcontrol.controllers;

import ec.edu.espol.flightcontrol.App;
import ec.edu.espol.flightcontrol.models.*;
import ec.edu.espol.flightcontrol.utils.GraphContext;
import ec.edu.espol.flightcontrol.utils.PersistenceController;
import java.io.File;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author Grupo 1 - P1
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
    Button saveAirportBtn;
    
    @FXML
    StackPane imagePreviewContainer;
    
    private String selectedImagePath = "";
    
    @FXML
    public void initialize() {
        imagePreviewContainer.setVisible(false);
        imagePreviewContainer.setManaged(false); 
    }

    @FXML
    private void selectImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Imagen");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        Stage currentStage = (Stage) saveAirportBtn.getScene().getWindow();
        File file = fileChooser.showOpenDialog(currentStage);
        
        if (file != null) {
            selectedImagePath = file.getAbsolutePath();

            Image img = new Image(file.toURI().toString());
            airportImage.setImage(img);
            airportImage.setFitHeight(110);
            airportImage.setPreserveRatio(true);
            imagePreviewContainer.setVisible(true);
            imagePreviewContainer.setManaged(true);
        }
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
        
        if (selectedImagePath.isEmpty()) {
            UtilController.showAlert(AlertType.ERROR, "Error de Validación", "Por favor, selecciona una imagen para el aeropuerto.");
            return; 
        }
        
        if (code.isEmpty() || name.isEmpty() || city.isEmpty() || country.isEmpty()) {
            UtilController.showAlert(AlertType.ERROR, "Error de Validación", "Todos los campos son obligatorios.");
            return;
        }
        
        String finalImagePath = PersistenceController.copyImageToAppFolder(selectedImagePath, code);

        if (finalImagePath == null) {
            UtilController.showAlert(AlertType.ERROR, "Error de Archivo", "No se pudo copiar la imagen al directorio de la aplicación.");
            return;
        }
        
        saveAirportBtn.setDisable(true);
        Airport newAirport = new Airport(code, name, city, country, finalImagePath);
        GraphAL<Airport, Flight> currentGraph = GraphContext.getCurrentGraph();
        if (currentGraph.addVertex(newAirport)) {
            UtilController.showAlert(AlertType.INFORMATION, "Éxito", "Aeropuerto '" + name + "' guardado correctamente.");
            GraphContext.updateGraph(currentGraph);
            App.setUnsavedChanges(true);
            switchToAirports();
        } else {
            saveAirportBtn.setDisable(false);
            UtilController.showAlert(AlertType.ERROR, "Error al Guardar", "Ya existe un aeropuerto con ese código.");
        }
    }
}
