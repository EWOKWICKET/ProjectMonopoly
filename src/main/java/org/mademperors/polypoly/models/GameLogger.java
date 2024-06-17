package org.mademperors.polypoly.models;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import org.mademperors.polypoly.listeners.Logger;

public class GameLogger implements Logger {
    private static final GameLogger instance = new GameLogger();

    @FXML
    private static TextArea statisticsTextArea;

    public static GameLogger getInstance() {
        return instance;
    }

    public static void setStatisticsTextArea(TextArea statisticsTextArea) {
        GameLogger.statisticsTextArea = statisticsTextArea;
    }

    @Override
    public void logInfo(String message) {
        statisticsTextArea.insertText(0, message + "\n");
    }
}