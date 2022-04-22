package uk.qub.se.board;

import uk.qub.se.board.area.Area;

import java.util.List;

public class Board {

    public static final int MIN_AREAS_COUNT = 8;
    private final List<Area> areas;

    private final List<Field> fields;

    public Board(final List<Area> areas, final List<Field> fields) {
        if (areas == null || areas.size() < MIN_AREAS_COUNT) {
            throw new IllegalArgumentException("Board has to consist of at least " + MIN_AREAS_COUNT + " areas");
        }

        this.areas = areas;
        this.fields = fields;
    }

    public Area getNextArea(final Area currentArea, final int steps) {
        if (steps < 0) {
            throw new IllegalArgumentException("Number of steps may not be negative number");
        }
        if (currentArea == null) {
            throw new IllegalArgumentException("Current area reference may not be null");
        }

        int indexOfCurrent = areas.indexOf(currentArea);
        if (indexOfCurrent < 0) {
            return null;
        }

        return areas.get((indexOfCurrent + steps) % areas.size());
    }

    //adding a method to get startArea
    public Area getStartArea ( ) {
        List<Area> areas = this.getAreas();
        Area startArea = areas.get(0);
        return startArea;
    }
    public List<Area> getAreas() {
        return areas;
    }

    public List<Field> getFields() {
        return fields;
    }
}
