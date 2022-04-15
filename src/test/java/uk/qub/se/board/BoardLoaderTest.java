package uk.qub.se.board;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import uk.qub.se.board.area.factory.AreaFactory;
import uk.qub.se.utils.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BoardLoaderTest {

    @ParameterizedTest
    @MethodSource("provideIllegalDependencies")
    public void throwsWhenInvalidDependenciesPassed(final InputStream inputStream, final AreaFactory areaFactory,
                                                    final ObjectMapper objectMapper,
                                                    final Class<? extends Throwable> exception, final String message) {
        assertThrows(exception, () -> new BoardLoader(inputStream, areaFactory, objectMapper), message);
    }

    @Test
    public void doesNotThrowWhenValidDependenciesPassed() {
        assertDoesNotThrow(() -> new BoardLoader(IOUtils.toInputStream("valid"), mock(AreaFactory.class), mock(ObjectMapper.class)),
                "Constructor should not throw when valid dependencies passed");
    }

    @Test
    public void throwsIOException_whenObjectMapperFailsToReadJson() throws JsonProcessingException {
        final var illegalJson = "Hello, world!";
        final var objectMapper = mock(ObjectMapper.class);
        when(objectMapper.readTree(illegalJson)).thenThrow(JsonProcessingException.class);

        final BoardLoader loader = new BoardLoader(IOUtils.toInputStream(illegalJson), mock(AreaFactory.class), objectMapper);
        assertThrows(IOException.class, loader::loadBoard, "Board loading should throw IOException when illegal format passed");
    }

    @Test
    public void throwsIOException_whenConfigurationIsNotJson() throws JsonProcessingException {
        final var illegalJson = "Hello, world!";
        final var objectMapper = mock(ObjectMapper.class);
        when(objectMapper.readTree(illegalJson)).thenReturn(null);

        final BoardLoader loader = new BoardLoader(IOUtils.toInputStream(illegalJson), mock(AreaFactory.class), objectMapper);
        assertThrows(IOException.class, loader::loadBoard, "Board loading should throw IOException when illegal format passed");
    }

    @Test
    public void throwsIllegalState_whenIllegalAreaNameConfigured() throws IOException {
        final var areaFactory = mock(AreaFactory.class);
        final var objectMapper = mock(ObjectMapper.class);
        final var jsonTree = mock(JsonNode.class);
        final var iterator = mock(Iterator.class);
        final var entry = mock(Map.Entry.class);
        final var entryValue = mock(JsonNode.class);

        final var key = "\"not_start\"";
        final var jsonBody = "{}";
        final var json = "{\n" + key + ": " + jsonBody + "\n}";
        final InputStream inputStream = IOUtils.toInputStream(json);

        when(objectMapper.readTree(inputStream)).thenReturn(jsonTree);
        //noinspection unchecked
        when(jsonTree.fields()).thenReturn(iterator);
        when(iterator.hasNext()).thenReturn(true);
        when(iterator.next()).thenReturn(entry);
        when(entry.getKey()).thenReturn(key);
        when(entry.getValue()).thenReturn(entryValue);
        when(objectMapper.writeValueAsString(entryValue)).thenReturn(jsonBody);
        when(areaFactory.construct(key, jsonBody, objectMapper)).thenThrow(IllegalArgumentException.class);

        final var loader = new BoardLoader(inputStream, areaFactory, objectMapper);

        assertThrows(IllegalStateException.class, loader::loadBoard,
                "Loading board configuration with invalid area name should throw IllegalState");
    }



    private static Stream<Arguments> provideIllegalDependencies() {
        return Stream.of(
                Arguments.of(null, mock(AreaFactory.class), mock(ObjectMapper.class), IllegalArgumentException.class,
                        "Constructor should throw IllegalArgument when null input stream passed"),
                Arguments.of(IOUtils.toInputStream("empty"), null, mock(ObjectMapper.class), IllegalArgumentException.class,
                        "Constructor should throw IllegalArgument when null area factory passed"),
                Arguments.of(IOUtils.toInputStream("empty"), mock(AreaFactory.class), null, IllegalArgumentException.class,
                        "Constructor should throw IllegalArgument when null object mapper passed")
        );
    }
}
