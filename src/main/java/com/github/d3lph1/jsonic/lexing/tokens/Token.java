package com.github.d3lph1.jsonic.lexing.tokens;

/**
 * Abstract class for all in-program lexeme representation.
 *
 * @author D3lph1
 */
abstract public class Token
{
    /**
     * Position of token beginning in incoming char sequence.
     */
    private int position;

    /**
     * @param position {@link #position}
     */
    public Token(int position)
    {
        this.position = position;
    }

    public int getPosition()
    {
        return position;
    }
}
