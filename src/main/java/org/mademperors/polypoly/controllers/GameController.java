package org.mademperors.polypoly.controllers;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import org.mademperors.polypoly.listeners.DiceResultListener;
import org.mademperors.polypoly.models.GameLogger;
import org.mademperors.polypoly.models.Player;
import org.mademperors.polypoly.models.Street;


import java.util.Random;

/**
 * The GameController class is responsible for controlling the game logic of the Polypoly game.
 */
public class GameController {
    private static Player currentPlayer;

    private static final GameLogger logger = GameLogger.getInstance();

    private static final Random rnd = new Random();

    private static PolypolyGameController ppgc;

    private static final Image[] DICES = {
            new Image(String.valueOf(GameController.class.getResource("/assets/dices/dice1.png"))),
            new Image(String.valueOf(GameController.class.getResource("/assets/dices/dice2.png"))),
            new Image(String.valueOf(GameController.class.getResource("/assets/dices/dice3.png"))),
            new Image(String.valueOf(GameController.class.getResource("/assets/dices/dice4.png"))),
            new Image(String.valueOf(GameController.class.getResource("/assets/dices/dice5.png"))),
            new Image(String.valueOf(GameController.class.getResource("/assets/dices/dice6.png")))
    };

    public static final ImageView diceImageView1 = new ImageView(DICES[1]), diceImageView2 = new ImageView(DICES[2]);

    /**
     * Throws the dice and invokes the provided listener with the dice result.
     *
     * @param listener The listener to be invoked with the dice result.
     */
    public static void throwDices(DiceResultListener listener) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.115), e -> {
            diceImageView1.setImage(DICES[rnd.nextInt(0, DICES.length)]);
            diceImageView2.setImage(DICES[rnd.nextInt(0, DICES.length)]);
        }));
        timeline.setCycleCount(15);
        timeline.setOnFinished(e -> {
            int dice1 = extractDiceValue(diceImageView1.getImage());
            int dice2 = extractDiceValue(diceImageView2.getImage());
            logger.logInfo(String.format("%s кинув кубики %d : %d", currentPlayer.getName(), dice1, dice2));
            listener.onDiceResult(dice1, dice2);
        });
        timeline.play();
    }

    /**
     * Buys the specified street for the current player.
     *
     * @param street The street to be bought.
     */
    public static void buyStreet(Street street) {
        currentPlayer.buyStreet(street);
        logger.logInfo(String.format("%s купив %s", currentPlayer.getName(), street.getName()));
    }

    /**
     * Performs mortgage actions on the specified street.
     *
     * @param street The street to perform mortgage actions on.
     */
    public static void mortgageActions(Street street) {
        if (street.isMortgaged()) {
            street.unmortgage();
            logger.logInfo(String.format("%s unmortgaged", street.getName()));
        } else {
            street.mortgage();

            logger.logInfo(String.format("%s mortgaged", street.getName()));
        }
    }

    /**
     * Upgrades the specified street.
     * This method can only be used for monopolies.
     *
     * @param street The street to be upgraded.
     */
    public static void upgradeStreet(Street street) {
        street.upgrade();
        logger.logInfo(String.format("Бюдівлю %s побудовано", street.getName()));
    }

    /**
     * Downgrades the specified street.
     * This method can only be used for monopolies with at least 1 house.
     *
     * @param street The street to be downgraded.
     */
    public static void downgradeStreet(Street street) {
        street.downgrade();
        logger.logInfo(String.format("Будівлю %s знищено", street.getName()));
    }

    /**
     * Sets the current player.
     *
     * @param currentPlayer The current player.
     */
    public static void setCurrentPlayer(Player currentPlayer) {
        GameController.currentPlayer = currentPlayer;
    }

    /**
     * Gets the current player.
     *
     * @return The current player.
     */
    public static Player getCurrentPlayer() {
        return currentPlayer;
    }

    private static int extractDiceValue(Image image) {
        String url = image.getUrl();
        char ch = url.charAt(url.length() - 5);
        return Character.getNumericValue(ch);
    }

    public static PolypolyGameController getPpgc() {
        return ppgc;
    }

    public static void setPpgc(PolypolyGameController ppgc) {
        GameController.ppgc = ppgc;
    }
}