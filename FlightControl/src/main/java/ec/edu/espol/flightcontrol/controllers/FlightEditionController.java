/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.flightcontrol.controllers;

import ec.edu.espol.flightcontrol.App;
import ec.edu.espol.flightcontrol.models.*;
import ec.edu.espol.flightcontrol.utils.GraphContext;
import ec.edu.espol.flightcontrol.utils.AirlinesData;
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
public class FlightEditionController {
    
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
    
    private Edge<Airport, Flight> edgeToEdit;
    
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

    public void initData(Edge<Airport, Flight> edge) {
        this.edgeToEdit = edge;
        Flight flightData = edge.getData();
        Airport sourceAirport = edge.getSourceVertex().getContent();
        Airport targetAirport = edge.getTargetVertex().getContent();
        
        codeInput.setText(flightData.getFlightNumber());
        distanceInput.setText(String.valueOf(flightData.getDistance()));
        airlineCombo.setValue(flightData.getAirline());
        originCombo.setValue(sourceAirport.toString()); 
        targetCombo.setValue(targetAirport.toString());
        
        LocalDateTime departure = flightData.getDepartureTime();
        LocalDateTime arrival = flightData.getArrivalTime();

        departureDate.setValue(departure.toLocalDate());
        arrivalDate.setValue(arrival.toLocalDate());

        departureHour.getValueFactory().setValue(departure.getHour());
        departureMinute.getValueFactory().setValue(departure.getMinute());
        arrivalHour.getValueFactory().setValue(arrival.getHour());
        arrivalMinute.getValueFactory().setValue(arrival.getMinute());
        
        // Deshabilitar campos esenciales
        codeInput.setDisable(true);
        airlineCombo.setDisable(true);
        originCombo.setDisable(true);
        targetCombo.setDisable(true);
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
    private void updateFlight() throws IOException {
        Flight flightToUpdate = edgeToEdit.getData();

        int distance = parseDistance(distanceInput.getText().trim());
        if (distance <= 0) return;

        LocalDateTime departureDateTime = buildDateTime(departureDate, departureHour, departureMinute);
        LocalDateTime arrivalDateTime = buildDateTime(arrivalDate, arrivalHour, arrivalMinute);

        if (!validateDates(departureDateTime, arrivalDateTime)) return;
                
        flightToUpdate.setDistance(distance);
        edgeToEdit.setWeight(flightToUpdate.getDistance());
        flightToUpdate.setDepartureTime(departureDateTime);
        flightToUpdate.setArrivalTime(arrivalDateTime);

        UtilController.showAlert(AlertType.INFORMATION, "Éxito", "Vuelo '" + flightToUpdate.getFlightNumber() + "' actualizado correctamente.");
        GraphContext.updateGraph(GraphContext.getCurrentGraph());

        App.setUnsavedChanges(true);
        switchToFlights();
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
        if (!arrival.isAfter(departure)) {
            UtilController.showAlert(AlertType.ERROR, "Error de Fechas", "La fecha y hora de llegada debe ser posterior a la de salida.");
            return false;
        }
        return true;
    }
}
