package org.mademperors.polypoly;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController {

    @FXML
    private TextField amountOfMoney;

    @FXML
    private Button closeButton;

    @FXML
    private Button minusMoney;

    @FXML
    private Button minusPlayers;

    @FXML
    private TextField numberOfPlayers;

    @FXML
    private Button plusMoney;

    @FXML
    private Button plusPlayers;

    @FXML
    private Button startButton;

    @FXML
    void closeWindow(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    void minusMoney(MouseEvent event) {
        int money=Integer.parseInt(amountOfMoney.getText());
        money-=200;
        if(money<200){
            money=200;
        }
        amountOfMoney.setText(String.valueOf(money));
    }


    @FXML
    void minusPlayers(MouseEvent event) {
        int players =Integer.parseInt(numberOfPlayers.getText());
        players --;
        if(players <2){
            players =2;
        }
        numberOfPlayers.setText(String.valueOf(players));
    }

    @FXML
    void plusMoney(MouseEvent event) {
        int money=Integer.parseInt(amountOfMoney.getText());
        money+=200;
        amountOfMoney.setText(String.valueOf(money));
    }

    @FXML
    void plusPlayers(MouseEvent event) {
        int players =Integer.parseInt(numberOfPlayers.getText());
        players++;
        if(players >6){
            players =6;
        }
        numberOfPlayers.setText(String.valueOf(players));
    }

    @FXML
    void startGame(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EditPlayers.fxml"));
        try {

            Parent editPlayersMenu = loader.load();

            EditPlayersMenuController editplayers=loader.getController();
            editplayers.setGridOfPlayers(Integer.parseInt(numberOfPlayers.getText()), Integer.parseInt(amountOfMoney.getText()));
            Stage stage = (Stage) startButton.getScene().getWindow();
            // Set the new scene
            stage.setScene(new Scene(editPlayersMenu));
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
    void setPlayerAndMoney(int players, int money){
        numberOfPlayers.setText(String.valueOf(players));
        amountOfMoney.setText(String.valueOf(money));

    }

}
