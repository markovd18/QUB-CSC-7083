package uk.qub.se.board.utils;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

@SuppressWarnings("unused")
public class ConfigurationProvider {

    public static Stream<Arguments> provideValidConfiguration() {
        return Stream.of(
                Arguments.of(
                        """
                                {
                                  "start": {
                                    "initialResources": 22,
                                    "regularGrant": 10
                                  },
                                  "field_1": {
                                    "name": "Wind element",
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
                                  "empty_area": {},
                                  "field_2": {
                                    "name": "Earth element",
                                    "areas": [
                                      {
                                        "name": "Recycling",
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
                                        "name": "Re-forestation",
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
                                  "random": {},
                                  "field_3": {
                                    "name": "Water element",
                                    "areas": [
                                      {
                                        "name": "Hydro electric power",
                                        "catchPhrase": "Your research team has found a way to use the falling raindrops during rains and storms as a source od renewable energy.",
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
                                        "catchPhrase": "Your scientists made a breakthrough in hydrogen production research which found a way to eliminate hydrogen's ability to cause global warming when released into the atmosphere",
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
                                  "field_4": {
                                    "name": "Fire element",
                                    "areas": [
                                      {
                                        "name": "Forest fires",
                                        "catchPhrase": "Forest fires are inevitable. But you invest in new technologies to prevent them from spreading and douse them ASAP.",
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
                                        "name": "Solar power",
                                        "catchPhrase": "You invest in development of solar panels. Research is making them more efficient and world can rely more on renewable energies!",
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
                                        "name": "Nuclear fusion",
                                        "catchPhrase": "After years of failed attempts, your research team has come up with successful cold fusion experiment. This means major breakthrough in fusion energy field!",
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
                                }"""
                )
        );
    }

    public static Stream<Arguments> provideIllegalJsons() {
        return Stream.of(
                Arguments.of("{"),
                Arguments.of("}"),
                Arguments.of("{ param"),
                Arguments.of("{ param: }"),
                Arguments.of("{ \"param\": }")
        );
    }

    public static Stream<Arguments> provideIllegalConfigurations() {
        return Stream.of(
                Arguments.of("{" +
                                "\"not_start\": {\"initialResources\": 22,\"regularGrant\": 50}," +
                                "\"field_1\": {\"name\": \"test field\", \"areas\": [" +
                                "{\"name\": \"test area\", \"catchPhrase\": \"catch me\"}" +
                                "]}" +
                                "}", IllegalStateException.class,
                        "Creating board should throw IllegalArgument when configuration contains unknown attribute"),
                Arguments.of("{" +
                                "\"start\": {\"initialResources\": 22,\"regularGrant\": 50}," +
                                "\"fild\": {}" +
                                "}", IllegalStateException.class,
                        "Creating board should throw IllegalArgument when configuration contains unknown attribute"),
                Arguments.of("""
                        {
                            "start": {},
                            "field_1": {
                                
                            }
                        }
                        """, IllegalStateException.class,
                        "")
        );
    }
}
