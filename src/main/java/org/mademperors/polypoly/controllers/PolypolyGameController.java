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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.mademperors.polypoly.PrisonAlert;
import org.mademperors.polypoly.StreetCharacteristicsAlert;
import org.mademperors.polypoly.listeners.DiceResultListener;
import org.mademperors.polypoly.models.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;
import java.util.List;

/**
 * Controller class for the Polypoly game.
 */
public class PolypolyGameController implements Initializable, DiceResultListener {

    private static Player[] players;
    private static int initialMoney;

    // Dice interfaces and fields
    private static int lastDiceResult;
    private static int doublesInRow = 0;
    private static int currentPlayerIndex = 0;
    private static boolean isDiceThrown = false;

    SoundManager soundManager;

    @FXML
    public BorderPane polypolyField;
    @FXML
    public Button AddRed;
    @FXML
    public Button throwDicesButton;
    public Button endMoveButton;
    public Button buyStreetButton;
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
    private boolean isEventCardPresent = false;
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
    private GameLogger logger;
    private Street[] playerStreets;

    private final Map<StackPane, Street> streetMap = new HashMap<>();
    private final Map<StackPane, String> streetToType = new HashMap<>();

    /**
     * Method to return an array of all StackPanes.
     *
     * @return an array of all StackPanes
     */
    private StackPane[] getAllStackPanes() {
        return new StackPane[]{
                field1, field2, field3, field4, field5, field6, field7, field8, field9, field10,
                field11, field12, field13, field14, field15, field16, field17, field18, field19, field20,
                field21, field22, field23, field24, field25, field26, field27, field28, field29, field30,
                field31, field32, field33, field34, field35, field36, field37, field38, field39, field40
        };
    }

    /**
     * Method to return an array of all player ImageViews.
     *
     * @return an array of all player ImageViews
     */
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
        String musicPath = "src/main/resources/sounds/mainGameBcgrMuusic.mp3"; // Replace with your path
        Media music = new Media(new File(musicPath).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(music);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        System.out.println(mediaPlayer.getVolume());
        mediaPlayer.setVolume(mediaPlayer.getVolume() - 0.6);
        mediaPlayer.play();

        bank = new Bank();
        GameController.setPpgc(this);
        logger = GameLogger.getInstance();
        initStreetToType();
        initStreets(bank.getAllStreets());
        setPlayerMenu(GameController.getCurrentPlayer());
        GameLogger.setStatisticsTextArea(statisticsTextArea);
        soundManager = new SoundManager();
    }

