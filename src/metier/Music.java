/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier;

import java.util.Objects;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

/**
 *
 * @author pipetit1
 */
public class Music {
    
    private StringProperty title = new SimpleStringProperty();
    private StringProperty artist = new SimpleStringProperty();
    private StringProperty album = new SimpleStringProperty();
    private StringProperty path = new SimpleStringProperty();

    
    public StringProperty titleProperty() {
        return title;
    }
    public StringProperty artistProperty() {
        return artist;
    }
    public StringProperty albumProperty() {
        return album;
    }
    public StringProperty pathProperty() {
        return path;
    }
    
    public Music(String title, String artist, String album, String path) {
        this.title = new SimpleStringProperty(title);
        this.artist = new SimpleStringProperty(artist);
        this.album = new SimpleStringProperty(album);
        this.path = new SimpleStringProperty(path);
    }

    public String getTitle() {
        return title.get();
    }

    public String getArtist() {
        return artist.get();
    }

    public String getAlbum() {
        return album.get();
    }
    
    public String getPath() {
        return path.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public void setArtist(String artist) {
        this.artist.set(artist);
    }

    public void setAlbum(String album) {
        this.album.set(album);
    } 
    
    public void setPath(String path) {
        this.album.set(path);
    } 
    
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
    
        str.append(title.get()).append(" par ").append(artist.get());
        
        return str.toString();
    }
    
    @Override
    public boolean equals(Object other) {
        if (other==null)
            return false;
        if (other.getClass()!=this.getClass())
            return false;
        Music otherM = (Music)other;
        if (otherM.getPath()==null)
            return false;
        if (otherM.getPath().equals(this.getPath()))
            return true;
        else
            return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.path);
        return hash;
    }
}
