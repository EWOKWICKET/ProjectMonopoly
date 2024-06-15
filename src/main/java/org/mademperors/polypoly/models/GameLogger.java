package org.mademperors.polypoly.models;

import org.mademperors.polypoly.listeners.Logger;

public class GameLogger implements Logger {
    private static final GameLogger instance = new GameLogger();

    private GameLogger() {}

    public static GameLogger getInstance() {
        return instance;
    }

    @Override
    public void logInfo(String message) {
        System.out.println(message);
    }
}