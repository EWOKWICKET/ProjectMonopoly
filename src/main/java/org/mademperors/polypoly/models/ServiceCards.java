package org.mademperors.polypoly.models;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.mademperors.polypoly.controllers.serviceCardsControllers.CityCardController;
import org.mademperors.polypoly.controllers.serviceCardsControllers.ChanceCardController;


import java.io.IOException;
import java.net.URL;
import java.util.Random;

public class ServiceCards {
    private static Random rgen=new Random();

    private final static String[][] chanceCards = {
            {
                    "19|Перейдіть на Андріївський спуск",
                    "1|Перейдіть на старт."
            },
            {
                    "-40|Заплатіть 40 за візит до лікаря",
                    "500|Бабуся померла і залишила вам спадок у розмірі 500",
                    "100|Ваша ощадна книжка виграла! Отримайте 100.",
                    "50|Ви успішно провели ділові переговори. Отримайте $50.",
                    "200|Ви звільнені від сплати податків. Економте $200.",
                    "10|Вас запросили на вечірку. Отримайте $10.",
                    "-150|Відремонтуйте дах вашого будинку. Сплатіть $150.",
                    "-75|Вас оштрафували за перевищення швидкості. Сплатіть $75.",
                    "-50|Ви застрягли в пробці. Сплатіть $50."
            },
            {
                    "|Ви йдете до в'язниці. Не отримуєте зарплату, поки не вийдете.",
                    "|Йдіть до в'язниці"

            },
            {
                "|Отримайте карту «Вийти з в'язниці безкоштовно»"
            },
            {
                    "-120|Заплатіть за ремонт будівель 120 за кожну",
                    "-100|Сплатіть податок на нерухомість. 100 за кожен будинок і готель, яким володієте.",
                    "10|Цього тижня було багато туристів. Отримайте 10 за кожну будівлю"
            }
    };
    private final static String[][] cityCards = {
            {

                    "перейдіть і заплатіть на Андріївський спуск"
            },
            {
                " отримайте 500"
            },
            {
                    "екскурсія на пл. Слави"
            }

    };

    public static void showChance(BorderPane place, Player player){
        /* chanceType:
        * 0-move to specified street
        * 1-monetary transactions
        * 2-go to jail
        * 3- free from jail card
        * 4- repairs/ expences (pay for all your houses $25)*/

        int chanceType=rgen.nextInt(0,5);
        String chanceText=chanceCards[chanceType][rgen.nextInt(0,chanceCards[chanceType].length)];


        //set text on gaming field

        // Create the first Label
        Label label1 = new Label("Шанс");
        label1.setPrefHeight(34.0);
        label1.setPrefWidth(596.0);
        label1.setFont(new Font(30.0));
        label1.setStyle("-fx-alignment: center;");


        // Create the second Label
        Label label2 = new Label(chanceText.split("\\|")[1]);
        label2.setPrefHeight(109.0);
        label2.setPrefWidth(602.0);
        label2.setWrapText(true);
        label2.setFont(new Font(10.0));

//        // Create a VBox to hold the Labels
//        VBox vbox = new VBox();
//        vbox.setPadding(new Insets(10));
//        vbox.getChildren().addAll(label1, label2);
        place.setPadding(new Insets(10));
        place.setCenter(label2);
        place.setTop(label1);
        int finalChanceType = chanceType;
        place.setOnMouseClicked(mouseEvent -> {
            URL resource = ServiceCards.class.getResource("/org/mademperors/polypoly/chanceCard.fxml");
            if (resource == null) {
                throw new RuntimeException("FXML file not found");
            }

            FXMLLoader loader = new FXMLLoader(resource);
            try {
                Parent root = loader.load();
                Stage dialogStage = new Stage();
                dialogStage.initOwner(place.getScene().getWindow());
                dialogStage.initModality(Modality.APPLICATION_MODAL); // Block input events to other windows

                dialogStage.setScene(new Scene(root));
                dialogStage.setTitle("Шанс");
                ChanceCardController ccc=loader.getController();
                ccc.setPlayer(player);
                ccc.setChanceTypeAndText(finalChanceType,chanceText );

                dialogStage.showAndWait();


            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        });


    }
    public static void showCity(BorderPane place, Player player){
        /* chanceType:
         * 0-move on excursion to specified street and pay money
         * 1-monetary transactions
         * 2-have excursion and pay money
         * 3- free from jail card*/

        int cityType =rgen.nextInt(0,5);
        cityType=2;
        String cityText=cityCards[cityType][rgen.nextInt(0,cityCards[cityType].length)];


        //set text on gaming field

        // Create the first Label
        Label label1 = new Label("Місто");
        label1.setPrefHeight(34.0);
        label1.setPrefWidth(596.0);
        label1.setFont(new Font(30.0));
        label1.setStyle("-fx-alignment: center;");

        // Create the second Label
        Label label2 = new Label(cityText);
        label2.setPrefHeight(109.0);
        label2.setPrefWidth(602.0);
        label2.setWrapText(true);
        label2.setFont(new Font(15.0));

//        // Create a VBox to hold the Labels
//        VBox vbox = new VBox();
//        vbox.setPadding(new Insets(10));
//        vbox.getChildren().addAll(label1, label2);
        place.setPadding(new Insets(10));
        place.setCenter(label2);
        place.setTop(label1);
        int finalChanceType = cityType;
        place.setOnMouseClicked(mouseEvent -> {
            URL resource = ServiceCards.class.getResource("/org/mademperors/polypoly/CityCard.fxml");
            if (resource == null) {
                throw new RuntimeException("FXML file not found");
            }

            FXMLLoader loader = new FXMLLoader(resource);
            try {
                Parent root = loader.load();
                Stage dialogStage = new Stage();
                dialogStage.initOwner(place.getScene().getWindow());
                dialogStage.initModality(Modality.APPLICATION_MODAL); // Block input events to other windows

                dialogStage.setScene(new Scene(root));
                dialogStage.setTitle("Місто");
                CityCardController ccc=loader.getController();
                ccc.setPlayer(player);
                ccc.setCityType(finalChanceType);
                ccc.setText(cityText);
                dialogStage.showAndWait();


            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        });
    }

}
