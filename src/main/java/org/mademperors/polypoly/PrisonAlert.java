package org.mademperors.polypoly;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

/**
 * The PrisonAlert class represents an alert window for the prison in a game.
 * It provides methods to set the text, buttons, and actions of the alert window.
 */
public class PrisonAlert {
    @FXML
    public Text jailTurnsText;
    @FXML
    public Button payBailButton;
    @FXML
    public Button freeExitButton;
    @FXML
    public Button rollDiceButton;
    private Runnable closeAlert;
    private Runnable payBail;
    private Runnable freeExit;
    private Runnable rollDice;
    private int jailFreeCards = 0;
    private int playerMoney = 0;

    /**
     * Sets the text for the jail turns.
     * @param jailTurns the number of jail turns remaining
     */
    public void setJailTurnsText(int jailTurns) {
        jailTurnsText.setText("Залишилось ходів: " + jailTurns);
    }

    /**
     * Sets the action to be performed when the alert window is closed.
     * @param closeAlert the action to be performed
     */
    public void setCloseAlert(Runnable closeAlert) {
        this.closeAlert = closeAlert;
    }

    /**
     * Sets the action to be performed when the pay bail button is clicked.
     * @param payBail the action to be performed
     */
    public void setPayBail(Runnable payBail) {
        this.payBail = payBail;
    }

    /**
     * Sets the action to be performed when the free exit button is clicked.
     * @param freeExit the action to be performed
     */
    public void setFreeExit(Runnable freeExit) {
        this.freeExit = freeExit;
    }

    /**
     * Sets the action to be performed when the roll dice button is clicked.
     * @param rollDice the action to be performed
     */
    public void setRollDice(Runnable rollDice) {
        this.rollDice = rollDice;
    }

    /**
     * Closes the alert window by running the closeAlert action.
     */
    public void closeAlert() {
        closeAlert.run();
    }

    /**
     * Pays the bail by running the payBail action.
     */
    public void payBail() {
        payBail.run();
    }

    /**
     * Exits the prison for free by running the freeExit action.
     */
    public void freeExit() {
        freeExit.run();
    }

    /**
     * Rolls the dice by running the rollDice action.
     */
    public void rollDice() {
        rollDice.run();
    }

    /**
     * Sets the number of jail free cards and updates the free exit button accordingly.
     * @param jailFreeCards the number of jail free cards
     */
    public void setJailFreeCards(int jailFreeCards) {
        this.jailFreeCards = jailFreeCards;
        this.freeExitButton.setDisable(jailFreeCards == 0);
        this.freeExitButton.setText("Вийти з в'язниці безкоштовно (" + jailFreeCards + " залишилось)");
    }

    /**
     * Sets the player's money and updates the pay bail button accordingly.
     * @param playerMoney the player's money
     */
    public void setPlayerMoney(int playerMoney) {
        this.playerMoney = playerMoney;
        this.payBailButton.setDisable(playerMoney < 50);
    }
}
