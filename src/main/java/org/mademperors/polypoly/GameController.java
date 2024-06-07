package org.mademperors.polypoly;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.net.URL;
import java.util.Objects;
import java.util.Random;

public class GameController {

    private static final Random rnd = new Random();

    private static final Image[] DICES = {
            new Image(String.valueOf(GameController.class.getResource("/assets/dices/dice1.jpg"))),
            new Image(String.valueOf(GameController.class.getResource("/assets/Dices/dice2.jpg"))),
            new Image(String.valueOf(GameController.class.getResource("/assets/Dices/dice3.jpg"))),
            new Image(String.valueOf(GameController.class.getResource("/assets/Dices/dice4.jpg"))),
            new Image(String.valueOf(GameController.class.getResource("/assets/Dices/dice5.jpg"))),
            new Image(String.valueOf(GameController.class.getResource("/assets/Dices/dice6.jpg")))
    };

    private static final ImageView diceImageView1 = new ImageView();
    private static final ImageView diceImageView2 = new ImageView();

    public static void throwDices() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.2), e -> {
            diceImageView1.setImage(DICES[rnd.nextInt(0, DICES.length)]);
            diceImageView2.setImage(DICES[rnd.nextInt(0, DICES.length)]);
        }));
        timeline.setCycleCount(10);
        timeline.setOnFinished(e -> {
            int dice1 = extractDiceValue(diceImageView1.getImage());
            int dice2 = extractDiceValue(diceImageView2.getImage());
            System.out.println("Dice 1: " + dice1);
            System.out.println("Dice 2: " + dice2);

        });
        timeline.play();
    }

    private static int extractDiceValue(Image image) {
        String url = image.getUrl();
        char ch = url.charAt(url.length() - 5);
        return Character.getNumericValue(ch);
    }
}