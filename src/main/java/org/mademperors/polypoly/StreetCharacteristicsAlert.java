package org.mademperors.polypoly;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.mademperors.polypoly.controllers.GameController;
import org.mademperors.polypoly.models.Street;

import java.net.URL;
import java.util.ResourceBundle;

public class StreetCharacteristicsAlert {

        public FlowPane buttonPanel;
        public Label mortgageLabel;
        public Button addBuidingButton;
        public Button tradeButton;
        public Button delBuildingButton;
        public Button mortgageButton;
        @FXML
        private Label fourHousePrice;

        @FXML
        private Label hotelPrice;

        @FXML
        private Label oneHousePrice;

        @FXML
        private Label onlyStreetPrice;

        @FXML
        private Label priceForHotelLabel;

        @FXML
        private Label priceForHouseLabel;

        @FXML
        private HBox streetColor;

        @FXML
        private Label streetName;

        @FXML
        private Label threeHousePrice;

        @FXML
        private Label twoHousePrice;

        @FXML
        private Label mortgagePrice;

        private boolean isToShow=false;
        private Street street;

        public void setFourHousePrice(int fourHouse) {
                fourHousePrice.setText(String.valueOf(fourHouse));
        }

        public void setHotelPrice(int hotelPric) {
                hotelPrice.setText(String.valueOf(hotelPric));
        }

        public void setOneHousePrice(int oneHouse) {
                oneHousePrice.setText(String.valueOf(oneHouse));
        }

        public void setOnlyStreetPrice(int onlyStreet) {
                onlyStreetPrice.setText(Integer.toString(onlyStreet));
        }

        public void setPriceForHotelLabel(int priceForHotel) {
                priceForHotelLabel.setText("Готель- за "+priceForHotel);
        }

        public void setPriceForHouseLabel(int priceForHouse) {
                priceForHouseLabel.setText("Будинок- по "+priceForHouse+" за кожний");
        }

        public void setStreetColor(String color) {
                streetColor.setStyle("-fx-background-color: "+color+"; -fx-border-color:red; -fx-border-width:7;");
        }

        public void setStreetName(String name) {
                streetName.setText(name);
        }

        public void setThreeHousePrice(int threeHouse) {
                threeHousePrice.setText(String.valueOf(threeHouse));
        }

        public void setTwoHousePrice(int twoHousee) {
                twoHousePrice.setText(String.valueOf(twoHousee));
        }

        public void setMortgagePrice(int mortgage) {
                mortgagePrice.setText("Залогова ціна: "+String.valueOf(mortgage));
        }

        public void setStreet(Street street) {
                this.street = street;
        }

        public void setToShow(boolean toShow) {
                isToShow = toShow;
                if(isToShow){
                        buttonPanel.setVisible(false);
                        buttonPanel.setManaged(false);
                }
        }

        public void trade(MouseEvent mouseEvent) {
                if(street.getNumberOfHouses()==0){
                        System.out.println("trading");
                        init();}
                else{
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Ще є будинки");
                        alert.setHeaderText(null);
                        alert.setContentText("Ви не можете провести цю операцію, бо на вулиці все ще є будинки.");

                        alert.showAndWait();
                }
        }


        public void mortage(MouseEvent mouseEvent) {
                if(street.getNumberOfHouses()==0){
                GameController.mortgageActions(street);
                init();
                }
                else{
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Ще є будинки");
                        alert.setHeaderText(null);
                        alert.setContentText("Ви не можете провести цю операцію, бо на вулиці все ще є будинки.");

                        alert.showAndWait();
                }
        }



        public void init() {
                if(street.isMortgaged()){
                        mortgageLabel.setVisible(true);
                        buttonPanel.getChildren().forEach(node -> {
                                if (node instanceof javafx.scene.control.Button && !node.equals(mortgageButton)) {
                                        node.setDisable(true);
                                }
                        });
                }else{
                        tradeButton.setDisable(false);
                        mortgageLabel.setVisible(false);
                        if(street.getNumberOfHouses()==0) {
                                addBuidingButton.setDisable(false);
                                delBuildingButton.setDisable(true);
                        }
                        if(street.getNumberOfHouses()>0)   {
                                addBuidingButton.setDisable(false);
                                delBuildingButton.setDisable(false);
                        }
                        if(street.isHasHotel())   {
                                addBuidingButton.setDisable(true);
                                delBuildingButton.setDisable(false);
                        }

                }
        }

        public void delBuilding(MouseEvent mouseEvent) {
                        GameController.downgradeStreet(street);
                        init();
        }

        public void addBuilding(MouseEvent mouseEvent) {
                int playerMoney=street.getOwner().getMoney();
                int housePrice=street.getHousePrice();
                if(playerMoney>=housePrice){
                        GameController.upgradeStreet(street);
                        init();
                }
                else{
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Недостатньо грошей");
                        alert.setHeaderText(null);
                        alert.setContentText("На жаль, у вас недостатньо грошей для цієї операції.");

                        alert.showAndWait();
                }

        }
}


