package org.mademperors.polypoly.controllers;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import org.mademperors.polypoly.models.DiceResultListener;
import org.mademperors.polypoly.models.Player;
import org.mademperors.polypoly.models.Street;

import java.util.Arrays;
import java.util.Random;

public class GameController {

    private static final Random rnd = new Random();

    private static final Image[] DICES = {
            new Image(String.valueOf(GameController.class.getResource("/assets/dices/dice1.jpg"))),
            new Image(String.valueOf(GameController.class.getResource("/assets/dices/dice2.jpg"))),
            new Image(String.valueOf(GameController.class.getResource("/assets/dices/dice3.jpg"))),
            new Image(String.valueOf(GameController.class.getResource("/assets/dices/dice4.jpg"))),
            new Image(String.valueOf(GameController.class.getResource("/assets/dices/dice5.jpg"))),
            new Image(String.valueOf(GameController.class.getResource("/assets/dices/dice6.jpg")))
    };

    public static final ImageView diceImageView1 = new ImageView(DICES[1]);
    public static final ImageView diceImageView2 = new ImageView(DICES[2]);

    public static void throwDices(DiceResultListener listener) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.115), e -> {
            diceImageView1.setImage(DICES[rnd.nextInt(0, DICES.length)]);
            diceImageView2.setImage(DICES[rnd.nextInt(0, DICES.length)]);
        }));
        timeline.setCycleCount(20);
        timeline.setOnFinished(e -> {
            int result = extractDiceValue(diceImageView1.getImage()) + extractDiceValue(diceImageView2.getImage());
            listener.onDiceResult(result);
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

    public static void trade(Player p1, int p1Money, Street[] p1Streets, int p1JailFreeCardsAmount, Player p2, int p2Money, Street[] p2Streets, int p2JailFreeCardsAmount) {
        //to player 1
        p1.addMoney(p2Money);
        p1.acquireJailFreeCards(p2JailFreeCardsAmount);
        p2.spendJailFreeCards(p2JailFreeCardsAmount);
        Arrays.stream(p2Streets).forEach(street -> street.tradedTo(p1));

        //to player 2
        p2.addMoney(p1Money);
        p2.acquireJailFreeCards(p1JailFreeCardsAmount);
        p1.spendJailFreeCards(p1JailFreeCardsAmount);
        Arrays.stream(p1Streets).forEach(street -> street.tradedTo(p2));
    }

    public void mortgageActions(Street street) {
        if (street.isMortgaged()) {
            street.unmortgage();
        } else {
            street.mortgage();
        }
    }

    //LIMIT so that it can be used only for monopolies
    public void upgradeStreet(Street street) {
        street.upgrade();
    }

    //LIMIT so that it can be used only for monopolies with 1 house minimum
    public void downgradeStreet(Street street) {
        street.downgrade();
    }

    private static int extractDiceValue(Image image) {
        String url = image.getUrl();
        char ch = url.charAt(url.length() - 5);
        return Character.getNumericValue(ch);
    }
}