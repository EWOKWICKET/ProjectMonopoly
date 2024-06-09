package org.mademperors.polypoly.models;

public class Street {

    private String name;
    private int price;
    private int housePrice;
    private int hotelPrice;
    private int rent;
    /* rent with no monopoly, with monopoly, with houses and hotel */
    private int[] rentModel = {0, 0, 0, 0, 0, 0, 0};
    private int numberOfHouses = 0;
    private boolean isMonopoly = false;
    private boolean hasHotel = false;
    private boolean isMortgaged = false;
    private Player owner = null;

    public Street(String name, int price, int housePrice, int hotelPrice, int[] rentModel) {
        this.name = name;
        this.price = price;
        this.housePrice = housePrice;
        this.hotelPrice = hotelPrice;
        this.rentModel = rentModel;
        this.rent = rentModel[0];
    }

    public void buyStreet(Player owner) {
        this.owner = owner;
        rent = rentModel[0];
        owner.decreaseMoney(price);
    }

    public void unmortgage() {
        owner.decreaseMoney(price/2);
        isMortgaged = false;
        Bank.returnStreet(this);
    }

    public void tradedTo(Player newOwner) {
        owner = newOwner;
        Bank.checkMonopolyByStreet(this);
    }

    public void loseStreet() {
        owner = null;
        isMonopoly = false;
        isMortgaged = false;
        hasHotel = false;
        numberOfHouses = 0;
    }

    public void upgrade() {
        if (numberOfHouses < 4) {
            buyHouse();
        } else {
            buyHotel();
        }
    }

    public void downgrade() {
        if (hasHotel) {
            sellHotel();
        } else {
            sellHouse();
        }
    }

    public void mortgage() {
        owner.addMoney(price/2);
        isMortgaged = true;
    }

    //getters and setters
    public void setMonopoly(boolean monopoly) {
        isMonopoly = monopoly;
        if (isMonopoly) {
            rent = rentModel[1];
        } else {
            rent = rentModel[0];
        }
    }
    public int getPrice() {
        return price;
    }
    public int getHousePrice() {
        return housePrice;
    }
    public int getHotelPrice() {
        return hotelPrice;
    }
    public void setRent(int rent) {
        this.rent = rent;
    }
    public int getNumberOfHouses() {
        return numberOfHouses;
    }
    public boolean isMonopoly() {
        return isMonopoly;
    }
    public String getName() {
        return name;
    }
    public boolean isMortgaged() {
        return isMortgaged;
    }
    public Player getOwner() {
        return owner;
    }
    public void setOwner(Player owner) {
        this.owner = owner;
    }


    //private methods
    private void buyHouse() {
        numberOfHouses++;
        rent = rentModel[1 + numberOfHouses];
        owner.decreaseMoney(housePrice);
    }

    private void sellHouse() {
        numberOfHouses--;
        rent = rentModel[1 + numberOfHouses];
        owner.addMoney((int) (housePrice / 1.5));
    }

    private void buyHotel() {
        hasHotel = true;
        rent = rentModel[6];
        owner.decreaseMoney(hotelPrice);
    }

    private void sellHotel() {
        hasHotel = false;
        rent = rentModel[5];
        owner.addMoney((int) (housePrice / 1.5));
    }
}
