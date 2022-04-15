package uk.qub.se.board;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import uk.qub.se.board.area.factory.AreaFactory;
import uk.qub.se.utils.IOUtils;
import uk.qub.se.utils.ReflectionsFactory;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BoardLoaderIntegrationTest {

    private AreaFactory areaFactory;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void init() {
        areaFactory = new AreaFactory(new ReflectionsFactory());
        objectMapper = new ObjectMapper();
    }

    @ParameterizedTest
    @MethodSource("uk.qub.se.board.utils.ConfigurationProvider#provideIllegalJsons")
    public void throwsIOExceptionWhenIllegalJsonPassed(final String illegalJson) {

        final BoardLoader loader = new BoardLoader(IOUtils.toInputStream(illegalJson), areaFactory, objectMapper);
        assertThrows(IOException.class, loader::loadBoard, "Board loading should throw IOException when illegal json passed");
    }

    @ParameterizedTest
    @MethodSource("uk.qub.se.board.utils.ConfigurationProvider#provideIllegalConfigurations")
    public void throwsWhenIllegalConfigurationPassed(final String illegalConfiguration,
                                                     final Class<? extends Throwable> exception,
                                                     final String message) {
        final var loader = new BoardLoader(IOUtils.toInputStream(illegalConfiguration),
                areaFactory, objectMapper);
        assertThrows(exception, loader::loadBoard, message);
    }

    @ParameterizedTest
    @MethodSource("uk.qub.se.board.utils.ConfigurationProvider#provideValidConfiguration")
    public void createsBoardWhenValidConfigurationPassed(final String validJsonConfig) throws IOException {
        final var loader = new BoardLoader(IOUtils.toInputStream(validJsonConfig),
                areaFactory, objectMapper);
        final Board board = loader.loadBoard();
        assertNotNull(board, "Created board from valid config may not be null");
    }
}
