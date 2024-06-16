package org.mademperors.polypoly.models;

import java.util.*;

public class Bank {

    private static final GameLogger logger = GameLogger.getInstance();

    private static final Street[][] streets = {
            {
                    new Street("a", 1, 1, 1, new int[]{1,2,3,4,5,6}),
                    new Street("b", 1, 1, 1, new int[]{1,2,3,4,5,6}),
                    new Street("d", 1, 1, 1, new int[]{1,2,3,4,5,6})
            },
            {
                    new Street("e", 1, 1, 1, new int[]{1,2,3,4,5,6}),
                    new Street("f", 1, 1, 1, new int[]{1,2,3,4,5,6}),
                    new Street("g", 1, 1, 1, new int[]{1,2,3,4,5,6})
            },
            {
                    new Street("h", 1, 1, 1, new int[]{1,2,3,4,5,6}),
                    new Street("i", 1, 1, 1, new int[]{1,2,3,4,5,6}),
                    new Street("j", 1, 1, 1, new int[]{1,2,3,4,5,6})
            },
            {
                    new Street("k", 1, 1, 1, new int[]{1,2,3,4,5,6}),
                    new Street("l", 1, 1, 1, new int[]{1,2,3,4,5,6}),
                    new Street("m", 1, 1, 1, new int[]{1,2,3,4,5,6})
            },
            {
                    new Street("n", 1, 1, 1, new int[]{1,2,3,4,5,6}),
                    new Street("o", 1, 1, 1, new int[]{1,2,3,4,5,6}),
                    new Street("p", 1, 1, 1, new int[]{1,2,3,4,5,6})
            },
            {
                    new Street("q", 1, 1, 1, new int[]{1,2,3,4,5,6}),
                    new Street("r", 1, 1, 1, new int[]{1,2,3,4,5,6}),
                    new Street("s", 1, 1, 1, new int[]{1,2,3,4,5,6})
            },
            {
                    new Street("t", 1, 1, 1, new int[]{1}),
                    new Street("u", 1, 1, 1, new int[]{1}),
                    new Street("v", 1, 1, 1, new int[]{1})
            },
            {
                    new Street("w", 1, 1, 1, new int[]{1}),
                    new Street("z", 1, 1, 1, new int[]{1}),
                    new Street("y", 1, 1, 1, new int[]{1})
            },
            {
                    new Street("z", 1, 1, 1, new int[]{1}),
                    new Street("N", 1, 1, 1, new int[]{1}),
                    new Street("D", 1, 1, 1, new int[]{1})
            }
    };

    private static final Map<Street, Integer> mortgaged = new HashMap<Street, Integer>();

//    public static void checkMonopolies() {
//        Arrays.stream(streets).forEach(Bank::checkMonopoly);
//    }

    public void nextTurn() {
        updateMortgaged();
    }

    public static void checkMonopoly(Street[] monopolyGroup) {
        Player firstOwner = monopolyGroup[0].getOwner();
        if (firstOwner == null) {
            return;
        }

        boolean allSameOwner = Arrays.stream(monopolyGroup)
                .allMatch(street -> street.getOwner() == firstOwner);

        if (allSameOwner) {
            Arrays.stream(monopolyGroup)
                    .forEach(street -> street.setMonopoly(true));
        }
    }

    public static void checkMonopolyByStreet(Street street) {
        Optional<Street[]> monopolyGroup = Arrays.stream(streets)
                .filter(group -> Arrays.asList(group).contains(street))
                .findFirst();

        monopolyGroup.ifPresent(Bank::checkMonopoly);
    }

    public static void mortgageStreets(Street[] streets) {
        Arrays.stream(streets).forEach(street -> {
            mortgaged.put(street, 10);
        });
    }



    public static void returnStreet(Street street) {
        mortgaged.remove(street);
    }

    public static void takeBankruptPlayerStreets(Player bankruptPlayer) {
        Arrays.stream(streets)
                .flatMap(Arrays::stream)
                .filter(street -> street.getOwner() == bankruptPlayer)
                .forEach(Street::loseStreet);
        logger.logInfo(String.format("%s went bankrupt", bankruptPlayer.getName()));
    }

    private static void updateMortgaged() {
        Iterator<Map.Entry<Street, Integer>> iterator = mortgaged.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<Street, Integer> entry = iterator.next();

            entry.setValue(entry.getValue() - 1);

            if (entry.getValue() == 0) {
                Street streetLost = entry.getKey();
                streetLost.loseStreet();
                iterator.remove();
                logger.logInfo(String.format("Mortgaged street %s lost", streetLost));
                checkMonopolyByStreet(streetLost);
            }
        }
    }

    public Street[][] getAllStreets(){
        return streets;
    }

}