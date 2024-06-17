package org.mademperors.polypoly.utils;

import javafx.scene.text.Text;
import org.mademperors.polypoly.models.Player;
import org.mademperors.polypoly.models.Street;

public class Utils {
    public static Text paintPlayerName(Player player) {
        Text playerName = new Text(player.getName());
        playerName.setFill(player.getPlayerColor());
        return playerName;
    }

    public static Text paintStreetName(Street street) {
        Text streetName = new Text(street.getName());
        streetName.setFill(street.getOwner().getPlayerColor());
        return streetName;
    }
}
