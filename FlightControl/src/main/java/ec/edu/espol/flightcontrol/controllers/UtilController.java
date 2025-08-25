/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.flightcontrol.controllers;

import ec.edu.espol.flightcontrol.App;
import ec.edu.espol.flightcontrol.utils.GraphContext;
import ec.edu.espol.flightcontrol.utils.PersistenceController;
import java.util.Optional;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

/**
 *
 * @author gabriel
 */
public class UtilController {
    public static void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public static void setupCloseRequestHandler(Stage stage) {
        stage.setOnCloseRequest(event -> {
            if (App.hasUnsavedChanges()) {
                event.consume();
                showConfirmationDialog(stage);
            }
        });
    }

    private static void showConfirmationDialog(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Salida");
        alert.setHeaderText("Tienes cambios sin guardar.");
        alert.setContentText("¿Qué deseas hacer antes de salir?");

        ButtonType saveButton = new ButtonType("Guardar y Salir");
        ButtonType exitButton = new ButtonType("Salir sin Guardar");
        ButtonType cancelButton = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(saveButton, exitButton, cancelButton);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent()) {
            if (result.get() == saveButton) {
                PersistenceController.graphSerializer(GraphContext.getCurrentGraph());
                Platform.exit(); 
            } else if (result.get() == exitButton) {
                Platform.exit(); 
            }
        }
    }
}
