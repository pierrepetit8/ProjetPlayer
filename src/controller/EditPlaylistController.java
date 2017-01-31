/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ListView.EditEvent;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentEvent.EventType;
import metier.Fichier;
import metier.Playlist;
import metier.Music;
import projetplayer.Fabrique;

/**
 *
 * @author djbousfira
 */
public class EditPlaylistController extends Stage implements Initializable {
    @FXML private TextField name;
    @FXML private TextField title;
    @FXML private TextField artist;
    @FXML private TextField album;
    @FXML private TextField music;
    /**
     * Liste des playlists
     */
    private List<Playlist> playlists;
    /**
     * Messages d'erreur 
     */
    private String errorTitleMessage, errorArtistMessage, errorAlbumMessage, 
            errorMusicMessage;

    
    @FXML private ListView<Fichier> listMusicsPlaylist;
    @FXML private ListView<Music> listMusics;
    /**
     * Playlist courante 
     */
    private Playlist selectedPlaylist;
    private Stage thisStage, primaryStage;
    /**
     * ObservableList des morceaux et des fichiers du dossier musique 
     */
    private ObservableList obsPlaylist, obsFichiers;
    /**
     * Fichier selectionné
     */
    private File selectedFile;
    /**
     * Chemin de la musique 
     */
    private String musicPath;

/**
 * 
 * @param selectedPlaylist Playlist que l'on veut modifier 
 * @param playlists Liste de toutes les playlists
 * @param primaryStage  
 * @param listFichier Liste des fichiers dans le dossier musique de l'utilisateur 
 */
    public EditPlaylistController(Playlist selectedPlaylist, List<Playlist> playlists, Stage primaryStage, List listFichier) {
        this.primaryStage = primaryStage;
        this.playlists = playlists;
        this.selectedPlaylist = selectedPlaylist;
     
        obsFichiers = FXCollections.observableArrayList(listFichier);
        obsPlaylist = FXCollections.observableArrayList(selectedPlaylist.getPlaylist());
        
        errorTitleMessage = "Veuillez entrer un titre valide";
        errorArtistMessage = "Veuillez entrer un artiste valide";
        errorAlbumMessage = "Veuillez entrer un album valide";
        errorMusicMessage = "Veuillez choisir une musique valide";
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configureListsView();
        
    } 
    /**
     * Configure les 2 listView: celle des fichiers du dossier musique 
     * et celle des musique dans la playlist.
     * Permet de selectionner un fichier dans la liste et de l'ajouter à la playlist.
     * 
     */
    private void configureListsView() {
        listMusicsPlaylist.setCellFactory(l->new ListCell<Fichier>(){ 
            @Override
            protected void updateItem(Fichier item, boolean empty) {
                super.updateItem(item, empty); 
                if (empty) {
                    textProperty().unbind();
                    setText("");
                } 
                else {
                    textProperty().bind(item.nameProperty());
                }
            }});
        listMusicsPlaylist.setItems(obsFichiers);
        listMusicsPlaylist.setOnMouseClicked((MouseEvent MouseEvent) -> {
                String selected = listMusicsPlaylist.getSelectionModel().getSelectedItem().getPath();
                if (selected==null)
                    return;
                music.setText(selected);
        });
        
        listMusics.setCellFactory(l->new ListCell<Music>(){ 
            @Override
            protected void updateItem(Music item, boolean empty) {
                super.updateItem(item, empty); 
                if (empty) {
                    textProperty().unbind();
                    setText("");
                } 
                else {
                    textProperty().bind(item.titleProperty());
                }
            }});
        listMusics.setItems(obsPlaylist);
        listMusics.setOnMouseClicked((MouseEvent MouseEvent) -> {
                String selected = listMusics.getSelectionModel().getSelectedItem().getPath();
                if (selected==null)
                    return;
                music.setText(selected);
        });
        obsPlaylist.addListener(new ListChangeListener<Music>() {   
            @Override
            public void onChanged(ListChangeListener.Change<? extends Music> change) 
            {               
                listMusics.setItems(obsPlaylist);
            }
        });

    }
    
    public void setStage (Stage thisStage) {
        this.thisStage = thisStage;
    } 
    /**
     * Permet de choisir une musique à ajouter grace à un fileChooser 
     */
    @FXML
    public void chooseMusic() {
        FileChooser musicChooser = new FileChooser();
        musicChooser.setTitle("Choisir une musique");
        
        musicChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Musiques", "*.mp3", "*.wav", "*.flac"),
            new FileChooser.ExtensionFilter("Tous les fichiers", "*.*"));
        
        selectedFile = musicChooser.showOpenDialog(this);
        
        if (selectedFile != null) {
            musicPath = selectedFile.toString();
            music.setText(musicPath);
        }
        
    }
    /**
     * Instencie une musique grace aux informations données. 
     * Vérifie que les noms donnés sont valide, sinon affiche la fenetre d'erreur 
     * correspondante.
     * Ajoute la la musique créée à la playlist et à la listView
     * @throws IOException 
     */
    @FXML
    public void add() throws IOException {
        if (title.getText().equals("") || artist.getText().equals("") || album.getText().equals("") || music.getText().equals("")) {
          Stage dialog = new Stage();
          Fabrique starter = new Fabrique(playlists);
          dialog.initOwner(thisStage);            
          if (title.getText().equals(""))
            starter.startErrorDialog(dialog, errorTitleMessage);
          else if (artist.getText().equals(""))
            starter.startErrorDialog(dialog, errorArtistMessage);
          else if (album.getText().equals(""))
            starter.startErrorDialog(dialog, errorAlbumMessage);
          else 
            starter.startErrorDialog(dialog, errorMusicMessage);
          return;
        }
        Music m = new Music(title.getText(), artist.getText(), album.getText(), music.getText());
        if (selectedPlaylist.getPlaylist().contains(m))
            return;
        selectedPlaylist.addMusic(m);
        obsPlaylist.add(m);
    }
    /**
     * Permet de supprimer un morceau de la playlist 
     * @throws IOException 
     */
    @FXML
    public void delete() throws IOException {
        Music selectedMusic = new Music(null,null,null,music.getText());
        if (!selectedPlaylist.getPlaylist().contains(selectedMusic))
            return;
        selectedPlaylist.getPlaylist().remove(selectedMusic);
        obsPlaylist.remove(selectedMusic);
    }
    /**
     * Permet de supprimer la playlist.
     * @throws IOException 
     */
   @FXML
   public void delete_playlist() throws IOException {
       playlists.remove(selectedPlaylist);
       quit();
   }
   /**
    * Ferme la fenetre d'édition de playlist 
    * @throws IOException 
    */
   @FXML
   public void quit() throws IOException {
        if (!name.getText().equals("")) {
            selectedPlaylist.setName(name.getText());
        }
       Fabrique starter = new Fabrique(playlists);
       primaryStage.close();
       thisStage.close();
       starter.start(new Stage());
   }
}