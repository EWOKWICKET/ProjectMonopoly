
package org.mademperors.polypoly;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The main class for the Polypoly application.
 */
public class PolypolyApplication extends Application {

    /**
     * The entry point for the Polypoly application.
     * 
     * @param stage the primary stage for the application
     * @throws IOException if an error occurs while loading the FXML file
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PolypolyApplication.class.getResource("MainMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setTitle("Polypoly");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main method that launches the Polypoly application.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch();
    }
}