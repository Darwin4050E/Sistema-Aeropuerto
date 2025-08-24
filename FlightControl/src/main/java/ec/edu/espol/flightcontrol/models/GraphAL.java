/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.flightcontrol.models;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;
import java.io.Serializable;

/**
 *
 * @author Grupo 1 - P1
 * @param <V>
 * @param <E>
 */

public class GraphAL<V,E> implements Serializable {
    
    // Atributos:

    private List<Vertex<V,E>> vertexs;
    private Comparator<V> cmp;
    private boolean isDirected;

    // Métodos:

    public GraphAL(Comparator<V> cmp, boolean isDirected) {
        this.cmp = cmp;
        this.isDirected = isDirected;
        this.vertexs = new LinkedList<>();
    }
    
    public List<Vertex<V, E>> getVertexs() {
        return vertexs;
    }

    public void setVertexs(List<Vertex<V, E>> vertexs) {
        this.vertexs = vertexs;
    }
    
    public Comparator<V> getCmp() {
        return this.cmp;
    }
    
    public void setCmp(Comparator<V> cmp) {
        this.cmp = cmp;
    }

    public boolean getIsDirected() {
        return isDirected;
    }

    public void setIsDirected(boolean isDirected) {
        this.isDirected = isDirected;
    }

    private Vertex<V,E> findVertex(V content){
        if(content == null){
            return null;
        }
        for(Vertex<V,E> vertex : vertexs){
            if(cmp.compare(vertex.getContent(), content) == 0){
                return vertex;
            }
        }
        return null;
    }

    private Edge<V,E> findEdge(V sourceContent, V targetContent){
        if(sourceContent == null || targetContent == null){
            return null;
        }
        Vertex<V,E> sourceVertex = this.findVertex(sourceContent);
        Vertex<V,E> targetVertex = this.findVertex(targetContent);
        if(sourceVertex != null && targetVertex != null){
            for(Edge<V,E> edge : sourceVertex.getEdges()){
                if(edge.getTargetVertex().equals(targetVertex)){
                    return edge;
                }
            }
        }
        return null;
    }
    
    private void resetVertexs(){
        for(Vertex<V,E> vertex : vertexs){
            vertex.setIsVisited(false);
            vertex.setCumulativeDistance(Integer.MAX_VALUE);
            vertex.setPredecessorVertex(null);
        }
    }

    public boolean addVertex(V content){
        if(content == null || this.findVertex(content) != null){
            return false;
        }
        this.vertexs.add(new Vertex<>(content));
        return true;
    }

    public boolean removeVertex(V content){
        if(content == null){
            return false;
        }
        Vertex<V,E> vertexToRemove = this.findVertex(content);
        if(vertexToRemove != null){
            for(Vertex<V,E> vertex : vertexs){
                for(Edge<V,E> edge : vertex.getEdges()){
                    if(cmp.compare(edge.getTargetVertex().getContent(), vertexToRemove.getContent()) == 0){
                        vertex.getEdges().remove(edge);
                    }
                }
            }
            vertexs.remove(vertexToRemove);
            return true;
        }
        return false;
    }

    public boolean addEdge(E data, V sourceContent, V targetContent, int weight){
        if(data == null || sourceContent == null || targetContent == null || weight < 0){
            return false;
        }
        Vertex<V,E> sourceVertex = this.findVertex(sourceContent);
        Vertex<V,E> targetVertex = this.findVertex(targetContent);
        if(sourceVertex != null && targetVertex != null){
            sourceVertex.getEdges().add(new Edge<>(data, sourceVertex, targetVertex, weight));
            if(!isDirected){
                targetVertex.getEdges().add(new Edge<>(data, targetVertex, sourceVertex, weight));
            }
            return true;
        }
        return false;
    }

    public boolean removeEdge(V sourceContent, V targetContent){
        if(sourceContent == null || targetContent == null){
            return false;
        }
        Vertex<V,E> sourceVertex = this.findVertex(sourceContent);
        Vertex<V,E> targetVertex = this.findVertex(targetContent);
        if(sourceVertex != null && targetVertex != null){
            sourceVertex.getEdges().remove(this.findEdge(sourceContent, targetContent));
            if(!isDirected){
                targetVertex.getEdges().remove(this.findEdge(targetContent, sourceContent));
            }
            return true;
        }
        return false;
    }
    
