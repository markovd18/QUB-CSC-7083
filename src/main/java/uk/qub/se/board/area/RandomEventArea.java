package uk.qub.se.board.area;

import uk.qub.se.board.area.factory.AreaFactory;
import uk.qub.se.player.Player;

public class RandomEventArea implements Area {

    @Override
    public BoardMovementResult acceptPlayer(final Player player) {

        return null;
    }

    @Override
    public void validate() {
        //TODO validation
    }

    public static void registerToFactory() {
        AreaFactory.getInstance().registerFactory("random", (json, mapper) -> {
            final Area area = mapper.readValue(json, RandomEventArea.class);
            area.validate();
            return area;
        });
    }
}
