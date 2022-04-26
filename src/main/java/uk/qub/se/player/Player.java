package uk.qub.se.player;

import uk.qub.se.board.area.Area;
import uk.qub.se.board.area.BoardMovementResult;
import uk.qub.se.board.area.DevelopableArea;
import uk.qub.se.board.area.Investment;
import uk.qub.se.game.Game;

import java.util.HashSet;
import java.util.Set;

public class Player {

    private final String name;
    private int resources = 0;
    private double investmentPoints = 0;
    private Area currentPosition;
    private Game currentGame;
    private final Set<DevelopableArea> ownedAreas = new HashSet<>();

    /**
     * constructs a player object
     * @param name - player nickname
     */
    public Player(final String name) { this.name= name; }

    public BoardMovementResult moveToArea(final Area area, Game game) {
        if (area == null) {
            throw new IllegalArgumentException("Area to move to may not be null");
        }
        currentGame = game;
        final BoardMovementResult result = area.acceptPlayer(this);
        currentPosition = area;
        return result;
    }

    @Override
    public String toString() {
        return "Player: " +
                "Name = " + getName()  +
                ", Resources = " + resources +
                ", Development Points = " + investmentPoints;
    }

    public void updateResourcesByAmount(final int amount) {
        setResources(getResources() + amount);
    }


    public void updateInvestmentPointsByAmount(final double amount) {
        setInvestmentPoints(getInvestmentPoints() + amount);

    }

    //getters
    public String getName() { return name; }

    public int getResources() {
        return resources;
    }

    public double getInvestmentPoints() {
        return investmentPoints;
    }

    public Area getCurrentPosition() {

        return currentPosition;
    }

    public Set<DevelopableArea> getOwnedAreas() {

        return ownedAreas;
    }

    //setters
    private void setResources(final int resources) {
        if (resources < 0) {
            throw new IllegalArgumentException("Player's resource amount may not be negative");
        }

        this.resources = resources;
    }

    private void setInvestmentPoints(final double investmentPoints) {
        if (investmentPoints < 0) {
            throw new IllegalArgumentException("Player's investment point amount may not be negative");
        }

        this.investmentPoints = investmentPoints;
    }



    public boolean hasEnoughResources(final int amount) {
        if (amount < 0) {
            return false;
        }

        return resources >= amount;
    }

    public void makeInvestment(final Investment costs) {
        if (costs == null) {
            return;
        }

        updateResourcesByAmount(- costs.resourceCost());
        updateInvestmentPointsByAmount(costs.investmentPointsReward());
        currentGame.addPoints(costs.investmentPointsReward());
    }


    public void addOwnedArea(final DevelopableArea area) {
        if (area == null) {
            return;
        }

        ownedAreas.add(area);
    }
}
