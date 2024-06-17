package org.mademperors.polypoly.models;

import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * Represents a player in the game.
 */
public class Player {

    private final String name;
    private int money;
    private int jailTime = 0;
    private int jailFreeCards = 0;
    private ImageView playerImageView;
    private int currentPositionIndex = 0;
    private int playerIndex;
    private final Color color;
    private boolean isBankrupt = false;

    /**
     * Constructs a new Player object with the specified name, initial money, and color.
     *
     * @param name  the name of the player
     * @param money the initial amount of money the player has
     * @param color the color of the player
     */
    public Player(String name, int money, String color) {
        this.name = name;
        this.money = money;
        this.color = Color.web(color);
    }

    /**
     * Makes the player go bankrupt. Removes the player from the game and sets their bankruptcy status.
     */
    public void goBankrupt() {
        Bank.takeBankruptPlayerStreets(this);
        playerImageView.setVisible(false);
        isBankrupt = true;
    }

    /**
     * Checks if the player is bankrupt.
     *
     * @return true if the player is bankrupt, false otherwise
     */
    public boolean isBankrupt() {
        return isBankrupt;
    }

    /**
     * Buys a street and sets the player as its owner.
     *
     * @param street the street to be bought
     */
    public void buyStreet(Street street) {
        street.setOwner(this);
        decreaseMoney(street.getPrice());
        Bank.checkMonopolyByStreet(street);
    }

    /**
     * Adds money to the player's balance.
     *
     * @param amount the amount of money to add
     */
    public void addMoney(int amount) {
        money += amount;
    }

    /**
     * Decreases the player's money balance.
     *
     * @param amount the amount of money to decrease
     */
    public void decreaseMoney(int amount) {
        money -= amount;
    }

    /**
     * Uses a jail free card to get out of jail.
     * Decreases the number of jail free cards and frees the player from jail.
     */
    public void useJailFreeCard() {
        spendJailFreeCards(1);
        freeFromJail();
    }

    /**
     * Gets the name of the player.
     *
     * @return the name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the amount of money the player has.
     *
     * @return the amount of money the player has
     */
    public int getMoney() {
        return money;
    }

    /**
     * Checks if the player is currently in jail.
     *
     * @return true if the player is in jail, false otherwise
     */
    public boolean isInJail() {
        return jailTime > 0;
    }

    /**
     * Puts the player in jail.
     */
    public void putInJail() {
        this.jailTime = 3;
    }

    /**
     * Decreases the remaining jail time for the player.
     */
    public void decreaseJailTime() {
        jailTime--;
    }

    /**
     * Frees the player from jail.
     */
    public void freeFromJail() {
        jailTime = 0;
    }

    /**
     * Gets the remaining jail time for the player.
     *
     * @return the remaining jail time for the player
     */
    public int getJailTime() {
        return jailTime;
    }

    /**
     * Gets the number of jail free cards the player has.
     *
     * @return the number of jail free cards the player has
     */
    public int getJailFreeCards() {
        return jailFreeCards;
    }

    /**
     * Decreases the number of jail free cards the player has.
     *
     * @param amount the number of jail free cards to spend
     */
    public void spendJailFreeCards(int amount) {
        this.jailFreeCards -= amount;
    }

    /**
     * Increases the number of jail free cards the player has.
     *
     * @param amount the number of jail free cards to acquire
     */
    public void acquireJailFreeCards(int amount) {
        this.jailFreeCards += amount;
    }

    /**
     * Gets the color of the player.
     *
     * @return the color of the player
     */
    public Color getPlayerColor() {
        return color;
    }

    /**
     * Gets the player's image view.
     *
     * @return the player's image view
     */
    public ImageView getPlayerImageView() {
        return playerImageView;
    }

    /**
     * Sets the player's image view.
     *
     * @param playerImageView the player's image view
     */
    public void setPlayerImageView(ImageView playerImageView) {
        this.playerImageView = playerImageView;
    }

    /**
     * Gets the current position index of the player.
     *
     * @return the current position index of the player
     */
    public int getCurrentPositionIndex() {
        return currentPositionIndex;
    }

    /**
     * Sets the current position index of the player.
     *
     * @param currentPositionIndex the current position index of the player
     */
    public void setCurrentPositionIndex(int currentPositionIndex) {
        this.currentPositionIndex = currentPositionIndex;
    }

    /**
     * Gets the player index.
     *
     * @return the player index
     */
    public int getPlayerIndex() {
        return playerIndex;
    }

    /**
     * Sets the player index.
     *
     * @param playerIndex the player index
     */
    public void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }
}