    public List<Vertex<V,E>> runBFS(V content){
        List<Vertex<V,E>> path = new LinkedList<>();
        if(content == null){
            return path;
        }
        boolean[] visited = new boolean[vertexs.size()];
        Queue<Vertex<V,E>> vertexQueue = new LinkedList<>();
        Vertex<V,E> startVertex = this.findVertex(content);
        if(startVertex != null){
            vertexQueue.offer(startVertex);
            visited[vertexs.indexOf(startVertex)] = true;
        }
        while(!vertexQueue.isEmpty()){
            Vertex<V,E> currentVertex = vertexQueue.poll();
            path.add(currentVertex);
            for(Edge<V,E> edge : currentVertex.getEdges()){
                Vertex<V,E> targetVertex = edge.getTargetVertex();
                if(targetVertex != null && !visited[vertexs.indexOf(targetVertex)]){
                    vertexQueue.offer(targetVertex);
                    visited[vertexs.indexOf(targetVertex)] = true;
                }
            }
        }
        return path;
    }
    
    public List<Vertex<V,E>> runDFS(V content){
        List<Vertex<V,E>> path = new LinkedList<>();
        if(content == null){
            return path;
        }
        boolean[] visited = new boolean[vertexs.size()];
        Stack<Vertex<V,E>> vertexStack = new Stack<>();
        Vertex<V,E> startVertex = this.findVertex(content);
        if(startVertex != null){
            vertexStack.push(startVertex);
            visited[vertexs.indexOf(startVertex)] = true;
        }
        while(!vertexStack.isEmpty()){
            Vertex<V,E> currentVertex = vertexStack.pop();
            path.add(currentVertex);
            for(Edge<V,E> edge : currentVertex.getEdges()){
                Vertex<V,E> targetVertex = edge.getTargetVertex();
                if(targetVertex != null && !visited[vertexs.indexOf(targetVertex)]){
                    vertexStack.push(targetVertex);
                    visited[vertexs.indexOf(targetVertex)] = true;
                }
            }
        }
        return path;
    }

    public List<Vertex<V,E>> runDijkstra(V sourceContent, V targetContent){
        List<Vertex<V,E>> path = new LinkedList<>();
        if(sourceContent == null || targetContent == null){
            return path;
        }
        Vertex<V,E> sourceVertex = this.findVertex(sourceContent);
        Vertex<V,E> targetVertex = this.findVertex(targetContent);
        if(sourceVertex == null || targetVertex == null){
            return path;
        }
        this.resetVertexs();
        PriorityQueue<Vertex<V,E>> vertexQueue = new PriorityQueue<>();
        vertexQueue.offer(sourceVertex);
        sourceVertex.setCumulativeDistance(0);
        while(!vertexQueue.isEmpty()){
            Vertex<V,E> currentVertex = vertexQueue.poll();
            if(!currentVertex.getIsVisited()){
                currentVertex.setIsVisited(true);
                for(Edge<V,E> edge : currentVertex.getEdges()){
                    Vertex<V,E> adjacentVertex = edge.getTargetVertex();
                    int newCumulativeDistance = currentVertex.getCumulativeDistance() + edge.getWeight();
                    if(newCumulativeDistance < adjacentVertex.getCumulativeDistance()){
                        adjacentVertex.setCumulativeDistance(newCumulativeDistance);
                        adjacentVertex.setPredecessorVertex(currentVertex);
                        vertexQueue.offer(adjacentVertex);
                    }
                }
            }
        }
        Vertex<V,E> currentVertex = targetVertex;
        while(currentVertex != null){
            path.add(0, currentVertex);
            currentVertex = currentVertex.getPredecessorVertex();
        }
        if(path.isEmpty() || path.get(0) != sourceVertex){
            path.clear();
        }
        return path;
    }
    
}
