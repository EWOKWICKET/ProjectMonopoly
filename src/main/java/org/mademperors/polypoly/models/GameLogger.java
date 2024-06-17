package org.mademperors.polypoly.models;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import org.mademperors.polypoly.listeners.Logger;

public class GameLogger implements Logger {
    private static final GameLogger instance = new GameLogger();

    @FXML
    private TextArea statisticsTextArea;

    public static GameLogger getInstance() {
        return instance;
    }

    @Override
    public void logInfo(String message) {
        statisticsTextArea.appendText(message + "\n");
    }
}