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
        Graph<String, String> grafo = new Graph<>((a,b) -> a.compareTo(b), true);

        // Crear vértices
        Vertex<String, String> a = new Vertex<>("A");
        Vertex<String, String> b = new Vertex<>("B");
        Vertex<String, String> c = new Vertex<>("C");
        Vertex<String, String> d = new Vertex<>("D");
        Vertex<String, String> e = new Vertex<>("E");

        // Agregarlos a la lista de vértices del grafo
        grafo.getVertexs().add(a);
        grafo.getVertexs().add(b);
        grafo.getVertexs().add(c);
        grafo.getVertexs().add(d);
        grafo.getVertexs().add(e);

        // Crear aristas (con peso)
        a.getEdges().add(new Edge<>("A-B", a, b, 4));
        a.getEdges().add(new Edge<>("A-C", a, c, 2));

        b.getEdges().add(new Edge<>("B-C", b, c, 5));
        b.getEdges().add(new Edge<>("B-D", b, d, 10));

        c.getEdges().add(new Edge<>("C-E", c, e, 3));

        e.getEdges().add(new Edge<>("E-D", e, d, 4));

        // Ejecutar Dijkstra
        List<Vertex<String, String>> camino = grafo.runDijkstra("A", "D");

        // Imprimir resultado
        if (!camino.isEmpty()) {
            System.out.print("Camino más corto de A a D: ");
            for (Vertex<String, String> v : camino) {
                System.out.print(v.getContent() + " ");
            }
            System.out.println("\nDistancia total: " + camino.get(camino.size() - 1).getCumulativeDistance());
        } else {
            System.out.println("No existe camino entre A y D");
        }
        
    }
}
