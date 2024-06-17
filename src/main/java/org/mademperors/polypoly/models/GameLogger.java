package org.mademperors.polypoly.models;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import org.mademperors.polypoly.listeners.Logger;

/**
 * The GameLogger class is responsible for logging game information to a text area.
 */
public class GameLogger implements Logger {
    private static final GameLogger instance = new GameLogger();

    @FXML
    private static TextArea statisticsTextArea;

    /**
     * Returns the singleton instance of the GameLogger class.
     *
     * @return The singleton instance of the GameLogger class.
     */
    public static GameLogger getInstance() {
        return instance;
    }

    /**
     * Sets the statistics text area for logging game information.
     *
     * @param statisticsTextArea The text area to set as the statistics text area.
     */
    public static void setStatisticsTextArea(TextArea statisticsTextArea) {
        GameLogger.statisticsTextArea = statisticsTextArea;
    }

    /**
     * Logs an informational message to the statistics text area.
     *
     * @param message The message to log.
     */
    @Override
    public void logInfo(String message) {
        statisticsTextArea.insertText(0, message + "\n");
    }
}