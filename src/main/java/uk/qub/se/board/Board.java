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
        int boardSize = areas.size();
        int indexOfCurrent = areas.indexOf(currentArea);
        return areas.get((indexOfCurrent + steps) % boardSize);
    }

    public List<Area> getAreas() {
        return areas;
    }

    public List<Field> getFields() {
        return fields;
    }
}
