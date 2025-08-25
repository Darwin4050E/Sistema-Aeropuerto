/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.flightcontrol.utils;
import ec.edu.espol.flightcontrol.models.*;
import java.util.HashMap;
import java.util.Map;
import com.mxgraph.view.mxGraph;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxStylesheet;
import java.util.Hashtable;

/**
 *
 * @author gabriel
 */
public class GraphAdapter {
    public static <V,E> mxGraph toJGraphX(GraphAL<V,E> graphAL) {
        mxGraph jgxGraph = new mxGraph();
        Object parent = jgxGraph.getDefaultParent();

        mxStylesheet stylesheet = jgxGraph.getStylesheet();
        Hashtable<String, Object> style = new Hashtable<>();
        style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ELLIPSE);
        style.put(mxConstants.STYLE_FILLCOLOR, "#C3D9FF");
        style.put(mxConstants.STYLE_STROKECOLOR, "#6482B9");
        style.put(mxConstants.STYLE_FONTCOLOR, "#333333");
        style.put(mxConstants.STYLE_FONTSIZE, 12);
        style.put(mxConstants.STYLE_FONTSTYLE, mxConstants.FONT_BOLD);
        stylesheet.putCellStyle("ROUNDED", style);
        
        Map<Vertex<V, E>, Object> vertexMap = new HashMap<>();

        jgxGraph.getModel().beginUpdate();
        try {
            for (Vertex<V, E> v : graphAL.getVertexs()) {
                String label = (v.getContent() != null) ? v.getContent().toString() : "";
                Object jgxVertex = jgxGraph.insertVertex(parent, null, label, 0, 0, 60, 60, "ROUNDED");
                vertexMap.put(v, jgxVertex);
            }

            String edgeStyle = "endArrow=classic;";

            for (Vertex<V, E> sourceVertexAL : graphAL.getVertexs()) {
                for (Edge<V, E> edgeAL : sourceVertexAL.getEdges()) {
                    Object jgxSource = vertexMap.get(sourceVertexAL);
                    Object jgxTarget = vertexMap.get(edgeAL.getTargetVertex());

                    if (jgxSource != null && jgxTarget != null) {
                        String label = String.valueOf(edgeAL.getWeight());
                        jgxGraph.insertEdge(parent, null, label, jgxSource, jgxTarget, edgeStyle);
                    }
                }
            }
        } finally {
            jgxGraph.getModel().endUpdate();
        }
        return jgxGraph;
    }
}
