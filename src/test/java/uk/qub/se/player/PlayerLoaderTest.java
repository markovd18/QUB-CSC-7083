package uk.qub.se.player;

import org.junit.jupiter.api.Test;
import uk.qub.se.utils.IOUtils;

import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PlayerLoaderTest {

    @Test
    public void throwsWhenNoScannerPassed() {
        assertThrows(IllegalArgumentException.class,
                () -> new PlayerLoader(null, System.out, 1),
                "Constructor has to throw when no input scanner is passed!");
    }

    @Test
    public void throwsWhenNegativeNumberOfPlayersPassed() {
        assertThrows(IllegalArgumentException.class,
                () -> new PlayerLoader(new Scanner(IOUtils.toInputStream("")), System.out, -5),
                "Constructor has to throw when negative number of players is passed!");
    }

    @Test
    public void doesNotThrow_whenNullPrintStreamPassed() {
        assertDoesNotThrow(() -> new PlayerLoader(new Scanner(IOUtils.toInputStream("")), null, 2),
                "Passing null print stream should not throw!");
    }

    @Test
    public void loadsPLayerWithCorrectName() {
        final int playersToLoad = 1;
        final var nickname = "Nickname";

        final Scanner scanner = new Scanner(IOUtils.toInputStream(nickname + "\n\n"));
        final PlayerLoader loader = new PlayerLoader(scanner, System.out, playersToLoad);
        final List<Player> players = loader.loadPlayers();

        assertAll(() -> assertEquals(playersToLoad, players.size(), "Number of loaded players should be " + playersToLoad),
                () -> assertEquals(nickname, players.get(0).getName(), "Player's name has to match the user input!"));
    }

    @Test
    public void loadsPlayerOnSecondTry_afterDecliningNickname() {
        final int playersToLoad = 1;
        final var nickname = "Nickname2";

        final Scanner scanner = new Scanner(IOUtils.toInputStream("Nickname\nq\n" + nickname + "\n\n"));
        final PlayerLoader loader = new PlayerLoader(scanner, System.out, playersToLoad);
        final List<Player> players = loader.loadPlayers();

        assertAll(() -> assertEquals(playersToLoad, players.size(), "Number of loaded players should be " + playersToLoad),
                () -> assertEquals(nickname, players.get(0).getName(), "Player's name has to match the user input!"));
    }

    @Test
    public void loadsNoPlayers_whenUserDeclinesToEnterNickname() {
        final Scanner scanner = new Scanner(IOUtils.toInputStream("q\n"));
        final PlayerLoader loader = new PlayerLoader(scanner, System.out, 1);
        final List<Player> players = loader.loadPlayers();

        assertEquals(0, players.size(), "Loader should not load any player when first user declines to play");
    }

    @Test
    public void doesNotLoadTwoIdenticalNames() {
        final int playersToLoad = 2;

        final Scanner scanner = new Scanner(IOUtils.toInputStream("Nickname\n\nNickname\nnickname2\n\n"));
        final PlayerLoader loader = new PlayerLoader(scanner, System.out, playersToLoad);
        final List<Player> players = loader.loadPlayers();

        assertAll(() -> assertEquals(playersToLoad, players.size(), "Two players should be loaded"),
                () -> assertFalse(() -> players.get(0).getName().equals(players.get(1).getName()),
                        "Players names may not be equal"));
    }
    @Test
    public void doesNotLoadNameTooLong(){
        final var invalidNickname = "ThisNickNameIsTooLong";
        final String validNickname = "OKNickName";
        final int numPlayers = 1;

        final Scanner scanner = new Scanner(IOUtils.toInputStream(invalidNickname+"\n"+validNickname+"\n\n"));
        final PlayerLoader loader = new PlayerLoader(scanner, System.out, numPlayers);
        final List<Player> players = loader.loadPlayers();

        assertEquals(1, players.size(), "One player with acceptable nickname should be loaded");
    }


}
