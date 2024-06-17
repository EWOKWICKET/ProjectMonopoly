package org.mademperors.polypoly.controllers.serviceCardsControllers;

import com.sun.security.jgss.GSSUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import org.mademperors.polypoly.controllers.GameController;
import org.mademperors.polypoly.controllers.PolypolyFieldController;
import org.mademperors.polypoly.controllers.PolypolyGameController;
import org.mademperors.polypoly.models.Bank;
import org.mademperors.polypoly.models.Player;

import java.util.Arrays;

/**
 * Controller class for the Chance Card UI component.
 */
public class ChanceCardController {

    /**
     * The label to display the chance card text.
     */
    public Label chanceText;

    @FXML
    private Button moveChip;

    @FXML
    private FlowPane paneButtonType0;

    @FXML
    private FlowPane paneButtonType1;

    @FXML
    private FlowPane paneButtonType2;

    @FXML
    private FlowPane paneButtonType3;

    @FXML
    private FlowPane paneButtonType4;

    private int streetIndex, payment, housePayment;

    /**
     * Event handler for the "Go to Jail" button click.
     * Moves the player to jail and closes the chance card window.
     *
     * @param event The mouse event.
     */
    @FXML
    void goToJail(MouseEvent event) {
        GameController.getPpgc().goToJail();
        makeEventResolved();
        Stage st = (Stage) chanceText.getScene().getWindow();
        st.close();
    }

    /**
     * Event handler for the "Move Chip" button click.
     * Updates the player's position and enables the buy street button.
     * Closes the chance card window.
     *
     * @param event The mouse event.
     */
    @FXML
    void moveChip(MouseEvent event) {
        GameController.getPpgc().updatePlayerPosition(streetIndex);
        GameController.getPpgc().buyStreetButton.setDisable(false);
        makeEventResolved();
        Stage st = (Stage) chanceText.getScene().getWindow();
        st.close();
    }

    /**
     * Event handler for the "Pay Money" button click.
     * Adds the payment amount to the player's money.
     * Closes the chance card window.
     *
     * @param event The mouse event.
     */
    @FXML
    void payMoney(MouseEvent event) {
        if (payment > 0) {
            player.addMoney(payment);
            makeEventResolved();
            updatePlayerMenu();
            Stage st = (Stage) chanceText.getScene().getWindow();
            st.close();
        } else {
            if (player.getMoney() > (-payment)) {
                player.addMoney(payment);
                makeEventResolved();
                updatePlayerMenu();
                Stage st = (Stage) chanceText.getScene().getWindow();
                st.close();
            } else {
                notEnoughMoney();
            }
        }
    }

    /**
     * Event handler for the "Pay Money for Buildings" button click.
     * Calculates the payment amount based on the player's owned streets and houses.
     * Adds the payment amount to the player's money.
     * Closes the chance card window.
     *
     * @param event The mouse event.
     */
    @FXML
    void payMoneyBuildings(MouseEvent event) {
        final int[] numberOfHouses = {0};
        Arrays.stream(GameController.getPpgc().getBank().getAllStreets())
                .flatMap(Arrays::stream)
                .filter(street -> street.getOwner() == player)
                .forEach(street -> {
                    numberOfHouses[0] += street.getNumberOfHouses();
                    if (street.isHasHotel()) {
                        numberOfHouses[0]++;
                    }
                });
        int playmentForHouses = numberOfHouses[0] * housePayment;
        if (playmentForHouses > 0) {
            player.addMoney(playmentForHouses);
            updatePlayerMenu();
            Stage st = (Stage) chanceText.getScene().getWindow();
            st.close();
        } else {
            if (player.getMoney() > (-playmentForHouses)) {
                player.addMoney(playmentForHouses);
                makeEventResolved();
                updatePlayerMenu();
                Stage st = (Stage) chanceText.getScene().getWindow();
                st.close();
            } else {
                notEnoughMoney();
            }
        }
    }

    /**
     * Displays an alert when the player does not have enough money.
     * Provides options to declare bankruptcy or sell properties.
     */
    private void notEnoughMoney() {
        // Create an alert
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Недостатньо коштів");
        alert.setHeaderText(null);
        alert.setContentText("У вас недостатньо коштів. Продайте будівлі/вулиці або оголосіть себе банкрутом.");

        // Create custom buttons
        ButtonType buttonTypeBankrupt = new ButtonType("Оголосити себе банкрутом");
        ButtonType buttonTypeSellRealty = new ButtonType("Продати майно");

        // Set the buttons on the alert
        alert.getButtonTypes().setAll(buttonTypeBankrupt, buttonTypeSellRealty);

        // Resize alert dialog if necessary
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);

