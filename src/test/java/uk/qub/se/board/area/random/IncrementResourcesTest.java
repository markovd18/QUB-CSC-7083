package uk.qub.se.board.area.random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.qub.se.board.area.BoardMovementResult;
import uk.qub.se.player.Player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

public class IncrementResourcesTest {

    private IncrementResources action;

    @BeforeEach
    public void init() {
        this.action = new IncrementResources();
    }

    @Test
    public void throwsIllegalArgument_whenPassedPlayerIsNull() {
        assertThrows(IllegalArgumentException.class, () -> action.initiate(null),
                "Action should throw IllegalArgument when null player is passed");
    }

    @Test
    public void returnsNextPlayerTurn_whenPlayerIsPassed() {
        assertEquals(BoardMovementResult.NEXT_PLAYER_TURN, action.initiate(mock(Player.class)),
                "Action should return NEXT_PLAYER_TURN when player is passed");
    }
}
