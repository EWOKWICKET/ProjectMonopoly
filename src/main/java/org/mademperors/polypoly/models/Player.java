package org.mademperors.polypoly.models;

import javafx.scene.image.ImageView;

public class Player {

    private final String name;
    private int money;
    private boolean inJail = false;
    private int jailFreeCards = 0;
    private final String color;
    private ImageView playerImageView;
    private int currentPositionIndex = 0;
    private int playerIndex;

    public Player(String name, int money, String color) {
        this.name = name;
        this.money = money;
        this.color = color;
    }

    // DELETE player from players array()
    public void goBankrupt() {
        Bank.takeBankruptPlayerStreets(this);
        playerImageView.setVisible(false);
    }

    public void addMoney(int amount) {
        money += amount;
    }

    public void decreaseMoney(int amount) {
        money -= amount;
    }

    //LIMIT so that player could use only in jail
    public void useJailFreeCard() {
        spendJailFreeCards(1);
        inJail = false;
    }

    //getters and setters
    public String getName() {
        return name;
    }
    public int getMoney() {
        return money;
    }
    public boolean isInJail() {
        return inJail;
    }
    public void setInJail(boolean inJail) {
        this.inJail = inJail;
    }
    public int getJailFreeCards() {
        return jailFreeCards;
    }
    public void spendJailFreeCards(int amount) {
        this.jailFreeCards -= amount;
    }
    public void acquireJailFreeCards(int amount) {
        this.jailFreeCards += amount;
    }
    public String getPlayerColor() {
        return color;
    }
    public ImageView getPlayerImageView() {
        return playerImageView;
    }
    public void setPlayerImageView(ImageView playerImageView) {
        this.playerImageView = playerImageView;
    }
    public int getCurrentPositionIndex() {
        return currentPositionIndex;
    }
    public void setCurrentPositionIndex(int currentPositionIndex) {
        this.currentPositionIndex = currentPositionIndex;
    }
    public int getPlayerIndex() {
        return playerIndex;
    }
    public void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }
}
