package uk.qub.se.board.area;

import uk.qub.se.board.area.factory.AreaFactory;
import uk.qub.se.player.Player;

public class RandomEventArea implements Area {

    @Override
    public void acceptPlayer(final Player player) {

    }

    @Override
    public void validate() {
        //TODO validation
    }

    public static void registerToFactory() {
        AreaFactory.getInstance().registerFactory("random", (json, mapper) -> mapper.readValue(json, RandomEventArea.class));
    }
}
