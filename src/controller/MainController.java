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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import metier.Playlist;
import javafx.util.Duration;
import metier.Fichier;
import metier.Frequency;
import metier.Music;
import projetplayer.Fabrique;

/**
 *FXML Controller class
 *
 * @author Djamel
 */
public class MainController extends Stage implements Initializable {
    @FXML private Button btn_play_pause;
    @FXML private Slider slider_music;
    @FXML private Label title;
    @FXML private ListView<Playlist> playlistsList;
    @FXML private ImageView imagePlaylist;
    @FXML private MenuItem equaBtn;
    @FXML private Slider volumeSlider;
    
    @FXML private ListView<Music> playlist;
    
    private File selectedFile;
    private Stage thisStage;
    /**
     * Player pour lire la musique
     */
    private MediaPlayer player;
    /**
     * ObservableList des playlists
     */
    private final ObservableList obsPlaylists;
    /**
     * Liste des playlists
     */
    private List<Playlist> playlists;
    /**
     * TimeLine pour le slider qui représente l'avancée de la chanson
     */
    private Timeline timeline = new Timeline();
    /**
     * Liste des presets de l'equaliser (réglages que l'on peut enregistrer)
     */
    private  List<Frequency> listPreset = new ArrayList<Frequency>();
    /**
     * Liste des fichiers dans le dossier musique de l'ordinateur
     */
    private ArrayList<File> listFile;
    /**
     * ArrayList des objets Fichiers 
     */
    private List<Fichier> listFichier = new ArrayList<Fichier>();
    private File selectedFileChooser;
    
