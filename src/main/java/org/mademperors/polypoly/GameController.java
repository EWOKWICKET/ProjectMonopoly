package org.mademperors.polypoly;

import javafx.animation.PauseTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.net.URL;
import java.util.Objects;
import java.util.Random;
import java.util.ResourceBundle;

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
}