/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author petit
 */
public class Fichier {
    /**
     * Nom de l'user sur l'ordinateur de l'utilisateur
     */
    private String userName;
    /**
     * nom du fichier
     */
    private String stringValue;
    /**
     * chemin du fichier
     */
    private String path;/**
     * String property du nom du fichier
     */
    private StringProperty name = new SimpleStringProperty();
    public StringProperty nameProperty() {
       return name;
    }
    /**
     * Constructeur qui remplace le nom du fichir donné en paramètre en enlevant 
     * le chemin du fichier ainsi que le type du fichier. Pour n'avoir que ne nom de la musique.
     *
     * @param name Nom du fichier à créer 
     * 
     */
    public Fichier(String name) {
        userName = System.getProperty("user.name");
        this.name = new SimpleStringProperty(name);
        path=name;
        String replace = this.name.getValue().replace("C:\\Users\\"+userName+"\\Music\\","").replace(".mp3", "").replace(".wav", "").replace("flac", "");
        this.name.set(replace);
        stringValue = name;
    }
    /**
     * 
     * @return Chemin de la musique 
     */
    public String getPath() {
        return path;
    }
    /**
     * 
     * @return Le nom de musique sans le chemin et le type.
     */
    @Override
    public String toString() {
        return stringValue;
    }

}