    /**
     * Sets the player menu with the current player's details.
     *
     * @param player the current player
     */
    public void setPlayerMenu(Player player) {
        if (!isEventCardPresent) {
            eventCard.setTop(null);
            eventCard.setBottom(null);
            eventCard.setLeft(null);
            eventCard.setRight(null);
            eventCard.setCenter(null);
        }
        playerName.setText("Гравець №" + (currentPlayerIndex + 1) + ":" + player.getName());
        playerMoney.setText("Гроші: " + player.getMoney());
        playerStreets = Arrays.stream(bank.getAllStreets()).flatMap(Arrays::stream).filter(street -> street.getOwner() == player).toArray(Street[]::new);

        VBox colorStreets = paneForStreetColors;
        // clear panes
        paneForStreets.getChildren().clear();
        colorStreets.getChildren().clear();

        colorStreets.setSpacing(10);
        Label steets = new Label("Вулиці:");
        steets.setStyle("-fx-font-size: 30px;");
        colorStreets.getChildren().add(steets);
        // set street colors in vBox
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

    /**
     * Updates the player's money display.
     */
    public void updateMoney() {
        playerMoney.setText("Гроші: " + players[currentPlayerIndex].getMoney());
    }

    /**
     * Initializes the street to type mapping.
     */
    private void initStreetToType() {
        streetToType.put(field1, "start");
        streetToType.put(field2, "street");
        streetToType.put(field3, "street");
        streetToType.put(field4, "chance");
        streetToType.put(field5, "street");
        streetToType.put(field6, "tax");
        streetToType.put(field7, "street");
        streetToType.put(field8, "chance");
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
        streetToType.put(field34, "chance");
        streetToType.put(field35, "street");
        streetToType.put(field36, "chance");
        streetToType.put(field37, "street");
        streetToType.put(field38, "chance");
        streetToType.put(field39, "street");
        streetToType.put(field40, "street");
    }

    /**
     * Initializes player images.
     */
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

    /**
     * Shows the streets of a specific color.
     *
     * @param color         the color of the streets
     * @param playerStreets the player's streets
     */
    private void showStreetThisColor(String color, Street[] playerStreets) {
        VBox colorStreets = paneForStreets;
        colorStreets.getChildren().clear();
        Arrays.stream(playerStreets).forEach(street -> {

            HBox streetOne = new HBox();
            if (street.getColor().equals(color)) {
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
            }
        });
    }

    /**
     * Initializes the streets on the game board.
     *
     * @param allStreets the array of all streets
     */
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

    /**
     * Sets the services (e.g., Chance, Tax) on the game board.
     *
     * @param streets the array of all StackPanes
     */
    private void setServices(StackPane[] streets) {
        setChance(4, streets);
        setTax(6, 200, streets);
        setChance(8, streets);

        setChance(14, streets);
        setTax(16, 200, streets);
        setChance(18, streets);

        setTax(24, 200, streets);
        setChance(26, streets);
        setTax(28, 200, streets);

        setChance(34, streets);
        setTax(36, 200, streets);
        setChance(38, streets);
    }

    /**
     * Sets a city on the game board.
     *
     * @param i       the index of the city
     * @param streets the array of all StackPanes
     */
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

    /**
     * Sets a tax on the game board.
     *
     * @param i        the index of the tax field
     * @param taxPrice the tax price
     * @param streets  the array of all StackPanes
     */
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

    /**
     * Sets a chance on the game board.
     *
     * @param i       the index of the chance field
     * @param streets the array of all StackPanes
     */
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

    /**
     * Shows the alert dialog for a street.
     *
     * @param streetOne the street node
     * @param street    the street object
     * @param isToShow  whether to show the dialog
     */
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
            sca.setStreetColor(street.getColor());
            sca.setToShow(isToShow);
            sca.setStreetName(street.getName());
            sca.setStreetPrices(street.getRentModel());
            sca.setMortgagedPrice(street.getMortgagePrice());
            sca.setPriceForBuildingsLabel(street.getHousePrice(), street.getHotelPrice());
            sca.setBoughtHouseThisTurn(false);
            sca.init();
            dialogStage.showAndWait();

            if (!isToShow) {
                setPlayerMenu(street.getOwner());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the prison dialog.
     */
    private void showPrisonDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/mademperors/polypoly/prison.fxml"));
            Parent root = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.initOwner(field1.getScene().getWindow());
            dialogStage.initModality(Modality.APPLICATION_MODAL); // Block input events to other windows

            dialogStage.setScene(new Scene(root));
            dialogStage.setTitle("В'язниця");

            PrisonAlert prisonAlert = loader.getController();

            prisonAlert.setJailTurnsText(players[currentPlayerIndex].getJailTime());
            prisonAlert.setCloseAlert(dialogStage::close);
            prisonAlert.setPayBail(() -> {
                players[currentPlayerIndex].decreaseMoney(50);
                players[currentPlayerIndex].freeFromJail();
                dialogStage.close();
            });
            prisonAlert.setFreeExit(() -> {
                players[currentPlayerIndex].useJailFreeCard();
                players[currentPlayerIndex].freeFromJail();
                dialogStage.close();
            });
            prisonAlert.setRollDice(() -> {
                throwDices(null);
                dialogStage.close();
            });
            prisonAlert.setPlayerMoney(players[currentPlayerIndex].getMoney());
            prisonAlert.setJailFreeCards(players[currentPlayerIndex].getJailFreeCards());

            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action of the Add Red button.
     *
     * @param mouseEvent the mouse event
     */
    public void addRed(MouseEvent mouseEvent) {
        showPrisonDialog();
    }

    /**
     * Handles the action of the Throw Dices button.
     *
     * @param mouseEvent the mouse event
     */
    @FXML
    public void throwDices(MouseEvent mouseEvent) {
        if (GameController.getCurrentPlayer().isInJail()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ви у в'язниці");
            alert.setHeaderText(null);
            alert.setContentText("Ви у в'язниці. Ви не можете кидати ходити");

            alert.showAndWait();
        } else {
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
    }

    /**
     * Handles the action of the Buy Street button.
     */
    @FXML
    public void buyStreet() {
        soundManager.playPay();
        GameController.buyStreet(streetMap.get(getAllStackPanes()[GameController.getCurrentPlayer().getCurrentPositionIndex()]));
        updateMoney();
        updateStreet(GameController.getCurrentPlayer().getCurrentPositionIndex());
        buyStreetButton.setDisable(true);
        setPlayerMenu(GameController.getCurrentPlayer());
    }

    /**
     * Updates the street display.
     *
     * @param streetIndex the index of the street
     */
    public void updateStreet(int streetIndex) {
        StackPane street = getAllStackPanes()[streetIndex];
        Street streetObj = streetMap.get(street);

        if (streetObj.getOwner() == null) {
            street.setBorder(null);
            return;
        }

        Color ownerColor = streetObj.isMortgaged() ? Color.GRAY : streetObj.getOwner().getPlayerColor();
        BorderWidths borderWidths = null;

        if (streetIndex > 0 && streetIndex < 10) {
            borderWidths = new BorderWidths(0, 0, 20, 0);
        } else if (streetIndex > 10 && streetIndex < 20) {
            borderWidths = new BorderWidths(0, 0, 0, 10);
        } else if (streetIndex > 20 && streetIndex < 30) {
            borderWidths = new BorderWidths(20, 0, 0, 0);
        } else {
            borderWidths = new BorderWidths(0, 10, 0, 0);
        }

        Border ownerBorder = new Border(new BorderStroke(ownerColor, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, borderWidths));
        street.setBorder(ownerBorder);
    }

    /**
     * Handles the action of the End Turn button.
     */
    @FXML
    public void endTurn() {
        if (isDiceThrown && !isEventCardPresent) {
            applyTurnChanges();
        } else {
            if (!isDiceThrown) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Не кидані кубики");
                alert.setHeaderText(null);
                alert.setContentText("Ви ще не кинули кубики. Не можна закінчити хід, не кинувши кубики.");

                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Не виконане завдання");
                alert.setHeaderText(null);
                alert.setContentText("Ви ще не виконали завдання з картки. Виконайте завдання перед тим як ходити");

                alert.showAndWait();
            }
        }
    }

    /**
     * Applies changes for the turn.
     */
    private void applyTurnChanges() {
        buyStreetButton.setDisable(true);

        if (players[currentPlayerIndex].getMoney() < 0) {
            players[currentPlayerIndex].goBankrupt();
            players = Arrays.stream(players).filter(player -> !player.isBankrupt()).toArray(Player[]::new);
        }

        if (players.length == 1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Кінець гри");
            alert.setHeaderText(null);
            alert.setContentText("Гра закінчилась. Виграв гравець " + players[0].getName() + "!");
            alert.setOnCloseRequest(event -> {
                System.exit(0);
            });
            alert.showAndWait();

            return;
        }

        currentPlayerIndex = (currentPlayerIndex + 1) % players.length;

        GameController.setCurrentPlayer(players[currentPlayerIndex]);
        setPlayerMenu(GameController.getCurrentPlayer());
        isDiceThrown = false;
        doublesInRow = 0;

        bank.updateMortgaged();
        if (players[currentPlayerIndex].isInJail()) {
            showPrisonDialog();
        }
    }

    /**
     * Sets the players for the game.
     *
     * @param names the array of player names
     */
    public static void setPlayers(String[] names) {
        String[] shortenedNames = Arrays.stream(names)
                .filter(Objects::nonNull)
                .toArray(String[]::new);

        String[] colors = {"blue", "red", "green", "purple", "yellow"};

        players = new Player[shortenedNames.length];

        for (int i = 0; i < shortenedNames.length; i++) {
            players[i] = new Player(shortenedNames[i], initialMoney, colors[i]);
            players[i].setPlayerIndex(i);
        }

        GameController.setCurrentPlayer(players[currentPlayerIndex]);
    }

    /**
     * Sets the initial money for each player.
     *
     * @param initialMoney the initial money
     */
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
                logger.logInfo(players[currentPlayerIndex].getName() + " вийшов з в'язниці");
            } else {
                players[currentPlayerIndex].decreaseJailTime();
                logger.logInfo(players[currentPlayerIndex].getName() + " залишився в в'язниці");
                return;
            }
        }

