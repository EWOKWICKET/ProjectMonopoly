package org.mademperors.polypoly.controllers.serviceCardsControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import org.mademperors.polypoly.models.Player;

/**
 * The controller class for the City Card in the PolyPoly game.
 */
public class CityCardController {

    /**
     * The label to display the city text.
     */
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
        // TODO: Implement the logic for moving the chip and paying money for an exception.
    }

    @FXML
    void payGetMoney(MouseEvent event) {
        // TODO: Implement the logic for paying and getting money.
    }

    @FXML
    void takeFreeJailCard(MouseEvent event) {
        // TODO: Implement the logic for taking a free jail card.
    }

    private Player player;

    /**
     * Sets the player for the city card controller.
     *
     * @param player The player to set.
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Sets the text for the city label.
     *
     * @param cityText The text to set.
     */
    public void setText(String cityText) {
        this.cityText.setText(cityText);
    }

    /**
     * Sets the city type and updates the visibility of the panes accordingly.
     *
     * @param cityType The type of the city.
     *                 - 0: Type 0 city
     *                 - 1 or 2: Type 1 or 2 city
     *                 - 3: Type 3 city
     * @throws IllegalArgumentException if an invalid city type is provided.
     */
    public void setCityType(int cityType) {
        // Hide all panes initially
        paneButtonType0.setVisible(false);
        paneButtonType12.setVisible(false);
        paneButtonType3.setVisible(false);

        // Set the appropriate pane to visible based on cityType
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
                // Handle invalid cityType
                throw new IllegalArgumentException("Invalid city type: " + cityType);
        }
    }
}
