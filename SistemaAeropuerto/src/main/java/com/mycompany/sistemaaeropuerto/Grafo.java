/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemaaeropuerto;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author Grupo 1 - P1
 * @param <E>
 */

public class Grafo<E> {
    
    private int MAX = 20;
    private int[][] matrizAdyacencia;
    private E[] vertices;
    private boolean esDirigido;

    public Grafo(boolean esDirigido){
        this.esDirigido = esDirigido;
        this.matrizAdyacencia = new int[MAX][MAX];
        this.vertices = (E[]) new Object[MAX];
    }

    private int obtenerIndiceVertice(E vertice){
        if(vertice == null){
            return -1;
        }
        for(int i = 0; i < MAX; i++){
            if(vertices[i] != null && vertices[i].equals(vertice)){
                return i;
            }
        }
        return -1;
    }

    public void agregarVertice(E vertice){
        if(vertice == null){
            return;
        }
        for(int i = 0; i < MAX; i++){
            if(vertices[i] == null){
                vertices[i] = vertice;
                break;
            }
        }
    }

    public void eliminarVertice(E vertice){
        if(vertice == null){
            return;
        }
        for(int i = 0; i < MAX; i++){
            if(vertices[i] != null && vertices[i].equals(vertice)){ 
                vertices[i] = null;
                for(int j = 0; j < MAX; j++){
                    matrizAdyacencia[i][j] = 0;
                    matrizAdyacencia[j][i] = 0;
                }
            }
        }
    }

    public void agregarArista(E verticeOrigen, E verticeDestino, int peso){
        if(verticeOrigen == null || verticeDestino == null || peso <= 0){
            return;
        }
        int indiceOrigen = obtenerIndiceVertice(verticeOrigen);
        int indiceDestino = obtenerIndiceVertice(verticeDestino);
        if(indiceOrigen != -1 && indiceDestino != -1){
            matrizAdyacencia[indiceOrigen][indiceDestino] = peso;
            if(!esDirigido){
                matrizAdyacencia[indiceDestino][indiceOrigen] = peso;
            }
        }
    }

    public void eliminarArista(E verticeOrigen, E verticeDestino){
        if(verticeOrigen == null || verticeDestino == null){
            return;
        }
        agregarArista(verticeOrigen, verticeDestino, 0);
    }

    public boolean sonAdyacentes(E verticeOrigen, E verticeDestino){
        if(verticeOrigen == null || verticeDestino == null){
            return false;
        }
        int indiceOrigen = obtenerIndiceVertice(verticeOrigen);
        int indiceDestino = obtenerIndiceVertice(verticeDestino);
        if(indiceOrigen != -1 && indiceDestino != -1){
            return matrizAdyacencia[indiceOrigen][indiceDestino] > 0;
        }
        return false;
    }

    public List<E> recorrerEnAnchura(E verticeInicio){
        List<E> recorrido = new LinkedList<>();
        int indiceVerticeInicio = obtenerIndiceVertice(verticeInicio);
        if(verticeInicio == null || indiceVerticeInicio == -1){
            return recorrido;
        }
        boolean[] visitados = new boolean[MAX];
        Queue<E> colaVertices = new LinkedList<>();
        colaVertices.offer(verticeInicio);
        visitados[indiceVerticeInicio] = true;
        while(!colaVertices.isEmpty()){
            E verticeActual = colaVertices.poll();
            recorrido.add(verticeActual);
            int indiceVerticeActual = obtenerIndiceVertice(verticeActual);
            for(int j = 0; j < MAX; j++){
                if(matrizAdyacencia[indiceVerticeActual][j] > 0 && !visitados[j]){
                    colaVertices.offer(vertices[j]);
                    visitados[j] = true;
                }
            }
        }
        return recorrido;
    }

    public List<E> recorrerEnProfundidad(E verticeInicio){
        List<E> recorrido = new LinkedList<>();
        int indiceVerticeInicio = obtenerIndiceVertice(verticeInicio);
        if(verticeInicio == null || indiceVerticeInicio == -1){
            return recorrido;
        }
        boolean[] visitados = new boolean[MAX];
        Stack<E> pilaVertices = new Stack<>();
        pilaVertices.push(verticeInicio);
        visitados[indiceVerticeInicio] = true;
        while(!pilaVertices.isEmpty()){
            E verticeActual = pilaVertices.pop();
            recorrido.add(verticeActual);
            int indiceVerticeActual = obtenerIndiceVertice(verticeActual);
            for(int j = 0; j < MAX; j++){
                if(matrizAdyacencia[indiceVerticeActual][j] > 0 && !visitados[j]){
                    pilaVertices.push(vertices[j]);
                    visitados[j] = true;
                }
            }
        }
        return recorrido;
    }

    public void imprimirGrafo() {
        System.out.println("Vértices:");
        for (int i = 0; i < MAX; i++) {
            if (vertices[i] != null) {
                System.out.print(vertices[i] + "\t");
            }
        }
        System.out.println("\n\nMatriz de Adyacencia:");
        // Encabezados de columnas
        System.out.print("\t");
        for (int i = 0; i < MAX; i++) {
            if (vertices[i] != null) {
                System.out.print(vertices[i] + "\t");
            }
        }
        System.out.println();
        // Filas con sus respectivos valores
        for (int i = 0; i < MAX; i++) {
            if (vertices[i] != null) {
                System.out.print(vertices[i] + "\t");
                for (int j = 0; j < MAX; j++) {
                    if (vertices[j] != null) {
                        System.out.print(matrizAdyacencia[i][j] + "\t");
                    }
                }
                System.out.println();
            }
        }
    }
    
}
