/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.flightcontrol.controllers;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.element.Div;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.layout.properties.HorizontalAlignment;

import ec.edu.espol.flightcontrol.App;
import ec.edu.espol.flightcontrol.models.*;
import ec.edu.espol.flightcontrol.utils.GraphContext;
import java.io.File;
import java.io.IOException;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;
import javax.imageio.ImageIO;

/**
 *
 * @author Grupo 1 - P1
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
    @FXML
    private void generatePDF() {
        String dest = System.getProperty("user.home") + java.io.File.separator + "reporte_vuelos.pdf";

        try {
            WritableImage image = chart.snapshot(new SnapshotParameters(), null);
            File tempImageFile = new File("chart_snapshot.png"); 
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", tempImageFile);

            PdfWriter writer = new PdfWriter(dest);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf, PageSize.A4.rotate());

            document.add(new Paragraph("Reporte de Estadísticas de Vuelos")
                .setTextAlignment(TextAlignment.CENTER)
                .setBold()
                .setFontSize(20));
            
            document.add(new Paragraph("\n"));
            document.add(new Div()
                .add(new Paragraph().add(new Text("Aeropuerto con más salidas: ").setBold()).add(mostOutLabel.getText()))
                .add(new Paragraph().add(new Text("Aeropuerto con menos salidas: ").setBold()).add(lessOutLabel.getText()))
                .add(new Paragraph().add(new Text("Aeropuerto con más llegadas: ").setBold()).add(mostInLabel.getText()))
                .add(new Paragraph().add(new Text("Aeropuerto con menos llegadas: ").setBold()).add(lessInLabel.getText()))
                .setFontSize(12).setMarginBottom(20));

            document.add(new Paragraph("Gráfico de Vuelos por Aeropuerto")
                .setTextAlignment(TextAlignment.CENTER)
                .setBold()
                .setFontSize(14));
            
            ImageData imageData = ImageDataFactory.create(tempImageFile.toURI().toURL());
            Image pdfImage = new Image(imageData);
            pdfImage.setAutoScale(true);
            pdfImage.setHorizontalAlignment(HorizontalAlignment.CENTER);
            document.add(pdfImage);

            document.close();
            tempImageFile.delete();

            UtilController.showAlert(Alert.AlertType.INFORMATION, "Éxito", "Reporte generado correctamente en tu carpeta personal.");
        } catch (IOException e) {
            e.printStackTrace();
            UtilController.showAlert(Alert.AlertType.ERROR, "Error", "No se pudo generar el PDF.");
        }
    }
    
}
