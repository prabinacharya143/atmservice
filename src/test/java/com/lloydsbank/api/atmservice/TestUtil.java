package com.lloydsbank.api.atmservice;

import java.io.File;
import java.util.Objects;

/**
 * The type Test util.
 */
public class TestUtil {

    /**
     * Read file from resource file.
     *
     * @param fileName the file name
     * @return the file
     */
    public File readFileFromResource(String fileName) {
        return new File(Objects.requireNonNull(getClass().getClassLoader().getResource(fileName).getFile()));
    }
}
