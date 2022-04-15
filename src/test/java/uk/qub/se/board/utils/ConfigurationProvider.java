package uk.qub.se.board.utils;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class ConfigurationProvider {

    public static Stream<Arguments> provideValidConfiguration() {
        return Stream.of(
                Arguments.of(
                        """
                           {
                               "start":{
                                 "initialResources":22,
                                 "regularGrant":10
                               },
                               "field_1":{
                                 "name":"green",
                                 "areas":[
                                   {
                                     "name":"recycling",
                                     "catchPhrase":"Your research in to awesome recycling tech cuts down waste across the globe!",
                                     "costs":{
                                       "initialCost":{
                                         "resourceCost":12,
                                         "investmentPointsReward":5
                                       },
                                       "developmentCost":{
                                         "resourceCost":12,
                                         "investmentPointsReward":5
                                       },
                                       "masterDevelopmentCost":{
                                         "resourceCost":12,
                                         "investmentPointsReward":5
                                       },
                                       "investmentCost":{
                                         "resourceCost":12,
                                         "investmentPointsReward":5
                                       }
                                     }
                                   },
                                   {
                                     "name":"re-forestation",
                                     "catchPhrase":"You research new ways to plant bigger, better trees witch suck all the carbon out of the air.",
                                     "costs":{
                                       "initialCost":{
                                         "resourceCost":12,
                                         "investmentPointsReward":5
                                       },
                                       "developmentCost":{
                                         "resourceCost":12,
                                         "investmentPointsReward":5
                                       },
                                       "masterDevelopmentCost":{
                                         "resourceCost":12,
                                         "investmentPointsReward":5
                                       },
                                       "investmentCost":{
                                         "resourceCost":12,
                                         "investmentPointsReward":5
                                       }
                                     }
                                   },
                                   {
                                     "name":"Low carbon agriculture",
                                     "catchPhrase":"You research new ways to produce food which dramatically reduces all the carbon produced by farming.",
                                     "costs":{
                                       "initialCost":{
                                         "resourceCost":12,
                                         "investmentPointsReward":5
                                       },
                                       "developmentCost":{
                                         "resourceCost":12,
                                         "investmentPointsReward":5
                                       },
                                       "masterDevelopmentCost":{
                                         "resourceCost":12,
                                         "investmentPointsReward":5
                                       },
                                       "investmentCost":{
                                         "resourceCost":12,
                                         "investmentPointsReward":5
                                       }
                                     }
                                   }
                                 ]
                               },
                               "empty_area":{
                                \s
                               },
                               "field_2":{
                                 "name":"wind",
                                 "areas":[
                                   {
                                     "name":"Wind power",
                                     "catchPhrase":"Your research into wind turbines makes them more powerful, more efficient and the word can rely on them for its power needs!",
                                     "costs":{
                                       "initialCost":{
                                         "resourceCost":12,
                                         "investmentPointsReward":5
                                       },
                                       "developmentCost":{
                                         "resourceCost":12,
                                         "investmentPointsReward":5
                                       },
                                       "masterDevelopmentCost":{
                                         "resourceCost":12,
                                         "investmentPointsReward":5
                                       },
                                       "investmentCost":{
                                         "resourceCost":12,
                                         "investmentPointsReward":5
                                       }
                                     }
                                   },
                                   {
                                     "name":"Carbon capture",
                                     "catchPhrase":"You discover new tech to pull carbon out of the atmosphere - saving everyone's ass",
                                     "costs":{
                                       "initialCost":{
                                         "resourceCost":12,
                                         "investmentPointsReward":5
                                       },
                                       "developmentCost":{
                                         "resourceCost":12,
                                         "investmentPointsReward":5
                                       },
                                       "masterDevelopmentCost":{
                                         "resourceCost":12,
                                         "investmentPointsReward":5
                                       },
                                       "investmentCost":{
                                         "resourceCost":12,
                                         "investmentPointsReward":5
                                       }
                                     }
                                   }
                                 ]
                               },
                               "field_3":{
                                 "name":"water",
                                 "areas":[
                                   {
                                     "name":"Hydro electric power",
                                     "catchPhrase":"Hydro catchPhrase",
                                     "costs":{
                                       "initialCost":{
                                         "resourceCost":12,
                                         "investmentPointsReward":5
                                       },
                                       "developmentCost":{
                                         "resourceCost":12,
                                         "investmentPointsReward":5
                                       },
                                       "masterDevelopmentCost":{
                                         "resourceCost":12,
                                         "investmentPointsReward":5
                                       },
                                       "investmentCost":{
                                         "resourceCost":12,
                                         "investmentPointsReward":5
                                       }
                                     }
                                   },
                                   {
                                     "name":"Hydrogen production",
                                     "catchPhrase":"Hydrogen catchPhrase",
                                     "costs":{
                                       "initialCost":{
                                         "resourceCost":12,
                                         "investmentPointsReward":5
                                       },
                                       "developmentCost":{
                                         "resourceCost":12,
                                         "investmentPointsReward":5
                                       },
                                       "masterDevelopmentCost":{
                                         "resourceCost":12,
                                         "investmentPointsReward":5
                                       },
                                       "investmentCost":{
                                         "resourceCost":12,
                                         "investmentPointsReward":5
                                       }
                                     }
                                   }
                                 ]
                               }
                             }
                                """
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
