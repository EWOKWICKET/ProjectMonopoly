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

/**
 * The controller class for the main menu of the game.
 */
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

    /**
     * Closes the application window.
     *
     * @param event The mouse event that triggered the method.
     */
    @FXML
    void closeWindow(MouseEvent event) {
        System.exit(0);
    }

    /**
     * Decreases the amount of money by 200.
     *
     * @param event The mouse event that triggered the method.
     */
    @FXML
    void minusMoney(MouseEvent event) {
        int money = Integer.parseInt(amountOfMoney.getText());
        money -= 200;
        if (money < 200) {
            money = 200;
        }
        amountOfMoney.setText(String.valueOf(money));
    }

    /**
     * Decreases the number of players by 1.
     *
     * @param event The mouse event that triggered the method.
     */
    @FXML
    void minusPlayers(MouseEvent event) {
        int players = Integer.parseInt(numberOfPlayers.getText());
        players--;
        if (players < 2) {
            players = 2;
        }
        numberOfPlayers.setText(String.valueOf(players));
    }

    /**
     * Increases the amount of money by 200.
     *
     * @param event The mouse event that triggered the method.
     */
    @FXML
    void plusMoney(MouseEvent event) {
        int money = Integer.parseInt(amountOfMoney.getText());
        money += 200;
        amountOfMoney.setText(String.valueOf(money));
    }

    /**
     * Increases the number of players by 1.
     *
     * @param event The mouse event that triggered the method.
     */
    @FXML
    void plusPlayers(MouseEvent event) {
        int players = Integer.parseInt(numberOfPlayers.getText());
        players++;
        if (players == 6) {
            players = 5;
        }
        numberOfPlayers.setText(String.valueOf(players));
    }

    /**
     * Starts the game by loading the edit players menu.
     *
     * @param event The mouse event that triggered the method.
     */
    @FXML
    void startGame(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/mademperors/polypoly/EditPlayers.fxml"));
        try {
            Parent editPlayersMenu = loader.load();

            EditPlayersMenuController editplayers = loader.getController();
            editplayers.setGridOfPlayers(Integer.parseInt(numberOfPlayers.getText()), Integer.parseInt(amountOfMoney.getText()));
            editplayers.setMediaPlayer(mediaPlayer);
            Stage stage = (Stage) startButton.getScene().getWindow();
            stage.setScene(new Scene(editPlayersMenu));
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Помилка");
            alert.setHeaderText(null);
            alert.setContentText("Упс.. Щось пішло не так");
            alert.showAndWait();
        }
    }

    /**
     * Sets the initial number of players and amount of money.
     *
     * @param players The number of players.
     * @param money   The amount of money.
     */
    void setPlayerAndMoney(int players, int money) {
        numberOfPlayers.setText(String.valueOf(players));
        amountOfMoney.setText(String.valueOf(money));
    }

    /**
     * Initializes the controller by setting up the background music and styling.
     *
     * @param url            The URL of the location used to resolve relative paths for the root object.
     * @param resourceBundle The resource bundle containing localized objects.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String musicPath = "src/main/resources/sounds/backgroundMusic.mp3";
        Media music = new Media(new File(musicPath).toURI().toString());
        mediaPlayer = new MediaPlayer(music);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();

        BorderPane borderPane = (BorderPane) startButton.getParent().getParent().getParent();
        borderPane.getStyleClass().add("border-pane");
    }
}
