/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.flightcontrol.utils;

import ec.edu.espol.flightcontrol.models.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.time.LocalTime;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

/**
 *
 * @author Nueva
 */
public class PersistenceController {
    
    private static final String APP_FOLDER_NAME = ".flightcontrol";
    private static final String SAVE_FILE_NAME = "airportsystem.ser";
    
    private static Path getSaveFilePath() {
        try {
            // obtiene la carpeta home del usuario
            String userHome = System.getProperty("user.home");
            
            // ruta completa hasta la carpeta
            Path dataFolderPath = Paths.get(userHome, APP_FOLDER_NAME);
            
            // Crea el directorio si no existe
            if (Files.notExists(dataFolderPath)) {
                Files.createDirectories(dataFolderPath);
            }
            
            // retorn el path completo hasta el serializado
            return dataFolderPath.resolve(SAVE_FILE_NAME);
        } catch (IOException e) {
            System.out.println("Error creando el directorio: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    public static void graphSerializer(GraphAL graph){
        Path saveFile = getSaveFilePath();
        if (saveFile == null) {
            System.out.println("Error al intentar obtener el path");
            return;
        }
        
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(saveFile.toFile()))) {
            out.writeObject(graph);
            System.out.println("Grafo correctamente serializado en: " + saveFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static GraphAL graphDeserializer(){
        Path saveFile = getSaveFilePath();
        if (saveFile == null || Files.notExists(saveFile)) {
            System.out.println("No se encontró el grafo. Se creará un grafo inicial");
            return null; 
        }
        
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(saveFile.toFile()))) {
            System.out.println("Cargando grafo desde: " + saveFile);
            return (GraphAL) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error deserializando el grafo");
            e.printStackTrace();
            return null;
        }
    }
    
    public static GraphAL<Airport, Flight> getInitialGraph() {

        GraphAL<Airport, Flight> graph = new GraphAL<>(new AirportComparator(), true);

        // Aeropuertos
        Airport pkx = new Airport("PKX", "Beijing Daxing International Airport", "Beijing", "China");
        Airport jfk = new Airport("JFK", "John F. Kennedy International Airport", "New York", "USA");
        Airport lax = new Airport("LAX", "Los Angeles International Airport", "Los Angeles", "USA");
        Airport ord = new Airport("ORD", "O'Hare International Airport", "Chicago", "USA");
        Airport cdg = new Airport("CDG", "Charles de Gaulle Airport", "Paris", "France");
        Airport lhr = new Airport("LHR", "Heathrow Airport", "London", "UK");
        Airport hnd = new Airport("HND", "Haneda Airport", "Tokyo", "Japan");
        Airport syd = new Airport("SYD", "Sydney Kingsford Smith Airport", "Sydney", "Australia");
        Airport gru = new Airport("GRU", "São Paulo–Guarulhos International Airport", "São Paulo", "Brazil");
        Airport dxb = new Airport("DXB", "Dubai International Airport", "Dubai", "UAE");

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
        graph.addVertex(dxb);

        Flight f1 = new Flight("CA101", "Air China", 50, LocalDateTime.of(2025, 9, 10, 8, 0), LocalDateTime.of(2025, 9, 10, 12, 0));
        Flight f2 = new Flight("UA200", "United Airlines", 40, LocalDateTime.of(2025, 9, 15, 14, 0), LocalDateTime.of(2025, 9, 15, 16, 0));
        Flight f3 = new Flight("AF300", "Air France", 20, LocalDateTime.of(2025, 10, 5, 10, 0), LocalDateTime.of(2025, 10, 5, 18, 0));
        Flight f4 = new Flight("BA400", "British Airways", 60, LocalDateTime.of(2025, 10, 8, 9, 0), LocalDateTime.of(2025, 10, 8, 17, 0));
        Flight f5 = new Flight("JL500", "Japan Airlines", 70, LocalDateTime.of(2025, 11, 20, 7, 0), LocalDateTime.of(2025, 11, 20, 13, 0));
        Flight f6 = new Flight("QF600", "Qantas", 40, LocalDateTime.of(2025, 11, 21, 12, 0), LocalDateTime.of(2025, 11, 21, 22, 0));
        Flight f7 = new Flight("EK700", "Emirates", 50, LocalDateTime.of(2025, 12, 1, 15, 0), LocalDateTime.of(2025, 12, 1, 20, 0));
        Flight f8 = new Flight("LA800", "LATAM Airlines", 100, LocalDateTime.of(2025, 12, 3, 11, 0), LocalDateTime.of(2025, 12, 3, 21, 0));
        Flight f9 = new Flight("AA900", "American Airlines", 60, LocalDateTime.of(2025, 9, 10, 16, 0), LocalDateTime.of(2025, 9, 10, 22, 0));

        Flight f10 = new Flight("DL1000", "Delta Airlines", 45, LocalDateTime.of(2025, 11, 22, 20, 0), LocalDateTime.of(2025, 11, 23, 11, 30));


        // Conexiones principales desde PKX (nodo central)
        graph.addEdge(f1, pkx, jfk, f1.getDistance());
        graph.addEdge(f2, pkx, lax, f2.getDistance());
        graph.addEdge(f3, pkx, cdg, f3.getDistance());
        graph.addEdge(f4, pkx, lhr, f4.getDistance());
        graph.addEdge(f5, pkx, hnd, f5.getDistance());
        graph.addEdge(f6, pkx, syd, f6.getDistance());
        graph.addEdge(f7, pkx, dxb, f7.getDistance());
        graph.addEdge(f8, pkx, gru, f8.getDistance());

        // Conexiones adicionales para dar más realismo
        graph.addEdge(f9, jfk, ord, f9.getDistance());
        graph.addEdge(f10, lax, syd, f10.getDistance());

        return graph;
    }

}
