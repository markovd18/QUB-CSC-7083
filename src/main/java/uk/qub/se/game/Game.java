package uk.qub.se.game;

import uk.qub.se.board.Board;
import uk.qub.se.board.area.Area;
import uk.qub.se.dice.Dice;
import uk.qub.se.player.Player;

import java.util.*;

public class Game {

    private final List<Player> players;

    private final Board board;

    private final Random random;

    private Dice dice;

    private Area area;

    //NUMBER TO BE CHANGED, FOR TESTING GAME LOOP ONLY
    private static final int MAX_POINTS = 20;

    private GameStatus status;

    private int totalInvestment = 0;

    public Game(final List<Player> players, final Board board, final Random random, final Dice dice) {
        validateDependencies(players, board, random, dice);
        this.players = players;
        this.board = board;
        this.random = random;
        this.dice = dice;
    }

    private void validateDependencies(final List<Player> players, final Board board, final Random random, final Dice dice) {
        if (players == null || players.isEmpty() || players.size() < 2) {
            throw new IllegalArgumentException("At least 2 players are required to play the game");
        }
        if (board == null) {
            throw new IllegalArgumentException("Board may not be null");
        }
        if (random == null) {
            throw new IllegalArgumentException("Random generator may not be null");
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

    private void addPoints(int pointsToAdd){
        totalInvestment = totalInvestment + pointsToAdd;
    }

    public void startGame() {
        final List<Player> activePlayers = initiateActivePlayers();
        //TODO Game loop
        //set GameStatus to RUNNING
        run();

        //sets all players initial currentPosition to startArea
        for(Player player : activePlayers){
            player.moveToArea(board.getStartArea());
        }
        //show starting stats
        displayGameStats(activePlayers);
        //iterate over each player & Display the menu allowing each player to choose their next move whilst game
        //is in play only
         do{
             for(Player player : activePlayers){
                 if(isGameRunning() && isInvestmentPointsWithinLimit()){
                     displayMenu(activePlayers, player);
                 }
             }
         }while(isGameRunning() && isInvestmentPointsWithinLimit());

        System.out.println("Game has ended with final stats:");
        System.out.println("-------------------------------");
        displayGameStats(activePlayers);
    }

    private void displayMenu(List<Player> activePlayers, Player currentPlayer) {
        boolean actionTaken = false;
        Scanner scanner = new Scanner(System.in);   // TODO replace with UI API calls

        while (!actionTaken) {
            System.out.println( "\n " + currentPlayer.getName() + " Enter your next move:");
            System.out.println("--------------------------------");
            System.out.println("1. Display Stats");
            System.out.println("2. Roll Dice");
            System.out.println("3. Quit Game");

            final String userInput = scanner.nextLine();

            switch (userInput) {
                case "1" -> displayGameStats(activePlayers);
                case "2" -> {
                    rollDice(currentPlayer);
                    actionTaken = true;
                }
                case "3" -> {
                    actionTaken=quitGame();

                }
                default -> System.out.println("Invalid option entered.");
            }
        }
    }


    private boolean quitGame() {
        boolean actionTaken = false;
        String numberSelected;

        System.out.println("\nAre you sure you want to quit the game?\nPress 1 to quit\nPress any other key to continue");
        Scanner userInput = new Scanner(System.in);
        numberSelected = userInput.nextLine();

        if (numberSelected.equals("1")) {
            stop();
            return true;
        } else {
            System.out.println("\nOK, back to the game...");
            return false;
        }

    }


    private void rollDice(Player currentPlayer) {
        System.out.println(currentPlayer.getName() + " is Rolling Dice...");

        //**TO BE DELETED**//
        currentPlayer.updateInvestmentPointsByAmount(5);
        addPoints(5);

        System.out.println(currentPlayer);
        //**TO BE DELETED**//
        int result = dice.diceRoll();
        System.out.println(currentPlayer);
        currentPlayer.moveToArea(board.getNextArea(currentPlayer.getCurrentPosition(), result));


    }


    /**
     * Prints the current game status showing players' names, resources, inv points and owned areas
     * @param players - the list of active players
     */
    private void displayGameStats(List<Player> players) {
        String lineDiv = "----------------------------------------------------------------------";
        System.out.printf ("|%-15s |%-15s |%-10s |%-10s |%-15s\n", "PLAYER NAME", "CURRENT POS", "INV POINTS", "RESOURCES", "OWNED AREAS");
        System.out.println(lineDiv);
        for (Player p : players){
            String name = p.getName();
            String pos = p.getCurrentPosition().getName();
            int invpts = p.getInvestmentPoints();
            int res = p.getResources();
            System.out.printf ("|%-15s |%-15s |%-10d |%-10d |", name, pos, invpts, res);

            Set<Area> playerOwnedAreas = p.getOwnedAreas();
                for (Area a : playerOwnedAreas){
                    String areaName = a.getName();
                    System.out.printf ("%-15s | \n|%-15s |%-15s |%-10s |%-10s", areaName, "", "", "", "");
                }


            System.out.println("\n"+lineDiv);
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
            System.out.println("Max investment points reached");
            stop();
        }
        return isGameRunning();

    }



    private List<Player> initiateActivePlayers() {
        final List<Player> activePlayers = new ArrayList<>();
        activePlayers.addAll(players);
        return activePlayers;
    }

}
