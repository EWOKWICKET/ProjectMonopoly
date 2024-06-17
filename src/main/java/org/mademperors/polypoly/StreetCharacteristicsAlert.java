package org.mademperors.polypoly;


import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import org.mademperors.polypoly.controllers.GameController;
import org.mademperors.polypoly.models.Street;

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
    private Street street;

    public void setPriceForBuildingsLabel(int priceForHouse, int priceForHotel) {
        priceForHouseLabel.setText("Будинок- по " + priceForHouse + " за кожний");
        priceForHotelLabel.setText("Готель- за " + priceForHotel);
    }

    public void setStreetPrices(int[] rentModel) {
        streetPrice.setText(String.valueOf(rentModel[0]));
        monopolyStreetPrice.setText(String.valueOf(rentModel[1]));
        oneHousePrice.setText(String.valueOf(rentModel[2]));
        twoHousePrice.setText(String.valueOf(rentModel[3]));
        threeHousePrice.setText(String.valueOf(rentModel[4]));
        fourHousePrice.setText(String.valueOf(rentModel[5]));
        hotelPrice.setText(String.valueOf(rentModel[6]));
    }

    public void setMortgagedPrice(int mortgagedPrice) {
        mortgagePrice.setText("Залогова ціна: " + mortgagedPrice);
    }

    public void setStreetColor(String color) {
        streetColor.setStyle("-fx-background-color: " + color + "; -fx-border-color:red; -fx-border-width:7;");
    }

    public void setStreetName(String name) {
        streetName.setText(name);
    }

    public void setStreet(Street street) {
        this.street = street;
    }

    public void setToShow(boolean toShow) {
        isToShow = toShow;
        if (isToShow) {
            buttonPanel.setVisible(false);
            buttonPanel.setManaged(false);
            checkButons();
        }
    }

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


    public void init() {
        if (street.isMortgaged()) {
            mortgageLabel.setVisible(true);
            buttonPanel.getChildren().forEach(node -> {
                if (node instanceof javafx.scene.control.Button && !node.equals(mortgageButton)) {
                    node.setDisable(true);
                }
            });
        } else {
            tradeButton.setDisable(false);
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
    }

    @FXML
    public void upgradeStreet(MouseEvent mouseEvent) {
        GameController.downgradeStreet(street);
        init();
    }

    @FXML
    public void downgradeStreet(MouseEvent mouseEvent) {
        int playerMoney = street.getOwner().getMoney();
        int housePrice = street.getHousePrice();
        if (playerMoney >= housePrice) {
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

    public void checkButons() {
        if (street.isMonopoly()) {
            downgradeStreetButton.setDisable(street.getNumberOfHouses() > 0);
            checkUpgradeButton();
        } else {
            upgradeStreetButton.setDisable(true);
            downgradeStreetButton.setDisable(true);
        }
    }

    private void checkUpgradeButton() {
        int ownerMoney = street.getOwner().getMoney();

        if (street.getNumberOfHouses() == 4) {
            upgradeStreetButton.setDisable(ownerMoney <= street.getHotelPrice());
        } else {
            upgradeStreetButton.setDisable(ownerMoney <= street.getHousePrice());
        }
    }

}


