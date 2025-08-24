/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.flightcontrol.controllers;

import ec.edu.espol.flightcontrol.App;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

/**
 *
 * @author gabriel
 */
public class AirportCreationController {
    @FXML
    ImageView airportImage;
    
    @FXML
    private void selectImage() {
        System.out.println("Seleccionando imagen...");
    }
    
    @FXML
    private void switchToAirports() throws IOException {
        App.setRoot("airport");
    }
}
