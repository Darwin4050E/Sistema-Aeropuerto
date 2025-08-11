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

public class Vertice<V,E> {
    
    // Atributos:

    private V contenido;
    private List<Arista<V,E>> aristas;

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
    
}
