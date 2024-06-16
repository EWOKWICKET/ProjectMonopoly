package org.mademperors.polypoly.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.mademperors.polypoly.StreetCharacteristicsAlert;
import org.mademperors.polypoly.listeners.DiceResultListener;
import org.mademperors.polypoly.models.Bank;
import org.mademperors.polypoly.models.GameLogger;
import org.mademperors.polypoly.models.Player;
import org.mademperors.polypoly.models.Street;


import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;
import java.util.List;

public class PolypolyGameController implements Initializable, DiceResultListener {


    private static Player[] players;
    private static int initialMoney;

    //Dice interfaces and fields
    private static int lastDiceResult;
    private static int currentPlayerIndex = 0;


    @FXML
    public BorderPane polypolyField;
    @FXML
    public Button AddRed;
    @FXML
    public Button throdDicesButton;
    @FXML
    public ImageView player1;
    @FXML
    public ImageView player2;
    @FXML
    public ImageView player3;
    @FXML
    public ImageView player4;
    @FXML
    public ImageView player5;
    @FXML
    private VBox paneForStreetColors;

    private PolypolyFieldController ppfc;

    @FXML
    private BorderPane paneForStreets;

    @FXML
    private BorderPane panelForPolypolyField;
    @FXML
    private BorderPane dice1, dice2;


    @FXML
    private BorderPane eventCard;

    @FXML
    private TextArea eventCardTextArea;

    @FXML
    private BorderPane field111, field1111;

    @FXML private StackPane field1, field2, field3, field4, field5, field6, field7, field8, field9, field10,
            field11, field12, field13, field14, field15, field16, field17, field18, field19, field20,
            field21, field22, field23, field24, field25, field26, field27, field28, field29, field30,
            field31, field32, field33, field34, field35, field36, field37, field38, field39, field40;

    @FXML
    private TextArea statisticsTextArea;
    private Bank bank;

    // Method to return an array of all StackPanes
    private StackPane[] getAllStackPanes() {
        return new StackPane[] {
                field1, field2, field3, field4, field5, field6, field7, field8, field9, field10,
                field11, field12, field13, field14, field15, field16, field17, field18, field19, field20,
                field21, field22, field23, field24, field25, field26, field27, field28, field29, field30,
                field31, field32, field33, field34, field35, field36, field37, field38, field39, field40
        };
    }

    private ImageView[] getAllPlayerImages() {
        return new ImageView[] {
                player1,
                player2,
                player3,
                player4,
                player5
        };
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        bank = new Bank();
        initStreets(bank.getAllStreets());

        VBox colorStreets = paneForStreetColors;
        colorStreets.setSpacing(10);
        Label streets = new Label("Вулиці:");
        streets.setStyle("-fx-font-size: 30px;");
        colorStreets.getChildren().add(streets);
    }

    public void initPlayerImages() {
        String[] images = {"/assets/players/blue.png",
                "/assets/players/red.png",
                "/assets/players/green.png",
                "/assets/players/purple.png",
                "/assets/players/yellow.png"};

        ImageView[] playerImages = getAllPlayerImages();
        for (int i = 0; i < players.length; i++) {
            InputStream imageStream = getClass().getResourceAsStream(images[i]);
            assert imageStream != null; // говнокод але що поробиш
            Image image = new Image(imageStream);
            playerImages[i].setImage(image);
            players[i].setPlayerImageView(playerImages[i]);
        }
    }

