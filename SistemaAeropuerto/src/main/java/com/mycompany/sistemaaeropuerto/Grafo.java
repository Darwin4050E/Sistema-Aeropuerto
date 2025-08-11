/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemaaeropuerto;

import java.util.LinkedList;
import java.util.List;

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
    
}