        if (dice1 == dice2) {
            if (++doublesInRow == 3) {
                goToJail();
                logger.logInfo(String.format("%s викинув 3 дублі підряд\nГравець їде до в'язниці", players[currentPlayerIndex].getName()));
            } else {
                isDiceThrown = false;
                logger.logInfo("Дубль! Можна кинути ще раз!");
            }
        } else {
            isDiceThrown = true;
        }

        int newPositionIndex = players[currentPlayerIndex].getCurrentPositionIndex() + result;

        if (newPositionIndex >= 40) {
            soundManager.playPay();
            players[currentPlayerIndex].addMoney(200);
            newPositionIndex -= 40;
            logger.logInfo(players[currentPlayerIndex].getName() + " отримав 200$ за проходження старту");
            updateMoney();
        }

        updatePlayerPosition(newPositionIndex);

        players[currentPlayerIndex].setCurrentPositionIndex(newPositionIndex);

        switch (streetToType.get(getAllStackPanes()[newPositionIndex])) {
            case "start" -> {
            }
            case "street" -> {
                stepOnStreet();
            }
            case "chance" -> {
                isEventCardPresent = true;
                ServiceCards.showChance(eventCard, GameController.getCurrentPlayer());
            }
            case "tax" -> {
                players[currentPlayerIndex].decreaseMoney(200);
                logger.logInfo(players[currentPlayerIndex].getName() + " заплатив 200$ податку");
                updateMoney();
                setPlayerMenu(GameController.getCurrentPlayer());
            }
            case "city" -> {
                isEventCardPresent = true;
                ServiceCards.showCity(eventCard, GameController.getCurrentPlayer());
            }
            case "jail", "goToJail" -> {
                goToJail();
            }
            case "parking" -> {
                logger.logInfo(players[currentPlayerIndex].getName() + " припаркувався");
            }
        }

