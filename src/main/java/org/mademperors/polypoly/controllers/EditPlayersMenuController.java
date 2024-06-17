package org.mademperors.polypoly.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Controller class for the Edit Players Menu.
 */
public class EditPlayersMenuController implements Initializable {

    /**
     * The player images.
     */
    public Circle player1Image, player2Image, player3Image, player4Image, player5Image;

    @FXML
    private HBox player1Field, player2Field, player3Field, player4Field, player5Field;

    @FXML
    private HBox player1Label, player2Label, player3Label, player4Label, player5Label;

    @FXML
    private GridPane playerGrid;

    @FXML
    private TextField playerName1, playerName2, playerName3, playerName4, playerName5;

    @FXML
    private Button startGame, goBackToMenu;

    private int initialMoney, players;
    private MediaPlayer mediaPlayer;

    /**
     * Event handler for the "Begin Game" button click.
     * Starts the game with the entered player names.
     *
     * @param event The mouse event.
     */
    @FXML
    void beginGame(MouseEvent event) {
        String[] playerNames = new String[5];
        if (isAllNamesNormal(playerNames)) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/mademperors/polypoly/PolypolyGame.fxml"));
            try {
                PolypolyGameController.setInitialMoney(initialMoney);
                PolypolyGameController.setPlayers(playerNames);
                Parent polypolyGame = loader.load();
                PolypolyGameController polypolyGameController = loader.getController();
                polypolyGameController.initPlayerImages();

                Stage stage = (Stage) startGame.getScene().getWindow();

                fadeOutMusic();
                // Set the new scene
                stage.setScene(new Scene(polypolyGame));

            } catch (IOException e) {
                e.printStackTrace();
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
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);

            // Set the title of the Alert dialog
            alert.setTitle("Помилка");

            // Set the header text
            alert.setHeaderText(null);

            // Set the content text
            alert.setContentText("Введіть коректні назви гравців. Не може бути гравців з однаковими іменами або без імені.");

            // Show the Alert dialog and wait for user response
            alert.showAndWait();
        }
    }

    private void fadeOutMusic() {
        final double[] currentVolume = {mediaPlayer.getVolume()};
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                currentVolume[0] -= 0.01; // Adjust decrement value for fade speed
                if (currentVolume[0] <= 0.0) {
                    mediaPlayer.stop();
                    timer.cancel();
                } else {
                    mediaPlayer.setVolume(currentVolume[0]);
                }
            }
        };
        timer.schedule(task, 0, 10); // Adjust delay and period for fade duration
    }

    /**
     * Checks if all player names are valid.
     *
     * @param playerNames The array to store the player names.
     * @return True if all names are valid, false otherwise.
     */
    private boolean isAllNamesNormal(String[] playerNames) {
        return checkPLayer(playerName1.getText(), playerNames, 1) &&
                checkPLayer(playerName2.getText(), playerNames, 2) &&
                checkPLayer(playerName3.getText(), playerNames, 3) &&
                checkPLayer(playerName4.getText(), playerNames, 4) &&
                checkPLayer(playerName5.getText(), playerNames, 5);
    }

    /**
     * Gets an array of player names.
     *
     * @return The array of player names.
     */
    public String[] getArrayOfPlayers() {
        String[] playerNames = new String[5];
        isAllNamesNormal(playerNames);
        return playerNames;
    }

    /**
     * Checks if a player name is valid and stores it in the playerNames array.
     *
     * @param playerName   The player name to check.
     * @param playerNames  The array to store the player names.
     * @param playerNumber The player number.
     * @return True if the name is valid, false otherwise.
     */
    private boolean checkPLayer(String playerName, String[] playerNames, int playerNumber) {
        if (playerNumber > players) {
            return true;
        } else {
            if (playerName.isEmpty()) {
                return false;
            } else {
                for (String name : playerNames) {
                    if (playerName.equals(name)) {
                        return false;
                    }
                }
                playerNames[playerNumber - 1] = playerName;
                return true;
            }
        }
    }

    /**
     * Event handler for the "Go to Menu" button click.
     * Navigates back to the main menu.
     *
     * @param event The mouse event.
     */
    @FXML
    void goToMenu(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/mademperors/polypoly/MainMenu.fxml"));
        try {
            Parent mainMenu = loader.load();
            MainMenuController mainMenuController = loader.getController();

            mainMenuController.setPlayerAndMoney(players, initialMoney);
            Stage stage = (Stage) goBackToMenu.getScene().getWindow();

            // Set the new scene
            stage.setScene(new Scene(mainMenu));
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

    /**
     * Sets the grid of players based on the number of players and initial money.
     *
     * @param players The number of players.
     * @param money   The initial money.
     */
    void setGridOfPlayers(int players, int money) {
        this.initialMoney = money;
        this.players = players;
        for (int i = players + 1; i <= 5; i++) {
            switch (i) {
                case 2:
                    player2Field.setVisible(false);
                    player2Label.setVisible(false);
                    break;
                case 3:
                    player3Field.setVisible(false);
                    player3Label.setVisible(false);
                    break;
                case 4:
                    player4Field.setVisible(false);
                    player4Label.setVisible(false);
                    break;
                case 5:
                    player5Field.setVisible(false);
                    player5Label.setVisible(false);
                    break;
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //sets backgroundImage
        BorderPane borderPane = (BorderPane) startGame.getParent().getParent().getParent();
        borderPane.getStyleClass().add("border-pane");

        fillCircle(player1Image, "file:src/main/resources/assets/players/blue.png");
        fillCircle(player2Image, "file:src/main/resources/assets/players/red.png");
        fillCircle(player3Image, "file:src/main/resources/assets/players/green.png");
        fillCircle(player4Image, "file:src/main/resources/assets/players/purple.png");
        fillCircle(player5Image, "file:src/main/resources/assets/players/yellow.png");
    }

    /**
     * Fills the player image circle with the specified image.
     *
     * @param playerImage The player image circle.
     * @param imagePath   The path to the image.
     */
    private void fillCircle(Circle playerImage, String imagePath) {
        Image image = new Image(imagePath);
        playerImage.setFill(new ImagePattern(image));
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer=mediaPlayer;
    }
}
