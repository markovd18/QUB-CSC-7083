package uk.qub.se.player;

import uk.qub.se.board.area.Area;
import uk.qub.se.board.area.BoardMovementResult;

import java.util.Set;

public class Player {

    private final String name;
    private int resources = 0;
    private int investmentPoints = 0;
    private Area currentPosition;
    private Set<Area> ownedAreas;


    /**
     * constructs a player object
     * @param name
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

    public void updateResources(int res){
        int newRes = getResources() + res;
        if(newRes < 0){
            //PLAYER MUST QUIT THE GAME
        } else{
            setResources(newRes);
        }
    }

    public void updateInvPts(int inv) {
        int newInvPts = getInvPoints() + inv;
        if (newInvPts < 0) {
            //error state
        } else {
            setInvPts(newInvPts);
        }
    }

    //getters
    public String getName() { return name; }

    public int getResources() {
        return resources;
    }

    public int getInvPoints() {
        return investmentPoints;
    }

    public Area getCurrentPosition() { return currentPosition; }

    public Set<Area> getOwnedAreas() {
        return ownedAreas;
    }

    //setters
    private void setResources(int resources) {this.resources = resources; }

    private void setInvPts (int inv) {this.investmentPoints = inv;}






}
