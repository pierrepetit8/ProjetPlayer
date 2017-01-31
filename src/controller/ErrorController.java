package controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import javafx.scene.control.Label;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author hpiat
 */
public class ErrorController extends Stage implements Initializable {
    @FXML
    /**
     * Label du message d'erreur 
     */
    private Label errorMessage;
    /**
     * Message d'erreur
     */
    private final String message;
    private Stage thisStage;
/**
 * 
 * @param message message d'erreur à afficher 
 */
    public ErrorController(String message) {
        this.message = message;
    }
    /**
     * Set le texte du label avec le message que l'on veut.
     * @param url
     * @param rb 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        errorMessage.setText(message);
    }
    /**
     * 
     * @param thisStage 
     */
    public void setStage(Stage thisStage) {
        this.thisStage = thisStage;
    }
    /**
     * Ferme la fenetre
     */
    @FXML
    public void cancel() {
        thisStage.close();
    }
    
}
