/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioEqualizer;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.NumberStringConverter;
import metier.Frequency;

/**
 *
 * @author Thomas Klein
 */
public class EqualizerController extends Stage {
    
    
    @FXML
    private Slider slider60, slider250, slider1k,  slider35k, slider10k;
    @FXML 
    private MenuItem ampBass, ampHigh;
    @FXML
    private Button addPreset;
    @FXML
    private TextArea namePreset;
    @FXML 
    private ListView<Frequency> listViewPreset;
    @FXML 
    private Text dbValue1, dbValue2, dbValue3, dbValue4, dbValue5;
    
    /**
     * Les integer property pour le bind entre la valeur du slider et les labels dbValues
     */
    IntegerProperty sliderValue1 = new SimpleIntegerProperty(0);
    IntegerProperty sliderValue2 = new SimpleIntegerProperty(0);
    IntegerProperty sliderValue3 = new SimpleIntegerProperty(0);
    IntegerProperty sliderValue4 = new SimpleIntegerProperty(0);
    IntegerProperty sliderValue5 = new SimpleIntegerProperty(0);
    private AudioEqualizer equa;
    private Stage thisStage;
    private final ObservableList obsFreq;
    private  List<Frequency> listPreset;
    
    /**
     * Constructeur de l'equalizer
     * @param player
     * représente la musique joué pour modifier les fréquences en direct
     * @param listPreset 
     * prend la liste des presets en paramètre pour les enregistrer meme lorsque l'on ferme la fenetre
     */
    public EqualizerController(MediaPlayer player, List listPreset) {
        this.equa = player.getAudioEqualizer();
        this.listPreset = listPreset;
        
        this.obsFreq = FXCollections.observableArrayList(listPreset);
       
    }
        /**
         * Configure la list view des presets enregistrés pour afficher uniquement
         * le nom du preset et pouvoir lorsque l'on clique sur un preset de changer 
         * la valeur des sliders selon les valeurs dans l'objet Frequency
         */
     private void configureListView() {
        listViewPreset.setCellFactory(l->new ListCell<Frequency>() {
            @Override
            protected void updateItem(Frequency item, boolean empty) {
                super.updateItem(item, empty); 
                if (empty) {
                    textProperty().unbind();
                    setText(null);
                } else {
                    textProperty().bind(item.nameProperty());
                }
            }
        });
        listViewPreset.setItems(obsFreq);
        listViewPreset.setOnMouseClicked((MouseEvent MouseEvent) -> {
//            Image currentImage = playlistsList.getSelectionModel().getSelectedItem().getImage();
//            imagePlaylist.imageProperty().set(currentImage);
              setPreset(listViewPreset.getSelectionModel().getSelectedItem());
        });
    }
     /**
      * Initialise les sliders en modifiant selon la valeur du slider le gain 
      * de la fréquence demandée. 
      * Bind également la valeur du gain en dessous des sliders par rapport à 
      * la valeur des sliders
      */
    public void initialize() {
        
        equa.getBands().get(1).gainProperty().bind(slider60.valueProperty());
        equa.getBands().get(3).gainProperty().bind(slider250.valueProperty());
        equa.getBands().get(5).gainProperty().bind(slider1k.valueProperty());
        equa.getBands().get(7).gainProperty().bind(slider35k.valueProperty());
        equa.getBands().get(8).gainProperty().bind(slider10k.valueProperty());
        
        slider60.valueProperty().bindBidirectional(sliderValue1);
        dbValue1.textProperty().bind(sliderValue1.asString());
        
        slider250.valueProperty().bindBidirectional(sliderValue2);
        dbValue2.textProperty().bind(sliderValue2.asString());
        
        slider1k.valueProperty().bindBidirectional(sliderValue3);
        dbValue3.textProperty().bind(sliderValue3.asString());
        
        slider35k.valueProperty().bindBidirectional(sliderValue4);
        dbValue4.textProperty().bind(sliderValue4.asString());
        
        slider10k.valueProperty().bindBidirectional(sliderValue5);
        dbValue5.textProperty().bind(sliderValue5.asString());
        configureListView();        
    }
    
    public void setStage(Stage thisStage) {
        this.thisStage = thisStage;
    }
    /**
     * 
     * @param f 
     * Prend une Frequency en paramètre qui est composée de 5 Doubles 
     * Change la valeur des 5 sliders selon ces 5 Doubles 
     */
    public void setPreset(Frequency f) {
        slider60.setValue(f.getA());
        slider250.setValue(f.getB());
        slider1k.setValue(f.getC());
        slider35k.setValue(f.getD());
        slider10k.setValue(f.getE()); 
    }
    /**
     * Méthode qui permet lorsque l'on clique sur ajouter un preset de récupérer 
     * le nom que l'on lui a donné. Récupère également la valeur des 5 sliders et 
     * instencie une nouvelle Frequency que n'on ajoute à la liste des presets.
     */
     public void addPreset() {
        String name;
        name = namePreset.getText();
        Frequency f = new Frequency(slider60.getValue(), slider250.getValue(), slider1k.getValue(), slider35k.getValue(), slider10k.getValue(), name);
        this.listPreset.add(f);
        obsFreq.add(f);  
     }
 
    

   
    
    
    
    
    
     
}
