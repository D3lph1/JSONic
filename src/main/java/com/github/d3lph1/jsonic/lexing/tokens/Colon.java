package com.github.d3lph1.jsonic.lexing.tokens;

import java.lang.String;

/**
 * Represents a ":" lexeme.
 *
 * @author D3lph1
 */
public class Colon extends Token
{
    /**
     * Length of ":".
     */
    public static final int LEXEME_LENGTH = 1;

    public Colon(int position)
    {
        super(position);
    }

    @Override
    public String toString()
    {
        return ":";
    }
}
