package uk.qub.se.board.area.random;

import uk.qub.se.board.area.BoardMovementResult;
import uk.qub.se.player.Player;

public interface RandomAction {

    BoardMovementResult initiate(Player player);
}
