package org.mademperors.polypoly.models;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.net.URL;
import java.util.Objects;


/**
 * The SoundManager class is responsible for managing and playing sound effects in the game.
 */
public class SoundManager {
    private final String soundDir = "/sounds/";

    // Media Players for each sound
    private final MediaPlayer chipMovingPlayer;
    private final MediaPlayer payPlayer;
    private final MediaPlayer throwPlayer;
    private final MediaPlayer toJailPlayer;
    private final MediaPlayer winPlayer;

    /**
     * Constructs a new SoundManager object.
     * Initializes the MediaPlayers for each sound effect.
     */
    public SoundManager() {
        chipMovingPlayer = new MediaPlayer(new Media(Objects.requireNonNull(getClass().getResource(soundDir + "chipMoving.wav")).toString()));
        chipMovingPlayer.setVolume(2);

        payPlayer = new MediaPlayer(new Media(Objects.requireNonNull(getClass().getResource(soundDir + "pay.wav")).toString()));
        throwPlayer = new MediaPlayer(new Media(Objects.requireNonNull(getClass().getResource(soundDir + "throw5.wav")).toString()));
        toJailPlayer = new MediaPlayer(new Media(Objects.requireNonNull(getClass().getResource(soundDir + "toJail.wav")).toString()));
        winPlayer = new MediaPlayer(new Media(Objects.requireNonNull(getClass().getResource(soundDir + "win.wav")).toString()));
    }

    /**
     * Plays the chip moving sound effect.
     */
    public void playChipMoving() {
        chipMovingPlayer.play();
        restartSound(chipMovingPlayer);
    }

    /**
     * Plays the pay sound effect.
     */
    public void playPay() {
        payPlayer.play();
        restartSound(payPlayer);
    }

    /**
     * Plays the throw sound effect.
     */
    public void playThrow() {
        throwPlayer.play();
        restartSound(throwPlayer);
    }

    /**
     * Plays the to jail sound effect.
     */
    public void playToJail() {
        toJailPlayer.play();
        restartSound(toJailPlayer);
    }

    /**
     * Plays the win sound effect.
     */
    public void playWin() {
        winPlayer.play();
        restartSound(winPlayer);
    }

    /**
     * Restarts the specified MediaPlayer when it reaches the end of the media.
     * @param player The MediaPlayer to restart.
     */
    private void restartSound(MediaPlayer player) {
        player.setOnEndOfMedia(() -> {
            player.stop();
            player.seek(Duration.ZERO);
        });
    }
}
