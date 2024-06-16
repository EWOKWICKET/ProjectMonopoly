package org.mademperors.polypoly.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class PolypolyFieldController {

    @FXML
    private BorderPane dice1, dice2;

    @FXML
    private BorderPane eventCard;

    @FXML
    private TextArea eventCardTextArea;

    @FXML
    private BorderPane field111, field1111;

    @FXML private StackPane field1, field2, field3, field4, field5, field6, field7, field8, field9, field10,
            field11, field12, field13, field14, field15, field16, field17, field18, field19, field20,
            field21, field22, field23, field24, field25, field26, field27, field28, field29, field30,
            field31, field32, field33, field34, field35, field36, field37, field38, field39, field40;

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
