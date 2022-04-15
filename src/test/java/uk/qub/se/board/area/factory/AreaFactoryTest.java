package uk.qub.se.board.area.factory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.qub.se.utils.ReflectionsFactory;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AreaFactoryTest {

    private AreaFactory factory;

    @BeforeEach
    public void init() {
        factory = new AreaFactory(mock(ReflectionsFactory.class));
    }

    @Test
    public void doesNotRegisterNullKey() {
        factory.registerFactory(null, mock(AreaFactoryMethod.class));

        assertThrows(IllegalArgumentException.class,
                () -> factory.construct(null, "", mock(ObjectMapper.class)),
                "Area factory should not register null key and construction should throw");
    }

    @Test
    public void doesNotRegisterNullFactoryMethod() {
        final var key = "key";
        factory.registerFactory(key, null);

        assertThrows(IllegalArgumentException.class,
                () -> factory.construct(key, "json", mock(ObjectMapper.class)),
                "Area factory should not register null factory method and construction should throw");
    }

    @Test
    public void throwsWhenConstructingUnknownKey() {
        assertThrows(IllegalArgumentException.class,
                () -> factory.construct("test", "", mock(ObjectMapper.class)),
                "Area construction should throw when constructing unknown area");
    }

    @Test
    public void throwsWhenJsonProcessingFails() throws JsonProcessingException {
        final var mapper = mock(ObjectMapper.class);
        final var json = "json";
        final var factoryMethod = mock(AreaFactoryMethod.class);
        final var key = "key";

        when(factoryMethod.construct(json, mapper)).thenThrow(JsonProcessingException.class);

        factory.registerFactory(key, factoryMethod);
        assertThrows(AreaConstructionException.class,
                () -> factory.construct(key, json, mapper),
                "Area construction should throw AreaConstruction exception when json processing fails");
    }

    @Test
    public void doesNotThrowWhen_constructingValidArea() {
        final var json = "{ \"test\": 25 }";
        final var key = "empty_area";

        factory.registerFactory(key, mock(AreaFactoryMethod.class));

        assertDoesNotThrow(() -> factory.construct(key, json, mock(ObjectMapper.class)),
                "Constructing valid registered area should not throw");
    }
}
