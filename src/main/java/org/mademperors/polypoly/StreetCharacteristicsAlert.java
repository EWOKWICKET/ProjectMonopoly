package org.mademperors.polypoly;


import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import org.mademperors.polypoly.controllers.GameController;
import org.mademperors.polypoly.models.Bank;
import org.mademperors.polypoly.models.Street;

/**
 * This class represents an alert for displaying street characteristics.
 */
public class StreetCharacteristicsAlert {

    public FlowPane buttonPanel;
    public Label mortgageLabel;
    public Button tradeButton;
    public Button mortgageButton;

    @FXML
    public Button upgradeStreetButton, downgradeStreetButton;

    @FXML
    private Label streetPrice, monopolyStreetPrice, oneHousePrice, twoHousePrice, threeHousePrice, fourHousePrice, hotelPrice;

    @FXML
    private Label priceForHotelLabel, priceForHouseLabel;

    @FXML
    private HBox streetColor;

    @FXML
    private Label streetName;

    @FXML
    private Label mortgagePrice;

    private boolean isToShow = false;
    private boolean boughtHouseThisTurn = false;
    private Street street;

    /**
     * Sets the price for buildings label.
     * 
     * @param priceForHouse The price for a house.
     * @param priceForHotel The price for a hotel.
     */
    public void setPriceForBuildingsLabel(int priceForHouse, int priceForHotel) {
        priceForHouseLabel.setText("Будинок- по " + priceForHouse + " за кожний");
        priceForHotelLabel.setText("Готель- за " + priceForHouse);
    }

    /**
     * Sets the street prices.
     * 
     * @param rentModel An array of rent prices.
     */
    public void setStreetPrices(int[] rentModel) {
        streetPrice.setText(String.valueOf(rentModel[0]));
        monopolyStreetPrice.setText(String.valueOf(rentModel[1]));
        oneHousePrice.setText(String.valueOf(rentModel[2]));
        twoHousePrice.setText(String.valueOf(rentModel[3]));
        threeHousePrice.setText(String.valueOf(rentModel[4]));
        fourHousePrice.setText(String.valueOf(rentModel[5]));
        hotelPrice.setText(String.valueOf(rentModel[6]));
    }

    /**
     * Sets the mortgaged price.
     * 
     * @param mortgagedPrice The mortgaged price.
     */
    public void setMortgagedPrice(int mortgagedPrice) {
        mortgagePrice.setText("Залогова ціна: " + mortgagedPrice);
    }

    /**
     * Sets the street color.
     * 
     * @param color The color of the street.
     */
    public void setStreetColor(String color) {
        streetColor.setStyle("-fx-background-color: " + color + "; -fx-border-color:red; -fx-border-width:7;");
    }

    /**
     * Sets the street name.
     * 
     * @param name The name of the street.
     */
    public void setStreetName(String name) {
        streetName.setText(name);
    }

    /**
     * Sets the street.
     * 
     * @param street The street object.
     */
    public void setStreet(Street street) {
        this.street = street;
    }

    /**
     * Sets whether to show the alert.
     * 
     * @param toShow True to show the alert, false otherwise.
     */
    public void setToShow(boolean toShow) {
        isToShow = toShow;
        if (isToShow) {
            buttonPanel.setVisible(false);
            buttonPanel.setManaged(false);
            checkButtons();
        }
    }

    /**
     * Handles the trade event.
     * 
     * @param mouseEvent The mouse event.
     */
    public void trade(MouseEvent mouseEvent) {
        if (street.getNumberOfHouses() == 0) {
            System.out.println("trading");
            init();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ще є будинки");
            alert.setHeaderText(null);
            alert.setContentText("Ви не можете провести цю операцію, бо на вулиці все ще є будинки.");

            alert.showAndWait();
        }
    }

    /**
     * Handles the mortgage event.
     * 
     * @param mouseEvent The mouse event.
     */
    public void mortage(MouseEvent mouseEvent) {
        if (street.getNumberOfHouses() == 0) {
            GameController.mortgageActions(street);
            init();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ще є будинки");
            alert.setHeaderText(null);
            alert.setContentText("Ви не можете провести цю операцію, бо на вулиці все ще є будинки.");

            alert.showAndWait();
        }
    }

    /**
     * Initializes the alert.
     */
    public void init() {
        Bank.checkMonopolyByStreet(street);
        if (street.isMortgaged()) {
            mortgageLabel.setVisible(true);
            buttonPanel.getChildren().forEach(node -> {
                if (node instanceof javafx.scene.control.Button && !node.equals(mortgageButton)) {
                    node.setDisable(true);
                }
            });
        } else {
            if(street.isMonopoly()) {
                //  tradeButton.setDisable(false);
                mortgageLabel.setVisible(false);
                if (street.getNumberOfHouses() == 0) {
                    upgradeStreetButton.setDisable(false);
                    downgradeStreetButton.setDisable(true);
                }
                if (street.getNumberOfHouses() > 0) {
                    upgradeStreetButton.setDisable(false);
                    downgradeStreetButton.setDisable(false);
                }
                if (street.isHasHotel()) {
                    upgradeStreetButton.setDisable(true);
                    downgradeStreetButton.setDisable(false);
                }
            }
            else{
                upgradeStreetButton.setDisable(true);
                downgradeStreetButton.setDisable(true);
            }

        }
    }

    /**
     * Handles the downgrade street event.
     * 
     * @param mouseEvent The mouse event.
     */
    @FXML
    public void downgradeStreet(MouseEvent mouseEvent) {

        GameController.downgradeStreet(street);
        init();
    }

    /**
     * Handles the upgrade street event.
     * 
     * @param mouseEvent The mouse event.
     */
    @FXML
    public void upgradeStreet(MouseEvent mouseEvent) {
        int playerMoney = street.getOwner().getMoney();
        int housePrice = street.getHousePrice();
        if ( playerMoney >= housePrice) {
            GameController.upgradeStreet(street);
            init();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Недостатньо грошей");
            alert.setHeaderText(null);
            alert.setContentText("На жаль, у вас недостатньо грошей для цієї операції.");

            alert.showAndWait();
        }

    }

    /**
     * Checks the buttons.
     */
    public void checkButtons() {
        if (street.isMonopoly()) {
            checkUpgradeButton();
            checkDowngradeButton();
        } else {
            upgradeStreetButton.setDisable(true);
            downgradeStreetButton.setDisable(true);
        }
    }

    /**
     * Checks the downgrade button.
     */
    private void checkDowngradeButton() {
        downgradeStreetButton.setDisable(street.getNumberOfHouses() == 0);
    }

    /**
     * Checks the upgrade button.
     */
    private void checkUpgradeButton() {
        int ownerMoney = street.getOwner().getMoney();

        if (street.getNumberOfHouses() == 4) {
            upgradeStreetButton.setDisable(ownerMoney <= street.getHotelPrice());
        } else {
            upgradeStreetButton.setDisable(ownerMoney <= street.getHousePrice());
        }
    }

    /**
     * Sets whether a house was bought this turn.
     * 
     * @param boughtHouseThisTurn True if a house was bought this turn, false otherwise.
     */
    public void setBoughtHouseThisTurn(boolean boughtHouseThisTurn) {
        this.boughtHouseThisTurn = boughtHouseThisTurn;
        this.upgradeStreetButton.setDisable(boughtHouseThisTurn);
    }
}


