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

public class Vertice<V,E> implements Comparable<Vertice<V,E>> {
    
    // Atributos:

    private V contenido;
    private List<Arista<V,E>> aristas;
    
    // Atributos exclusivos para ejecutar Dijkstra:

    private boolean estaVisitado;
    private int distanciaAcumulada;
    private Vertice<V,E> verticePredecesor;

    // Métodos:

    public Vertice(V contenido) {
        this.contenido = contenido;
        this.aristas = new LinkedList<>();
    }

    public V getContenido() {
        return contenido;
    }

    public void setContenido(V contenido) {
        this.contenido = contenido;
    }

    public List<Arista<V,E>> getAristas() {
        return aristas;
    }

    public void setAristas(List<Arista<V,E>> aristas) {
        this.aristas = aristas;
    }
    
    public boolean getEstaVisitado() {
        return estaVisitado;
    }

    public void setEstaVisitado(boolean estaVisitado) {
        this.estaVisitado = estaVisitado;
    }

    public int getDistanciaAcumulada() {
        return distanciaAcumulada;
    }

    public void setDistanciaAcumulada(int distanciaAcumulada) {
        this.distanciaAcumulada = distanciaAcumulada;
    }

    public Vertice<V, E> getVerticePredecesor() {
        return verticePredecesor;
    }

    public void setVerticePredecesor(Vertice<V, E> verticePredecesor) {
        this.verticePredecesor = verticePredecesor;
    }

    @Override
    public int compareTo(Vertice<V, E> o) {
        return o.getDistanciaAcumulada() - this.distanciaAcumulada;
    }
    
}
