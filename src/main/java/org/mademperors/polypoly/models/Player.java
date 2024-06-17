package org.mademperors.polypoly.models;

import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Player {

    private final String name;
    private int money;
    private int jailTime = 0;
    private int jailFreeCards = 0;
    private ImageView playerImageView;
    private int currentPositionIndex = 0;
    private int playerIndex;
    private final Color color;

    public Player(String name, int money, String color) {
        this.name = name;
        this.money = money;
        this.color = Color.web(color);
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
        freeFromJail();
    }

    //getters and setters
    public String getName() {
        return name;
    }
    public int getMoney() {
        return money;
    }
    public boolean isInJail() {
        return jailTime > 0;
    }
    public void putInJail() {
        this.jailTime = 3;
    }
    public void decreaseJailTime() {
        jailTime--;
    }
    public void freeFromJail() {
        jailTime = 0;
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
    public Color getPlayerColor() {
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
