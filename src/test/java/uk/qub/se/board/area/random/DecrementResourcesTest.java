package uk.qub.se.board.area.random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.qub.se.board.area.BoardMovementResult;
import uk.qub.se.player.Player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DecrementResourcesTest {

    private DecrementResources action;

    @BeforeEach
    public void init() {
        this.action = new DecrementResources();
    }

    @Test
    public void throwsIllegalArgument_whenPlayerIsNull() {
        assertThrows(IllegalArgumentException.class, () -> action.initiate(null),
                "Action should throw IllegalArgument when passed player is null");
    }

    @Test
    public void returnsPlayerGameOver_whenPlayerDoesNotHaveEnoughResources() {
        final var player = mock(Player.class);
        when(player.getResources()).thenReturn(DecrementResources.AMOUNT_TO_DECREMENT - 10);

        assertEquals(BoardMovementResult.PLAYER_GAME_OVER, action.initiate(player),
                "Action should return PLAYER_GAME_OVER when player does not have enough resources");
    }

    @Test
    public void returnsNextPlayerTurn_whenPlayerHasEnoughResources() {
        final var player = mock(Player.class);
        when(player.getResources()).thenReturn(DecrementResources.AMOUNT_TO_DECREMENT + 10);

        assertEquals(BoardMovementResult.NEXT_PLAYER_TURN, action.initiate(player),
                "Action should return NEXT_PLAYER_TURN when player has enough resources");
    }
}
