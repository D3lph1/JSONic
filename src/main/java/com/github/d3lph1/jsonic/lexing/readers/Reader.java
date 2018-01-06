package com.github.d3lph1.jsonic.lexing.readers;

import java.io.IOException;

/**
 * Performs a character read of the data.
 *
 * @author D3lph1
 */
public interface Reader
{
    /**
     * Reads the next character.
     *
     * @return Next character.
     */
    char read() throws IOException;

    /**
     * Reads the next character and checks it to match the passed symbol.
     *
     * @param ch Comparable character.
     * @return The result of comparing the read character with the transmitted character.
     */
    default boolean read(char ch) throws IOException
    {
        return ch == read();
    }
}
