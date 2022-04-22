package uk.qub.se.game;

import uk.qub.se.board.Board;
import uk.qub.se.board.Field;
import uk.qub.se.board.area.Area;
import uk.qub.se.dice.Dice;
import uk.qub.se.player.Player;
import uk.qub.se.utils.DevelopmentCandidates;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class Game {

    private final List<Player> players;

    private final Board board;

    private Dice dice;


    //NUMBER TO BE CHANGED, FOR TESTING GAME LOOP ONLY
    private static final int MAX_POINTS = 20;

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

        int result = dice.diceRoll();
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

    /**
     * Lists through all areas owned by {@code player}, checks whether he owns some {@link Field} entirely and
     * returns all owned areas that are part of those fields.
     *
     * This method is to be used by the {@code developArea} functionality.
     * @param player player whose areas to check
     * @return list of areas that may be developed
     */
    private List<Area> getDevelopableAreas(final Player player) {
        final Set<Area> remainingAreas = new HashSet<>(player.getOwnedAreas());
        final Set<Field> fields = getParentFields(remainingAreas);

        final Map<Area, DevelopmentCandidates> candidatesMap = createCandidatesMap(remainingAreas, fields);
        checkCandidates(remainingAreas, candidatesMap);

        return collectDevelopableAreas(candidatesMap);
    }

    private Set<Field> getParentFields(final Set<Area> areas) {
        return areas.stream()
                .map(board::getParentField)
                .collect(Collectors.toSet());
    }

    private List<Area> collectDevelopableAreas(final Map<Area, DevelopmentCandidates> candidatesMap) {
        final List<Area> developableAreas = new LinkedList<>();
        for (DevelopmentCandidates candidates : getDistinctMapValues(candidatesMap)) {
            if (candidates.areAllFound()) {
                developableAreas.addAll(candidates.getFoundCandidates());
            }
        }

        return developableAreas;
    }

    private HashSet<DevelopmentCandidates> getDistinctMapValues(final Map<Area, DevelopmentCandidates> candidatesMap) {
        return new HashSet<>(candidatesMap.values());
    }

    private void checkCandidates(final Set<Area> remainingAreas, final Map<Area, DevelopmentCandidates> candidatesMap) {
        for (Area remainingArea : remainingAreas) {
            candidatesMap.get(remainingArea).checkCandidate(remainingArea);
        }
    }

    private Map<Area, DevelopmentCandidates> createCandidatesMap(final Set<Area> remainingAreas,
                                                                 final Set<Field> fields) {
        final Map<Area, DevelopmentCandidates> candidatesMap = new HashMap<>();
        final Set<Area> tempRemainingAreas = new HashSet<>(remainingAreas);

        for (final Field field : fields) {
            addCandidates(candidatesMap, tempRemainingAreas, field);
        }

        return candidatesMap;
    }

    private void addCandidates(final Map<Area, DevelopmentCandidates> candidatesMap,
                               final Set<Area> tempRemainingAreas,
                               final Field field) {
        final List<Area> fieldAreas = new ArrayList<>(field.getAreas());
        final var candidates = new DevelopmentCandidates(fieldAreas);

        final Iterator<Area> it = tempRemainingAreas.iterator();
        while (it.hasNext()) {
            final Area tempArea = it.next();
            if (board.getParentField(tempArea).equals(field)) {
                candidatesMap.put(tempArea, candidates);
                it.remove();
            }
        }
    }
}
