package uk.qub.se.game;

import uk.qub.se.board.Board;
import uk.qub.se.board.area.Area;
import uk.qub.se.board.area.BoardMovementResult;
import uk.qub.se.board.area.DevelopableArea;
import uk.qub.se.dice.Dice;
import uk.qub.se.player.Player;
import uk.qub.se.utils.GameUtils;
import uk.qub.se.utils.PrintUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Game {

    private final List<Player> players;

    private final Board board;

    private final Dice dice;

    private static final int MAX_POINTS = 100;

    private GameStatus status;

    private int totalInvestment = 0;

    public Game(final List<Player> players, final Board board, final Dice dice) {
        validateDependencies(players, board, dice);
        this.players = players;
        this.board = board;
        this.dice = dice;
    }

    private void validateDependencies(final List<Player> players, final Board board, final Dice dice) {
        if (players == null || players.isEmpty() || players.size() < 2) {
            throw new IllegalArgumentException("At least 2 players are required to play the game");
        }
        if (board == null) {
            throw new IllegalArgumentException("Board may not be null");
        }
        if (dice == null) {
            throw new IllegalArgumentException("Dice generator may not be null");
        }

    }


    private enum GameStatus {

        RUNNING, STOPPED
    }

    private int getPoints(){
       return totalInvestment;
    }

    public void addPoints(int pointsToAdd){
        totalInvestment = totalInvestment + pointsToAdd;
    }

    public void startGame() {
        final List<Player> activePlayers = initiateActivePlayers();
        //set GameStatus to RUNNING
        run();

        //sets all players initial currentPosition to startArea
        for(Player player : activePlayers){
            player.moveToArea(board.getStartArea(), this);
        }
        //show starting stats
        displayGameStats(activePlayers);
        //iterate over each player & Display the menu allowing each player to choose their next move whilst game
        //is in play only
         do {
             iterateThroughActivePlayers(activePlayers);
         } while(isGameRunning() && isInvestmentPointsWithinLimit());

        System.out.println("Game has ended with final stats:");
        System.out.println("-------------------------------");
        displayGameStats(players);
    }

    private void iterateThroughActivePlayers(final List<Player> activePlayers) {
        final Iterator<Player> iterator = activePlayers.iterator();

        while (iterator.hasNext()) {
            final Player currentPlayer = iterator.next();
            if(isGameRunning() && isInvestmentPointsWithinLimit()){
                final BoardMovementResult result = displayMenu(activePlayers, currentPlayer);
                handlePlayerActionResult(result, iterator);
            }

        }
    }

    private void handlePlayerActionResult(final BoardMovementResult result, final Iterator<Player> iterator) {
        switch (result) {
            case GAME_OVER -> stop();
            case PLAYER_GAME_OVER -> iterator.remove();
        }
    }

    private BoardMovementResult displayMenu(final List<Player> activePlayers, final Player currentPlayer) {
        BoardMovementResult action = BoardMovementResult.SAME_PLAYER_TURN;

        final List<DevelopableArea> developableAreas = GameUtils.getDevelopableAreas(currentPlayer, board);
        while (action == BoardMovementResult.SAME_PLAYER_TURN) {
            action = displayUserMenu(activePlayers, currentPlayer, developableAreas);
        }

        return action;
    }

    private BoardMovementResult displayUserMenu(final List<Player> activePlayers, final Player currentPlayer, List<DevelopableArea> developableAreas) {
        PrintUtils.printMenuOptions(currentPlayer, developableAreas);

        Scanner scanner = new Scanner(System.in);
        final String userInput = scanner.nextLine();

        return processUserInput(activePlayers, currentPlayer, developableAreas, userInput);
    }

    private BoardMovementResult processUserInput(final List<Player> activePlayers,
                                                 final Player currentPlayer,
                                                 final List<DevelopableArea> developableAreas,
                                                 final String userInput) {
        return switch (userInput) {
            case "1" -> handleDisplayGameStatsOption(activePlayers);
            case "2" -> rollDice(currentPlayer);
            case "3" -> quitGame();
            case "4" -> handleDevelopAreaOption(currentPlayer, developableAreas);
            default -> handleInvalidOption();
        };
    }

    private BoardMovementResult handleDisplayGameStatsOption(final List<Player> activePlayers) {
        displayGameStats(activePlayers);
        return BoardMovementResult.SAME_PLAYER_TURN;
    }

    private BoardMovementResult handleDevelopAreaOption(final Player currentPlayer,
                                                        final List<DevelopableArea> developableAreas) {
        if (developableAreas.isEmpty()) {
            System.out.println("Invalid option entered.");
            return BoardMovementResult.SAME_PLAYER_TURN;
        }

        return displayAreaDevelopmentMenu(currentPlayer, developableAreas);
    }

    private BoardMovementResult handleInvalidOption() {
        System.out.println("Invalid option entered.");
        return BoardMovementResult.SAME_PLAYER_TURN;
    }

    private BoardMovementResult quitGame() {
        System.out.println("\nAre you sure you want to quit the game?\nPress 'Enter' to quit\nPress any other key to continue");
        Scanner userInput = new Scanner(System.in);

        final String numberSelected = userInput.nextLine();
        if (numberSelected.isBlank()) {
            System.out.println("You chickened out. You chicken... Planet's dead. Hope you're happy.");
            return BoardMovementResult.GAME_OVER;
        }

        System.out.println("\nOK, back to the game...");
        return BoardMovementResult.SAME_PLAYER_TURN;
    }


    private BoardMovementResult rollDice(Player currentPlayer) {
        System.out.println("\n" + currentPlayer.getName() + " is Rolling Dice...");

        final int result = dice.diceRoll();
        final Area currentPosition = currentPlayer.getCurrentPosition();
        if (board.wouldPassStartArea(currentPosition, result)) {
            final int regularGrant = board.getStartArea().getRegularGrant();
            System.out.printf("You passed the start area. You receive the regular grant of %d resources.\n", regularGrant);
            currentPlayer.updateResourcesByAmount(regularGrant);
        }

        return currentPlayer.moveToArea(board.getNextArea(currentPosition, result), this);
    }

    private BoardMovementResult displayAreaDevelopmentMenu(final Player currentPlayer, final List<DevelopableArea> developableAreas) {
        if (developableAreas.isEmpty()) {
            return BoardMovementResult.SAME_PLAYER_TURN;
        }

        Scanner input = new Scanner(System.in);
        System.out.println("\nYou are eligible to develop within these areas.");
        boolean selected = false;
        while (!selected) {
            System.out.println("\nPlease select which area you wish to develop or press 'q' to return: ");
            PrintUtils.printDevelopableAreasList(developableAreas);

            final String inputString = input.nextLine();
            if (inputString.equals("q")) {
                return BoardMovementResult.SAME_PLAYER_TURN;
            }

            int areaSelected;
            try {
                areaSelected = Integer.parseInt(inputString);
            } catch (final NumberFormatException e) {
                System.out.println("Invalid option selected");
                continue;
            }

            if (areaSelected > developableAreas.size()) {
                System.out.println("Invalid option selected");
                continue;
            }

            DevelopableArea areaToDevelop = developableAreas.get(areaSelected - 1);
            System.out.println("\nYou have selected " + areaToDevelop.getName() + " to develop");
            areaToDevelop.developArea(currentPlayer);
            selected = true;
        }

        return BoardMovementResult.NEXT_PLAYER_TURN;
    }

    /**
     * Prints the current game status showing players' names, resources, inv points and owned areas
     * @param players - the list of active players
     */
    private void displayGameStats(final List<Player> players) {
        PrintUtils.printGameInfoHeader(totalInvestment, MAX_POINTS);

        for (Player p : players) {
            PrintUtils.printPlayerStats(p);
            PrintUtils.printLineDiv();
        }
    }

    private void stop() {
        status = GameStatus.STOPPED;
        System.out.println("\nGame Over!");
        System.out.println("\n----------");
    }

    private void run() {
        status = GameStatus.RUNNING;
        System.out.println("\nGame Running");
        System.out.println("------------");
    }

    private boolean isGameRunning() {
        return status == GameStatus.RUNNING;
    }

    private boolean isInvestmentPointsWithinLimit() {
        if (getPoints() > MAX_POINTS) {
            System.out.println("Max investment points reached! You saved the planet! You deserve a hot chocolate.");
            stop();
        }
        return isGameRunning();
    }

    private List<Player> initiateActivePlayers() {
        return new ArrayList<>(players);
    }
}
