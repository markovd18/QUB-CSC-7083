package uk.qub.se.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ValidationUtils {

    public static void validateAreaFactoryMethodParams(final String json, final ObjectMapper mapper) {
        if (json == null) {
            throw new IllegalArgumentException("Json may not be null");
        }
        if (mapper == null) {
            throw new IllegalArgumentException("Object mapper may not be null");
        }
    }
}
