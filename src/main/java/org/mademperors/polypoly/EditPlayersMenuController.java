package org.mademperors.polypoly;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class EditPlayersMenuController {

    @FXML
    private HBox player1Field;

    @FXML
    private HBox player1Label;

    @FXML
    private HBox player2Field;

    @FXML
    private HBox player2Label;

    @FXML
    private HBox player3Field;

    @FXML
    private HBox player3Label;

    @FXML
    private HBox player4Field;

    @FXML
    private HBox player4Label;

    @FXML
    private HBox player5Field;

    @FXML
    private HBox player5Label;

    @FXML
    private HBox player6Field;

    @FXML
    private HBox player6Label;

    @FXML
    private GridPane playerGrid;

    @FXML
    private TextField playerName1;

    @FXML
    private TextField playerName2;

    @FXML
    private TextField playerName3;

    @FXML
    private TextField playerName4;

    @FXML
    private TextField playerName5;

    @FXML
    private TextField playerName6;

    @FXML
    private Button startGame;

    @FXML
    private Button goBackToMenu;

    private int initialMoney;
    private int players;

    @FXML
    void beginGame(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        // Set the title of the Alert dialog
        alert.setTitle("Помилка");

        // Set the header text
        alert.setHeaderText(null);

        // Set the content text
        alert.setContentText("Гра почалась");
        // Show the Alert dialog and wait for user response
        alert.showAndWait();
        GameController.throwDices();
    }
    @FXML
    void goToMenu(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        try {

            Parent mainMenu = loader.load();
            MainMenuController mainMenuController=loader.getController();

            mainMenuController.setPlayerAndMoney(players, initialMoney);
            Stage stage = (Stage) goBackToMenu.getScene().getWindow();
            // Set the new scene
            stage.setScene(new Scene(mainMenu));
        }
        catch (IOException e){
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

    void setGridOfPlayers(int players, int money ){
        this.initialMoney=money;
        this.players=players;
        for(int i=players+1; i<=6;i++){
            switch (i){
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
                case 6:
                    player6Field.setVisible(false);
                    player6Label.setVisible(false);
                    break;
            }

        }
    }

}
