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
import java.util.Iterator;

/**
 *
 * @author Grupo 1 - P1
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
    
    private List<V> getVertexContents() {
        List<V> contents = new LinkedList<>();
        for (Vertex<V, E> vertex : this.vertexs) {
            contents.add(vertex.getContent());
        }
        return contents;
    }
    
    public int getOutDegree(V content) {
        Vertex<V, E> vertex = findVertex(content);
        if (vertex == null) return -1;
        
        return vertex.getEdges().size();
    }
    
    public int getInDegree(V content) {
        Vertex<V, E> vertex = findVertex(content);
        if (vertex == null) return -1;
        int counter = 0;
        for (Vertex<V, E> current : vertexs) {
            if (current == vertex) continue;
            for (Edge<V, E> edge : current.getEdges()) {
                Vertex<V, E> target = edge.getTargetVertex();
                if (target == vertex) counter++;
            }
        }
        return counter;
    }
    
    public int getTotalDegree(V content) {
        Vertex<V, E> vertex = findVertex(content);
        if (vertex == null) return -1;
        return getOutDegree(content) + getInDegree(content);
    }
    
    public V getMostInDegreeNode() {
        Comparator<V> cmp = new Comparator<>() {
            @Override
            public int compare(V v1, V v2) {
                return Integer.compare(getInDegree(v1), getInDegree(v2));
            }
        };
        Heap<V> heap = new Heap<>(cmp, true, getVertexContents());
        return heap.poll();
    }
    
    public V getLessInDegreeNode() {
        Comparator<V> cmp = new Comparator<>() {
            @Override
            public int compare(V v1, V v2) {
                return Integer.compare(getInDegree(v2), getInDegree(v1));
            }
        };
        Heap<V> heap = new Heap<>(cmp, true, getVertexContents());
        return heap.poll();
    }
    
    public V getMostOutDegreeNode() {
        Comparator<V> cmp = new Comparator<>() {
            @Override
            public int compare(V v1, V v2) {
                return Integer.compare(getOutDegree(v1), getOutDegree(v2));
            }
        };
        Heap<V> heap = new Heap<>(cmp, true, getVertexContents());
        return heap.poll();
    }
    
    public V getLessOutDegreeNode() {
        Comparator<V> cmp = new Comparator<>() {
            @Override
            public int compare(V v1, V v2) {
                return Integer.compare(getOutDegree(v2), getOutDegree(v1));
            }
        };
        Heap<V> heap = new Heap<>(cmp, true, getVertexContents());
        return heap.poll();
    }

    public Vertex<V,E> findVertex(V content){
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

        Vertex<V,E> vertexToRemove = this.findVertex(content);
        if(vertexToRemove == null) return false;

        for(Vertex<V,E> vertex : vertexs){
            Iterator<Edge<V,E>> it = vertex.getEdges().iterator();
            while(it.hasNext()){
                Edge<V,E> edge = it.next();
                if (cmp.compare(edge.getTargetVertex().getContent(), vertexToRemove.getContent()) == 0){
                    it.remove();
                }
            }
        }
        vertexs.remove(vertexToRemove);
        return true;
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
    
    // sobrecarga para manejar multiples aristas entre un par de nodos
    // actualmente solo funciona para grafos dirigidos
    public boolean removeEdge(E data, V sourceContent, V targetContent) {
        if (data == null || sourceContent == null || targetContent == null) {
            return false;
        }

        Vertex<V,E> sourceVertex = this.findVertex(sourceContent);
        if (sourceVertex == null) {
            return false;
        }
        
        Iterator<Edge<V,E>> iterator = sourceVertex.getEdges().iterator();
        while (iterator.hasNext()) {
            Edge<V,E> edge = iterator.next();

            if (edge.getTargetVertex().getContent().equals(targetContent) && edge.getData().equals(data)) {
                iterator.remove();
                return true;
            }
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
