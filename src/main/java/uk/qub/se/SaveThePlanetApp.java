package uk.qub.se;

import com.fasterxml.jackson.databind.ObjectMapper;
import uk.qub.se.board.Board;
import uk.qub.se.board.BoardLoader;
import uk.qub.se.board.area.factory.AreaFactory;
import uk.qub.se.game.Game;
import uk.qub.se.player.Player;
import uk.qub.se.player.PlayerLoader;
import uk.qub.se.utils.ExitCode;
import uk.qub.se.utils.ReflectionsFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
            System.exit(ExitCode.NOT_ENOUGH_PLAYERS.getValue());
        }


        Random random = new Random(System.currentTimeMillis());
        Game game = new Game(players, board, random);
        game.startGame();
    }

    private static Board loadBoard() {
        try {
            final BoardLoader boardLoader = createBoardLoader();
            return boardLoader.loadBoard();
        } catch (IOException e) {
            System.out.printf("Board loading failed.\n%s", e.getMessage());
            System.exit(ExitCode.CONFIG_ERROR.getValue());
            return null;
        }
    }

    private static BoardLoader createBoardLoader() throws FileNotFoundException {
        final InputStream boardConfig = new FileInputStream(BOARD_CONFIG_PATH);
        final ObjectMapper mapper = new ObjectMapper();
        final AreaFactory areaFactory = new AreaFactory(new ReflectionsFactory());
        return new BoardLoader(boardConfig, areaFactory, mapper);
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