package uk.qub.se.game;

import uk.qub.se.board.Board;
import uk.qub.se.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Game {

    private final List<Player> players;

    private final Board board;

    private final Random random;

    //NUMBER TO BE CHANGED, FOR TESTING GAME LOOP ONLY
    private static final int MAX_POINTS = 20;

    private GameStatus status;

    private int totalInvestment = 0;

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
        System.out.println("Outcome of rolling dice:");
        System.out.println(currentPlayer);
        //**TO BE DELETED**//

    }

    private void displayGameStats(List<Player> activePlayers) {
        //template code only for testing game loop, to be completed by Jamie
        System.out.print("\nTotal Investment Points: ");
        System.out.println(totalInvestment);
        System.out.println("-----------------------------");
        System.out.println("\nPlayer stats: ");
        System.out.println("--------------");
        for (Player player : activePlayers) {
            System.out.println("\n " + player.toString());
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
