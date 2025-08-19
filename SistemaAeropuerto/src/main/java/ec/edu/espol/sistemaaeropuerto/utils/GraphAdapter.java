/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.sistemaaeropuerto.utils;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import ec.edu.espol.sistemaaeropuerto.models.*;
/**
 *
 * @author gabriel
 */
public class GraphAdapter {
    public static <V,E> Graph toGraphStream(GraphAL<V,E> graphAL) {
        Graph gsGraph = new SingleGraph("Red de Vuelos");

        // Estilos CSS
        gsGraph.setAttribute("ui.stylesheet", 
            "node { size: 15px; fill-color: #1E90FF; text-size: 12px; text-alignment: under; } " +
            "edge { shape: line; size: 2px; fill-color: #777; arrow-size: 10px, 3px; }");
        
        // Agregar todos los vertices al grafo
        for (Vertex<V,E> v : graphAL.getVertexs()) {
            String id = v.getContent().toString(); // usa código único del aeropuerto
            gsGraph.addNode(id).setAttribute("ui.label", id);
        }

        // Añadir aristas (luego de tener todos los vertices)
        for (Vertex<V,E> v : graphAL.getVertexs()) {
            for (Edge<V,E> e : v.getEdges()) {
                String source = e.getSourceVertex().getContent().toString();
                String target = e.getTargetVertex().getContent().toString();
                String edgeId = source + "_" + target;

                if (gsGraph.getEdge(edgeId) == null) {
                    gsGraph.addEdge(edgeId, source, target, graphAL.getIsDirected())
                           .setAttribute("ui.label", e.getData().toString());
                }
            }
        }

        return gsGraph;
    }
}
