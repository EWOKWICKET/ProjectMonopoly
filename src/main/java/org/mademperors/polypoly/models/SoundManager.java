package org.mademperors.polypoly.models;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.net.URL;
import java.util.Objects;


public class SoundManager {
    private final String soundDir = "/sounds/";

    // Media Players for each sound
    private final MediaPlayer chipMovingPlayer;
    private final MediaPlayer payPlayer;
    private final MediaPlayer throwPlayer;
    private final MediaPlayer toJailPlayer;
    private final MediaPlayer winPlayer;


    public SoundManager() {
        chipMovingPlayer = new MediaPlayer(new Media(Objects.requireNonNull(getClass().getResource(soundDir + "chipMoving.wav")).toString()));
        chipMovingPlayer.setVolume(2);

        payPlayer = new MediaPlayer(new Media(Objects.requireNonNull(getClass().getResource(soundDir + "pay.wav")).toString()));
        throwPlayer = new MediaPlayer(new Media(Objects.requireNonNull(getClass().getResource(soundDir + "throw5.wav")).toString()));
        toJailPlayer = new MediaPlayer(new Media(Objects.requireNonNull(getClass().getResource(soundDir + "toJail.wav")).toString()));
        winPlayer = new MediaPlayer(new Media(Objects.requireNonNull(getClass().getResource(soundDir + "win.wav")).toString()));
    }

    public void playChipMoving() {
        chipMovingPlayer.play();
        restartSound(chipMovingPlayer);

    }

    public void playPay() {
        payPlayer.play();
        restartSound(payPlayer);
    }

    public void playThrow() {
        throwPlayer.play();
        restartSound(throwPlayer);
    }

    public void playToJail() {
        toJailPlayer.play();
        restartSound(toJailPlayer);
    }

    public void playWin() {
        winPlayer.play();
        restartSound(winPlayer);
    }

    private void restartSound(MediaPlayer player) {
        player.setOnEndOfMedia(() -> {
            player.stop();
            player.seek(Duration.ZERO);
        });
    }
}
