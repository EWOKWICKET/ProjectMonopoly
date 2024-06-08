package org.mademperors.polypoly.controllers;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import org.mademperors.polypoly.models.Player;
import org.mademperors.polypoly.models.Street;

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

    public static void throwDices() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.115), e -> {
            diceImageView1.setImage(DICES[rnd.nextInt(0, DICES.length)]);
            diceImageView2.setImage(DICES[rnd.nextInt(0, DICES.length)]);
        }));
        timeline.setCycleCount(20);
        timeline.setOnFinished(e -> {
            int dice1 = extractDiceValue(diceImageView1.getImage());
            int dice2 = extractDiceValue(diceImageView2.getImage());

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


    private static int extractDiceValue(Image image) {
        String url = image.getUrl();
        char ch = url.charAt(url.length() - 5);
        return Character.getNumericValue(ch);
    }

    public static void trade(Player p1, int p1Money, Street[] p1Streets, Player p2, int p2Money, Street[] p2Streets) {
        //to player 1
        p1.setMoney(p1.getMoney() + p2Money);
        for (Street p2Street : p2Streets) {
            p2Street.tradedTo(p1);
        }

        //to player 2
        p2.setMoney(p2.getMoney() + p1Money);
        for (Street p1Street : p1Streets) {
            p1Street.tradedTo(p1);
        }
    }
}