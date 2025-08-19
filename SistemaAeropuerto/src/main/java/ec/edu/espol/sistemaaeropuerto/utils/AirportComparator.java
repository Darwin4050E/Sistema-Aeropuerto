/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.sistemaaeropuerto.utils;
import ec.edu.espol.sistemaaeropuerto.models.*;
import java.io.Serializable;
import java.util.Comparator;
/**
 *
 * @author gabriel
 */
public class AirportComparator implements Comparator<Airport>, Serializable {
    private static final long serialVersionUID = 1L;

    @Override
    public int compare(Airport a1, Airport a2) {
        return a1.getCode().compareTo(a2.getCode());
    }
}
