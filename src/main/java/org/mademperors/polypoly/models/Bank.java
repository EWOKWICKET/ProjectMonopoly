package org.mademperors.polypoly.models;

import javafx.scene.text.Text;
import org.mademperors.polypoly.utils.Utils;

import java.util.*;

public class Bank {

    private static final GameLogger logger = GameLogger.getInstance();

    private static final Street[][] streets = {
            {
                    new Street("пл. Слави", 290, new int[]{29,58, 145, 435, 870, 1305, 1435}, 180, 190, 190, "orange"),
                    new Street("вул. П. Скоропадського", 300, new int[]{30, 60, 150, 450, 900, 1345, 1480}, 190, 200, 200, "orange"),
                    new Street("Андріївський узвіз", 310, new int[]{31,62, 155, 465, 930, 1395, 1535}, 200, 210, 210, "orange")
            },
            {
                    new Street("пл. Галицька", 230, new int[]{23,46,115,345,650,975,1075}, 120, 130, 130, "blue"),
                    new Street("вул. Сагайдачного", 250, new int[]{25,50,125,375,750,1125,1240}, 140, 150, 150, "blue"),
                    new Street("вул. Івана Мазепи", 240, new int[]{24,48,120,360,720,1080,1190}, 200, 210, 210, "blue")
            },
            {
                    new Street("Голос. парк", 200, new int[]{20,40,100,300,600,900,990}, 90, 100, 100, "lightgreen"),
                    new Street("парк ім. Шевченка", 220, new int[]{22,44,110,330,660,990,1090}, 110, 120, 120, "lightgreen"),
                    new Street("Хрещатий парк", 210, new int[]{21,42,105,315,630,945,1040}, 100, 110, 110, "lightgreen")
            },
            {
                    new Street("Поштова пл.", 260, new int[]{26,52,130,390,780,1170,1290}, 150, 160, 160, "yellow"),
                    new Street("вул. Васильківська", 280, new int[]{28,56,140,420,840,1260,1385}, 170, 18, 180, "yellow"),
                    new Street("вул. Гоголівська", 270, new int[]{24,48,120,360,720,1080,1190}, 160, 170, 170, "yellow")
            },

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

        Text playerName = Utils.paintPlayerName(bankruptPlayer);
        logger.logInfo(String.format("%s went bankrupt", playerName));
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

                Text streetName = Utils.paintStreetName(streetLost);
                logger.logInfo(String.format("Mortgaged street %s lost", streetLost));
                checkMonopolyByStreet(streetLost);
            }
        }
    }

    public Street[][] getAllStreets(){
        return streets;
    }

}