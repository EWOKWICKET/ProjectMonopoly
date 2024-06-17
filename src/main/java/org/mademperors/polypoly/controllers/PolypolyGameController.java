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
import org.mademperors.polypoly.StreetCharacteristicsAlert;
import org.mademperors.polypoly.listeners.DiceResultListener;
import org.mademperors.polypoly.models.*;


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
    private static boolean isDiceThrown = false;

    SoundManager soundManager;

    @FXML
    public BorderPane polypolyField;
    @FXML
    public Button AddRed;
    @FXML
    public Button throdDicesButton;
    public Button endMoveButton;

    public Label playerMoney;
    public Label playerName;
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

    @FXML
    private VBox paneForStreets;

    @FXML
    private BorderPane panelForPolypolyField;
    @FXML
    private BorderPane dice1, dice2;


    @FXML
    private BorderPane eventCard;

    @FXML
    private TextArea eventCardTextArea;

    @FXML
    private TextArea statisticsTextArea;

    @FXML
    private StackPane field1, field2, field3, field4, field5, field6, field7, field8, field9, field10,
            field11, field12, field13, field14, field15, field16, field17, field18, field19, field20,
            field21, field22, field23, field24, field25, field26, field27, field28, field29, field30,
            field31, field32, field33, field34, field35, field36, field37, field38, field39, field40;


    private Bank bank;
    private Street[] playerStreets;

    // Method to return an array of all StackPanes
    private StackPane[] getAllStackPanes() {
        return new StackPane[]{
                field1, field2, field3, field4, field5, field6, field7, field8, field9, field10,
                field11, field12, field13, field14, field15, field16, field17, field18, field19, field20,
                field21, field22, field23, field24, field25, field26, field27, field28, field29, field30,
                field31, field32, field33, field34, field35, field36, field37, field38, field39, field40
        };
    }

    private final Map<StackPane, Street> streetMap = new HashMap<>();
    private final Map<StackPane, String> streetToType = new HashMap<>();

    private ImageView[] getAllPlayerImages() {
        return new ImageView[]{
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
        initStreetToType();
        initStreets(bank.getAllStreets());
        setPlayerMenu(GameController.getCurrentPlayer());
        GameLogger.setStatisticsTextArea(statisticsTextArea);
        soundManager = new SoundManager();

//        statisticsTextArea.appendText("123412342134");
    }

    private void setPlayerMenu(Player player) {
        playerName.setText("Гравець №" + (currentPlayerIndex + 1) + ":" + player.getName());
        playerMoney.setText("Гроші: " + player.getMoney());
        playerStreets = Arrays.stream(bank.getAllStreets()).flatMap(Arrays::stream).filter(street -> street.getOwner() == player).toArray(Street[]::new);

        VBox colorStreets = paneForStreetColors;
        //clear panes
        paneForStreets.getChildren().clear();
        colorStreets.getChildren().clear();

        colorStreets.setSpacing(10);
        Label steets = new Label("Вулиці:");
        steets.setStyle("-fx-font-size: 30px;");
        colorStreets.getChildren().add(steets);
        //set street colors in vBox
        ArrayList<String> colors = new ArrayList<>();
        Arrays.stream(playerStreets).forEach(street -> {
            if (!colors.contains(street.getColor())) {
                HBox streetOne = new HBox();
                streetOne.setStyle("-fx-background-color: " + street.getColor() + ";");
                streetOne.setPadding(new Insets(5));
                streetOne.setPrefHeight(50);
                streetOne.setOnMouseClicked(event -> {
                    showStreetThisColor(street.getColor(), playerStreets);
                });
                colorStreets.getChildren().add(streetOne);
                colors.add(street.getColor());
            }
        });
    }

    private void initStreetToType() {
        streetToType.put(field1, "start");
        streetToType.put(field2, "street");
        streetToType.put(field3, "street");
        streetToType.put(field4, "chance");
        streetToType.put(field5, "street");
        streetToType.put(field6, "tax");
        streetToType.put(field7, "street");
        streetToType.put(field8, "city");
        streetToType.put(field9, "street");
        streetToType.put(field10, "street");
        streetToType.put(field11, "jail");
        streetToType.put(field12, "street");
        streetToType.put(field13, "street");
        streetToType.put(field14, "chance");
        streetToType.put(field15, "street");
        streetToType.put(field16, "tax");
        streetToType.put(field17, "street");
        streetToType.put(field18, "chance");
        streetToType.put(field19, "street");
        streetToType.put(field20, "street");
        streetToType.put(field21, "parking");
        streetToType.put(field22, "street");
        streetToType.put(field23, "street");
        streetToType.put(field24, "tax");
        streetToType.put(field25, "street");
        streetToType.put(field26, "chance");
        streetToType.put(field27, "street");
        streetToType.put(field28, "tax");
        streetToType.put(field29, "street");
        streetToType.put(field30, "street");
        streetToType.put(field31, "goToJail");
        streetToType.put(field32, "street");
        streetToType.put(field33, "street");
        streetToType.put(field34, "city");
        streetToType.put(field35, "street");
        streetToType.put(field36, "chance");
        streetToType.put(field37, "street");
        streetToType.put(field38, "city");
        streetToType.put(field39, "street");
        streetToType.put(field40, "street");
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

    private void showStreetThisColor(String color, Street[] playerStreets) {
        VBox colorStreets = paneForStreets;
        colorStreets.getChildren().clear();
        Arrays.stream(playerStreets).forEach(street -> {

            HBox streetOne = new HBox();

            colorStreets.setSpacing(10);
            streetOne.setStyle("-fx-background-color: " + color + ";");
            streetOne.setPadding(new Insets(5));
            streetOne.setPrefHeight(50);
            streetOne.setAlignment(Pos.CENTER); // Center align contents in the HBox

            // Create label with street name
            Label nameLabel = new Label(street.getName());
            nameLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px;"); // Example styles
            nameLabel.setAlignment(Pos.CENTER); // Center align label text
            nameLabel.setWrapText(true); // Wrap text if it's too long

            // Add label to the HBox
            streetOne.getChildren().add(nameLabel);

            // Set click event on the HBox
            streetOne.setOnMouseClicked(event -> {
                showAlertDialog(streetOne, street, false);
            });

            // Add HBox to the VBox
            colorStreets.getChildren().add(streetOne);
        });
    }

    private void initStreets(Street[][] allStreets) {
        StackPane[] streets = getAllStackPanes();
        int index = 0;
        setServices(streets);
        for (Street[] oneColorStreet : allStreets) {
            for (Street oneStreet : oneColorStreet) {
                while (!streetToType.get(streets[index]).equals("street")) {
                    index++;
                }

                ObservableList<Node> nodes = streets[index].getChildren();
                for (Node node : nodes) {
                    node.setOnMouseClicked(event -> {
                        showAlertDialog(node, oneStreet, true);
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
                    }
                }

                streetMap.put(streets[index], oneStreet);
                index++;
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
        if (i == 8) {
            ServiceCards.showCity(eventCard, GameController.getCurrentPlayer());
        }
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

    private void showAlertDialog(Node streetOne, Street street, boolean isToShow) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/mademperors/polypoly/streetCard.fxml"));
            Parent root = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.initOwner(streetOne.getScene().getWindow());
            dialogStage.initModality(Modality.APPLICATION_MODAL); // Block input events to other windows

            dialogStage.setScene(new Scene(root));
            dialogStage.setTitle("Характеристики вулиці");

            StreetCharacteristicsAlert sca = loader.getController();
            sca.setStreet(street);
            sca.setToShow(isToShow);
            sca.setStreetName(street.getName());
            sca.setStreetPrices(street.getRentModel());
            sca.setMortgagedPrice(street.getMortgagePrice());
            sca.setStreetColor(street.getColor());
            sca.setPriceForBuildingsLabel(street.getHousePrice(), street.getHotelPrice());
            sca.init();
            dialogStage.showAndWait();

            if (!isToShow) {
                setPlayerMenu(street.getOwner());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addRed(MouseEvent mouseEvent) {
        VBox colorStreets = paneForStreetColors;
        HBox streetOne = new HBox();
        streetOne.setStyle("-fx-background-color: red;");
        streetOne.setPadding(new Insets(5));
        streetOne.setPrefHeight(50);
        streetOne.setOnMouseClicked(event -> {
            showAlertDialog(streetOne, bank.getAllStreets()[0][0], false);
        });
        colorStreets.getChildren().add(streetOne);


        GameController.throwDices(this);
    }

    @FXML
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
        if (!isDiceThrown) {
            soundManager.playThrow();
            GameController.throwDices(this);
            dice1.setCenter(GameController.diceImageView1);
            dice2.setCenter(GameController.diceImageView2);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Вже кидані кубики");
            alert.setHeaderText(null);
            alert.setContentText("Ви вже кинули кубики. Не можна кидати кубики двічі.");

            alert.showAndWait();
        }
    }

    @FXML
    public void endTurn(MouseEvent mouseEvent) {
        if (isDiceThrown) {
            currentPlayerIndex = (currentPlayerIndex + 1) % players.length;
            GameController.setCurrentPlayer(players[currentPlayerIndex]);
            setPlayerMenu(GameController.getCurrentPlayer());
            isDiceThrown = false;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Не кидані кубики");
            alert.setHeaderText(null);
            alert.setContentText("Ви ще не кинули кубики. Не можна закінчити хід, не кинувши кубики.");

            alert.showAndWait();
        }
//        System.out.println(currentPlayerIndex);
    }

    public static void setPlayers(String[] names) {
        String[] shortenedNames = Arrays.stream(names)
                .filter(Objects::nonNull)
                .toArray(String[]::new);

//        String[] colors = {"#0000FF", "#FF0000", "#008000","#800080", "#FFFF00"};
        String[] colors = {"blue", "red", "green", "purple", "yellow"};

        players = new Player[shortenedNames.length];

        for (int i = 0; i < shortenedNames.length; i++) {
            players[i] = new Player(shortenedNames[i], initialMoney, colors[i]);
            players[i].setPlayerIndex(i);
            if (i == 0) {
                Bank bn = new Bank();
                bn.getAllStreets()[0][0].setOwner(players[i]);
                Street st = bn.getAllStreets()[0][1];
                st.setMortgaged(true);
                st.setOwner(players[i]);
            }
            if (i == 1) {
                Bank bn = new Bank();
                bn.getAllStreets()[0][2].setOwner(players[i]);
            }
        }

        GameController.setCurrentPlayer(players[currentPlayerIndex]);
    }

    public static void setInitialMoney(int initialMoney) {
        PolypolyGameController.initialMoney = initialMoney;
    }

    @Override
    public void onDiceResult(int dice1, int dice2) {
        int result = dice1 + dice2;
        lastDiceResult = result;

        if (players[currentPlayerIndex].isInJail()) {
            if (dice1 == dice2) {
                players[currentPlayerIndex].freeFromJail();
                GameLogger.getInstance().logInfo(players[currentPlayerIndex].getName() + " вийшов з в'язниці");
            } else {
                players[currentPlayerIndex].decreaseJailTime();
                GameLogger.getInstance().logInfo(players[currentPlayerIndex].getName() + " залишився в в'язниці");
                return;
            }
        }

        int newPositionIndex = players[currentPlayerIndex].getCurrentPositionIndex() + result;

        if (newPositionIndex >= 40) {
            players[currentPlayerIndex].addMoney(200);
            newPositionIndex -= 40;
            GameLogger.getInstance().logInfo(players[currentPlayerIndex].getName() + " отримав 200$ за проходження старту");
        }

        updatePlayerPosition(newPositionIndex);

        players[currentPlayerIndex].setCurrentPositionIndex(newPositionIndex);

        switch (streetToType.get(getAllStackPanes()[newPositionIndex])) {
            case "start" -> {}
            case "street" -> {
                stepOnStreet();
            }
            case "chance" -> {
                ServiceCards.showChance(eventCard, GameController.getCurrentPlayer());
            }
            case "tax" -> {
                players[currentPlayerIndex].decreaseMoney(200);
                GameLogger.getInstance().logInfo(players[currentPlayerIndex].getName() + " заплатив 200$ податку");
            }
            case "city" -> {
                ServiceCards.showCity(eventCard, GameController.getCurrentPlayer());
            }
            case "jail", "goToJail" -> {
                goToJail();
            }
            case "parking" -> {
                GameLogger.getInstance().logInfo(players[currentPlayerIndex].getName() + " припаркувався");
            }
        }
    }

    private void updatePlayerPosition(int newPositionIndex) {
        soundManager.playChipMoving();

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

    private void goToJail() {
        players[currentPlayerIndex].setCurrentPositionIndex(10);
        updatePlayerPosition(10);
        players[currentPlayerIndex].putInJail();
        GameLogger.getInstance().logInfo(players[currentPlayerIndex].getName() + " потрапив до в'язниці");
    }

    private void stepOnStreet() {
        Street street = streetMap.get(getAllStackPanes()[players[currentPlayerIndex].getCurrentPositionIndex()]);
        if (street.getOwner() != null) {
            if (street.getOwner() != players[currentPlayerIndex]) {
                players[currentPlayerIndex].decreaseMoney(street.getRent());
                street.getOwner().addMoney(street.getRent());
                GameLogger.getInstance().logInfo(players[currentPlayerIndex].getName() + " заплатив оренду " + street.getRent() + "$ гравцю " + street.getOwner().getName());
            }
        }
    }

    public static void setDiceThrown(boolean thrown) {
        isDiceThrown = thrown;
    }

}




