package uk.qub.se.board;

import uk.qub.se.board.area.Area;
import uk.qub.se.board.area.FieldArea;

import java.util.List;

public class Field {

    String name;

    List<FieldArea> areas;

    public Field() {
    }

    public Field(final String name, final List<FieldArea> areas) {
        this.name = name;
        this.areas = areas;
    }

    public void validate() {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Field name may not be null nor blank");
        }

        if (areas == null || areas.isEmpty()) {
            throw new IllegalArgumentException("Field has to consist of at least one area");
        }

        validateAreas();
    }

    private void validateAreas() {
        for (final Area area : areas) {
            area.validate();
        }
    }
    public String getName() {
        return name;
    }

    public List<FieldArea> getAreas() {
        return areas;
    }

}