        setPlayerMenu(GameController.getCurrentPlayer());
    }

    /**
     * Updates the player's position on the game board.
     *
     * @param newPositionIndex the new position index
     */
    public void updatePlayerPosition(int newPositionIndex) {
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

    /**
     * Sends the player to jail.
     */
    public void goToJail() {
        isDiceThrown = true;
        soundManager.playToJail();
        players[currentPlayerIndex].setCurrentPositionIndex(10);
        updatePlayerPosition(10);
        players[currentPlayerIndex].putInJail();
        logger.logInfo(players[currentPlayerIndex].getName() + " потрапив до в'язниці");
    }

    /**
     * Handles the player stepping on a street.
     */
    private void stepOnStreet() {
        Street street = streetMap.get(getAllStackPanes()[players[currentPlayerIndex].getCurrentPositionIndex()]);
        if (street.getOwner() != null) {
            if (street.getOwner() != players[currentPlayerIndex]) {
                soundManager.playPay();
                players[currentPlayerIndex].decreaseMoney(street.getRent());
                street.getOwner().addMoney(street.getRent());
                logger.logInfo(players[currentPlayerIndex].getName() + " заплатив оренду " + street.getRent() + "$ гравцю " + street.getOwner().getName());
                updateMoney();
            }
        } else {
            if (players[currentPlayerIndex].getMoney() >= street.getPrice()) {
                buyStreetButton.setDisable(false);
            }
        }
    }

    /**
     * Sets whether the dice have been thrown.
     *
     * @param thrown whether the dice have been thrown
     */
    public static void setDiceThrown(boolean thrown) {
        isDiceThrown = thrown;
    }

    /**
     * Gets the bank.
     *
     * @return the bank
     */
    public Bank getBank() {
        return bank;
    }

    /**
     * Sets the bank.
     *
     * @param bank the bank
     */
    public void setBank(Bank bank) {
        this.bank = bank;
    }

    /**
     * Gets whether an event card is present.
     *
     * @return whether an event card is present
     */
    public boolean isEventCardPresent() {
        return isEventCardPresent;
    }

    /**
     * Sets whether an event card is present.
     *
     * @param eventCardPresent whether an event card is present
     */
    public void setEventCardPresent(boolean eventCardPresent) {
        isEventCardPresent = eventCardPresent;
    }

    /**
     * Gets the players.
     *
     * @return the players
     */
    public static Player[] getPlayers() {
        return players;
    }

    /**
     * Sets the players.
     *
     * @param players the players
     */
    public static void setPlayers(Player[] players) {
        PolypolyGameController.players = players;
    }
}
