package uk.qub.se;

import uk.qub.se.board.Board;
import uk.qub.se.board.BoardLoader;
import uk.qub.se.game.Game;
import uk.qub.se.player.Player;
import uk.qub.se.player.PlayerLoader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


public class SaveThePlanetApp {

    public static final int MAX_PLAYER_NUM = 4;
    public static final int MIN_PLAYER_NUM = 3;
    public static final String BOARD_CONFIG_PATH = "board.json";

    public static void main(String[] args) {
        Board board = loadBoard();

        final List<Player> players = loadPlayers();
        if (notEnoughPlayersLoaded(players)) {
            System.out.printf("Minimum number of players required to play the game is %d. " +
                    "Please return once you have enough players.", MIN_PLAYER_NUM);
            System.exit(1);
        }


        Random random = new Random(System.currentTimeMillis());
        Game game = new Game(players, board, random);
        game.startGame();
    }

    private static Board loadBoard() {
        try {
            final InputStream boardConfig = new FileInputStream(BOARD_CONFIG_PATH);
            final BoardLoader boardLoader = new BoardLoader(boardConfig);
            return boardLoader.loadBoard();
        } catch (IOException e) {
            System.out.println("Board loading failed");
            System.out.println(e.getMessage());
            System.exit(3);
            return null;
        }
    }

    private static List<Player> loadPlayers() {
        final var scanner = new Scanner(System.in);
        final var loader = new PlayerLoader(scanner, System.out, MAX_PLAYER_NUM);
        return loader.loadPlayers();
    }

    private static boolean notEnoughPlayersLoaded(final List<Player> players) {
        return players.size() < MIN_PLAYER_NUM;
    }
}