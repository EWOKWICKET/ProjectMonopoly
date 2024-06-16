package org.mademperors.polypoly.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.mademperors.polypoly.listeners.DiceResultListener;
import org.mademperors.polypoly.models.Player;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class PolypolyGameController implements Initializable, DiceResultListener {

    //Dice interfaces and fields
    private static int lastDiceResult;
    private static Player currentPlayer;
//    private static final DiceResultListener diceResultListener = new DiceResultListener() {
//        @Override
//        public void onDiceResult(int result) {
//            lastDiceResult = result;
//            System.out.println(lastDiceResult);
//        }
//    };

    public BorderPane polypolyField;
    public Button AddRed;
    @FXML
    private VBox paneForStreetColors;

    @FXML
    private BorderPane paneForStreets;

    @FXML
    private BorderPane panelForPolypolyField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        VBox colorStreets = paneForStreetColors;

        Label steets = new Label("Вулиці:");
        steets.setStyle("-fx-font-size: 30px;");
        colorStreets.getChildren().add(steets);


    }

    private void showAlertDialog(HBox streetOne) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/mademperors/polypoly/streetCard.fxml"));
            Parent root = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.initOwner(streetOne.getScene().getWindow());
            dialogStage.initModality(Modality.APPLICATION_MODAL); // Block input events to other windows

            dialogStage.setScene(new Scene(root));
            dialogStage.setTitle("Custom Alert");


            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addRed(MouseEvent mouseEvent) {
        VBox colorStreets=paneForStreetColors;
        HBox streetOne=new HBox();
        streetOne.setStyle("-fx-background-color: red;");
        streetOne.setPadding(new Insets(5));
        streetOne.setPrefHeight(50);
        streetOne.setOnMouseClicked(event -> {
            showAlertDialog(streetOne);
        });
        colorStreets.getChildren().add(streetOne);

//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/mademperors/polypoly/monopolyField.fxml"));
//
//            PolypolyFieldController ppfc=loader.getController();
//            ppfc.throwDices();

        GameController.throwDices(this);

        AnchorPane anp = (AnchorPane) polypolyField.getCenter();
        for (Node node : anp.getChildren()) {

            if (node.getId() != null && node.getId().equals("dice1")) {
                BorderPane bp = (BorderPane) node;
                bp.setCenter(GameController.diceImageView1);
            }
            if (node.getId() != null && node.getId().equals("dice2")) {
                BorderPane bp = (BorderPane) node;
                bp.setCenter(GameController.diceImageView2);
            }
        }
    }


    @Override
    public void onDiceResult(int result) {
        lastDiceResult = result;
        System.out.println(lastDiceResult);
    }
//    private BorderPane[] getFields(){
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/mademperors/polypoly/monopolyField.fxml"));
////        System.out.println(getClass().getResource("GameController.java"));
//        try {
//
//            Parent polyfieldcontr = loader.load();
//
//            PolypolyFieldController ppfc = loader.getController();
//            return ppfc.getFields();
//
//        } catch (IOException e) {
//            // Create an Alert of type INFORMATION
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//
//            // Set the title of the Alert dialog
//            alert.setTitle("Помилка");
//
//            // Set the header text
//            alert.setHeaderText(null);
//
//            // Set the content text
//            alert.setContentText("Упс.. Щось пішло не так");
//
//            // Show the Alert dialog and wait for user response
//            alert.showAndWait();
//        }
//        return null;
//    }
}