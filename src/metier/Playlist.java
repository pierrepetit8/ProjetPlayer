/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

/**
 *
 * @author Djamel
 */
public class Playlist {
    private StringProperty name = new SimpleStringProperty();    
    public StringProperty nameProperty() {
        return name;
    }
    /**
     * Image de la playlist qui va s'afficher quand on joue un morceau de la playlist
     */
    private Image img;
    /**
     * Liste de musique qui forme une playlist
     */
    private ArrayList<Music> playlist;
    /**
     * 
     * @param name nom de la playlist
     * @param img image de la playlist
     */
    public Playlist(String name, Image img) {
        this.name = new SimpleStringProperty(name);
        playlist = new ArrayList<>();
        this.img = img;
    }
    /**
     * 
     * @return nom de la playlist
     */
    public StringProperty getName() {
        return name;
    }
    /**
     * 
     * @return la playlist entière
     */
    public List<Music> getPlaylist() {
        return playlist;
    }
    /**
     * 
     * @param name set le nom de la playlist
     */
    public void setName(String name) {
        this.name.set(name);
    }
    /**
     * 
     * @return nom de la playlist en string 
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(name);//.append("\n");
        
     /*   for (Music m : playlist) {
            str.append("\t").append(m).append("\n");
        }
        */
            
        return str.toString();
    }
    /**
     * 
     * @return image de la playlist 
     */
    public Image getImage() {
        return img;
    }
    /**
     * 
     * @param m ajoute une musique à la playlist
     */
    public void addMusic(Music m) {
        playlist.add(m);
    }
    /**
     * 
     * @param m enlève une musique à la playlist 
     */
    public void removeMusic(Music m) {
        playlist.remove(m);
    }
}
