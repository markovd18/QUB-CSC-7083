package uk.qub.se.board.area;

import uk.qub.se.board.area.factory.AreaFactory;
import uk.qub.se.player.Player;

public class EmptyArea implements Area {
    private static final String NAME = "Empty Area";

    @Override
    public BoardMovementResult acceptPlayer(final Player player) {
        if (player == null) {
            return null;
        }

        System.out.println("Alas, nothing happened.");
        return BoardMovementResult.NEXT_PLAYER_TURN;
    }

    @Override
    public void validate() {
        //
    }

    @Override
    public String getName() {
        return NAME;
    }

    @SuppressWarnings("unused")
    public static void registerToFactory(final AreaFactory factory) {
        if (factory == null) {
            return;
        }
        factory.registerFactory("empty_area", (json, mapper) -> new EmptyArea());
    }
}
