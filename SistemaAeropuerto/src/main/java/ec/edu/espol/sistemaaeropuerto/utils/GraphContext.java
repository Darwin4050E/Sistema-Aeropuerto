/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.sistemaaeropuerto.utils;
import ec.edu.espol.sistemaaeropuerto.models.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author troni
 */
public class GraphContext {
    private static GraphAL<Airport, Flight> currentGraph;
    private static final List<GraphSubscriber> subscribers = new ArrayList<>();
    
    public static GraphAL<Airport, Flight> getCurrentGraph() {
        return currentGraph;
    }
    
    public static void updateGraph(GraphAL<Airport, Flight> graph) {
        currentGraph = graph;
        notifySubscribers();
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
