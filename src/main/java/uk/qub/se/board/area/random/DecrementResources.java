package uk.qub.se.board.area.random;

import uk.qub.se.board.area.BoardMovementResult;
import uk.qub.se.player.Player;

public class DecrementResources implements RandomAction {

    public static final int AMOUNT_TO_DECREMENT = 50; //??

    @Override
    public BoardMovementResult initiate(final Player player) {
        if (player == null) {
            throw new IllegalArgumentException("Player to decrement resources to may not be null");
        }

        if (playerHasToQuit(player)) {
            return BoardMovementResult.PLAYER_GAME_OVER;
        }

        player.updateResourcesByAmount(- AMOUNT_TO_DECREMENT);
        return BoardMovementResult.NEXT_PLAYER_TURN;
    }

    private boolean playerHasToQuit(Player player) {
        return (player.getResources() - AMOUNT_TO_DECREMENT) < 0;
    }
}
