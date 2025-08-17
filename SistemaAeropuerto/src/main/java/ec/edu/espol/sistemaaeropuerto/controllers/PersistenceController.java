/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.sistemaaeropuerto.controllers;

import ec.edu.espol.sistemaaeropuerto.models.Graph;
//import org.graphstream.graph.Graph;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 *
 * @author Nueva
 */
public class PersistenceController {
    
    public static void graphSerializer(Graph graph){
        
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("airportsystem.ser"))) {
            out.writeObject(graph);
            System.out.println("It was successfully serialized in airportsystem.ser");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static Graph graphDeserializer(){
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("airportsystem.ser"))) {
            return (Graph) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
