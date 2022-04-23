package uk.qub.se.board;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import uk.qub.se.board.area.Area;
import uk.qub.se.board.area.StartArea;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

public class BoardTest {

    @Test
    public void throwsWhenNullAreasPassed() {
        assertThrows(IllegalArgumentException.class, () -> new Board(null, null),
                "Constructor should throw IllegalArgument when null areas passed");
    }

    @Test
    public void throwsWhenNotEnoughAreasPassed() {
        var areas = List.of(mock(Area.class));
        assertThrows(IllegalArgumentException.class, () -> new Board(areas, null));
    }

    @Test
    public void doesNotThrowWhenValidDependenciesPassed() {
        final List<Area> areas = prepareAreas();
        assertDoesNotThrow(() -> new Board(areas, null), "Valid constructor call should not throw");
    }

    @Test
    public void throwsIllegalArgument_whenNegativeStepsPassed() {
        final List<Area> areas = prepareAreas();
        var board = new Board(areas, null);
        assertThrows(IllegalArgumentException.class, () -> board.getNextArea(areas.get(0), -5),
                "Passing negative number of steps has to throw IllegalArgument");
    }

    @Test
    public void returnsNullWhenUnknownAreaPassed() {
        final List<Area> areas = prepareAreas();
        var board = new Board(areas, null);

        final Area nextArea = board.getNextArea(mock(Area.class), 4);
        assertNull(nextArea, "Passing unknown area has to return null");
    }

    @Test
    public void throwsIllegalArgument_whenNullAreaPassed() {
        final List<Area> areas = prepareAreas();
        var board = new Board(areas, null);

        assertThrows(IllegalArgumentException.class, () -> board.getNextArea(null, 7),
                "Passing null as reference area has to throw IllegalArgument");
    }

    @ParameterizedTest
    @MethodSource("provideAreasAndSteps")
    public void findsCorrectAreaWithValidSteps(final Integer steps, final List<Area> areas,
                                                      final Area referenceArea, final Area expected) {
        var board = new Board(areas, null);
        final Area result = board.getNextArea(referenceArea, steps);
        assertEquals(expected, result, "Expected area has to match the found area");
    }

    private static List<Area> prepareAreas() {
        final List<Area> areas = new ArrayList<>(Board.MIN_AREAS_COUNT);
        areas.add(mock(StartArea.class));

        for (int i = 0; i < (Board.MIN_AREAS_COUNT - 1); i++) {
            areas.add(mock(Area.class));
        }

        return areas;
    }

    public static Stream<Arguments> provideAreasAndSteps() {
        final List<Area> areas = prepareAreas();

        final Area reference0 = areas.get(0);

        return Stream.of(
                Arguments.of(0, areas, reference0, reference0),
                Arguments.of(3, areas, areas.get(2), areas.get(5)),
                Arguments.of(5, areas, areas.get(6), areas.get(11)),
                Arguments.of(4, areas, areas.get(10), areas.get((10 + 4) % Board.MIN_AREAS_COUNT))
        );
    }
}
