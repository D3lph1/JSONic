package com.github.d3lph1.jsonic.lexing.tokens.bools;

/**
 * Represents a boolean "false" lexeme.
 *
 * @author D3lph1
 */
public class False extends Boolean
{
    /**
     * Length of "false".
     */
    public static final int LEXEME_LENGTH = 5;

    public False(int position)
    {
        super(position);
    }

    @Override
    public String toString()
    {
        return "false";
    }
}
