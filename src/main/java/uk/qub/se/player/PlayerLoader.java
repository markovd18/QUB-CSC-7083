package uk.qub.se.player;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


@SuppressWarnings("ClassCanBeRecord")
public class PlayerLoader {

    public static final String DECLINE_ENTERING_NAME_SYMBOL = "q";
    public static final int MAXIMUM_NICK_LENGTH = 20;

    private final Scanner scanner;
    private final PrintStream outputStream;
    private final int maxNumberOfPlayersToLoad;

    public PlayerLoader(final Scanner scanner, final PrintStream outputStream, final int maxNumberOfPlayersToLoad) {
        validateDependencies(scanner, maxNumberOfPlayersToLoad);

        this.scanner = scanner;
        this.outputStream = outputStream == null ? System.out : outputStream;
        this.maxNumberOfPlayersToLoad = maxNumberOfPlayersToLoad;
    }

    private void validateDependencies(final Scanner scanner, final int maxNumberOfPlayersToLoad) {
        if (scanner == null) {
            throw new IllegalArgumentException("Scanner may not be null");
        }

        if (maxNumberOfPlayersToLoad < 0) {
            throw new IllegalArgumentException("Max number of players to load may not be negative");
        }
    }

    public List<Player> loadPlayers() {
        final List<Player> players = new ArrayList<>(maxNumberOfPlayersToLoad);
        while (playersToLoadLeft(players)) {
            final Player player = loadPlayer(players);
            if (declinedToLoadPlayer(player)) {
                break;
            }

            players.add(player);
        }

        return players;
    }

    private boolean declinedToLoadPlayer(final Player player) {
        return player == null;
    }

    private Player loadPlayer(final List<Player> loadedPlayers) {
        String nickname = loadNickname(loadedPlayers);
        if (nickname == null) return null;

        return new Player(nickname);
    }

    private String loadNickname(final List<Player> loadedPlayers) {
        String nickname = null;
        boolean nicknameConfirmed = false;

        while (!nicknameConfirmed) {
            outputStream.printf("Player no. %d enter nickname or 'q' to cancel: ", loadedPlayers.size() + 1);
            nickname = loadNickname();
            if (nickname == null) return null;

            if (isNicknameInvalid(loadedPlayers, nickname)) continue;

            outputStream.printf("Selected nickname is '%s'. Press 'Enter' to confirm or enter anything else to decline: ", nickname);
            nicknameConfirmed = loadNicknameConfirmation();
        }

        return nickname;
    }

    private boolean isNicknameInvalid(final List<Player> loadedPlayers, final String nickname) {
        if (nickname.isBlank()) {
            outputStream.println("Please enter at least one character as a nickname.");
            return true;
        }

        if (nicknameTaken(loadedPlayers, nickname)) {
            outputStream.println("Selected nickname is already taken. Please enter unique nickname.");
            return true;
        }

        if (isNicknameTooLong(nickname)){
            outputStream.println("Selected nickname is too long. Please enter nickname of 10 or less characters.");
            return true;
        }

        return false;
    }

    private boolean isNicknameTooLong(final String nickname) {
        return nickname.length() > MAXIMUM_NICK_LENGTH;
    }

    private boolean nicknameTaken(final List<Player> loadedPlayers, final String nickname) {
        return loadedPlayers.stream()
                .anyMatch(player -> player.getName().equals(nickname));
    }

    private String loadNickname() {
        final var nickname = scanner.nextLine();
        if (declinedToLoadPlayer(nickname)) {
            return null;
        }
        return nickname;
    }

    private boolean loadNicknameConfirmation() {
        final var input = scanner.nextLine();
        return input.isBlank();
    }

    private boolean declinedToLoadPlayer(final String nickname) {
        return nickname == null || nickname.equals(DECLINE_ENTERING_NAME_SYMBOL);
    }

    private boolean playersToLoadLeft(final List<Player> players) {
        return players.size() < maxNumberOfPlayersToLoad;
    }

}
