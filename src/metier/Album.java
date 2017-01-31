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
 * @author pipetit1
 */
public class Album {
    private StringProperty title = new SimpleStringProperty();
    public StringProperty titleProperty() {
        return title;
    }
    private Image imageAlbum;
    private List<Music> tracks = new ArrayList<>();

    public Album(String title, ArrayList<Music> tracks, Image imageAlbum) {
        this.title = new SimpleStringProperty(title);
        this.tracks = tracks;
        this.imageAlbum = imageAlbum;
    }
    
    public Image getImage() {
        return this.imageAlbum;
    }

    public String getTitle() {
        return title.get();
    }

    public List<Music> getTracks() {
        return tracks;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public void setTracks(List<Music> tracks) {
        this.tracks = tracks;
    }
}
