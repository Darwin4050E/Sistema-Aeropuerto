/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.flightcontrol.models;
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
    private int distance;
    private LocalTime departureTime;
    private LocalTime arrivalTime;

    // Métodos:

    public Flight(String flightNumber, String airline, int distance, LocalTime departureTime, LocalTime arrivalTime) {
        this.flightNumber = flightNumber;
        this.airline = airline;
        this.distance = distance;
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

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
    
    @Override
    public String toString() {
        return airline + " " + flightNumber; // "Latam LA1433"
    }
}
