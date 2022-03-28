package uk.qub.se.player;

public class Player {

    private String name;
    private int resources = 0;
    private int developmentPoints = 0;
    //TODO currentPosition, ownedAreas

    public Player(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
