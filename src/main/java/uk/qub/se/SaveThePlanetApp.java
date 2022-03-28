package uk.qub.se;

import uk.qub.se.player.Player;
import uk.qub.se.player.PlayerLoader;

import java.util.List;
import java.util.Scanner;

public class SaveThePlanetApp {

    public static final int MAX_PLAYER_NUM = 4;
    public static final int MIN_PLAYER_NUM = 3;

    public static void main(String[] args) {
        final List<Player> players = loadPlayers();

        if (notEnoughPlayersLoaded(players)) {
            System.out.printf("Minimum number of players required to play the game is %d. " +
                    "Please return once you have enough players.", MIN_PLAYER_NUM);
            System.exit(1);
        }

        for (final Player player : players) {
            System.out.println("Loaded player: " + player.getName());
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