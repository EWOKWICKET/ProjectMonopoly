package org.mademperors.polypoly.listeners;

/**
 * The DiceResultListener interface represents a listener for dice roll results.
 * Implementing classes can register as listeners to receive dice roll results.
 */
public interface DiceResultListener {
    /**
     * Called when a dice roll result is available.
     *
     * @param dice1 The value of the first dice.
     * @param dice2 The value of the second dice.
     */
    void onDiceResult(int dice1, int dice2);
}