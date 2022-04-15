package uk.qub.se.board.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import uk.qub.se.utils.ReflectionsFactory;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ReflectionsFactoryTest {

    private ReflectionsFactory reflectionsFactory;

    @BeforeEach
    public void init() {
        reflectionsFactory = new ReflectionsFactory();
    }

    @Test
    public void findDerivedClassesReturnsNull_whenNullPassed() {
        assertNull(reflectionsFactory.findDerivedClasses(null),
                "Passing null as a subclass should return null");
    }

    @ParameterizedTest
    @MethodSource("provideClassesAndDerivedCounts")
    public void findDerivedClassesReturnsCorrectNumberOfDerivedClasses(final Class<?> clazz, final Integer expectedCount) {
        assertEquals(expectedCount, reflectionsFactory.findDerivedClasses(clazz).size(),
                "Number of found derived classes should match the actual number of derived classes");
    }

    private static Stream<Arguments> provideClassesAndDerivedCounts() {
        return Stream.of(
                Arguments.of(ReflectionsFactory.class, 0),
                Arguments.of(TestIface.class, 1)
        );
    }

    interface TestIface {

    }

    @SuppressWarnings("unused")
    static class TestClass implements TestIface {

    }
}
