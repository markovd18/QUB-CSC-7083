package uk.qub.se.board.area;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StartAreaTest {

    @ParameterizedTest
    @MethodSource("provideInvalidDeps")
    public void throwsWhenInvalidDepsPassed(final Integer initialResources, final Integer regularGrant,
                                            final Class<? extends Throwable> exception, final String message) {
        assertThrows(exception, () -> new StartArea(initialResources, regularGrant), message);
    }

    @Test
    public void doesNotThrowWhenValidDepsPassed() {
        assertDoesNotThrow(() -> new StartArea(15, 156),
                "Constructor should not throw when valid dependencies passed");
    }

    @Test
    public void registerToFactoryDoesNotThrow_whenNullAreaFactoryPassed() {
        assertDoesNotThrow(() -> StartArea.registerToFactory(null),
                "Passing null area factory should not throw but just return");
    }

    public static Stream<Arguments> provideInvalidDeps() {
        return Stream.of(
                Arguments.of(null, 15, IllegalStateException.class,
                        "Constructor should throw IllegalState when null initial resources passed"),
                Arguments.of(-8915, 123, IllegalStateException.class,
                        "Constructor should throw IllegalState when negative initial resources passed"),
                Arguments.of(834, -156, IllegalStateException.class,
                        "Constructor should throw IllegalState when negative regular grant passed")
        );
    }
}
