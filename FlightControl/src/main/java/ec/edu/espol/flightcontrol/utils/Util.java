/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.flightcontrol.utils;

import ec.edu.espol.flightcontrol.models.Airport;
import ec.edu.espol.flightcontrol.models.BSTree;

/**
 *
 * @author Grupo 1 - P1
 */
public class Util {
    public static String extractAirportCode(String airportStr) {
        return airportStr.split("\\(")[1].replace(")", "");
    }
    
    public static Airport findAirportByCode(String code) {
        BSTree<Airport, String> searchTree = GraphContext.getAirportSearchTree();

        if (searchTree != null) {
            return searchTree.find(code);
        }

        return null; 
    }
}
