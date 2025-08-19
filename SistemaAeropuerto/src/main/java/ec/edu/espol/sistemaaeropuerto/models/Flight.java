/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.sistemaaeropuerto.models;
import java.time.LocalTime;
import java.io.Serializable;
/**
 *
 * @author Grupo 1 - P1
 */

public class Flight implements Serializable {
    
    // Atributos:
    
    private String flightNumber;
    private String airline;
    private LocalTime departureTime;
    private LocalTime arrivalTime;

    // Métodos:

    public Flight(String flightNumber, String airline, LocalTime departureTime, LocalTime arrivalTime) {
        this.flightNumber = flightNumber;
        this.airline = airline;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
    
    @Override 
    public String toString() {
        return flightNumber;
    }
}
