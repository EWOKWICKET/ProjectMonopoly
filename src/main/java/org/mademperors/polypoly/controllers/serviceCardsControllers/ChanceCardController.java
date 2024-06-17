package org.mademperors.polypoly.controllers.serviceCardsControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import org.mademperors.polypoly.models.Player;

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

    @FXML
    void goToJail(MouseEvent event) {

    }

    @FXML
    void moveChip(MouseEvent event) {

    }

    @FXML
    void payMoney(MouseEvent event) {

    }

    @FXML
    void payMoneyBuildings(MouseEvent event) {

    }

    @FXML
    void takeFreeJailCard(MouseEvent event) {

    }

    @FXML
    void useFreeJailCard(MouseEvent event) {

    }
    private Player player;

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setText(String chanceText) {
        this.chanceText.setText(chanceText);
    }

    public void setChanceType(int chanceType) {
        // Hide all panes initially
        paneButtonType0.setVisible(false);
        paneButtonType1.setVisible(false);
        paneButtonType2.setVisible(false);
        paneButtonType3.setVisible(false);
        paneButtonType4.setVisible(false);

        // Set the appropriate pane to visible based on chanceType
        switch (chanceType) {
            case 0:
                paneButtonType0.setVisible(true);
                break;
            case 1:
                paneButtonType1.setVisible(true);
                break;
            case 2:
                paneButtonType2.setVisible(true);
                break;
            case 3:
                paneButtonType3.setVisible(true);
                break;
            case 4:
                paneButtonType4.setVisible(true);
                break;
            default:
                // Handle invalid chanceType
                throw new IllegalArgumentException("Invalid chance type: " + chanceType);
        }
    }
}
