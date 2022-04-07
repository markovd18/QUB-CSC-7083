package uk.qub.se.board;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import uk.qub.se.board.area.Area;
import uk.qub.se.board.area.factory.AreaFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BoardLoader {

    public static final String FIELD_AREA_ENTRY_PREFIX = "field";
    private final File file;

    public BoardLoader(final File file) {
        this.file = file;
        if (file == null || !file.exists() || !isJsonFile(file)) {
            throw new IllegalArgumentException("File with board has to exist and be a json file");
        }
    }

    private boolean isJsonFile(final File file) {
        return file.getName().endsWith(".json");
    }

    public Board loadBoard() throws IOException {
        var objectMapper = new ObjectMapper();
        var jsonTree = objectMapper.readTree(file);

        List<Area> areas = new ArrayList<>();
        List<Field> fields = new ArrayList<>();

        for (var itr = jsonTree.fields(); itr.hasNext(); ) {
            var entry = itr.next();

            if (isEntryFieldArea(entry)) {
                var field = objectMapper.readValue(objectMapper.writeValueAsString(entry.getValue()), Field.class);
                fields.add(field);
                areas.addAll(field.getAreas());
            } else {
                Area area = AreaFactory.getInstance().construct(entry.getKey(), objectMapper.writeValueAsString(entry.getValue()), objectMapper);
                areas.add(area);
            }

        }

        return new Board(areas, fields);
    }

    private boolean isEntryFieldArea(final Map.Entry<String, JsonNode> entry) {
        return entry.getKey().startsWith(FIELD_AREA_ENTRY_PREFIX);
    }
}
