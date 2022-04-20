package uk.qub.se.board.area;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

public class FieldAreaTest {

    @ParameterizedTest
    @MethodSource("provideInvalidDeps")
    public void throwsWhenInvalidDepsPassed(final String name, final String catchPhrase, final AreaCosts costs,
                                            final Class<? extends Throwable> exception, final String message) {
        assertThrows(exception, () -> new FieldArea(name, catchPhrase, costs), message);
    }

    @Test
    public void doesNotThrowWhenValidDepsPassed() {
        assertDoesNotThrow(() -> new FieldArea("name", "Funny", mock(AreaCosts.class)),
                "Constructor should not throw when valid dependencies passed");
    }

    @Test
    public void returnsNull_whenAcceptingNull() {
        assertNull(new FieldArea("name", "Fun", mock(AreaCosts.class)).acceptPlayer(null),
                "Accepting null should return null");
    }

    public static Stream<Arguments> provideInvalidDeps() {
        return Stream.of(
                Arguments.of(null, "Catch me is u can", mock(AreaCosts.class), IllegalStateException.class,
                        "Constructor should throw IllegalState when null name passed"),
                Arguments.of("", "But can you?", mock(AreaCosts.class), IllegalStateException.class,
                        "Constructor should throw IllegalState when blank name passed"),
                Arguments.of("name", null, mock(AreaCosts.class), IllegalStateException.class,
                        "Constructor should throw IllegalState when null catch phrase passed"),
                Arguments.of("nameless", "", mock(AreaCosts.class), IllegalStateException.class,
                        "Constructor should throw IllegalState when blank catch phrase passed"),
                Arguments.of("nameless", "caught me!", null, IllegalStateException.class,
                        "Constructor should throw IllegalState when null costs passed")
                );
    }
}
