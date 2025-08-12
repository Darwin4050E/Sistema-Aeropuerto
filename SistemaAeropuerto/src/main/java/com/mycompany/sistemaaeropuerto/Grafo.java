/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemaaeropuerto;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author Grupo 1 - P1
 * @param <V>
 * @param <E>
 */

public class Grafo<V,E> {
    
    // Atributos:

    private List<Vertice<V,E>> vertices;
    private boolean esDirigido;

    // Métodos:

    public Grafo(boolean esDirigido) {
        this.esDirigido = esDirigido;
        this.vertices = new LinkedList<>();
    }
    
    public List<Vertice<V, E>> getVertices() {
        return vertices;
    }

    public void setVertices(List<Vertice<V, E>> vertices) {
        this.vertices = vertices;
    }

    public boolean getEsDirigido() {
        return esDirigido;
    }

    public void setEsDirigido(boolean esDirigido) {
        this.esDirigido = esDirigido;
    }

    private Vertice<V,E> obtenerVertice(V contenido){
        if(contenido == null){
            return null;
        }
        for(Vertice<V,E> vertice : vertices){
            if(vertice.getContenido().equals(contenido)){
                return vertice;
            }
        }
        return null;
    }

    private Arista<V,E> obtenerArista(V contenidoOrigen, V contenidoDestino){
        if(contenidoOrigen == null || contenidoDestino == null){
            return null;
        }
        Vertice<V,E> verticeOrigen = this.obtenerVertice(contenidoOrigen);
        Vertice<V,E> verticeDestino = this.obtenerVertice(contenidoDestino);
        if(verticeOrigen != null && verticeDestino != null){
            for(Arista<V,E> arista : verticeOrigen.getAristas()){
                if(arista.getVerticeDestino().equals(verticeDestino)){
                    return arista;
                }
            }
        }
        return null;
    }
    
    private void reiniciarVertices(){
        for(Vertice<V,E> vertice : vertices){
            vertice.setEstaVisitado(false);
            vertice.setDistanciaAcumulada(Integer.MAX_VALUE);
            vertice.setVerticePredecesor(null);
        }
    }

    public void agregarVertice(V contenido){
        if(contenido == null){
            return;
        }
        this.vertices.add(new Vertice<>(contenido));
    }

    public void eliminarVertice(V contenido){
        if(contenido == null){
            return;
        }
        Vertice<V,E> verticeEliminar = this.obtenerVertice(contenido);
        if(verticeEliminar != null){
            for(Vertice<V,E> vertice : vertices){
                for(Arista<V,E> arista : vertice.getAristas()){
                    if(arista.getVerticeDestino().equals(verticeEliminar)){
                        vertice.getAristas().remove(arista);
                    }
                }
            }
            vertices.remove(verticeEliminar);
        }

    }

    public void agregarArista(E dato, V contenidoOrigen, V contenidoDestino, int peso){
        if(dato == null || contenidoOrigen == null || contenidoDestino == null || peso < 0){
            return;
        }
        Vertice<V,E> verticeOrigen = this.obtenerVertice(contenidoOrigen);
        Vertice<V,E> verticeDestino = this.obtenerVertice(contenidoDestino);
        if(verticeOrigen != null && verticeDestino != null){
            verticeOrigen.getAristas().add(new Arista<>(dato, verticeOrigen, verticeDestino, peso));
            if(!esDirigido){
                verticeDestino.getAristas().add(new Arista<>(dato, verticeDestino, verticeOrigen, peso));
            }
        }
    }

    public void eliminarArista(V contenidoOrigen, V contenidoDestino){
        if(contenidoOrigen == null || contenidoDestino == null){
            return;
        }
        Vertice<V,E> verticeOrigen = this.obtenerVertice(contenidoOrigen);
        Vertice<V,E> verticeDestino = this.obtenerVertice(contenidoDestino);
        if(verticeOrigen != null && verticeDestino != null){
            verticeOrigen.getAristas().remove(this.obtenerArista(contenidoOrigen, contenidoDestino));
            if(!esDirigido){
                verticeDestino.getAristas().remove(this.obtenerArista(contenidoDestino, contenidoOrigen));
            }
        }
    }
    
