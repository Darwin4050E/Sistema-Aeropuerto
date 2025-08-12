/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.sistemaaeropuerto;

import java.util.List;

/**
 *
 * @author Grupo 1 - P1
 */

public class SistemaAeropuerto {

    public static void main(String[] args) {
        
                
        // Crear grafo dirigido
        Grafo<String, String> grafo = new Grafo<>(true);

        // Crear vértices
        Vertice<String, String> a = new Vertice<>("A");
        Vertice<String, String> b = new Vertice<>("B");
        Vertice<String, String> c = new Vertice<>("C");
        Vertice<String, String> d = new Vertice<>("D");
        Vertice<String, String> e = new Vertice<>("E");

        // Agregarlos a la lista de vértices del grafo
        grafo.getVertices().add(a);
        grafo.getVertices().add(b);
        grafo.getVertices().add(c);
        grafo.getVertices().add(d);
        grafo.getVertices().add(e);

        // Crear aristas (con peso)
        a.getAristas().add(new Arista<>("A-B", a, b, 4));
        a.getAristas().add(new Arista<>("A-C", a, c, 2));

        b.getAristas().add(new Arista<>("B-C", b, c, 5));
        b.getAristas().add(new Arista<>("B-D", b, d, 10));

        c.getAristas().add(new Arista<>("C-E", c, e, 3));

        e.getAristas().add(new Arista<>("E-D", e, d, 4));

        // Ejecutar Dijkstra
        List<Vertice<String, String>> camino = grafo.ejecutarDijkstra("A", "D");

        // Imprimir resultado
        if (!camino.isEmpty()) {
            System.out.print("Camino más corto de A a D: ");
            for (Vertice<String, String> v : camino) {
                System.out.print(v.getContenido() + " ");
            }
            System.out.println("\nDistancia total: " + camino.get(camino.size() - 1).getDistanciaAcumulada());
        } else {
            System.out.println("No existe camino entre A y D");
        }
        
    }
}
