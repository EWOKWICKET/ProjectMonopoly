package org.mademperors.polypoly.models;

/**
 * Represents a street in the game.
 */
public class Street {

    private final String name;
    private final int price;
    private final int housePrice, hotelPrice;
    private int mortgagePrice;
    /* rent with no monopoly, with monopoly, with houses and hotel */
    private int[] rentModel = {0, 0, 0, 0, 0, 0, 0};
    private int rent = rentModel[0];
    private int numberOfHouses = 0;
    private boolean isMonopoly, hasHotel, isMortgaged = false;
    private Player owner = null;
    private String color;

    /**
     * Constructs a new Street object.
     *
     * @param name        the name of the street
     * @param price       the price of the street
     * @param rentModel   the rent model for the street
     * @param housePrice  the price of a house
     * @param hotelPrice  the price of a hotel
     * @param color       the color of the street
     */
    public Street(String name, int price, int[] rentModel, int housePrice, int hotelPrice, String color) {
        this.name = name;
        this.price = price;
        this.rentModel = rentModel;
        this.housePrice = housePrice;
        this.hotelPrice = hotelPrice;
        this.mortgagePrice = price / 2;
        this.color = color;
        this.rent = rentModel[0];
    }

    /**
     * Sets the new owner of the street and checks for monopoly.
     *
     * @param newOwner the new owner of the street
     */
    public void tradedTo(Player newOwner) {
        owner = newOwner;
        Bank.checkMonopolyByStreet(this);
    }

    /**
     * Resets the street when it is lost by the owner.
     */
    public void loseStreet() {
        owner = null;
        isMonopoly = false;
        isMortgaged = false;
        hasHotel = false;
        numberOfHouses = 0;
    }

    /**
     * Upgrades the street by buying a house or a hotel.
     */
    public void upgrade() {
        if (numberOfHouses < 4) {
            buyHouse();
        } else {
            buyHotel();
        }
    }

    /**
     * Downgrades the street by selling a house or a hotel.
     */
    public void downgrade() {
        if (hasHotel) {
            sellHotel();
        } else {
            if (numberOfHouses > 0) sellHouse();
        }
    }

    /**
     * Mortgages the street.
     */
    public void mortgage() {
        owner.addMoney(mortgagePrice);
        isMortgaged = true;
        Bank.mortageStreet(this);
        Bank.checkMonopolyByStreet(this);
    }

    /**
     * Unmortgages the street.
     */
    public void unmortgage() {
        owner.decreaseMoney((int) (mortgagePrice * 1.5));
        isMortgaged = false;
        Bank.returnStreet(this);
    }

    /**
     * Sets whether the street has a monopoly.
     *
     * @param monopoly true if the street has a monopoly, false otherwise
     */
    public void setMonopoly(boolean monopoly) {
        isMonopoly = monopoly;
        if (isMonopoly) {
            rent = rentModel[1];
        } else {
            rent = rentModel[0];
        }
    }

    /**
     * Returns the price of the street.
     *
     * @return the price of the street
     */
    public int getPrice() {
        return price;
    }

    /**
     * Returns the price of a house.
     *
     * @return the price of a house
     */
    public int getHousePrice() {
        return housePrice;
    }

    /**
     * Returns the price of a hotel.
     *
     * @return the price of a hotel
     */
    public int getHotelPrice() {
        return hotelPrice;
    }

    /**
     * Sets the rent of the street.
     *
     * @param rent the new rent of the street
     */
    public void setRent(int rent) {
        this.rent = rent;
    }

    /**
     * Returns the number of houses on the street.
     *
     * @return the number of houses on the street
     */
    public int getNumberOfHouses() {
        return numberOfHouses;
    }

    /**
     * Returns whether the street has a monopoly.
     *
     * @return true if the street has a monopoly, false otherwise
     */
    public boolean isMonopoly() {
        return isMonopoly;
    }

    /**
     * Returns the name of the street.
     *
     * @return the name of the street
     */
    public String getName() {
        return name;
    }

    /**
     * Returns whether the street is mortgaged.
     *
     * @return true if the street is mortgaged, false otherwise
     */
    public boolean isMortgaged() {
        return isMortgaged;
    }

    /**
     * Returns the owner of the street.
     *
     * @return the owner of the street
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * Sets the owner of the street.
     *
     * @param owner the new owner of the street
     */
    public void setOwner(Player owner) {
        this.owner = owner;
    }

    /**
     * Returns the color of the street.
     *
     * @return the color of the street
     */
    public String getColor() {
        return color;
    }

    /**
     * Sets the color of the street.
     *
     * @param color the new color of the street
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Returns whether the street has a hotel.
     *
     * @return true if the street has a hotel, false otherwise
     */
    public boolean isHasHotel() {
        return hasHotel;
    }

    /**
     * Sets whether the street has a hotel.
     *
     * @param hasHotel true if the street has a hotel, false otherwise
     */
    public void setHasHotel(boolean hasHotel) {
        this.hasHotel = hasHotel;
    }

    /**
     * Sets whether the street is mortgaged.
     *
     * @param mortgaged true if the street is mortgaged, false otherwise
     */
    public void setMortgaged(boolean mortgaged) {
        isMortgaged = mortgaged;
    }

    /**
     * Sets the number of houses on the street.
     *
     * @param numberOfHouses the new number of houses on the street
     */
    public void setNumberOfHouses(int numberOfHouses) {
        this.numberOfHouses = numberOfHouses;
    }

    /**
     * Returns the rent model of the street.
     *
     * @return the rent model of the street
     */
    public int[] getRentModel() {
        return rentModel;
    }

    /**
     * Sets the rent model of the street.
     *
     * @param rentModel the new rent model of the street
     */
    public void setRentModel(int[] rentModel) {
        this.rentModel = rentModel;
    }

    /**
     * Returns the current rent of the street.
     *
     * @return the current rent of the street
     */
    public int getRent() {
        return rent;
    }

    /**
     * Returns the mortgage price of the street.
     *
     * @return the mortgage price of the street
     */
    public int getMortgagePrice() {
        return mortgagePrice;
    }

    //private methods

    /**
     * Buys a house on the street.
     */
    private void buyHouse() {
        numberOfHouses++;
        rent = rentModel[1 + numberOfHouses];
        owner.decreaseMoney(housePrice);
    }

    /**
     * Sells a house on the street.
     */
    private void sellHouse() {
        numberOfHouses--;
        rent = rentModel[1 + numberOfHouses];
        owner.addMoney((int) (housePrice / 1.5));
    }

    /**
     * Buys a hotel on the street.
     */
    private void buyHotel() {
        hasHotel = true;
        rent = rentModel[6];
        owner.decreaseMoney(hotelPrice);
    }

    /**
     * Sells a hotel on the street.
     */
    private void sellHotel() {
        hasHotel = false;
        rent = rentModel[5];
        owner.addMoney((int) (housePrice / 1.5));
    }
}

