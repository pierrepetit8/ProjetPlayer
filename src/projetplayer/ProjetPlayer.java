 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetplayer;

import java.io.IOException;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.stage.Stage;
import metier.Playlist;

/**
 *
 * @author thklein
 */
public class ProjetPlayer extends Application {
    ArrayList<Playlist> playlists;
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        playlists = new ArrayList<>();
        Fabrique starter = new Fabrique(playlists);
        starter.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
