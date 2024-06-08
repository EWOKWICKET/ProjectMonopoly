package org.mademperors.polypoly.models;

import java.util.*;

public class Bank {

    private static final Street[][] streets = {
            {
                    new Street("", 1, 1, 1, new int[]{1}),
                    new Street("", 1, 1, 1, new int[]{1}),
                    new Street("", 1, 1, 1, new int[]{1}),
                    new Street("", 1, 1, 1, new int[]{1})
            },
            {
                    new Street("", 1, 1, 1, new int[]{1}),
                    new Street("", 1, 1, 1, new int[]{1}),
                    new Street("", 1, 1, 1, new int[]{1})
            },
            {
                    new Street("", 1, 1, 1, new int[]{1}),
                    new Street("", 1, 1, 1, new int[]{1}),
                    new Street("", 1, 1, 1, new int[]{1})
            },
            {
                    new Street("", 1, 1, 1, new int[]{1}),
                    new Street("", 1, 1, 1, new int[]{1}),
                    new Street("", 1, 1, 1, new int[]{1})
            },
            {
                    new Street("", 1, 1, 1, new int[]{1}),
                    new Street("", 1, 1, 1, new int[]{1}),
                    new Street("", 1, 1, 1, new int[]{1})
            },
            {
                    new Street("", 1, 1, 1, new int[]{1}),
                    new Street("", 1, 1, 1, new int[]{1}),
                    new Street("", 1, 1, 1, new int[]{1})
            },
            {
                    new Street("", 1, 1, 1, new int[]{1}),
                    new Street("", 1, 1, 1, new int[]{1}),
                    new Street("", 1, 1, 1, new int[]{1})
            },
            {
                    new Street("", 1, 1, 1, new int[]{1}),
                    new Street("", 1, 1, 1, new int[]{1}),
                    new Street("", 1, 1, 1, new int[]{1})
            },
            {
                    new Street("", 1, 1, 1, new int[]{1}),
                    new Street("", 1, 1, 1, new int[]{1}),
                    new Street("", 1, 1, 1, new int[]{1})
            }
    };

    private static Map<Street, Integer> mortgaged = new HashMap<Street, Integer>();

//    public static void checkMonopolies() {
//        Arrays.stream(streets).forEach(Bank::checkMonopoly);
//    }

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

    private static void updateMortgaged() {
        Iterator<Map.Entry<Street, Integer>> iterator = mortgaged.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<Street, Integer> entry = iterator.next();

            entry.setValue(entry.getValue() - 1);

            if (entry.getValue() == 0) {
                takePlayerStreet(entry.getKey());
                iterator.remove();
            }
        }
    }

    public static void returnStreet(Street street) {
        mortgaged.remove(street);
    }

    public static void takePlayerStreet(Street street) {
        street.loseStreet();
    }

    public static void takeBankruptPlayerStreets(Player bankruptPlayer) {
        Arrays.stream(streets)
                .flatMap(Arrays::stream)
                .filter(street -> street.getOwner() == bankruptPlayer)
                .forEach(Street::loseStreet);
    }


}