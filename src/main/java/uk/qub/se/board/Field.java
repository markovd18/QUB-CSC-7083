package uk.qub.se.board;

import uk.qub.se.board.area.FieldArea;

import java.util.List;

public class Field {

    private String name;

    private List<FieldArea> areas;

    public Field() {
    }

    public Field(final String name, final List<FieldArea> areas) {
        this.name = name;
        this.areas = areas;
        validate();
    }

    public void validate() {
        if (name == null || name.isBlank()) {
            throw new IllegalStateException("Field name may not be null nor blank");
        }

        if (areas == null || areas.isEmpty()) {
            throw new IllegalStateException("Field has to consist of at least one area");
        }
    }

    public String getName() {
        return name;
    }

    public List<FieldArea> getAreas() {
        return areas;
    }

}
