package org.mademperors.polypoly.models;

import javafx.scene.text.Text;
import org.mademperors.polypoly.utils.Utils;

import java.util.*;

public class Bank {

    private static final GameLogger logger = GameLogger.getInstance();

    private static final Street[][] streets = {
            {
                    new Street("пл. Галицька", 230, new int[]{23,46,115,345,650,975,1075}, 120, 130, 130, "lightblue"),
                    new Street("вул. Сагайдачного", 250, new int[]{25,50,125,375,750,1125,1240}, 140, 150, 150, "lightblue"),
                    new Street("вул. Івана Мазепи", 240, new int[]{24,48,120,360,720,1080,1190}, 200, 210, 210, "lightblue")
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
            {
                    new Street("пл. Слави", 290, new int[]{29,58, 145, 435, 870, 1305, 1435}, 180, 190, 190, "orange"),
                    new Street("вул. П. Скоропадського", 300, new int[]{30, 60, 150, 450, 900, 1345, 1480}, 190, 200, 200, "orange"),
                    new Street("Андріївський узвіз", 310, new int[]{31,62, 155, 465, 930, 1395, 1535}, 200, 210, 210, "orange")
            },
            {
                    new Street("вул. Грушевського", 320, new int[]{32, 160, 480, 950, 1425, 1575}, 210, 220, 220, "#de6ba2"),
                    new Street("вул. Ярославів вал", 330, new int[]{33, 165, 495, 975, 1450, 1600}, 220, 230, 230, "#de6ba2"),
                    new Street("вул. Л. Українки", 340, new int[]{34, 170, 510, 1025, 1530, 1700}, 230, 240, 240, "#de6ba2")
            },
            {
                    new Street("вул. Хрещатик", 350, new int[]{35,70,175,500,1100,1300,1500}, 240, 250, 250, "#d94440"),
                    new Street("вул. Інститутська", 360, new int[]{36,72,180,500,1100,1300,1500}, 250, 260, 260, "#d94440"),
                    new Street("вул. Шовковична", 370, new int[]{37,74,185,550,1200,1400,1600}, 260, 270, 270, "#d94440")
            },
            {
                    new Street("площа Перемоги", 410, new int[]{41,82,205,625,1475,1750,2050}, 300, 310, 310, "#b79b72"),
                    new Street("вул. Л. Курбаса", 420, new int[]{42,84,210,630,1500,1800,2100}, 310, 320, 320, "#b79b72"),
                    new Street("вул. Сагайдачного", 430, new int[]{43,86,215,645,1525,1850,2150}, 320, 330, 330, "#b79b72")
            },
            {
                    new Street("вул. Л. Українки", 440, new int[]{44,88,220,660,1575,1850,2200}, 330, 340, 340, "green"),
                    new Street("вул. І. Франка", 450, new int[]{45,90,225,675,1600,1900,2250}, 340, 350, 350, "green"),
                    new Street("вул. Т. Шевченка", 460, new int[]{46,92,230,690,1625,1950,2300}, 350, 360, 360, "green")
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