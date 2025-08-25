/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.flightcontrol.models;

import java.io.Serializable;
/**
 *
 * @author Grupo 1 - P10
 */

public class Airport implements Serializable {
    
    // Atributos

    private String code;
    private String name;
    private String city;
    private String country;
    private String imagePath;
    
    // Métodos

    public Airport(String code, String name, String city, String country) {
        this(code, name, city, country, "");
    }
    
    public Airport(String code, String name, String city, String country, String imagePath) {
        this.code = code;
        this.name = name;
        this.city = city;
        this.country = country;
        this.imagePath = imagePath;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    
    @Override
    public String toString() {
        return name + " (" + code + ")"; //"José Joaquín de Olmedo (GYE)"
    }
    
}
