/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.sistemaaeropuerto;

import java.time.LocalTime;

/**
 *
 * @author Grupo 1 - P1
 */

public class SistemaAeropuerto {

    public static void main(String[] args) {
        
        Graph<Airport, Flight> graph = new Graph<>(null, true);

        Airport airport1 = new Airport("JFK", "John F. Kennedy International Airport", "New York", "USA", 40.6413, -73.7781);
        Airport airport2 = new Airport("LAX", "Los Angeles International Airport", "Los Angeles", "USA", 33.9416, -118.4085);
        Airport airport3 = new Airport("ORD", "O'Hare International Airport", "Chicago", "USA", 41.9742, -87.9073);

        graph.addVertex(airport1);
        graph.addVertex(airport2);
        graph.addVertex(airport3);

        Flight flight1 = new Flight("AA100", "American Airlines", LocalTime.of(10, 0), LocalTime.of(13, 0));
        Flight flight2 = new Flight("UA200", "United Airlines", LocalTime.of(14, 0), LocalTime.of(16, 0));
        Flight flight3 = new Flight("DL300", "Delta Airlines", LocalTime.of(12, 0), LocalTime.of(15, 0));

        graph.addEdge(flight1, airport1, airport2, 0);
        graph.addEdge(flight2, airport2, airport3, 0);
        graph.addEdge(flight3, airport1, airport3, 0);
        
    }
    
}
