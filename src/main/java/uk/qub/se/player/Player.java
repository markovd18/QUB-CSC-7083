package uk.qub.se.player;

import uk.qub.se.board.area.Area;
import uk.qub.se.board.area.BoardMovementResult;
import uk.qub.se.board.area.Investment;

import java.util.HashSet;
import java.util.Set;

public class Player {

    private final String name;
    private int resources = 0;
    private int investmentPoints = 0;
    private Area currentPosition;
    private Set<Area> ownedAreas = new HashSet<>();


    /**
     * constructs a player object
     * @param name - player nickname
     */
    public Player(final String name) { this.name= name; }

    public BoardMovementResult moveToArea(final Area area) {
        if (area == null) {
            throw new IllegalArgumentException("Area to move to may not be null");
        }

        return area.acceptPlayer(this);
    }

    //TODO developCurrentArea methods

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

    public void updateInvestmentPointsByAmount(final int amount) {
        setInvestmentPoints(getInvestmentPoints() + amount);
    }

    //getters
    public String getName() { return name; }

    public int getResources() {
        return resources;
    }

    public int getInvestmentPoints() {
        return investmentPoints;
    }

    public Area getCurrentPosition() {

        return currentPosition;
    }

    public Set<Area> getOwnedAreas() {

        return ownedAreas;
    }

    //setters
    private void setResources(final int resources) {
        if (resources < 0) {
            // error state - throw?
        }

        this.resources = resources;
    }

    private void setInvestmentPoints(final int investmentPoints) {
        if (investmentPoints < 0) {
            // error state - throw?
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
    }


    public void addOwnedArea(final Area area) {
        if (area == null) {
            return;
        }

        ownedAreas.add(area);
    }
}
