package org.mademperors.polypoly.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class PolypolyFieldController {

    @FXML
    private BorderPane dice1;

    @FXML
    private BorderPane dice2;

    @FXML
    private BorderPane eventCard;

    @FXML
    private TextArea eventCardTextArea;

    @FXML
    private StackPane field1;

    @FXML
    private StackPane field10;

    @FXML
    private StackPane field11;

    @FXML
    private BorderPane field111;

    @FXML
    private BorderPane field1111;

    @FXML
    private StackPane field12;

    @FXML
    private StackPane field13;

    @FXML
    private StackPane field14;

    @FXML
    private StackPane field15;

    @FXML
    private StackPane field16;

    @FXML
    private StackPane field17;

    @FXML
    private StackPane field18;

    @FXML
    private StackPane field19;

    @FXML
    private StackPane field2;

    @FXML
    private StackPane field20;

    @FXML
    private StackPane field21;

    @FXML
    private StackPane field22;

    @FXML
    private StackPane field23;

    @FXML
    private StackPane field24;

    @FXML
    private StackPane field25;

    @FXML
    private StackPane field26;

    @FXML
    private StackPane field27;

    @FXML
    private StackPane field28;

    @FXML
    private StackPane field29;

    @FXML
    private StackPane field3;

    @FXML
    private StackPane field30;

    @FXML
    private StackPane field31;

    @FXML
    private StackPane field32;

    @FXML
    private StackPane field33;

    @FXML
    private StackPane field34;

    @FXML
    private StackPane field35;

    @FXML
    private StackPane field36;

    @FXML
    private StackPane field37;

    @FXML
    private StackPane field38;

    @FXML
    private StackPane field39;

    @FXML
    private StackPane field4;

    @FXML
    private StackPane field40;

    @FXML
    private StackPane field5;

    @FXML
    private StackPane field6;

    @FXML
    private StackPane field7;

    @FXML
    private StackPane field8;

    @FXML
    private StackPane field9;

    @FXML
    private TextArea statisticsTextArea;

    // Method to return an array of all StackPanes
    public StackPane[] getAllStackPanes() {
        return new StackPane[] {
                field1, field2, field3, field4, field5, field6, field7, field8, field9, field10,
                field11, field12, field13, field14, field15, field16, field17, field18, field19, field20,
                field21, field22, field23, field24, field25, field26, field27, field28, field29, field30,
                field31, field32, field33, field34, field35, field36, field37, field38, field39, field40
        };
    }
}
