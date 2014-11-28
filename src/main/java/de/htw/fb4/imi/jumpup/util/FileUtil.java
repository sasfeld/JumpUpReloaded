/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * </p>
 * 
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 25.11.2014
 * 
 */
public class FileUtil
{
    public static final String WAR_NAME = "JumpUpReloaded.war";

    /**
     * Get the full path to the WEB-INF folder of the application
     */
    public static String getPathToWebInfFolder()
    {
        final String pathToClazz = FileUtil.class.getResource("FileUtil.class")
                .getFile();

        return pathToClazz.replaceAll(WAR_NAME + ".*", "").concat(
                WAR_NAME + "/WEB-INF");
    }

    /**
     * Read a file line based and return a list of String.
     * 
     * @param file
     * @param encoding
     *            the encoding, like "UTF-8" (see {@link Charset} for legal
     *            values)
     * @return
     */
    public static List<String> readFileLineBased(final File file,
            final String encoding)
    {
        try {
            if (null != encoding && !"false".equals(encoding)) {
                Charset.forName(encoding);
            }
        } catch (final UnsupportedCharsetException e) {
            return null;
        }

        if (file == null || !file.exists()) {
            throw new IllegalArgumentException(
                    "readFileLineBased(): The given file is null or doesn't exist!");
        }

        final List<String> lines = new ArrayList<String>();
        BufferedReader reader = null;
        try {
            if (null != encoding && !"false".equals(encoding)) {
                reader = new BufferedReader(new InputStreamReader(
                        new FileInputStream(file), encoding));
            } else {
                reader = new BufferedReader(new FileReader(file));
            }
            String line;
            while (null != (line = reader.readLine())) {
                lines.add(line); // add single line to collection
            }
        } catch (final IOException e) {
            return null;
        } finally { // clean up
            if (null != reader) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    // don't do anything here. an exception was already caught
                    // above
                }
            }
        }

        return lines;
    }

    /**
     * Read a whole file and get a single string including line separators.
     * 
     * @param file
     * @param  the encoding, like "UTF-8" (see {@link Charset} for legal
     *            values)
     * @return
     */
    public static String readFile(final File file, final String encoding)
    {
        final List<String> lines = readFileLineBased(file, encoding);

        final StringBuilder singleStrBuilder = new StringBuilder();
        for (final String line : lines) {
            singleStrBuilder.append(line + "\n");
        }

        return singleStrBuilder.toString();
    }
}
