package org.mademperors.polypoly.models;


import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.Objects;

public class SoundManager {
    private static MediaPlayer mediaPlayer;

    public static void playSound(String soundFileName) {
        if (mediaPlayer != null) {
            mediaPlayer.stop(); // Stop the currently playing media
            mediaPlayer.dispose(); // Release the resources used by the old MediaPlayer
        }

        Media media = new Media(Objects.requireNonNull(SoundManager.class.getResource("/sounds/" + soundFileName + ".wav")).toExternalForm());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }
}