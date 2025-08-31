/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.flightcontrol.controllers;

import ec.edu.espol.flightcontrol.App;
import ec.edu.espol.flightcontrol.models.Airport;
import ec.edu.espol.flightcontrol.models.Edge;
import ec.edu.espol.flightcontrol.models.Flight;
import ec.edu.espol.flightcontrol.utils.GraphContext;
import ec.edu.espol.flightcontrol.utils.PersistenceController;
import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 *
 * @author Grupo 1 - P1
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
    
    public static boolean showDeleteConfirmation(String itemName) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Eliminación");
        alert.setHeaderText("Estás a punto de eliminar: " + itemName);
        alert.setContentText("Esta acción no se puede deshacer. ¿Estás seguro?");

        Optional<ButtonType> result = alert.showAndWait();
        return (result.isPresent()) && (result.get() == ButtonType.OK);
    }
    
    public static void showAirportFlightInfo(Airport airport, List<Edge<Airport, Flight>> flights) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Vuelos de " + airport.getName());
        alert.setHeaderText("Información para el aeropuerto " + airport.getCode());

        alert.setGraphic(createAirportImageView(airport));

        StringBuilder flightInfo = new StringBuilder();
        if (flights == null || flights.isEmpty()) {
            flightInfo.append("No hay vuelos de salida registrados para este aeropuerto.");
        } else {
            flightInfo.append("Vuelos de salida:\n");
            for (Edge<Airport, Flight> edge : flights) {
                Flight flight = edge.getData();
                Airport destination = edge.getTargetVertex().getContent();
                flightInfo.append("  - Vuelo ")
                          .append(flight.getFlightNumber())
                          .append(" hacia ")
                          .append(destination.getCode())
                          .append("\n");
            }
        }

        alert.setContentText(flightInfo.toString());
        alert.showAndWait();
    }
    
    private static ImageView createAirportImageView(Airport airport) {
        String imagePath = airport.getImagePath();
        Image img = null;

        try {
            File file = new File(imagePath);
            if (file.exists() && file.isAbsolute()) {
                img = new Image("file:" + imagePath);
            } else {
                InputStream resourceStream = UtilController.class.getResourceAsStream(imagePath);
                if (resourceStream != null) {
                    img = new Image(resourceStream);
                }
            }
        } catch (Exception e) {
            System.err.println("No se pudo cargar la imagen: " + imagePath);
        }

        if (img == null) {
            img = new Image(UtilController.class.getResourceAsStream("/images/airports/placeholder.jpg"));
        }

        ImageView imageView = new ImageView(img);
        imageView.setFitHeight(150);
        imageView.setPreserveRatio(true);

        return imageView;
    }
}
