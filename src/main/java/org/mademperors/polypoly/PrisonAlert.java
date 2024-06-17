package org.mademperors.polypoly;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

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

    public void setJailTurnsText(int jailTurns) {
        jailTurnsText.setText("Залишилось ходів: " + jailTurns);
    }

    public void setCloseAlert(Runnable closeAlert) {
        this.closeAlert = closeAlert;
    }

    public void setPayBail(Runnable payBail) {
        this.payBail = payBail;
    }

    public void setFreeExit(Runnable freeExit) {
        this.freeExit = freeExit;
    }

    public void setRollDice(Runnable rollDice) {
        this.rollDice = rollDice;
    }

    public void closeAlert() {
        closeAlert.run();
    }

    public void payBail() {
        payBail.run();
    }

    public void freeExit() {
        freeExit.run();
    }

    public void rollDice() {
        rollDice.run();
    }

    public void setJailFreeCards(int jailFreeCards) {
        this.jailFreeCards = jailFreeCards;
        this.freeExitButton.setDisable(jailFreeCards == 0);
        this.freeExitButton.setText("Вийти з в'язниці безкоштовно (" + jailFreeCards + " залишилось)");
    }

    public void setPlayerMoney(int playerMoney) {
        this.playerMoney = playerMoney;
        this.payBailButton.setDisable(playerMoney < 50);
    }
}
