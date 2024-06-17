package org.mademperors.polypoly.controllers;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import org.mademperors.polypoly.listeners.DiceResultListener;
import org.mademperors.polypoly.models.Bank;
import org.mademperors.polypoly.models.GameLogger;
import org.mademperors.polypoly.models.Player;
import org.mademperors.polypoly.models.Street;

import java.util.Arrays;
import java.util.Random;

public class GameController {
    private static Player currentPlayer;

    private static final GameLogger logger = GameLogger.getInstance();

    private static final Random rnd = new Random();

    private static final Image[] DICES = {
            new Image(String.valueOf(GameController.class.getResource("/assets/dices/dice1.png"))),
            new Image(String.valueOf(GameController.class.getResource("/assets/dices/dice2.png"))),
            new Image(String.valueOf(GameController.class.getResource("/assets/dices/dice3.png"))),
            new Image(String.valueOf(GameController.class.getResource("/assets/dices/dice4.png"))),
            new Image(String.valueOf(GameController.class.getResource("/assets/dices/dice5.png"))),
            new Image(String.valueOf(GameController.class.getResource("/assets/dices/dice6.png")))
    };

    public static final ImageView diceImageView1 = new ImageView(DICES[1]), diceImageView2 = new ImageView(DICES[2]);

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

            /* makes dices to disappear 5 seconds later */
//            PauseTransition pause = new PauseTransition(Duration.seconds(5));
//            pause.setOnFinished(ev -> {
//                diceImageView1.setVisible(false);
//                diceImageView2.setVisible(false);
//            });
//            pause.play();
        });
        timeline.play();
    }

    public static void buyStreet(Street street) {
        currentPlayer.buyStreet(street);
        logger.logInfo(String.format("%s купив %s", currentPlayer.getName(), street.getName()));
    }

    public static void trade(int p1Money, Street[] p1Streets, int p1JailFreeCardsAmount, Player p2, int p2Money, Street[] p2Streets, int p2JailFreeCardsAmount) {
        //to player 1(currentPlayer)
        currentPlayer.addMoney(p2Money);
        currentPlayer.acquireJailFreeCards(p2JailFreeCardsAmount);
        p2.spendJailFreeCards(p2JailFreeCardsAmount);
        Arrays.stream(p2Streets).forEach(street -> street.tradedTo(currentPlayer));

        //logging
        StringBuilder info1 = new StringBuilder(String.format("%s got %s", currentPlayer.getName(), Arrays.toString(p2Streets)));
        if (p2JailFreeCardsAmount > 0) info1.append(String.format(", %d jail free cards", p2JailFreeCardsAmount));
        info1.append(String.format(" and %d$", p2Money));
        logger.logInfo(info1.toString());

        //to player 2
        p2.addMoney(p1Money);
        p2.acquireJailFreeCards(p1JailFreeCardsAmount);
        currentPlayer.spendJailFreeCards(p1JailFreeCardsAmount);
        Arrays.stream(p1Streets).forEach(street -> street.tradedTo(p2));

        //logging
        StringBuilder info2 = new StringBuilder(String.format("%s got %s", p2.getName(), Arrays.toString(p1Streets)));
        if (p1JailFreeCardsAmount > 0) info2.append(String.format(", %d jail free cards", p1JailFreeCardsAmount));
        info2.append(String.format(" and %d$", p1Money));
        logger.logInfo(info2.toString());
    }

    public static void mortgageActions(Street street) {
        if (street.isMortgaged()) {
            street.unmortgage();
            logger.logInfo(String.format("%s unmortgaged", street.getName()));
        } else {
            street.mortgage();

            logger.logInfo(String.format("%s mortgaged", street.getName()));
        }
    }

    //LIMIT so that it can be used only for monopolies
    public static void upgradeStreet(Street street) {
        street.upgrade();
        logger.logInfo(String.format("%s upgraded", street.getName()));
    }

    //LIMIT so that it can be used only for monopolies with 1 house minimum
    public static void downgradeStreet(Street street) {
        street.downgrade();
        logger.logInfo(String.format("%s downgraded", street.getName()));
    }

    public static void setCurrentPlayer(Player currentPlayer) {
        GameController.currentPlayer = currentPlayer;
    }

    public static Player getCurrentPlayer() {
        return currentPlayer;
    }

    private static int extractDiceValue(Image image) {
        String url = image.getUrl();
        char ch = url.charAt(url.length() - 5);
        return Character.getNumericValue(ch);
    }
}