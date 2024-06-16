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
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditPlayersMenuController implements Initializable {

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

    @FXML
    void beginGame(MouseEvent event) {
        String[] playerNames = new String[5];
        if (isAllNamesNormal(playerNames)) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/mademperors/polypoly/PolypolyGame.fxml"));
            try {
                Parent polypolyGame = loader.load();
                PolypolyGameController polypolyGameController = loader.getController();

                Stage stage = (Stage) startGame.getScene().getWindow();
                // Set the new scene
                stage.setScene(new Scene(polypolyGame));

                PolypolyGameController.setInitialMoney(initialMoney);
                PolypolyGameController.setPlayers(playerNames);
                polypolyGameController.initPlayerImages();

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

    private boolean isAllNamesNormal(String[] playerNames) {
        return checkPLayer(playerName1.getText(), playerNames, 1) &&
                checkPLayer(playerName2.getText(), playerNames, 2) &&
                checkPLayer(playerName3.getText(), playerNames, 3) &&
                checkPLayer(playerName4.getText(), playerNames, 4) &&
                checkPLayer(playerName5.getText(), playerNames, 5);
    }

    public String[] getArrayOfPlayers() {
        String[] playerNames = new String[5];
        isAllNamesNormal(playerNames);
        return playerNames;
    }

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

    private void fillCircle(Circle playerImage, String s) {
        Image image = new Image(s);
        playerImage.setFill(new ImagePattern(image));
    }
}