    private void initStreets(Street[][] allStreets) {
        StackPane[] streets = getAllStackPanes();
        int index = 0;
        setServices(streets);
        for (Street[] oneColorStreet : allStreets) {
            for (Street oneStreet : oneColorStreet) {
                while (streets[index].getId().equals("field1") || streets[index].getId().equals("field11") || streets[index].getId().equals("field21") || streets[index].getId().equals("field31")
                        || streets[index].getId().equals("field4")) {
                    index++;
                }

                ObservableList<Node> nodes = streets[index].getChildren();
                for (Node node : nodes) {
                    node.setOnMouseClicked(event -> {
                        showAlertDialog(node, oneStreet);
                    });
                    if (node.getClass().toString().contains("AnchorPane")) {
                        AnchorPane ap = (AnchorPane) node;
                        ap.setStyle("-fx-background-color: " + oneStreet.getColor() + ";");
                        ObservableList<Node> labels = ap.getChildren();
                        for (Node label : labels) {
                            Label realLabel = (Label) label;
                            if (realLabel.getText().contains("вул")) {
                                realLabel.setText(oneStreet.getName());

                            } else {
                                realLabel.setText("$ " + oneStreet.getPrice());
                            }
                        }
                        index++;

                    }
                }

            }
        }
    }

    private void setServices(StackPane[] streets) {
        setChance(4, streets);
        setTax(6, 200, streets);
        setCity(8, streets);

        setChance(14, streets);
        setTax(16, 200, streets);
        setChance(18, streets);

        setTax(24, 200, streets);
        setChance(26, streets);
        setTax(28, 200, streets);

        setCity(34, streets);
        setChance(36, streets);
        setCity(38, streets);
    }

    private void setCity(int i, StackPane[] streets) {
        ObservableList<Node> nodes = streets[i - 1].getChildren();
        for (Node node : nodes) {
            if (node instanceof AnchorPane) {
                AnchorPane ap = (AnchorPane) node;
                ap.setStyle("-fx-background-color: darkblue;");
                ObservableList<Node> labels = ap.getChildren();

                // Collect nodes to remove
                List<Node> nodesToRemove = new ArrayList<>();

                for (Node label : labels) {
                    if (label instanceof Label) {
                        Label realLabel = (Label) label;
                        if (realLabel.getText().contains("вул")) {
                            realLabel.setText("Місто");
                            realLabel.setStyle("-fx-font-size: 25;");
                            realLabel.setTextFill(Color.WHITE);
                        } else {
                            nodesToRemove.add(realLabel);
                        }
                    }
                }

                // Remove nodes after iteration
                labels.removeAll(nodesToRemove);
            }
        }
    }

    private void setTax(int i, int taxPrice, StackPane[] streets) {
        ObservableList<Node> nodes = streets[i - 1].getChildren();
        for (Node node : nodes) {

            if (node.getClass().toString().contains("AnchorPane")) {
                AnchorPane ap = (AnchorPane) node;
                ap.setStyle("-fx-background-color: white;");
                ObservableList<Node> labels = ap.getChildren();
                for (Node label : labels) {
                    Label realLabel = (Label) label;
                    if (realLabel.getText().contains("вул")) {
                        realLabel.setText("Податок");
                        realLabel.setStyle("-fx-font-size: 20;");
                    } else {
                        realLabel.setText("$ " + taxPrice);
                    }
                }

            }
        }
    }

    private void setChance(int i, StackPane[] streets) {
        ObservableList<Node> nodes = streets[i-1].getChildren();
        for (Node node : nodes) {
            if (node instanceof AnchorPane) {
                AnchorPane ap = (AnchorPane) node;
                ap.setStyle("-fx-background-color: darkblue;");
                ObservableList<Node> labels = ap.getChildren();

                // Collect nodes to remove
                List<Node> nodesToRemove = new ArrayList<>();

                for (Node label : labels) {
                    if (label instanceof Label) {
                        Label realLabel = (Label) label;
                        if (realLabel.getText().contains("вул")) {
                            realLabel.setText("Шанс");
                            realLabel.setStyle("-fx-font-size: 25;");
                            realLabel.setTextFill(Color.WHITE);
                        } else {
                            nodesToRemove.add(realLabel);
                        }
                    }
                }

                // Remove nodes after iteration
                labels.removeAll(nodesToRemove);
            }
        }

    }

    private void showAlertDialog(Node streetOne, Street street) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/mademperors/polypoly/streetCard.fxml"));
            Parent root = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.initOwner(streetOne.getScene().getWindow());
            dialogStage.initModality(Modality.APPLICATION_MODAL); // Block input events to other windows

