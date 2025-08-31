/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.flightcontrol.controllers;

import ec.edu.espol.flightcontrol.App;
import ec.edu.espol.flightcontrol.models.*;
import ec.edu.espol.flightcontrol.utils.GraphContext;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/**
 *
 * @author gabriel
 */
public class StatsController {

    @FXML
    BarChart<String, Number> chart;
    
    @FXML
    Label mostOutLabel;
    
    @FXML
    Label lessOutLabel;
    
    @FXML
    Label mostInLabel;
    
    @FXML
    Label lessInLabel;
    
    @FXML
    private void switchToMain() throws IOException {
        App.setRoot("mainView");
    }
    
    public void initialize() {
        GraphAL<Airport, Flight> mainGraph = GraphContext.getCurrentGraph();
        loadAirportStats(mainGraph);
        loadChartData(mainGraph);
    }
    
    private void loadAirportStats(GraphAL<Airport, Flight> graph) {
        Airport mostOutAirport = graph.getMostOutDegreeNode();
        Airport lessOutAirport = graph.getLessOutDegreeNode();
        Airport mostInAirport = graph.getMostInDegreeNode();
        Airport lessInAirport = graph.getLessInDegreeNode();
        
        mostOutLabel.setText(String.format("%s - %d vuelos", mostOutAirport, graph.getOutDegree(mostOutAirport)));
        lessOutLabel.setText(String.format("%s - %d vuelos", lessOutAirport, graph.getOutDegree(lessOutAirport)));
        mostInLabel.setText(String.format("%s - %d vuelos", mostInAirport, graph.getInDegree(mostInAirport)));
        lessInLabel.setText(String.format("%s - %d vuelos", lessInAirport, graph.getInDegree(lessInAirport)));
    }
    
    private void loadChartData(GraphAL<Airport, Flight> graph) {
        XYChart.Series<String, Number> outDegreeSeries = new XYChart.Series<>();
        outDegreeSeries.setName("Vuelos de Salida");
        
        XYChart.Series<String, Number> inDegreeSeries = new XYChart.Series<>();
        inDegreeSeries.setName("Vuelos de Llegada");
        
        for (Vertex<Airport, Flight> vertex : graph.getVertexs()) {
            Airport currentAirport = vertex.getContent();
            String airportName = currentAirport.getName(); 
            
            int outDegree = graph.getOutDegree(currentAirport);
            int inDegree = graph.getInDegree(currentAirport);
            
            outDegreeSeries.getData().add(new XYChart.Data<>(airportName, outDegree));
            inDegreeSeries.getData().add(new XYChart.Data<>(airportName, inDegree));
        }
        
        chart.getData().addAll(outDegreeSeries, inDegreeSeries);
    }
    
}
