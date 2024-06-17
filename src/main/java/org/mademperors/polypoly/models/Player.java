package org.mademperors.polypoly.models;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Player {

    private final String name;
    private int money;
    private boolean inJail = false;
    private int jailFreeCards = 0;
    private final Color color;

    public Player(String name, int money, String color) {
        this.name = name;
        this.money = money;
        this.color = Color.web(color);
    }

    // DELETE player from players array()
    public void goBankrupt() {
        Bank.takeBankruptPlayerStreets(this);
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
    public Color getPlayerColor() {
        return color;
    }
}
