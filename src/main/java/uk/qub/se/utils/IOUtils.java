package uk.qub.se.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class IOUtils {

    public static InputStream toInputStream(final String string) {
        if (string == null) {
            throw new IllegalArgumentException("String may not be null while creating input stream");
        }

        return new ByteArrayInputStream(string.getBytes());
    }
}
