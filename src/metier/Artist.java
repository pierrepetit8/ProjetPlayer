/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

/**
 *
 * @author pipetit1
 * 
 */

public class Artist {
    /**
     * StringProperty du nom de l'artiste
     */
    private StringProperty name = new SimpleStringProperty();
    public StringProperty nameProperty() {
        return name;
    }
    
    private ListProperty albums = new SimpleListProperty();
    public List<Music> albumsProperty() {
        return albums;
    }
            /**
             * 
             * @param name nom de l'artiste
             * @param albums liste des albums de l'artiste
             */
     public Artist(String name, ArrayList<Music> albums) {
        this.name = new SimpleStringProperty(name);
        this.albums = new SimpleListProperty((ObservableList) albums);
    }
/**
 * 
 * @return nom
 */
    public String getName() {
        return name.get();
    }
     /**
      * 
      * @return Liste des albums de l'artiste
      */
    public List<Music> getAlbums() {
        return albums;
    }
/**
 * 
 * @param name nom de l'artiste
 * 
 */
    public void setName(String name) {
        this.name.set(name);
    }
    /**
     * 
     * @param albums albums de l'artiste
     */
    public void setAlbums(List<Music> albums) {
        this.albums.set(albums);
    }
    /**
     * 
     * @return le nom de l'artiste en String
     */      
    @Override
    public String toString() {
        return name.toString();
    }
}