    /**
     * 
     * @param playlists liste des playlists
     */
    public MainController(List<Playlist> playlists) {
        this.obsPlaylists = FXCollections.observableArrayList(playlists);
        this.playlists = playlists;
    }
    /**
     * Initialise la listeView, le slider, le volume, les presets et la liste de fichiers
     * @param url
     * @param rb 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configureListView();
        configureSlider();
        setVolume();
        initPreset();
        initFile();
    }
    /**
     * Configure le slider pour l'avancement de la musique 
     */
    private void configureSlider() {
        slider_music.setOnMousePressed((MouseEvent MouseEvent) -> {
            if (btn_play_pause.getText().equals("Pause")) {
                player.pause();
                timeline.pause();
                double nValue = (double)slider_music.getValue();
                player.seek(Duration.seconds(nValue));
                timeline.jumpTo(Duration.seconds(nValue));
                player.play();
                timeline.play();
            }
            else {
                double nValue = (double)slider_music.getValue();
                player.seek(Duration.seconds(nValue));
                timeline.jumpTo(Duration.seconds(nValue));
            }
        });
    }
    /**
     * Initialise la liste des fichiers que l'on va trouver dans la fenetre 
     * d'ajout des morceau dans la playlist. Ces fichiers vont etre ceux du répertoire 
     * musique sur l'ordinateur de l'utilisateur. 
     */
    private void initFile() {
        String workingDirectory = System.getProperty("user.home"); //Récupère le répertoire home de l'odinateur
        String absoluteFilePath = "";
        String dir = "Music";
        absoluteFilePath = workingDirectory + File.separator + dir; //Ajoute Music pour obtenir le chemin du répertoire music de l'ordinateur
        File f = new File(absoluteFilePath); 
        File [] listechemin; 
        listechemin=f.listFiles(); // Liste les fichiers du répertoire dans listechemin
        listFile = new ArrayList(Arrays.asList(listechemin)); //Convertie le tableau en liste 
        for (File file : listFile) 
            this.listFichier.add(new Fichier(file.toString()));  //Instancie des Fichier          
    }
    /**
     * Ajoute à la liste des Preset des presets de base 
     */
    private void initPreset() {
        this.listPreset.add(new Frequency(-24,-24,0.1,12,12,"High Amp"));
        this.listPreset.add(new Frequency(12,12,0,-24,-24,"Bass Amp"));
    }
    /**
     * Configure la listView des playlists.
     * Affiche l'image de la playlist lorsque l'on en selectionne une.
     * Affiche la listView des morceaux dans la playlist si elle contient des musiques.
     */
    private void configureListView() {
        playlistsList.setCellFactory(l->new ListCell<Playlist>() {
            @Override
            protected void updateItem(Playlist item, boolean empty) {
                super.updateItem(item, empty); 
                if (empty) {
                    textProperty().unbind();
                    setText(null);
                } else {
                    textProperty().bind(item.nameProperty());
                    System.out.println("Affichage playlist");
                    System.out.println(playlist);
                }
            }
        });

        playlistsList.setItems(obsPlaylists);
        playlistsList.setOnMouseClicked((MouseEvent MouseEvent) -> {
            if (playlistsList.getSelectionModel().getSelectedItem()==null)
                return;
            Image currentImage = playlistsList.getSelectionModel().getSelectedItem().getImage();
            imagePlaylist.imageProperty().set(currentImage);
            if (!playlistsList.getSelectionModel().getSelectedItem().getPlaylist().isEmpty())
                playlist.setVisible(true);
            else
                playlist.setVisible(false);
            configureCurrentPlaylist();
        });        
    }
    /**
     * Configure la liste des morceaux dans une playlist.
     * Affiche les morceaux avec un affichage: "titre" par "artiste"
     * 
     */
    private void configureCurrentPlaylist() {
        playlist.setCellFactory(l->new ListCell<Music>() {
            @Override
            protected void updateItem(Music item, boolean empty) {
                super.updateItem(item, empty); 
                if (empty) {
                    textProperty().unbind();
                    setText(null);
                } else {
                    textProperty().bind(item.titleProperty().concat(" par ").concat(item.artistProperty()));
                }
            }
        });

        playlist.setItems(FXCollections.observableArrayList(playlistsList.getSelectionModel().getSelectedItem().getPlaylist()));
        playlist.setOnMouseClicked((MouseEvent MouseEvent) -> {
            Music m = playlist.getSelectionModel().getSelectedItem();
            if (m==null)
                return;
            stop();
            File file = new File(m.getPath());
            try {
                player = new MediaPlayer(new Media(file.toURI().toURL().toExternalForm()));
                configurePlayer();
            } catch (MalformedURLException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });        
    }
    /**
     * Permet qu'a la fin d'un morceau le player joue la musique suivante de la
     * playlist.
     */
    private void configurePlayer() {
        player.setOnEndOfMedia(() -> {
            Playlist p = playlistsList.getSelectionModel().getSelectedItem();
            Music m = new Music(null,null,null,playlist.getSelectionModel().getSelectedItem().getPath());
            Music next = p.getPlaylist().get(p.getPlaylist().indexOf(m)+1);
            playlist.getSelectionModel().selectNext();
            File file = new File(next.getPath());
            try {
                player = new MediaPlayer(new Media(file.toURI().toURL().toExternalForm()));
            } catch (MalformedURLException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
            stop();
        });
    }
    
    public void setStage(Stage thisStage) {
      this.thisStage = thisStage;
    }
     /**
      * Ouvre la fenetre d'ajout de playlist. 
      * @throws IOException 
      */   
    @FXML
    public void addPlaylist() throws IOException {
      Fabrique starter = new Fabrique(playlists);
      starter.startAddPlaylist(thisStage);
      configureListView();
    }
    /**
     * Configure les boutons play et pause.
     * Permet de couper la musique joué et de la reprendre.
     * @throws InterruptedException 
     */
    @FXML 
    public void play_pause() throws InterruptedException {
      if (player != null) {
        if (btn_play_pause.getText().equals("Play")) {
            btn_play_pause.setText("Pause");
            player.play();
            slider_change();
        }
        else {
            btn_play_pause.setText("Play");
            player.pause();
            timeline.pause();
        }
      }
    }
    /**
     * Permet d'arreter la musique et de remettre le slider à 0
     */
    @FXML
    public void stop() {
        if (player!=null) {
            player.stop();
            if (btn_play_pause.getText().equals("Pause"))
                btn_play_pause.setText("Play");
            if (timeline!=null)
                timeline.stop();
            slider_music.setValue(0);
        }
    }
    /**
     * Ouvre la fenetre pour éditer une playlist que l'on a selectionné avec la listView
     * @throws IOException 
     */
    @FXML
    public void editPlaylist() throws IOException {
        Playlist selectedPlaylist = playlistsList.getSelectionModel().
                getSelectedItem();
        if (selectedPlaylist==null)
            return;
        Fabrique starter = new Fabrique(playlists);
        starter.startEditPlaylist(selectedPlaylist, thisStage, listFichier);
        configureCurrentPlaylist();
    }
    
    private void slider_change() throws InterruptedException {
        int maxValue = (int)player.getMedia().getDuration().toSeconds();
        timeline.getKeyFrames().clear();
        slider_music.setMax(maxValue);
        timeline.setCycleCount(maxValue);
        timeline.setAutoReverse(false);
        for (int i=0; i<=maxValue; i++) {
            KeyFrame evt = new KeyFrame(Duration.seconds(i), new KeyValue(slider_music.valueProperty(),i));
            timeline.getKeyFrames().add(evt);
        }
        timeline.play();
    }
    /**
     * Ouvre la fenetre de l'equalizer si un morceau est joué
     * @throws IOException 
     */
    @FXML
    public void openEqualizer() throws IOException {
        if (player != null) {
            Fabrique starter = new Fabrique(playlists);
            starter.startEqualizer(thisStage, player, listPreset);
        } 
    }
    /**
     * Change le volume du morceau joué
     */
    public void setVolume() {
        volumeSlider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                player.setVolume(volumeSlider.getValue());
            }
        });
    }
}
