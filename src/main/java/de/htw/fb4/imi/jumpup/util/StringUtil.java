/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.util;

/**
 * <p>General String utilities.</p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 24.11.2014
 *
 */
public class StringUtil
{
    /**
     * Build a string by an array of {@link String}.<br />
     * The resulting string will contain new line characters (\n).
     * 
     * @param strArray
     * @return the builded string
     */
    public static String buildString( final String[] strArray, final String delimiter ) {
        final StringBuilder builder = new StringBuilder();

        int lineNum = 1;
        for( final String line : strArray ) {
            if( null != line ) {
                builder.append( line
                        + ( ( strArray.length == lineNum ) ? "" : delimiter ) );

                lineNum++;
            }
        }

        return builder.toString();
    }
}
