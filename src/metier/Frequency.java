/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier;

import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author petit
 */
public class Frequency {
    private double a, b, c, d, e;
    /**
     * StringProperty pour afficher dans une list view 
     */
    private StringProperty name = new SimpleStringProperty();

    public StringProperty nameProperty() {
        return name;
    }
    /**
     * Une frequency est composée de 5 Doubles qui vont etre les valeurs des 5 sliders
     * et d'un nom qui va nommer un preset (réglage de l'équalizer que l'on va pouvoir 
     * enregistrer). 
     * @param a
     * @param b
     * @param c
     * @param d
     * @param e
     * @param name 
     */
    public Frequency(double a, double b, double c, double d, double e, String name) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;   
        this.name = new SimpleStringProperty(name);

    }

    /**
     * @return the a
     */
    public double getA() {
        return a;
    }

    /**
     * @return the b
     */
    public double getB() {
        return b;
    }

    /**
     * @return the c
     */
    public double getC() {
        return c;
    }

    /**
     * @return the d
     */
    public double getD() {
        return d;
    }

    /**
     * @return the e
     */
    public double getE() {
        return e;
    }


}
