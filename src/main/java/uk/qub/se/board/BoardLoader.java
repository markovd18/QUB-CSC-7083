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
    private final AreaFactory areaFactory;
    private final ObjectMapper objectMapper;

    public BoardLoader(final InputStream inputStream, final AreaFactory areaFactory, final ObjectMapper objectMapper) {
        validateDependencies(inputStream, areaFactory, objectMapper);

        this.inputStream = inputStream;
        this.areaFactory = areaFactory;
        this.objectMapper = objectMapper;
    }

    private void validateDependencies(final InputStream inputStream, final AreaFactory areaFactory, final ObjectMapper objectMapper) {
        if (inputStream == null) {
            throw new IllegalArgumentException("Input stream may not be null");
        }
        if (areaFactory == null) {
            throw new IllegalArgumentException("Area factory may not be null");
        }
        if (objectMapper == null) {
            throw new IllegalArgumentException("Object mapper may not be null");
        }
    }

    public Board loadBoard() throws IOException {
        final JsonNode jsonTree = readJsonTree();

        List<Area> areas = new ArrayList<>();
        List<Field> fields = new ArrayList<>();

        for (var itr = jsonTree.fields(); itr.hasNext(); ) {
            var entry = itr.next();

            if (isEntryFieldArea(entry)) {
                loadField(areas, fields, entry);
            } else {
                loadArea(areas, entry);
            }
        }

        return new Board(areas, fields);
    }

    private JsonNode readJsonTree() throws IOException {
        final JsonNode jsonTree = objectMapper.readTree(inputStream);
        if (jsonTree == null) {
            throw new IOException("Provided configuration is not json format");
        }

        return jsonTree;
    }

    private void loadArea(final List<Area> areas, final Map.Entry<String, JsonNode> entry) throws JsonProcessingException {
        Area area = constructArea(objectMapper, entry);
        areas.add(area);
    }

    private void loadField(final List<Area> areas, final List<Field> fields, final Map.Entry<String, JsonNode> entry) throws JsonProcessingException {
        var field = objectMapper.readValue(objectMapper.writeValueAsString(entry.getValue()), Field.class);
        validateField(field);
        fields.add(field);
        areas.addAll(field.getAreas());
    }

    private void validateField(final Field field) {
        field.validate();
        validateAreas(field.getAreas());
    }

    private void validateAreas(final List<? extends Area> areas) {
        for (final Area area : areas) {
            area.validate();
        }
    }

    private Area constructArea(final ObjectMapper objectMapper, final Map.Entry<String, JsonNode> entry) throws JsonProcessingException {
        try {
            return areaFactory.construct(entry.getKey(), objectMapper.writeValueAsString(entry.getValue()), objectMapper);
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("Error while constructing area", e);
        }
    }

    private boolean isEntryFieldArea(final Map.Entry<String, JsonNode> entry) {
        return entry.getKey().startsWith(FIELD_AREA_ENTRY_PREFIX);
    }
}
