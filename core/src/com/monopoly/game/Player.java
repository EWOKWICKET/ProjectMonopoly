package com.monopoly.game;

public class Player {

    private int balance = MainMenu.getCurrentNumber();
    private String name;

    public Player(String name) {
        this.name = name;
    }

    public int getBalance() {
        return balance;
    }
    public void setBalance(int balance) {
        this.balance = balance;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Player: " + name + ", balance: " + balance;
    }
}
