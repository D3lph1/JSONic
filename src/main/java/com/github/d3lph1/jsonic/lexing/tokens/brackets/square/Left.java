package com.github.d3lph1.jsonic.lexing.tokens.brackets.square;

import com.github.d3lph1.jsonic.lexing.tokens.Token;

/**
 * Represents a "[" lexeme.
 *
 * @author D3lph1
 */
public class Left extends Token
{
    /**
     * Length of "[".
     */
    public static final int LEXEME_LENGTH = 1;

    public Left(int position)
    {
        super(position);
    }
}
