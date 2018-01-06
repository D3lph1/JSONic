package com.github.d3lph1.jsonic.lexing.readers;

/**
 * Performs a one-character string reading.
 *
 * @author D3lph1
 */
public class StringReader implements Reader
{
    /**
     * The string from which the reading occurs.
     */
    private String src;

    /**
     * Length of the string.
     */
    private int len;

    /**
     * Index of the readable character.
     */
    private int index = 0;

    /**
     * @param src {@link #src}
     */
    public StringReader(String src)
    {
        this.src = src;
        len = src.length();
    }

    @Override
    public char read()
    {
        if (index < len) {
            return src.charAt(index++);
        }

        // End of string character.
        return (char)-1;
    }
}
