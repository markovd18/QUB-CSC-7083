package uk.qub.se.board;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import uk.qub.se.utils.IOUtils;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BoardLoaderTest {

    @Test
    public void throwsWhenNullInputStreamPassed() {
        assertThrows(IllegalArgumentException.class, () -> new BoardLoader(null),
                "Constructor should throw IllegalArgument when null input stream passed");
    }

    @Test
    public void doesNotThrowWhenInputStreamPassed() {
        assertDoesNotThrow(() -> new BoardLoader(IOUtils.toInputStream("empty")),
                "Constructor should not throw when valid dependencies passed");
    }

    @ParameterizedTest
    @MethodSource("provideIllegalJsons")
    public void throwsIOExceptionWhenIllegalJsonPassed(final String illegalJson) {
        final BoardLoader loader = new BoardLoader(IOUtils.toInputStream(illegalJson));
        assertThrows(IOException.class, loader::loadBoard, "Board loading should throw IOException when illegal json passed");
    }

    @ParameterizedTest
    @MethodSource("provideIllegalConfigurations")
    public void throwsIllegalStateException_whenIllegalConfigurationPassed(final String illegalConfiguration) {
        final BoardLoader loader = new BoardLoader(IOUtils.toInputStream(illegalConfiguration));
        assertThrows(IllegalStateException.class, loader::loadBoard, "Board loading should throw IllegalState when illegal config passed");
    }

    @Test
    public void createsBoardWhenValidConfigurationPassed() throws IOException {
        final BoardLoader loader = new BoardLoader(IOUtils.toInputStream(prepareValidConfiguration()));
        final Board board = loader.loadBoard();
        assertNotNull(board, "Created board from valid config may not be null");
    }

    private String prepareValidConfiguration() {
        return """
                {
                   "start": {
                     "initialResources": 22,
                     "regularGrant": 10
                   },
                   "field_1": {
                     "name": "green",
                     "areas": [
                       {
                         "name": "recycling",
                         "catchPhrase": "Your research in to awesome recycling tech cuts down waste across the globe!",
                         "costs": {
                           "initialCost": {
                             "resourceCost": 12,
                             "investmentPointsReward": 5
                           },
                           "developmentCost": {
                             "resourceCost": 12,
                             "investmentPointsReward": 5
                           },
                           "masterDevelopmentCost": {
                             "resourceCost": 12,
                             "investmentPointsReward": 5
                           },
                           "investmentCost": {
                             "resourceCost": 12,
                             "investmentPointsReward": 5
                           }
                         }
                       },
                       {
                         "name": "re-forestation",
                         "catchPhrase": "You research new ways to plant bigger, better trees witch suck all the carbon out of the air.",
                         "costs": {
                           "initialCost": {
                             "resourceCost": 12,
                             "investmentPointsReward": 5
                           },
                           "developmentCost": {
                             "resourceCost": 12,
                             "investmentPointsReward": 5
                           },
                           "masterDevelopmentCost": {
                             "resourceCost": 12,
                             "investmentPointsReward": 5
                           },
                           "investmentCost": {
                             "resourceCost": 12,
                             "investmentPointsReward": 5
                           }
                         }
                       },
                       {
                         "name": "Low carbon agriculture",
                         "catchPhrase": "You research new ways to produce food which dramatically reduces all the carbon produced by farming.",
                         "costs": {
                           "initialCost": {
                             "resourceCost": 12,
                             "investmentPointsReward": 5
                           },
                           "developmentCost": {
                             "resourceCost": 12,
                             "investmentPointsReward": 5
                           },
                           "masterDevelopmentCost": {
                             "resourceCost": 12,
                             "investmentPointsReward": 5
                           },
                           "investmentCost": {
                             "resourceCost": 12,
                             "investmentPointsReward": 5
                           }
                         }
                       }
                     ]
                   },
                   "empty_area": {},
                   "field_2": {
                     "name": "wind",
                     "areas": [
                       {
                         "name": "Wind power",
                         "catchPhrase": "Your research into wind turbines makes them more powerful, more efficient and the word can rely on them for its power needs!",
                         "costs": {
                           "initialCost": {
                             "resourceCost": 12,
                             "investmentPointsReward": 5
                           },
                           "developmentCost": {
                             "resourceCost": 12,
                             "investmentPointsReward": 5
                           },
                           "masterDevelopmentCost": {
                             "resourceCost": 12,
                             "investmentPointsReward": 5
                           },
                           "investmentCost": {
                             "resourceCost": 12,
                             "investmentPointsReward": 5
                           }
                         }
                       },
                       {
                         "name": "Carbon capture",
                         "catchPhrase": "You discover new tech to pull carbon out of the atmosphere - saving everyone's ass",
                         "costs": {
                           "initialCost": {
                             "resourceCost": 12,
                             "investmentPointsReward": 5
                           },
                           "developmentCost": {
                             "resourceCost": 12,
                             "investmentPointsReward": 5
                           },
                           "masterDevelopmentCost": {
                             "resourceCost": 12,
                             "investmentPointsReward": 5
                           },
                           "investmentCost": {
                             "resourceCost": 12,
                             "investmentPointsReward": 5
                           }
                         }
                       }
                     ]
                   },
                   "field_3": {
                     "name": "water",
                     "areas": [
                       {
                         "name": "Hydro electric power",
                         "catchPhrase": "Hydro catchPhrase",
                         "costs": {
                           "initialCost": {
                             "resourceCost": 12,
                             "investmentPointsReward": 5
                           },
                           "developmentCost": {
                             "resourceCost": 12,
                             "investmentPointsReward": 5
                           },
                           "masterDevelopmentCost": {
                             "resourceCost": 12,
                             "investmentPointsReward": 5
                           },
                           "investmentCost": {
                             "resourceCost": 12,
                             "investmentPointsReward": 5
                           }
                         }
                       },
                       {
                         "name": "Hydrogen production",
                         "catchPhrase": "Hydrogen catchPhrase",
                         "costs": {
                           "initialCost": {
                             "resourceCost": 12,
                             "investmentPointsReward": 5
                           },
                           "developmentCost": {
                             "resourceCost": 12,
                             "investmentPointsReward": 5
                           },
                           "masterDevelopmentCost": {
                             "resourceCost": 12,
                             "investmentPointsReward": 5
                           },
                           "investmentCost": {
                             "resourceCost": 12,
                             "investmentPointsReward": 5
                           }
                         }
                       }
                     ]
                   }
                 }
                 """;
    }

    public static Stream<Arguments> provideIllegalJsons() {
        return Stream.of(
                Arguments.of("{"),
                Arguments.of("}"),
                Arguments.of("{ param"),
                Arguments.of("{ param: }"),
                Arguments.of("{ \"param\": }"),
                Arguments.of("Hello, world!")
        );
    }

    public static Stream<Arguments> provideIllegalConfigurations() {
        return Stream.of(
                Arguments.of("{" +
                        "\"not_start\": {\"initialResources\": 22,\"regularGrant\": 50}," +
                        "\"field_1\": {\"name\": \"test field\", \"areas\": [" +
                            "{\"name\": \"test area\", \"catchPhrase\": \"catch me\"}" +
                        "]}" +
                        "}"),
                Arguments.of("{" +
                        "\"start\": {\"initialResources\": 22,\"regularGrant\": 50}," +
                        "\"fild\": {}" +
                        "}")
        );
    }
}
