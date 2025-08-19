/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.sistemaaeropuerto.utils;

import ec.edu.espol.sistemaaeropuerto.models.*;
//import org.graphstream.graph.GraphAL;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.time.LocalTime;
import java.io.Serializable;

/**
 *
 * @author Nueva
 */
public class PersistenceController {
    
    public static void graphSerializer(GraphAL graph){
        
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("airportsystem.ser"))) {
            out.writeObject(graph);
            System.out.println("It was successfully serialized in airportsystem.ser");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static GraphAL graphDeserializer(){
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("airportsystem.ser"))) {
            return (GraphAL) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static GraphAL<Airport, Flight> getInitialGraph() {

        GraphAL<Airport, Flight> graph = new GraphAL<>(new AirportComparator(), true);

        // Aeropuertos
        Airport pkx = new Airport("PKX", "Beijing Daxing International Airport", "Beijing", "China", 39.5098, 116.4100);
        Airport jfk = new Airport("JFK", "John F. Kennedy International Airport", "New York", "USA", 40.6413, -73.7781);
        Airport lax = new Airport("LAX", "Los Angeles International Airport", "Los Angeles", "USA", 33.9416, -118.4085);
        Airport ord = new Airport("ORD", "O'Hare International Airport", "Chicago", "USA", 41.9742, -87.9073);
        Airport cdg = new Airport("CDG", "Charles de Gaulle Airport", "Paris", "France", 49.0097, 2.5479);
        Airport lhr = new Airport("LHR", "Heathrow Airport", "London", "UK", 51.4700, -0.4543);
        Airport hnd = new Airport("HND", "Haneda Airport", "Tokyo", "Japan", 35.5494, 139.7798);
        Airport syd = new Airport("SYD", "Sydney Kingsford Smith Airport", "Sydney", "Australia", -33.9399, 151.1753);
        Airport gru = new Airport("GRU", "São Paulo–Guarulhos International Airport", "São Paulo", "Brazil", -23.4356, -46.4731);
        Airport dxB = new Airport("DXB", "Dubai International Airport", "Dubai", "UAE", 25.2532, 55.3657);

        // Añadir vértices al grafo
        graph.addVertex(pkx);
        graph.addVertex(jfk);
        graph.addVertex(lax);
        graph.addVertex(ord);
        graph.addVertex(cdg);
        graph.addVertex(lhr);
        graph.addVertex(hnd);
        graph.addVertex(syd);
        graph.addVertex(gru);
        graph.addVertex(dxB);

        // Vuelos (edges) de ejemplo
        Flight f1 = new Flight("CA101", "Air China", LocalTime.of(8, 0), LocalTime.of(12, 0));
        Flight f2 = new Flight("UA200", "United Airlines", LocalTime.of(14, 0), LocalTime.of(16, 0));
        Flight f3 = new Flight("AF300", "Air France", LocalTime.of(10, 0), LocalTime.of(18, 0));
        Flight f4 = new Flight("BA400", "British Airways", LocalTime.of(9, 0), LocalTime.of(17, 0));
        Flight f5 = new Flight("JL500", "Japan Airlines", LocalTime.of(7, 0), LocalTime.of(13, 0));
        Flight f6 = new Flight("QF600", "Qantas", LocalTime.of(12, 0), LocalTime.of(22, 0));
        Flight f7 = new Flight("EK700", "Emirates", LocalTime.of(15, 0), LocalTime.of(20, 0));
        Flight f8 = new Flight("LA800", "LATAM", LocalTime.of(11, 0), LocalTime.of(21, 0));
        Flight f9 = new Flight("AA900", "American Airlines", LocalTime.of(16, 0), LocalTime.of(22, 0));
        Flight f10 = new Flight("DL1000", "Delta Airlines", LocalTime.of(6, 0), LocalTime.of(10, 0));

        // Conexiones principales desde PKX (nodo central)
        graph.addEdge(f1, pkx, jfk, 0);
        graph.addEdge(f2, pkx, lax, 0);
        graph.addEdge(f3, pkx, cdg, 0);
        graph.addEdge(f4, pkx, lhr, 0);
        graph.addEdge(f5, pkx, hnd, 0);
        graph.addEdge(f6, pkx, syd, 0);
        graph.addEdge(f7, pkx, dxB, 0);
        graph.addEdge(f8, pkx, gru, 0);

        // Conexiones adicionales para dar más realismo
        graph.addEdge(f9, jfk, ord, 0);
        graph.addEdge(f10, lax, syd, 0);

        return graph;
    }

}
