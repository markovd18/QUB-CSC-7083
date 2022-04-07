package uk.qub.se.game;

import uk.qub.se.board.Board;
import uk.qub.se.player.Player;

import java.util.List;
import java.util.Random;

public class Game {

    private final List<Player> players;

    private final Board board;

    private final Random random;

    public Game(final List<Player> players, final Board board, final Random random) {
        validateDependencies(players, board, random);
        this.players = players;
        this.board = board;
        this.random = random;
    }

    private void validateDependencies(final List<Player> players, final Board board, final Random random) {
        if (players == null || players.isEmpty() || players.size() < 2) {
            throw new IllegalArgumentException("At least 2 players are required to play the game");
        }
        if (board == null) {
            throw new IllegalArgumentException("Board may not be null");
        }
        if (random == null) {
            throw new IllegalArgumentException("Random generator may not be null");
        }
    }

    public void startGame() {
        //TODO Game loop
    }

}