            dialogStage.setScene(new Scene(root));
            dialogStage.setTitle("Street info");

            StreetCharacteristicsAlert sca=loader.getController();
            sca.setStreetName(street.getName());
            sca.setOnlyStreetPrice(street.getRentModel()[0]);
            sca.setOneHousePrice(street.getRentModel()[1]);
            sca.setTwoHousePrice(street.getRentModel()[2]);
            sca.setThreeHousePrice(street.getRentModel()[3]);
            sca.setFourHousePrice(street.getRentModel()[4]);
            sca.setHotelPrice(street.getRentModel()[5]);
            sca.setStreetColor(street.getColor());
            sca.setPriceForHotelLabel(street.getHotelPrice());
            sca.setPriceForHouseLabel(street.getHousePrice());
            sca.setMortgagePrice(street.getMortgagePrice());

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
            showAlertDialog(streetOne,bank.getAllStreets()[0][0]);
        });
        colorStreets.getChildren().add(streetOne);


        GameController.throwDices(this);
    }

    public void endTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.length;
        GameController.setCurrentPlayer(players[currentPlayerIndex]);
//        System.out.println(currentPlayerIndex);
    }

    public static void setPlayers(String[] names) {
        String[] shortenedNames = Arrays.stream(names)
                .filter(Objects::nonNull)
                .toArray(String[]::new);

//        String[] colors = {"#0000FF", "#FF0000", "#008000","#800080", "#FFFF00"};
        String[] colors = {"blue", "red", "green","purple", "yellow"};

        players = new Player[shortenedNames.length];

        for (int i = 0; i < shortenedNames.length; i ++) {
            players[i] = new Player(shortenedNames[i], initialMoney, colors[i]);
            players[i].setPlayerIndex(i);
        }

        GameController.setCurrentPlayer(players[currentPlayerIndex]);
    }

    public static void setInitialMoney(int initialMoney) {
        PolypolyGameController.initialMoney = initialMoney;
    }

    @Override
    public void onDiceResult(int result) {
        lastDiceResult = result;
        int newPositionIndex = players[currentPlayerIndex].getCurrentPositionIndex() + result;

        if (newPositionIndex >= 40) {
            players[currentPlayerIndex].addMoney(200);
            newPositionIndex -= 40;
        }

        updatePlayerPosition(newPositionIndex);

        players[currentPlayerIndex].setCurrentPositionIndex(newPositionIndex);
//        System.out.println(lastDiceResult);
    }

    private void updatePlayerPosition(int newPositionIndex) {
        ImageView currentPlayerImageView = players[currentPlayerIndex].getPlayerImageView();
        int currentPositionIndex = players[currentPlayerIndex].getCurrentPositionIndex();
        StackPane[] stackPanes = getAllStackPanes();

        stackPanes[currentPositionIndex].getChildren().forEach(node -> {
            if (node instanceof FlowPane fp) {
                fp.getChildren().remove(currentPlayerImageView);
            }
        });
        stackPanes[newPositionIndex].getChildren().forEach(node -> {
            if (node instanceof FlowPane fp) {
                fp.getChildren().add(currentPlayerImageView);
            }
        });
    }

    public void throwDices(MouseEvent mouseEvent) {

//        AnchorPane anp = (AnchorPane) polypolyField.getCenter();
//        for (Node node : anp.getChildren()) {
//
//            if (node.getId() != null && node.getId().equals("dice1")) {
//                BorderPane bp = (BorderPane) node;
//                bp.setCenter(GameController.diceImageView1);
//            }
//            if (node.getId() != null && node.getId().equals("dice2")) {
//                BorderPane bp = (BorderPane) node;
//                bp.setCenter(GameController.diceImageView2);
//            }
//        }
        dice1.setCenter(GameController.diceImageView1);
        dice2.setCenter(GameController.diceImageView2);

        GameController.throwDices(this);
    }
}




