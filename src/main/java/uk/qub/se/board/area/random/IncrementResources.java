package uk.qub.se.board.area.random;

import uk.qub.se.board.area.BoardMovementResult;
import uk.qub.se.player.Player;

public class IncrementResources implements RandomAction {

    private static final int AMOUNT_TO_INCREMENT = 50; //??

    @Override
    public BoardMovementResult initiate(final Player player) {
        if (player == null) {
            throw new IllegalArgumentException("Player to increment resources to may not be null");
        }

        player.updateResourcesByAmount(AMOUNT_TO_INCREMENT);
        return BoardMovementResult.NEXT_PLAYER_TURN;
    }
}
