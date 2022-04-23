package uk.qub.se.board;

import uk.qub.se.board.area.Area;
import uk.qub.se.board.area.StartArea;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {

    public static final int MIN_AREAS_COUNT = 12;
    private final List<Area> areas;
    private final StartArea startArea;

    private final List<Field> fields;

    private final Map<Area, Field> areaFieldMap = new HashMap<>();

    public Board(final List<Area> areas, final List<Field> fields) {
        if (areas == null || areas.size() < MIN_AREAS_COUNT) {
            throw new IllegalArgumentException("Board has to consist of at least " + MIN_AREAS_COUNT + " areas");
        }

        if (!isFirstAreaStartArea(areas)) {
            throw new IllegalArgumentException("First area on the board has to be the start area");
        }

        this.startArea = (StartArea) areas.get(0);
        this.areas = areas;
        this.fields = fields;
        initializeAreaMapping();
    }

    private boolean isFirstAreaStartArea(final List<Area> areas) {
        return areas.get(0) instanceof StartArea;
    }

    private void initializeAreaMapping() {
        if (fields == null) {
            return;
        }

        for (final Field field : fields) {
            for (Area area : field.getAreas()) {
                areaFieldMap.put(area, field);
            }
        }
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

    public boolean wouldPassStartArea(final Area initialPosition, final int steps) {
        final int indexOfCurrent = areas.indexOf(initialPosition);
        if (indexOfCurrent < 0) {
            return false;
        }

        return (indexOfCurrent + steps) > areas.size();
    }

    //adding a method to get startArea
    public StartArea getStartArea () {
        return startArea;
    }

    public Field getParentField(final Area area) {
        if (area == null) {
            return null;
        }

        return areaFieldMap.get(area);
    }

    public List<Area> getAreas() {
        return areas;
    }

    public List<Field> getFields() {
        return fields;
    }
}
