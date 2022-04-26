package uk.qub.se.board.area;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InvestmentTest {

    @ParameterizedTest
    @MethodSource("provideInvalidDeps")
    public void throwsWhenInvalidDepsPassed(final Integer resourceConst, final double investmentPointsReward,
                                            final Class<? extends Throwable> exception, final String message) {
        assertThrows(exception, () -> new Investment(resourceConst, investmentPointsReward).validate(), message);
    }

    @Test
    public void doesNotThrowWhenValidDepsPassed() {
        assertDoesNotThrow(() -> new Investment(89, 65.0).validate(),
                "Constructor should not throw when valid dependencies passed");
    }

    public static Stream<Arguments> provideInvalidDeps() {
        return Stream.of(
                Arguments.of(null, 15, IllegalStateException.class,
                        "Constructor should throw IllegalState when null resource cost passed"),
                Arguments.of(-5, 89, IllegalStateException.class,
                        "Constructor should throw IllegalState when negative resource cost passed"),
                Arguments.of(98, null, IllegalStateException.class,
                        "Constructor should throw IllegalState when null investment points reward passed"),
                Arguments.of(98, -89, IllegalStateException.class,
                        "Constructor should throw IllegalState when negative investment points reward passed")
        );
    }

}
