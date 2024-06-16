package org.mademperors.polypoly;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class StreetCharacteristicsAlert {

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
                priceForHouseLabel.setText("Будинок- по"+priceForHouse+" за кожний");
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
                mortgagePrice.setText(String.valueOf(mortgage));
        }
}