    public List<Vertice<V,E>> recorrerEnAnchura(V contenido){
        List<Vertice<V,E>> recorrido = new LinkedList<>();
        if(contenido == null){
            return recorrido;
        }
        boolean[] visitados = new boolean[vertices.size()];
        Queue<Vertice<V,E>> colaVertices = new LinkedList<>();
        Vertice<V,E> verticeInicio = this.obtenerVertice(contenido);
        if(verticeInicio != null){
            colaVertices.offer(verticeInicio);
            visitados[vertices.indexOf(verticeInicio)] = true;
        }
        while(!colaVertices.isEmpty()){
            Vertice<V,E> verticeActual = colaVertices.poll();
            recorrido.add(verticeActual);
            for(Arista<V,E> arista : verticeActual.getAristas()){
                Vertice<V,E> verticeDestino = arista.getVerticeDestino();
                if(verticeDestino != null && !visitados[vertices.indexOf(verticeDestino)]){
                    colaVertices.offer(verticeDestino);
                    visitados[vertices.indexOf(verticeDestino)] = true;
                }
            }
        }
        return recorrido;
    }
    
    public List<Vertice<V,E>> recorrerEnProfundidad(V contenido){
        List<Vertice<V,E>> recorrido = new LinkedList<>();
        if(contenido == null){
            return recorrido;
        }
        boolean[] visitados = new boolean[vertices.size()];
        Stack<Vertice<V,E>> pilaVertices = new Stack<>();
        Vertice<V,E> verticeInicio = this.obtenerVertice(contenido);
        if(verticeInicio != null){
            pilaVertices.push(verticeInicio);
            visitados[vertices.indexOf(verticeInicio)] = true;
        }
        while(!pilaVertices.isEmpty()){
            Vertice<V,E> verticeActual = pilaVertices.pop();
            recorrido.add(verticeActual);
            for(Arista<V,E> arista : verticeActual.getAristas()){
                Vertice<V,E> verticeDestino = arista.getVerticeDestino();
                if(verticeDestino != null && !visitados[vertices.indexOf(verticeDestino)]){
                    pilaVertices.push(verticeDestino);
                    visitados[vertices.indexOf(verticeDestino)] = true;
                }
            }
        }
        return recorrido;
    }

    public List<Vertice<V,E>> ejecutarDijkstra(V contenidoOrigen, V contenidoDestino){
        List<Vertice<V,E>> recorrido = new LinkedList<>();
        if(contenidoOrigen == null || contenidoDestino == null){
            return recorrido;
        }
        Vertice<V,E> verticeOrigen = this.obtenerVertice(contenidoOrigen);
        Vertice<V,E> verticeDestino = this.obtenerVertice(contenidoDestino);
        if(verticeOrigen == null || verticeDestino == null){
            return recorrido;
        }
        this.reiniciarVertices();
        PriorityQueue<Vertice<V,E>> colaVertices = new PriorityQueue<>();
        colaVertices.offer(verticeOrigen);
        verticeOrigen.setDistanciaAcumulada(0);
        while(!colaVertices.isEmpty()){
            Vertice<V,E> verticeActual = colaVertices.poll();
            if(!verticeActual.getEstaVisitado()){
                verticeActual.setEstaVisitado(true);
                for(Arista<V,E> arista : verticeActual.getAristas()){
                    Vertice<V,E> verticeAdyacente = arista.getVerticeDestino();
                    int nuevaDistanciaAcumulada = verticeActual.getDistanciaAcumulada() + arista.getPeso();
                    if(nuevaDistanciaAcumulada < verticeAdyacente.getDistanciaAcumulada()){
                        verticeAdyacente.setDistanciaAcumulada(nuevaDistanciaAcumulada);
                        verticeAdyacente.setVerticePredecesor(verticeActual);
                        colaVertices.offer(verticeAdyacente);
                    }
                }
            }
        }
        Vertice<V,E> verticeActual = verticeDestino;
        while(verticeActual != null){
            recorrido.add(0, verticeActual);
            verticeActual = verticeActual.getVerticePredecesor();
        }
        if(recorrido.isEmpty() || recorrido.get(0) != verticeOrigen){
            recorrido.clear();
        }
        return recorrido;
    }
    
}
