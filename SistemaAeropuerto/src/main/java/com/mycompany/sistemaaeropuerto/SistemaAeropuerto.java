/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.sistemaaeropuerto;

/**
 *
 * @author Grupo 1 - P1
 */

public class SistemaAeropuerto {

    public static void main(String[] args) {
        
        /* 
        Grafo<String> grafo = new Grafo<>(false); // No dirigido
    
        // Agregamos vértices
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarVertice("C");
        
        // Agregamos aristas con pesos
        grafo.agregarArista("A", "B", 5);
        grafo.agregarArista("B", "C", 3);
        grafo.agregarArista("A", "C", 2);
        grafo.eliminarVertice("A");
        
        // Imprimir grafo
        grafo.imprimirGrafo();
        
        // Probar adyacencia
        System.out.println("\n¿A y B son adyacentes? " + grafo.sonAdyacentes("A", "B"));
        System.out.println("¿B y A son adyacentes? " + grafo.sonAdyacentes("B", "A"));
        System.out.println("¿A y D son adyacentes? " + grafo.sonAdyacentes("A", "D"));
        */

        Grafo<String> grafo = new Grafo<>(false); // No dirigido

        // Agregar vértices
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarVertice("C");
        grafo.agregarVertice("D");
        grafo.agregarVertice("E");

        // Agregar aristas
        grafo.agregarArista("A", "B", 1);
        grafo.agregarArista("A", "C", 1);
        grafo.agregarArista("B", "D", 1);
        grafo.agregarArista("C", "E", 1);
        grafo.agregarArista("D", "E", 1);

        // Imprimir grafo
        System.out.println("=== Grafo ===");
        grafo.imprimirGrafo();

        // Recorrido en Anchura (BFS)
        System.out.println("\nRecorrido en Anchura desde A:");
        System.out.println(grafo.recorrerEnAnchura("A"));

        // Recorrido en Profundidad (DFS)
        System.out.println("\nRecorrido en Profundidad desde A:");
        System.out.println(grafo.recorrerEnProfundidad("A"));
        
    }
}
