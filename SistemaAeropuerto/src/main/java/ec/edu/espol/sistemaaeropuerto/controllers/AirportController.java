/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.sistemaaeropuerto.controllers;

import ec.edu.espol.sistemaaeropuerto.App;
import java.io.IOException;
import javafx.fxml.FXML;

/**
 *
 * @author grupo1
 */
public class AirportController {
    @FXML
    private void switchToMain() throws IOException {
        App.setRoot("mainView");
    }
    
    public void initialize() {
        
    }
}
