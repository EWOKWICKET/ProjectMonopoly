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

public class ChanceCardController {

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

    @FXML
    void goToJail(MouseEvent event) {
        GameController.getPpgc().goToJail();
        makeEventResolved();
        Stage st = (Stage) chanceText.getScene().getWindow();
        st.close();
    }

    @FXML
    void moveChip(MouseEvent event) {
        GameController.getPpgc().updatePlayerPosition(streetIndex);
        makeEventResolved();
        Stage st = (Stage) chanceText.getScene().getWindow();
        st.close();
    }

    @FXML
    void payMoney(MouseEvent event) {
        if(payment>0){
            player.addMoney(payment);
            makeEventResolved();
            updatePlayerMenu();
            Stage st = (Stage) chanceText.getScene().getWindow();
            st.close();
        }
        else{
            if(player.getMoney()>(-payment)){
                player.addMoney(payment);
                makeEventResolved();
                updatePlayerMenu();
                Stage st = (Stage) chanceText.getScene().getWindow();
                st.close();
            }
            else {
                notEnoughMoney();
            }
        }
    }

    @FXML
    void payMoneyBuildings(MouseEvent event) {
        final int[] numberOfHouses = {0};
        Arrays.stream(GameController.getPpgc().getBank().getAllStreets())
                .flatMap(Arrays::stream)
                .filter(street -> street.getOwner()==player)
                .forEach(street -> {
                    numberOfHouses[0] +=street.getNumberOfHouses();
                    if(street.isHasHotel()){
                        numberOfHouses[0]++;
                    }
                });
        int playmentForHouses=numberOfHouses[0]*housePayment;
        if(playmentForHouses>0){
            player.addMoney(playmentForHouses);
            updatePlayerMenu();
            Stage st = (Stage) chanceText.getScene().getWindow();
            st.close();
        }
        else{
            if(player.getMoney()>(-playmentForHouses)){
                player.addMoney(playmentForHouses);
                makeEventResolved();
                updatePlayerMenu();
                Stage st = (Stage) chanceText.getScene().getWindow();
                st.close();
            }
            else {
                notEnoughMoney();
            }
        }
       
    }

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
                PolypolyGameController.setPlayers(Arrays.stream( PolypolyGameController.getPlayers())
                        .filter(element -> !element.getName().equals(player.getName()))
                        .toArray(Player[]::new));
                Alert bankruptcyAlert = new Alert(Alert.AlertType.WARNING);
                bankruptcyAlert.setTitle("Гравець банкрот");
                bankruptcyAlert.setHeaderText(null);
                bankruptcyAlert.setContentText("Гравець "+player.getName()+" оголосив себе банкротом");

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

    private void makeEventResolved() {
        GameController.getPpgc().setEventCardPresent(false);
    }

    @FXML
    void useFreeJailCard(MouseEvent event) {
        if(player.getJailFreeCards()>0) {
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
        }
        else {
            // Create an alert
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Карта «Вийти з в'язниці безкоштовно»");
            alert.setHeaderText(null);
            alert.setContentText("У вас нема карти «Вийти з в'язниці безкоштовно».");

            // Show the alert and wait for it to be closed
            alert.showAndWait();

        }
    }

    private void updatePlayerMenu() {
        GameController.getPpgc().setPlayerMenu(player);
    }

    private Player player;

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setText(String chanceText) {
        this.chanceText.setText(chanceText);
    }
    public void setChanceTypeAndText(int chanceType, String chanceText) {
        // Hide all panes initially
        paneButtonType0.setVisible(false);
        paneButtonType1.setVisible(false);
        paneButtonType2.setVisible(false);
        paneButtonType3.setVisible(false);
        paneButtonType4.setVisible(false);

        int number=0;
        if(!chanceText.split("\\|")[0].isEmpty()) {
            number = Integer.parseInt(chanceText.split("\\|")[0]);
        }
        setText(chanceText.split("\\|")[1]);
        // Set the appropriate pane to visible based on chanceType
        switch (chanceType) {
            case 0:
                streetIndex=number;
                paneButtonType0.setVisible(true);
                break;
            case 1:
                payment=number;
                paneButtonType1.setVisible(true);
                break;
            case 2:
                paneButtonType2.setVisible(true);
                break;
            case 3:
                paneButtonType3.setVisible(true);
                break;
            case 4:
                housePayment=number;
                paneButtonType4.setVisible(true);
                break;
            default:
                // Handle invalid chanceType
                throw new IllegalArgumentException("Invalid chance type: " + chanceType);
        }
    }
}
