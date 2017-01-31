/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import metier.Playlist;
import projetplayer.Fabrique;

/**
 * FXML Controller class
 *
 * @author Djamel
 */
public class AddPlaylistController extends Stage implements Initializable {
    /**
     * Imag view de l'image correspondant à la playlist 
     */
    @FXML
    private ImageView imagePlaylist;
    @FXML 
    /**
     * Nom de la playlist
     */
    private TextField newName;
    @FXML
    private GridPane gridContener;
    /**
     * Image choisie par l'utilisateur
     */
    private File selectedFile;
    /**
     * Chemin de l'image
     */
    private String name, imagePath;
    /**
     * Message d'erreur
     */
    private final String errorNameMessage;
    private Stage thisStage;
    /**
     * Liste des playlists
     */
    private final List<Playlist> playlists;
    /**
     * 
     * @param playlists liste des playlists
     */
    public AddPlaylistController(List<Playlist> playlists) {
        this.playlists = playlists;
        errorNameMessage = "Veuillez entrer un nom";
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
    public void setStage(Stage thisStage) {
        this.thisStage = thisStage;
    } 
    /**
     * Ferme la fenetre d'ajout de playlist
     * @throws IOException 
     */
    @FXML
    public void cancel() throws IOException {
        thisStage.close();
    }
    /**
     * Récupère le nom que l'on a choisi et l'image, instancie une Playlist et 
     * l'ajoute à la liste des playlists et ferme la fenetre.
     * Si le nom n'est pas valide affiche une page d'erreur 
     * @throws IOException 
     */
    @FXML
    public void ok() throws IOException {
        if (newName.getText().equals("")) {
          Stage dialog = new Stage();
          Fabrique starter = new Fabrique(playlists);
          dialog.initOwner(thisStage);            
          starter.startErrorDialog(dialog, errorNameMessage);
          return;
        }
        if (selectedFile==null)
            imagePath = "file:ressources/no_image.png";
        Playlist p = new Playlist(newName.getText(), new Image(imagePath));
        playlists.add(p);
        cancel();
    }
    /**
     * Ouvre un fileChooser pour choisir l'image que l'on veut.
     * 
     * @throws MalformedURLException 
     */
    @FXML
    public void chooseImage() throws MalformedURLException {
        FileChooser imageChooser = new FileChooser();
        imageChooser.setTitle("Choisir une image");
        
        imageChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Images","*.png","*.jpg","*.jpeg"),
            new FileChooser.ExtensionFilter("Tous les fichiers", "*.*"));
        
        selectedFile = imageChooser.showOpenDialog(this);
        if (selectedFile != null) {
            imagePath = selectedFile.toURL().toString();
            imagePlaylist.setImage(new Image(imagePath));
        }
    }
    
}
