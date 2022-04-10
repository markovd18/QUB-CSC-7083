package uk.qub.se.board.area;

import org.junit.jupiter.api.Test;
import uk.qub.se.player.Player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;

public class EmptyAreaTest {

    @Test
    public void returnsNull_whenAcceptingNull() {
        assertNull(new EmptyArea().acceptPlayer(null), "Accepting null should return null result");
    }

    @Test
    public void returnsNextPlayerTurn_whenAcceptingPlayer() {
        assertEquals(BoardMovementResult.NEXT_PLAYER_TURN, new EmptyArea().acceptPlayer(mock(Player.class)),
                "Accepting player should always return next player turn");
    }

}