        // Handle the button clicks
        alert.showAndWait().ifPresent(response -> {
            if (response == buttonTypeBankrupt) {
                Bank.takeBankruptPlayerStreets(player);
                PolypolyGameController.setPlayers(Arrays.stream(PolypolyGameController.getPlayers())
                        .filter(element -> !element.getName().equals(player.getName()))
                        .toArray(Player[]::new));
                Alert bankruptcyAlert = new Alert(Alert.AlertType.WARNING);
                bankruptcyAlert.setTitle("Гравець банкрот");
                bankruptcyAlert.setHeaderText(null);
                bankruptcyAlert.setContentText("Гравець " + player.getName() + " оголосив себе банкротом");

                bankruptcyAlert.showAndWait();
                makeEventResolved();
                GameController.getPpgc().endTurn();
                Stage st = (Stage) chanceText.getScene().getWindow();
                st.close();
            } else if (response == buttonTypeSellRealty) {
                // sellProperties
                Stage st = (Stage) chanceText.getScene().getWindow();
                st.close();
            }
        });
    }

    /**
     * Event handler for the "Take Free Jail Card" button click.
     * Acquires a jail free card for the player.
     * Displays an information alert.
     * Closes the chance card window.
     *
     * @param event The mouse event.
     */
    @FXML
    void takeFreeJailCard(MouseEvent event) {
        player.acquireJailFreeCards(1);

        // Create an alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Карта «Вийти з в'язниці безкоштовно»");
        alert.setHeaderText(null);
        alert.setContentText("Ви отримали карту «Вийти з в'язниці безкоштовно».");

        // Show the alert and wait for it to be closed
        alert.showAndWait();

        makeEventResolved();
        updatePlayerMenu();

        Stage st = (Stage) chanceText.getScene().getWindow();
        st.close();
    }

    /**
     * Marks the current event as resolved.
     * Sets the event card present flag to false.
     */
    private void makeEventResolved() {
        GameController.getPpgc().setEventCardPresent(false);
    }

    /**
     * Event handler for the "Use Free Jail Card" button click.
     * Uses a jail free card for the player.
     * Displays an information alert.
     * Closes the chance card window.
     *
     * @param event The mouse event.
     */
    @FXML
    void useFreeJailCard(MouseEvent event) {
        if (player.getJailFreeCards() > 0) {
            player.spendJailFreeCards(1);

            // Create an alert
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Карта «Вийти з в'язниці безкоштовно»");
            alert.setHeaderText(null);
            alert.setContentText("Ви використали карту «Вийти з в'язниці безкоштовно».");

            // Show the alert and wait for it to be closed
            alert.showAndWait();

            makeEventResolved();
            updatePlayerMenu();

            Stage st = (Stage) chanceText.getScene().getWindow();
            st.close();
        } else {
            // Create an alert
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Карта «Вийти з в'язниці безкоштовно»");
            alert.setHeaderText(null);
            alert.setContentText("У вас нема карти «Вийти з в'язниці безкоштовно».");

            // Show the alert and wait for it to be closed
            alert.showAndWait();
        }
    }

    /**
     * Updates the player menu.
     * Sets the current player in the game controller.
     */
    private void updatePlayerMenu() {
        GameController.getPpgc().setPlayerMenu(player);
    }

    private Player player;

    /**
     * Sets the player for the chance card controller.
     *
     * @param player The player object.
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Sets the chance card text.
     *
     * @param chanceText The chance card text.
     */
    public void setText(String chanceText) {
        this.chanceText.setText(chanceText);
    }

    /**
     * Sets the chance type and text for the chance card.
     * Shows the appropriate pane based on the chance type.
     *
     * @param chanceType The chance type.
     * @param chanceText The chance card text.
     */
    public void setChanceTypeAndText(int chanceType, String chanceText) {
        // Hide all panes initially
        paneButtonType0.setVisible(false);
        paneButtonType1.setVisible(false);
        paneButtonType2.setVisible(false);
        paneButtonType3.setVisible(false);
        paneButtonType4.setVisible(false);

        int number = 0;
        if (!chanceText.split("\\|")[0].isEmpty()) {
            number = Integer.parseInt(chanceText.split("\\|")[0]);
        }
        setText(chanceText.split("\\|")[1]);
        // Set the appropriate pane to visible based on chanceType
        switch (chanceType) {
            case 0:
                streetIndex = number;
                paneButtonType0.setVisible(true);
                break;
            case 1:
                payment = number;
                paneButtonType1.setVisible(true);
                break;
            case 2:
                paneButtonType2.setVisible(true);
                break;
            case 3:
                paneButtonType3.setVisible(true);
                break;
            case 4:
                housePayment = number;
                paneButtonType4.setVisible(true);
                break;
            default:
                // Handle invalid chanceType
                throw new IllegalArgumentException("Invalid chance type: " + chanceType);
        }
    }
}
