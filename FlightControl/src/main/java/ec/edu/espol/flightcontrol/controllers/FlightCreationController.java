/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.flightcontrol.controllers;

import ec.edu.espol.flightcontrol.App;
import ec.edu.espol.flightcontrol.models.*;
import ec.edu.espol.flightcontrol.utils.AirlinesData;
import ec.edu.espol.flightcontrol.utils.GraphContext;
import ec.edu.espol.flightcontrol.utils.Util;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;

/**
 *
 * @author Grupo 1 - P1
 */
public class FlightCreationController {
    
    @FXML
    TextField codeInput;
    
    @FXML
    ComboBox<String> airlineCombo;
    
    @FXML
    ComboBox<String> originCombo;
    
    @FXML
    ComboBox<String> targetCombo;
    
    @FXML
    TextField distanceInput;
    
    @FXML
    DatePicker departureDate;
    
    @FXML
    DatePicker arrivalDate;
    
    @FXML
    Spinner departureHour;
    
    @FXML
    Spinner departureMinute;
    
    @FXML
    Spinner arrivalHour;
    
    @FXML
    Spinner arrivalMinute;
    
    @FXML
    private void switchToFlights() throws IOException {
        App.setRoot("flights");
    }
    
    @FXML
    public void initialize() {
        setupComboBoxes();
        setupDatePickers();
        setupSpinners();
    }
    
    private void setupComboBoxes() {
        airlineCombo.getItems().setAll(AirlinesData.getAvailableAirlines());

        GraphAL<Airport, Flight> graph = GraphContext.getCurrentGraph();
        List<String> airportCodes = new ArrayList<>();

        for (Vertex<Airport, Flight> vertex : graph.getVertexs()) {
            airportCodes.add(vertex.getContent().toString());
        }

        originCombo.getItems().setAll(airportCodes);
        targetCombo.getItems().setAll(airportCodes);
    }
    
    private void setupSpinners() {
        SpinnerValueFactory<Integer> hourFactory = 
            new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 12);

        SpinnerValueFactory<Integer> hourFactory2 = 
            new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 14);

        departureHour.setValueFactory(hourFactory);
        arrivalHour.setValueFactory(hourFactory2);

        SpinnerValueFactory<Integer> minuteFactory = 
            new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0);

        SpinnerValueFactory<Integer> minuteFactory2 = 
            new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 30);

        departureMinute.setValueFactory(minuteFactory);
        arrivalMinute.setValueFactory(minuteFactory2);
    }
    
    private void setupDatePickers() {
        departureDate.setValue(LocalDate.now());
        arrivalDate.setValue(LocalDate.now());
    }
    
    @FXML
    private void saveFlight() throws IOException {
        if (!validateRequiredFields()) return;
        
        String flightCode = codeInput.getText().trim();
        String airline = airlineCombo.getValue();
        
        if (GraphContext.getAllFlightCodes().contains(flightCode)) {
            UtilController.showAlert(AlertType.ERROR, "Error de Validación", "El código de vuelo '" + flightCode + "' ya existe.");
            return;
        }

        String originCode = Util.extractAirportCode(originCombo.getValue());
        String targetCode = Util.extractAirportCode(targetCombo.getValue());

        if (!validateDifferentAirports(originCode, targetCode)) return;

        int distance = parseDistance(distanceInput.getText().trim());
        if (distance <= 0) return;

        LocalDateTime departureDateTime = buildDateTime(departureDate, departureHour, departureMinute);
        LocalDateTime arrivalDateTime = buildDateTime(arrivalDate, arrivalHour, arrivalMinute);

        if (!validateDates(departureDateTime, arrivalDateTime)) return;
        
        Airport origin = Util.findAirportByCode(originCode);
        Airport target = Util.findAirportByCode(targetCode);
        Flight flight = new Flight(flightCode, airline, distance, departureDateTime, arrivalDateTime);
        
        GraphAL<Airport, Flight> currentGraph = GraphContext.getCurrentGraph();
        if (currentGraph.addEdge(flight, origin, target, flight.getDistance())) {
            UtilController.showAlert(AlertType.INFORMATION, "Éxito", "Vuelo '" + flightCode + "' guardado correctamente.");
            GraphContext.updateGraph(currentGraph);
            App.setUnsavedChanges(true);
            switchToFlights();
        } else {
            UtilController.showAlert(AlertType.ERROR, "Error al Guardar", "No se pudo guardar el vuelo");
        }
    }
    
    private boolean validateRequiredFields() {
        String flightCode = codeInput.getText().trim();
        String airline = airlineCombo.getValue();
        String origin = originCombo.getValue();
        String target = targetCombo.getValue();
        String distanceStr = distanceInput.getText().trim();

        if (flightCode.isEmpty() || airline == null || origin == null || target == null ||
            distanceStr.isEmpty() || departureDate.getValue() == null || arrivalDate.getValue() == null) {

            UtilController.showAlert(AlertType.ERROR, "Error de Validación", "Todos los campos son obligatorios.");
            return false;
        }
        return true;
    }

    private boolean validateDifferentAirports(String originCode, String targetCode) {
        if (originCode.equals(targetCode)) {
            UtilController.showAlert(AlertType.ERROR, "Error de Validación", "El aeropuerto de origen y destino no pueden ser el mismo.");
            return false;
        }
        return true;
    }

    private int parseDistance(String distanceStr) {
        try {
            int distance = Integer.parseInt(distanceStr);
            if (distance <= 0) {
                UtilController.showAlert(AlertType.ERROR, "Error de Validación", "La distancia debe ser un número positivo.");
                return -1;
            }
            return distance;
        } catch (NumberFormatException e) {
            UtilController.showAlert(AlertType.ERROR, "Error de Validación", "El valor de la distancia no es un número válido.");
            return -1;
        }
    }

    private LocalDateTime buildDateTime(DatePicker datePicker, Spinner hourCombo, Spinner minuteCombo) {
        return datePicker.getValue().atTime((Integer) hourCombo.getValue(), (Integer) minuteCombo.getValue());
    }

    private boolean validateDates(LocalDateTime departure, LocalDateTime arrival) {
        if (!departure.isAfter(LocalDateTime.now())) {
            UtilController.showAlert(AlertType.ERROR, "Error de Fechas", "La fecha y hora de salida debe ser posterior a la fecha y hora actual.");
            return false;
        }
        
        if (!arrival.isAfter(departure)) {
            UtilController.showAlert(AlertType.ERROR, "Error de Fechas", "La fecha y hora de llegada debe ser posterior a la de salida.");
            return false;
        }
        return true;
    }
    
}
