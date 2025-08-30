/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.flightcontrol.utils;
import ec.edu.espol.flightcontrol.models.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author troni
 */
public class GraphContext {
    private static GraphAL<Airport, Flight> currentGraph;
    
    // estructuras adicionales que se derivan a partir del grafo
    private static BSTree<Airport, String> airportSearchTree;
    private static Set<String> allFlightCodes = new HashSet<>();
    
    private static final List<GraphSubscriber> subscribers = new ArrayList<>();
    
    public static GraphAL<Airport, Flight> getCurrentGraph() {
        return currentGraph;
    }
    
    public static BSTree<Airport, String> getAirportSearchTree() {
        return airportSearchTree;
    }
    
    public static Set<String> getAllFlightCodes() {
        return allFlightCodes;
    }
    
    public static void buildAirportSearchTree() {
        if (currentGraph == null) return;

        airportSearchTree = new BSTree<>((String c1, String c2) -> c1.compareTo(c2));

        for (Vertex<Airport, Flight> vertex : currentGraph.getVertexs()) {
            Airport airport = vertex.getContent();
            airportSearchTree.insertToAVL(airport, airport.getCode());
        }
    }
    
    public static void buildFlightCodeSet() {
        if (currentGraph == null) return;

        allFlightCodes.clear(); 
        for (Vertex<Airport, Flight> vertex : currentGraph.getVertexs()) {
            for (Edge<Airport, Flight> edge : vertex.getEdges()) {
                Flight flight = edge.getData();
                allFlightCodes.add(flight.getFlightNumber());
            }
        }
    }
    
    public static void updateGraph(GraphAL<Airport, Flight> graph) {
        currentGraph = graph;
        buildAirportSearchTree();
        notifySubscribers();
        buildFlightCodeSet();
    }
    
    public static void addListener(GraphSubscriber subscriber) {
        if (!subscribers.contains(subscriber)) {
            subscribers.add(subscriber);
        }
    }
    
    private static void notifySubscribers() {
        for (GraphSubscriber subscriber : subscribers) {
            subscriber.update();
        }
    }
    
    
}
