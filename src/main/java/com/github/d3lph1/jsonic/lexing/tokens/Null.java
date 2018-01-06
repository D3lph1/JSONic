package com.github.d3lph1.jsonic.lexing.tokens;

import java.lang.String;

/**
 * Represents a JSON null lexeme.
 *
 * @author D3lph1
 */
public class Null extends Token
{
    /**
     * Length of "null".
     */
    public static final int LEXEME_LENGTH = 4;

    public Null(int position)
    {
        super(position);
    }

    @Override
    public String toString()
    {
        return "null";
    }
}
