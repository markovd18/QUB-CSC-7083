package uk.qub.se.game;

import org.junit.jupiter.api.Test;
import uk.qub.se.board.Board;
import uk.qub.se.dice.Dice;
import uk.qub.se.player.Player;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

public class GameTest {

    @Test
    public void throwsWhenNullPlayerListPassed() {
        Board board = mock(Board.class);
        Dice dice = mock(Dice.class);

        assertThrows(IllegalArgumentException.class, () -> new Game(null, board, dice),
                "Game constructor should throw IllegalArgument when null player list passed");
    }

    @Test
    public void throwsWhenEmptyPlayerListPassed() {
        Board board = mock(Board.class);
        Dice dice = mock(Dice.class);
        var players = new ArrayList<Player>();

        assertThrows(IllegalArgumentException.class, () -> new Game(players, board, dice),
                "Game constructor should throw IllegalArgument when empty player list passed");
    }

    @Test
    public void throwsWhenNotEnoughPlayersPassed() {
        Board board = mock(Board.class);
        Dice dice = mock(Dice.class);
        var players = List.of(new Player("Nameless"));

        assertThrows(IllegalArgumentException.class, () -> new Game(players, board, dice),
                "Game constructor should throw IllegalArgument when not enough players passed");
    }

    @Test
    public void throwsWhenNullBoardPassed() {
        Dice dice = mock(Dice.class);
        var players = List.of(mock(Player.class), mock(Player.class), mock(Player.class));

        assertThrows(IllegalArgumentException.class, () -> new Game(players, null, dice),
                "Game constructor should throw IllegalArgument when null Board passed");
    }

    @Test
    public void throwsWhenNullDicePassed() {
        Board board = mock(Board.class);
        var players = List.of(mock(Player.class), mock(Player.class), mock(Player.class));

        assertThrows(IllegalArgumentException.class, () -> new Game(players, board, null),
                "Game constructor should throw IllegalArgument when null Dice passed");
    }

    @Test
    public void doesNotThrowWithValidDependencies() {
        Board board = mock(Board.class);
        var players = List.of(mock(Player.class), mock(Player.class), mock(Player.class));
        Dice dice = mock(Dice.class);

        assertDoesNotThrow(() -> new Game(players, board, dice), "Valid constructor call should not throw");
    }
}




