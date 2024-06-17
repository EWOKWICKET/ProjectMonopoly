package org.mademperors.polypoly.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    @FXML
    private TextField amountOfMoney;

    @FXML
    private Button closeButton, startButton;

    @FXML
    private Button minusMoney, plusMoney, minusPlayers, plusPlayers;

    @FXML
    private TextField numberOfPlayers;
    private MediaPlayer mediaPlayer;


    @FXML
    void closeWindow(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    void minusMoney(MouseEvent event) {
        int money = Integer.parseInt(amountOfMoney.getText());
        money -= 200;
        if (money < 200) {
            money = 200;
        }
        amountOfMoney.setText(String.valueOf(money));
    }


    @FXML
    void minusPlayers(MouseEvent event) {
        int players = Integer.parseInt(numberOfPlayers.getText());
        players--;
        if (players < 2) {
            players = 2;
        }
        numberOfPlayers.setText(String.valueOf(players));
    }

    @FXML
    void plusMoney(MouseEvent event) {
        int money = Integer.parseInt(amountOfMoney.getText());
        money += 200;
        amountOfMoney.setText(String.valueOf(money));
    }

    @FXML
    void plusPlayers(MouseEvent event) {
        int players = Integer.parseInt(numberOfPlayers.getText());
        players++;
        if (players == 6) {
            players = 5;
        }
        numberOfPlayers.setText(String.valueOf(players));
    }

    @FXML
    void startGame(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/mademperors/polypoly/EditPlayers.fxml"));
//        System.out.println(getClass().getResource("GameController.java"));
        try {

            Parent editPlayersMenu = loader.load();

            EditPlayersMenuController editplayers = loader.getController();
            editplayers.setGridOfPlayers(Integer.parseInt(numberOfPlayers.getText()), Integer.parseInt(amountOfMoney.getText()));
            editplayers.setMediaPlayer(mediaPlayer);
            Stage stage = (Stage) startButton.getScene().getWindow();
            // Set the new scene
            stage.setScene(new Scene(editPlayersMenu));
        } catch (IOException e) {
            // Create an Alert of type INFORMATION
            Alert alert = new Alert(Alert.AlertType.ERROR);

            // Set the title of the Alert dialog
            alert.setTitle("Помилка");

            // Set the header text
            alert.setHeaderText(null);

            // Set the content text
            alert.setContentText("Упс.. Щось пішло не так");

            // Show the Alert dialog and wait for user response
            alert.showAndWait();
        }
    }

    void setPlayerAndMoney(int players, int money) {
        numberOfPlayers.setText(String.valueOf(players));
        amountOfMoney.setText(String.valueOf(money));

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String musicPath = "src/main/resources/sounds/backgroundMusic.mp3"; // Replace with your path
        Media music = new Media(new File(musicPath).toURI().toString());
        mediaPlayer = new MediaPlayer(music);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();

        BorderPane borderPane = (BorderPane) startButton.getParent().getParent().getParent();
        borderPane.getStyleClass().add("border-pane");
//        System.out.println(getClass().getResource("/styles.css"));
//        System.out.println(getClass().getResource("/org/mademperors/polypoly/PolypolyGame.fxml"));

    }
}
