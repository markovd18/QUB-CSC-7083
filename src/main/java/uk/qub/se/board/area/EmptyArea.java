package uk.qub.se.board.area;

import uk.qub.se.board.area.factory.AreaFactory;
import uk.qub.se.player.Player;

public class EmptyArea implements Area {

    @Override
    public void acceptPlayer(Player player) {

    }

    @Override
    public void validate() {
        //
    }

    public static void registerToFactory() {
        AreaFactory.getInstance().registerFactory("empty_area", (json, mapper) -> new EmptyArea());
    }
}
