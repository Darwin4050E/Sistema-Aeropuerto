/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemaaeropuerto;

/**
 *
 * @author Grupo 1 - P1
 * @param <V>
 * @param <E>
 */

public class Arista<V,E> {
    
    // Atributos:

    private E dato;
    private Vertice<V,E> verticeOrigen;
    private Vertice<V,E> verticeDestino;
    private int peso;

    // Métodos:

    public Arista(E dato, Vertice<V,E> nodoOrigen, Vertice<V,E> nodoDestino, int peso) {
        this.dato = dato;
        this.verticeOrigen = nodoOrigen;
        this.verticeDestino = nodoDestino;
        this.peso = peso;
    }

    public E getDato() {
        return dato;
    }

    public void setDato(E dato) {
        this.dato = dato;
    }

    public Vertice<V, E> getVerticeOrigen() {
        return verticeOrigen;
    }

    public void setVerticeOrigen(Vertice<V, E> nodoOrigen) {
        this.verticeOrigen = nodoOrigen;
    }

    public Vertice<V, E> getVerticeDestino() {
        return verticeDestino;
    }

    public void setVerticeDestino(Vertice<V, E> nodoDestino) {
        this.verticeDestino = nodoDestino;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }
    
}
