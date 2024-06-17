package org.mademperors.polypoly.controllers.serviceCardsControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import org.mademperors.polypoly.models.Player;

public class CityCardController {

    public Label cityText;
    @FXML
    private Button moveChip;

    @FXML
    private FlowPane paneButtonType0;

    @FXML
    private FlowPane paneButtonType12;

    @FXML
    private FlowPane paneButtonType3;

    @FXML
    void moveChipAndPayMoneyForExc(MouseEvent event) {

    }

    @FXML
    void payGetMoney(MouseEvent event) {

    }

    @FXML
    void takeFreeJailCard(MouseEvent event) {

    }
    private Player player;

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setText(String cityText) {
        this.cityText.setText(cityText);
    }
    public void setCityType(int cityType) {
        // Hide all panes initially
        paneButtonType0.setVisible(false);
        paneButtonType12.setVisible(false);
        paneButtonType3.setVisible(false);

        // Set the appropriate pane to visible based on chanceType
        switch (cityType) {
            case 0:
                paneButtonType0.setVisible(true);
                break;
            case 1, 2:
                paneButtonType12.setVisible(true);
                break;
            case 3:
                paneButtonType3.setVisible(true);
                break;

            default:
                // Handle invalid chanceType
                throw new IllegalArgumentException("Invalid chance type: " + cityType);
        }
    }
}
