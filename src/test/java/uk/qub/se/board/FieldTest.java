package uk.qub.se.board;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import uk.qub.se.board.area.FieldArea;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

public class FieldTest {

    @ParameterizedTest
    @MethodSource("provideInvalidDeps")
    public void throwsWhenInvalidDepsPassed(final String name, final List<FieldArea> areas,
                                            final Class<? extends Throwable> exception, final String message) {
        assertThrows(exception, () -> new Field(name, areas), message);
    }

    @Test
    public void doesNotThrowWhenValidDependenciesPassed() {
        assertDoesNotThrow(() -> new Field("electricity", List.of(mock(FieldArea.class))),
                "Constructor should not throw when valid dependencies passed");
    }

    public static Stream<Arguments> provideInvalidDeps() {
        return Stream.of(
                Arguments.of(null, List.of(), IllegalStateException.class,
                        "Constructor should throw IllegalState when null name passed"),
                Arguments.of("", List.of(), IllegalStateException.class,
                        "Constructor should throw IllegalState when blank name passed"),
                Arguments.of("test", null, IllegalStateException.class,
                        "Constructor should throw IllegalState when null areas passed"),
                Arguments.of("another one", List.of(), IllegalStateException.class,
                        "Constructor should throw IllegalState when empty areas passed")
        );
    }
}
