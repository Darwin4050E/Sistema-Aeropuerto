/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.flightcontrol.models;
import java.time.LocalTime;
import java.io.Serializable;
import java.time.LocalDateTime;
/**
 *
 * @author Grupo 1 - P1
 */

public class Flight implements Serializable {
    
    // Atributos:
    
    private String flightNumber;
    private String airline;
    private int distance;
    private LocalDateTime departure;
    private LocalDateTime arrival;

    // Métodos:

    public Flight(String flightNumber, String airline, int distance, LocalDateTime departureTime, LocalDateTime arrivalTime) {
        this.flightNumber = flightNumber;
        this.airline = airline;
        this.distance = distance;
        this.departure = departureTime;
        this.arrival = arrivalTime;
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

    public LocalDateTime getDepartureTime() {
        return departure;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departure = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrival;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrival = arrivalTime;
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
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        
        Flight other = (Flight) obj;
        
        return this.flightNumber.equals(other.flightNumber);
    }
}
