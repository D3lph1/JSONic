package com.github.d3lph1.jsonic.lexing.tokens.bools;

/**
 * Represents a boolean "true" lexeme.
 *
 * @author D3lph1
 */
public class True extends Boolean
{
    /**
     * Length of "true".
     */
    public static final int LEXEME_LENGTH = 4;

    public True(int position)
    {
        super(position);
    }

    @Override
    public String toString()
    {
        return "true";
    }
}
