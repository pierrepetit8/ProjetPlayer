/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetplayer;

import controller.AddPlaylistController;
import controller.EqualizerController;
import controller.ErrorController;
import controller.MainController;
import controller.EditPlaylistController;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Modality;
import javafx.stage.Stage;
import metier.Playlist;

/**
 *
 * @author hpiat
 */
public class Fabrique {
    ArrayList<Playlist> playlists;
    
    public Fabrique(List<Playlist> playlists) {
        this.playlists = (ArrayList<Playlist>) playlists;
    } 
    
    public void start(Stage primaryStage) throws IOException {
        MainController controller = new MainController(playlists);
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/ihm/Main.fxml"));
        loader.setController(controller);
        controller.setStage(primaryStage);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setTitle("Player");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public void startAddPlaylist(Stage primaryStage) throws IOException {
        Stage addPlaylistStage = new Stage();
        AddPlaylistController controller = new AddPlaylistController(playlists);
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/ihm/AddPlaylist.fxml"));
        loader.setController(controller);
        controller.setStage(addPlaylistStage);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        addPlaylistStage.setTitle("Ajout Playlist");
        addPlaylistStage.setScene(scene);
        primaryStage.close();
        addPlaylistStage.showAndWait();
        start(new Stage());
    }
    
    public void startErrorDialog(Stage errorStage,String message) throws IOException {
        errorStage.initModality(Modality.APPLICATION_MODAL);
        ErrorController controller = new ErrorController(message);
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/ihm/Error.fxml"));
        loader.setController(controller);
        controller.setStage(errorStage);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        errorStage.setResizable(false);
        errorStage.setTitle("Avertissement");
        errorStage.setScene(scene);
        errorStage.showAndWait();
    }
    
    public void startEditPlaylist(Playlist selectedPlaylist, Stage primaryStage,
            List listFichier) throws IOException {
        Stage EditPlaylistStage = new Stage();
        EditPlaylistStage.initModality(Modality.APPLICATION_MODAL);
        EditPlaylistController controller = new EditPlaylistController(
                selectedPlaylist,playlists,primaryStage, listFichier);
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource(
                "/ihm/EditPlaylist.fxml"));
        loader.setController(controller);
        controller.setStage(EditPlaylistStage);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        EditPlaylistStage.setTitle("Modifier playlist");
        EditPlaylistStage.setResizable(false);
        EditPlaylistStage.setScene(scene);
        EditPlaylistStage.showAndWait();
    }
    
    public void startEqualizer(Stage primaryStage, MediaPlayer player, List
            listPreset) throws IOException {
        
        Stage equalizerStage = new Stage();
        EqualizerController controller = new EqualizerController(player, 
                listPreset);
        
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource(
                "/ihm/Equalizer.fxml"));
        
        loader.setController(controller);
        controller.setStage(equalizerStage);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        equalizerStage.setTitle("Equalizer");
        equalizerStage.setResizable(false);
        equalizerStage.setScene(scene);
        equalizerStage.showAndWait();
    }
}
