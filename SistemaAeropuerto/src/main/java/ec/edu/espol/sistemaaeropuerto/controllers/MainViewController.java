package ec.edu.espol.sistemaaeropuerto.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane; 
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.fx_viewer.FxViewer;
import org.graphstream.ui.view.View;


public class MainViewController {

    @FXML
    private BorderPane graphPane;

    public void initialize() {
        System.setProperty("org.graphstream.ui", "javafx");

        // 1. Crea un grafo de GraphStream
        Graph displayGraph = new SingleGraph("Red de Vuelos");

        // 2. Añade estilo CSS para que los nodos sean más visibles (opcional pero recomendado)
        displayGraph.setAttribute("ui.stylesheet", 
            "node { size: 15px; fill-color: #1E90FF; text-size: 12px; text-alignment: under; } " +
            "edge { shape: line; size: 2px; fill-color: #777; arrow-size: 10px, 3px; }");

        // 3. Añade tus aeropuertos (nodos) y vuelos (aristas)
        //    (Aquí harías un bucle sobre tu propia estructura de grafo)
        displayGraph.addNode("PKX").setAttribute("ui.label", "Daxing");
        displayGraph.addNode("JFK").setAttribute("ui.label", "J.F. Kennedy");
        displayGraph.addNode("LHR").setAttribute("ui.label", "Heathrow");
        displayGraph.addEdge("PKX_JFK", "PKX", "JFK", true);
        displayGraph.addEdge("JFK_LHR", "JFK", "LHR", true);
        displayGraph.addEdge("LHR_PKX", "LHR", "PKX", true);

        // 4. Crea el visor (Viewer)
        FxViewer viewer = new FxViewer(displayGraph, FxViewer.ThreadingModel.GRAPH_IN_GUI_THREAD);

        // 5. ✅ NUEVO: Activa la interactividad y el layout automático
        viewer.enableAutoLayout();

        // 6. Crea la vista (View)
        View view = viewer.addDefaultView(false);

        // 7. ✅ NUEVO: Ajusta el zoom de la cámara para que todo sea visible
        view.getCamera().setViewPercent(1); // Aleja la cámara para ver el 90% del grafo

        // 8. Integra la vista en tu panel de JavaFX
        graphPane.setCenter((javafx.scene.Node) view);
    }
}
