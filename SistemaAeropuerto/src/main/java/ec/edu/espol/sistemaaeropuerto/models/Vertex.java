/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.sistemaaeropuerto.models;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Grupo 1 - P1
 * @param <V>
 * @param <E>
 */

public class Vertex<V,E> implements Comparable<Vertex<V,E>>, Serializable {
    
    // Atributos:

    private V content;
    private List<Edge<V,E>> edges;
    
    // Atributos exclusivos para ejecutar Dijkstra:

    private boolean isVisited;
    private int cumulativeDistance;
    private Vertex<V,E> predecessorVertex;

    // Métodos:

    public Vertex(V content) {
        this.content = content;
        this.edges = new LinkedList<>();
    }

    public V getContent() {
        return content;
    }

    public void setContent(V content) {
        this.content = content;
    }

    public List<Edge<V,E>> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge<V,E>> edges) {
        this.edges = edges;
    }
    
    public boolean getIsVisited() {
        return isVisited;
    }

    public void setIsVisited(boolean isVisited) {
        this.isVisited = isVisited;
    }

    public int getCumulativeDistance() {
        return cumulativeDistance;
    }

    public void setCumulativeDistance(int cumulativeDistance) {
        this.cumulativeDistance = cumulativeDistance;
    }

    public Vertex<V, E> getPredecessorVertex() {
        return predecessorVertex;
    }

    public void setPredecessorVertex(Vertex<V, E> predecessorVertex) {
        this.predecessorVertex = predecessorVertex;
    }

    @Override
    public int compareTo(Vertex<V, E> o) {
        return o.getCumulativeDistance() - this.cumulativeDistance;
    }
    
}
