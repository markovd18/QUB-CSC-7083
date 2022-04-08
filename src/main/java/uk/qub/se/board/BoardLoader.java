package uk.qub.se.board;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import uk.qub.se.board.area.Area;
import uk.qub.se.board.area.factory.AreaFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BoardLoader {

    public static final String FIELD_AREA_ENTRY_PREFIX = "field";
    private final InputStream inputStream;

    public BoardLoader(final InputStream inputStream) {
        if (inputStream == null) {
            throw new IllegalArgumentException("Input stream may not be null");
        }

        this.inputStream = inputStream;
    }

    public Board loadBoard() throws IOException {
        var objectMapper = new ObjectMapper();
        var jsonTree = objectMapper.readTree(inputStream);

        List<Area> areas = new ArrayList<>();
        List<Field> fields = new ArrayList<>();

        for (var itr = jsonTree.fields(); itr.hasNext(); ) {
            var entry = itr.next();

            if (isEntryFieldArea(entry)) {
                var field = objectMapper.readValue(objectMapper.writeValueAsString(entry.getValue()), Field.class);
                field.validate();
                validateAreas(field.getAreas());
                fields.add(field);
                areas.addAll(field.getAreas());
            } else {
                Area area = constructArea(objectMapper, entry);
                areas.add(area);
            }

        }

        return new Board(areas, fields);
    }

    private void validateAreas(final List<? extends Area> areas) {
        for (final Area area : areas) {
            area.validate();
        }
    }

    private Area constructArea(ObjectMapper objectMapper, Map.Entry<String, JsonNode> entry) throws JsonProcessingException {
        try {
            return AreaFactory.getInstance().construct(entry.getKey(), objectMapper.writeValueAsString(entry.getValue()), objectMapper);
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("Error while constructing area", e);
        }
    }

    private boolean isEntryFieldArea(final Map.Entry<String, JsonNode> entry) {
        return entry.getKey().startsWith(FIELD_AREA_ENTRY_PREFIX);
    }
}
